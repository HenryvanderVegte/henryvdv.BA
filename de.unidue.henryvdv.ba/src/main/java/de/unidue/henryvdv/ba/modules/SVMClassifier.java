package de.unidue.henryvdv.ba.modules;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.dkpro.tc.core.Constants;

import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordParser.DependenciesMode;


public class SVMClassifier extends JCasAnnotator_ImplBase implements Constants {

	public static enum ClassificationMode {
		
		/**
         * Uses the method of Bergsma
         * -> searching backwards for the last 2 (/3) sentences
         *    until an antecedent is found - if not, lower the threshold and repeat
         */
		BERGSMA,
		/**
         * First implementation of an own method
         * -> checking all possible antecedents for the last n sentences - maybe choose the 
         *    first or the best
         */
		LAST_N_SENTENCES
		
	}
	
    public static final String PARAM_CLASS_MODE = "classificationMode";
    @ConfigurationParameter(name = PARAM_CLASS_MODE, mandatory = false, defaultValue = "BERGSMA")
    protected ClassificationMode mode;
	
	private static final String BINARIES_BASE_LOCATION = "src/main/resources/svm/bin";

	// where the trained model is stored
	private static final String MODEL_NAME = "svm_light.model";

	private static final String MODEL_DIRECTORY = "src/main/resources/svm/dat";
	
	private static final String TEST_NAME = "test.dat";

	private static final String TEST_DIRECTORY = "src/main/resources/svm/dat";
	
	private static final String PREDICTION_NAME = "output.dat";

	private static final String PREDICTION_DIRECTORY = "src/main/resources/svm/dat";
	
	private File modelFile;
	private File testFile;
	private File outputFile;
	
	private JCas aJCas;
	
	public void initialize(UimaContext context) throws ResourceInitializationException{
		super.initialize(context);
		String modelFilePath = MODEL_DIRECTORY + "/" + MODEL_NAME;
		modelFile = new File(modelFilePath);
		
		String testFilePath = TEST_DIRECTORY + "/" + TEST_NAME;
		testFile = new File(testFilePath);
		
		String outputFilePath = PREDICTION_DIRECTORY + "/" + PREDICTION_NAME;
		outputFile = new File(outputFilePath);
		
		if(!modelFile.isFile() || !testFile.isFile()){
			System.out.println("Cant read file.");
			throw new ResourceInitializationException();
		}	
		
		if(outputFile.isFile()){
			outputFile.delete();
		}			
		try {
			outputFile.getParentFile().mkdirs();
			outputFile.createNewFile();
		} catch (IOException e) {
			System.out.println("Error while creating output file.");
			throw new ResourceInitializationException();
		}
		if(testFile.isFile()){
			testFile.delete();
		}			
		try {
			testFile.getParentFile().mkdirs();
			testFile.createNewFile();
		} catch (IOException e) {
			System.out.println("Error while creating test file.");
			throw new ResourceInitializationException();
		}
		
		
	}
	
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		this.aJCas = aJCas;
		
	}
	
	

	public void classify() throws IOException  {		
		List<String> command = buildTrainCommand(testFile, modelFile, outputFile);
		System.out.println(command);
		
		try {
			runCommand(command);
		} catch (Exception e) {
			System.out.println("Error occured while creating the modelfile.");
			e.printStackTrace();
		}
	}
	
	private List<String> buildTrainCommand(File testFile, File modelFile, File outputFile) {
		List<String> result = new ArrayList<String>();
		result.add(BINARIES_BASE_LOCATION + "/svm_classify.exe");		
		// test file
		result.add(testFile.getAbsolutePath());

		// model file
		result.add(modelFile.getAbsolutePath());
		
		// output file
		result.add(outputFile.getAbsolutePath());

		return result;
	}

	private static void runCommand(List<String> command) throws Exception {
		Process process = new ProcessBuilder().inheritIO().command(command).start();
		process.waitFor();
	}


}
