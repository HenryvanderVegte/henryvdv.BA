package de.unidue.henryvdv.ba.modules;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.dkpro.tc.core.Constants;

import de.tudarmstadt.ukp.dkpro.core.api.ner.type.NamedEntity;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.constituent.NP;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.dependency.Dependency;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordParser.DependenciesMode;
import de.unidue.henryvdv.ba.type.Anaphora;
import de.unidue.henryvdv.ba.type.Antecedent;
import de.unidue.henryvdv.ba.type.AntecedentFeatures;
import de.unidue.henryvdv.ba.type.DetectedNP;
import de.unidue.henryvdv.ba.type.GoldNP;
import de.unidue.henryvdv.ba.type.PronounAntecedentFeatures;
import de.unidue.henryvdv.ba.type.PronounFeatures;
import de.unidue.henryvdv.ba.type.Quotation;
import de.unidue.henryvdv.ba.util.AnaphoraEvaluator;
import de.unidue.henryvdv.ba.util.AnnotationUtils;
import de.unidue.henryvdv.ba.util.AntecedentFeatureUtils;
import de.unidue.henryvdv.ba.util.FeatureVectorUtils;
import de.unidue.henryvdv.ba.util.PronounAntecedentFeatureUtils;
import de.unidue.henryvdv.ba.util.PronounFeatureUtils;


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
	
	private static final String PREDICTION_NAME = "output.txt";

	private static final String PREDICTION_DIRECTORY = "src/main/resources/svm/dat";
	
	
	private static final int MAX_SENTENCE_DIST = 2;
	
	
	private Collection<Anaphora> anaphoras;
	private Collection<Token> tokens;
	private Collection<Sentence> sentences;
	private Collection<Dependency> dependencies;
	private Collection<NamedEntity> namedEntities;
	private Collection<Quotation> quotes;
	private AntecedentFeatureUtils aFUtil;
	private PronounAntecedentFeatureUtils paFUtil;
	private PronounFeatureUtils pFUtil;
	private FeatureVectorUtils featureVectorUtil;
	
	private File modelFile;
	private File testFile;
	private File outputFile;
	private String featureVector;
	private List<String> testCommand;
	private Collection<NP> allNPs;
	private List<GoldNP> goldAntecedents;
	private List<DetectedNP> detectedAntecedents;
	private List<NP> fixedNPs;
	
	private List<String> outputFileText;
	private AnaphoraEvaluator eval;
	
	private JCas aJCas;
	
	public void initialize(UimaContext context) throws ResourceInitializationException{
		super.initialize(context);
		eval = new AnaphoraEvaluator();

		featureVectorUtil = new FeatureVectorUtils();
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
		sentences = JCasUtil.select(aJCas, Sentence.class);
		dependencies = JCasUtil.select(aJCas, Dependency.class);
		tokens = JCasUtil.select(aJCas, Token.class);
		namedEntities = JCasUtil.select(aJCas, NamedEntity.class);
		quotes = JCasUtil.select(aJCas, Quotation.class);	
		
		aFUtil = new AntecedentFeatureUtils(aJCas);
		paFUtil = new PronounAntecedentFeatureUtils(aJCas);
		pFUtil = new PronounFeatureUtils();	
		
		goldAntecedents = new ArrayList<GoldNP>();
		detectedAntecedents = new ArrayList<DetectedNP>();
		allNPs = JCasUtil.select(aJCas, NP.class);
	
		setFixedNPs();
		setGoldNPs();
		setDetectedNPs();
		
		eval.evaluate(detectedAntecedents, goldAntecedents);
	}
	
	@Override
	public void collectionProcessComplete(){
		eval.printResults();
	}
	
	
	private void setGoldNPs(){
		for(Anaphora anaphora : anaphoras){
			if(!anaphora.getHasCorrectAntecedent())
				continue;
			GoldNP np = new GoldNP(aJCas, anaphora.getAntecedent().getBegin(), anaphora.getAntecedent().getEnd());
			goldAntecedents.add(np);
		}
	}
	
	private void setDetectedNPs(){
		for(Anaphora anaphora : anaphoras){
			if(!anaphora.getHasCorrectAntecedent())
				continue;
			int anteNPnumber = 0;
			for(int i = 0; i < fixedNPs.size(); i++){
				if(fixedNPs.get(i).getBegin() >= anaphora.getBegin()){
					anteNPnumber = i - 1;
					if(anteNPnumber < 0 )
						System.out.println("!!!!!!!!!!!!!!");
					break;
				}
			}		
			boolean foundAntecedent = false;
			float threshold = 1.0f;
			int anaphoraSentenceNr = AnnotationUtils.getSentenceNr(anaphora.getBegin(), sentences);
			while(!foundAntecedent){
				for(int i = anteNPnumber; i > 0; i--){
					int anteSentenceNr = AnnotationUtils.getSentenceNr(fixedNPs.get(i).getBegin(), sentences);
					if((anaphoraSentenceNr - anteSentenceNr) > MAX_SENTENCE_DIST){
						break;
					}

					
					float outputValue = getOutputValue(anaphora, fixedNPs.get(i));
					if(outputValue > threshold){
						foundAntecedent = true;
						DetectedNP det = new DetectedNP(aJCas, fixedNPs.get(i).getBegin(),
								fixedNPs.get(i).getEnd());
						detectedAntecedents.add(det);
						break;
					}
				}
				if(!foundAntecedent){
					threshold -= 0.01f;
				}
			}
		}
	}
	
	private float getOutputValue(Anaphora a, NP n){
		Anaphora possibleA = new Anaphora(aJCas, a.getBegin(), a.getEnd());
		Antecedent ant = new Antecedent(aJCas, n.getBegin(), n.getEnd());
		possibleA.setAntecedent(ant);
		featureVector = createFeatureVector(possibleA);
		writeFeatureVectorToFile();
		try{
			classify();
		} catch(IOException e){
			System.out.println("Error while classifying");
			e.printStackTrace();
		}
		
		try {
			outputFileText = FileUtils.readLines(outputFile);
		} catch (IOException e) {
			System.out.println("Error while reading the output file");
			e.printStackTrace();
		}
		float value = Float.parseFloat(outputFileText.get(0));
		return value;
	}
	
	private String createFeatureVector(Anaphora a){		
		a.setAntecedentFeatures(new AntecedentFeatures(aJCas));
		a.setPronounAntecedentFeatures(new PronounAntecedentFeatures(aJCas));
		a.setPronounFeatures(new PronounFeatures(aJCas));
		
		paFUtil.annotateFeatures(a);
		aFUtil.annotateFeatures(a);
		pFUtil.annotateFeatures(a);
		return featureVectorUtil.createFeatureVector(a);
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
	
	
	private void writeFeatureVectorToFile(){
		BufferedWriter writer = null;
		try {
            writer = new BufferedWriter(new FileWriter(testFile));
		    writer.write(featureVector);
		    writer.newLine();
	    
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
			System.out.println("Error occured while creating the output file.");
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

	private void runCommand(List<String> command) throws Exception {
		ProcessBuilder b = new ProcessBuilder();

		
		Process process = b.command(command).start();
		process.waitFor();
		/*
		Process process = new ProcessBuilder().inheritIO().command(command).start();
		process.waitFor();
		*/
	}


}
