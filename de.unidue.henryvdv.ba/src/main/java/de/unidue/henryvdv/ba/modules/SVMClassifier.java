package de.unidue.henryvdv.ba.modules;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.dkpro.tc.core.Constants;

import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.constituent.NP;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordParser.DependenciesMode;
import de.unidue.henryvdv.ba.type.Anaphora;
import de.unidue.henryvdv.ba.type.Antecedent;
import de.unidue.henryvdv.ba.type.DetectedNP;
import de.unidue.henryvdv.ba.type.GoldNP;


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

	private static final String MODEL_NAME = "svm_light.model";

	private static final String MODEL_DIRECTORY = "src/main/resources/svm/dat";
	
	private static final String TEST_NAME = "test.dat";

	private static final String TEST_DIRECTORY = "src/main/resources/svm/dat";
	
	private static final String PREDICTION_NAME = "output.dat";

	private static final String PREDICTION_DIRECTORY = "src/main/resources/svm/dat";
	
	private File modelFile;
	private File testFile;
	private File outputFile;
	private String featureVector;
	private List<String> testCommand;
	private Collection<Anaphora> anaphoras;
	private Collection<NP> allNPs;
	private List<GoldNP> goldAntecedent;
	private List<DetectedNP> detectedAntecedent;
	private List<NP> fixedNPs;
	
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
		
		testCommand = buildTestCommand(testFile, modelFile, outputFile);
	}
	
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		this.aJCas = aJCas;
		anaphoras = JCasUtil.select(aJCas, Anaphora.class);
		goldAntecedent = new ArrayList<GoldNP>();
		detectedAntecedent = new ArrayList<DetectedNP>();
		allNPs = JCasUtil.select(aJCas, NP.class);
		setFixedNPs();
		setGoldNPs();
	}
	
	private void setGoldNPs(){
		for(Anaphora anaphora : anaphoras){
			if(!anaphora.getHasCorrectAntecedent())
				continue;
			GoldNP np = new GoldNP(aJCas, anaphora.getAntecedent().getBegin(), anaphora.getAntecedent().getEnd());
			goldAntecedent.add(np);
		}
	}
	
	private void setDetectedNPs(){
		for(Anaphora anaphora : anaphoras){
			if(!anaphora.getHasCorrectAntecedent())
				continue;
			for(int i = 1; i < fixedNPs.size(); i++){
				if(fixedNPs.get(i).getBegin() >= anaphora.getBegin()){
					checkPossibleAntecedent(anaphora, fixedNPs.get(i-1));
					break;
				}
			}	
		}
	}
	
	private void checkPossibleAntecedent(Anaphora a, NP n){
		Anaphora possibleA = new Anaphora(aJCas, a.getBegin(), a.getEnd());
		Antecedent ant = new Antecedent(aJCas, n.getBegin(), n.getEnd());
		possibleA.setAntecedent(ant);
		
		createFeatureVector();
	}
	
	/**
	 * The feature numbers must(!) match the feature numbers in
	 *  SVMLearn
	 * 
	 **/	
	private void createFeatureVector(){
		
	}
	
	
	
	/**
	 *  This method removes the chance of discoveringg noun phrases in noun phrases
	 * 
	 **/
	private void setFixedNPs(){	
		fixedNPs = new ArrayList<NP>();
		for(NP np1 : allNPs){
			boolean addIt = true;
			for(NP np2 : allNPs){
				if(np1 != np2){
					if(np1.getBegin() <=  np2.getBegin() && np1.getEnd() >= np2.getEnd()){
						addIt = false;
					}
				}
			}
			if(addIt){
				fixedNPs.add(np1);
			}
		}
	}
	
	
	private void writeFeaturevectorToFile(){
		BufferedWriter writer = null;
		try {
            writer = new BufferedWriter(new FileWriter(testFile));
		    writer.write("# svm test \"WikiCoref\"");	    
		    writer.newLine();
		    writer.write(featureVector);
		    
		    
		} catch (IOException e) {
		  System.out.println("Failed to write feature vectors to train file.");
		  e.printStackTrace();
		} finally {
		   try {
			   writer.close();
		   } catch (Exception e)
		   {
			   System.out.println("Failed to close writer.");
			   e.printStackTrace();
		   }
		}		
	}

	public void classify() throws IOException  {			
		try {
			runCommand(testCommand);
		} catch (Exception e) {
			System.out.println("Error occured while creating the modelfile.");
			e.printStackTrace();
		}
	}
	
	private List<String> buildTestCommand(File testFile, File modelFile, File outputFile) {
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
