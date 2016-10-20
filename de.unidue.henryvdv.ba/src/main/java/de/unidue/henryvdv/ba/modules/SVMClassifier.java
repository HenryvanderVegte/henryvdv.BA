package de.unidue.henryvdv.ba.modules;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.dkpro.tc.core.Constants;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.constituent.NP;
import de.unidue.henryvdv.ba.param.Parameters;
import de.unidue.henryvdv.ba.type.Anaphora;
import de.unidue.henryvdv.ba.type.Antecedent;
import de.unidue.henryvdv.ba.type.AntecedentFeatures;
import de.unidue.henryvdv.ba.type.GenderFeatures;
import de.unidue.henryvdv.ba.type.MyCoreferenceChain;
import de.unidue.henryvdv.ba.type.MyNP;
import de.unidue.henryvdv.ba.type.PronounAntecedentFeatures;
import de.unidue.henryvdv.ba.type.PronounFeatures;
import de.unidue.henryvdv.ba.util.AnaphoraEvaluator;
import de.unidue.henryvdv.ba.util.AnnotationUtils;
import de.unidue.henryvdv.ba.util.FeatureUtils_Antecedent;
import de.unidue.henryvdv.ba.util.FeatureUtils_Gender;
import de.unidue.henryvdv.ba.util.FeatureVectorUtils;
import de.unidue.henryvdv.ba.util.FeatureUtils_PronounAntecedent;
import de.unidue.henryvdv.ba.util.FeatureUtils_Pronoun;

/**
 * Assigns for each anaphora and each of its candidates the svm output value.
 * Passes these information to the AnaphoraEvaluator to get all required 
 * evaluations
 * @author Henry
 *
 */
@TypeCapability(
        inputs = {"de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token",
                 "de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence",
                 "de.tudarmstadt.ukp.dkpro.core.api.syntax.type.dependency.Dependency",
                 "de.tudarmstadt.ukp.dkpro.core.api.ner.type.NamedEntity",
                 "de.tudarmstadt.ukp.dkpro.core.api.syntax.type.constituent.Constituent",
                 "de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.POS",
                 "de.unidue.henryvdv.ba.type.Anaphora"
        },
        outputs = {})
public class SVMClassifier extends JCasAnnotator_ImplBase implements Constants {

	/**
	 * Location of the svm .exe files
	 */
	private static final String BINARIES_BASE_LOCATION = "src/main/resources/svm/bin";
	
	/**
	 * Location of model file + its name
	 */
	private static final String MODEL_NAME = "svm_light.model";
	private static final String MODEL_DIRECTORY = "src/main/resources/svm/dat";
	/**
	 * Location of test file + its name
	 */
	private static final String TEST_NAME = "test.dat";
	private static final String TEST_DIRECTORY = "src/main/resources/svm/dat";
	/**
	 * Location of the prediction file + its name
	 */
	private static final String PREDICTION_NAME = "output.txt";
	private static final String PREDICTION_DIRECTORY = "src/main/resources/svm/dat";
	
	/**
	 * Required annotations
	 */
	private Collection<Anaphora> anaphoras;
	private Collection<Sentence> sentences;
	private Collection<MyCoreferenceChain> corefChains;
	/**
	 * Required utils
	 */
	private FeatureUtils_Antecedent aFUtil;
	private FeatureUtils_PronounAntecedent paFUtil;
	private FeatureUtils_Pronoun pFUtil;
	private FeatureUtils_Gender gFUtil;
	private FeatureVectorUtils featureVectorUtil;
	
	/**
	 * Gender corpus frequencies
	 */
	private Map<String, Integer[]> corpusFrequencies = new HashMap<String, Integer[]>();
	
	/**
	 * Used files
	 */
	private File modelFile;
	private File testFile;
	private File outputFile;
	
	private String featureVector;
	private List<String> testCommand;
	private Collection<NP> allNPs;
	private List<NP> fixedNPs;
	
	private List<String> outputFileText;
	private AnaphoraEvaluator eval;
	
	private JCas aJCas;
	
	public void initialize(UimaContext context) throws ResourceInitializationException{
		super.initialize(context);
		eval = new AnaphoraEvaluator();
		featureVectorUtil = new FeatureVectorUtils();
		prepareFiles();
		readCorpus();
	}
	
	/**
	 * Initializes all used files
	 * @throws ResourceInitializationException
	 */
	private void prepareFiles() throws ResourceInitializationException{
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

		corefChains = JCasUtil.select(aJCas, MyCoreferenceChain.class);

		eval.setSentences(sentences);
		
		aFUtil = new FeatureUtils_Antecedent(aJCas);
		paFUtil = new FeatureUtils_PronounAntecedent(aJCas);
		pFUtil = new FeatureUtils_Pronoun();
		gFUtil = new FeatureUtils_Gender(aJCas, corpusFrequencies);

		allNPs = JCasUtil.select(aJCas, NP.class);
		setFixedNPs(); 
		List<Anaphora> anaphorasList = new ArrayList<Anaphora>();
		for(Anaphora a : anaphoras){
			if(a.getHasCorrectAntecedent()){
				anaphorasList.add(a);
			}
		}
		Map<Anaphora, List<MyNP>> allAnaphors = getAllAnaphors(anaphorasList);
		
		//Evaluates all kind of measures:
		eval.evaluateBergsma_SameEntity(allAnaphors, corefChains, false);
		eval.evaluateBergsma_KeepThreshold(allAnaphors, false);
		eval.evaluateBergsma_LowerThreshold(allAnaphors, false);
		eval.evaluateBergsma_HighestValue(allAnaphors);
		eval.evaluateBergsma_Baseline(allAnaphors, false);
		eval.evaluate_last_n_sentences(allAnaphors, false);
		eval.evaluate_last_n_sentences_sameEntity(allAnaphors, corefChains, false);

	}
	
	@Override
	public void collectionProcessComplete(){
		eval.printResults();
	}
	
	/**
	 * Annotates all nounphrases that have been accepted by the svm classifier of 
	 * the last n sentences
	 * 
	 */
	private Map<Anaphora, List<MyNP>> getAllAnaphors(List<Anaphora> anaphorasList){
		Map<Anaphora, List<MyNP>> frequencies = new HashMap<Anaphora, List<MyNP>>();	
		for(Anaphora anaphora : anaphorasList){
			int anaphoraSentenceNr = AnnotationUtils.getSentenceNr(anaphora.getBegin(), sentences);
			int maxAntentenceNr = anaphoraSentenceNr - Parameters.MAX_SENTENCE_DIST;
			if(maxAntentenceNr < 0)
				maxAntentenceNr = 0;	
			List<MyNP> consideredNPs = new ArrayList<MyNP>();
			for(NP np : fixedNPs){
				int anteSentenceNr = AnnotationUtils.getSentenceNr(np.getBegin(), sentences);
				if(anteSentenceNr < maxAntentenceNr || np.getEnd() >= anaphora.getBegin())
					continue;
				float outputValue = getOutputValue(anaphora, np);
				MyNP myNP = new MyNP(aJCas, np.getBegin(), np.getEnd());
				myNP.setOutputValue(outputValue);
				myNP.setFeatureVector(featureVector);
				consideredNPs.add(myNP);			
			}
			frequencies.put(anaphora, consideredNPs);
		}
		return frequencies;
	}
	
	/**
	 * Returns the test result of an anaphora and its antecedent candidate
	 * @param anaphora Anaphora
	 * @param np Antecedent candidate
	 * @return
	 */
	private float getOutputValue(Anaphora anaphora, NP np){
		Anaphora possibleA = new Anaphora(aJCas, anaphora.getBegin(), anaphora.getEnd());
		Antecedent ant = new Antecedent(aJCas, np.getBegin(), np.getEnd());
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
	
	/**
	 * Annotates all feature values for a given Anaphora and then returns the feature vector
	 * @param anaphora
	 * @return
	 */
	private String createFeatureVector(Anaphora anaphora){		
		anaphora.setAntecedentFeatures(new AntecedentFeatures(aJCas));
		anaphora.setPronounAntecedentFeatures(new PronounAntecedentFeatures(aJCas));
		anaphora.setPronounFeatures(new PronounFeatures(aJCas));
		anaphora.setGenderFeatures(new GenderFeatures(aJCas));
		
		paFUtil.annotateFeatures(anaphora);
		aFUtil.annotateFeatures(anaphora);
		pFUtil.annotateFeatures(anaphora);
		gFUtil.annotateFeatures(anaphora);
		return featureVectorUtil.createFeatureVector(anaphora);
	}
	
	/**
	 * If Parameter removeCoveringNPs in Parameters is true, 
	 * all np covering other np will be removed
	 */
	private void setFixedNPs(){		
		fixedNPs = new ArrayList<NP>();
		if(Parameters.removeCoveringNPs){			
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
		} else {
			for(NP np1 : allNPs){
				fixedNPs.add(np1);
			}
        }
		//Gold antecedents are added as candidates
		for(Anaphora a : anaphoras){
			if(!a.getHasCorrectAntecedent())
				continue;
			int anteBegin = a.getAntecedent().getBegin();
			int anteEnd = a.getAntecedent().getEnd();
			boolean alreadyContained = false;
			for(NP np : fixedNPs){
				if(np.getBegin() == anteBegin && np.getEnd() == anteEnd){
					alreadyContained = true;
				}
			}
			if(!alreadyContained){
				NP np = new NP(aJCas, anteBegin, anteEnd);
				fixedNPs.add(np);
			}
		}
	}
	
	/**
	 * Writes the observed feature vector to train file 
	 * (All feature vectors are observed one by one)
	 */
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
	
	/**
	 * Runs the test and writes the output in the svm/dat/output.txt
	 * @throws IOException
	 */
	public void classify() throws IOException  {			
		try {
			runCommand(testCommand);
		} catch (Exception e) {
			System.out.println("Error occured while creating the output file.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates the command for executing the test
	 * @param testFile
	 * @param modelFile
	 * @param outputFile
	 * @return
	 */
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

	/**
	 * Runs the command for executing the test
	 * @param command
	 * @throws Exception
	 */
	private void runCommand(List<String> command) throws Exception {
		ProcessBuilder b = new ProcessBuilder();

		Process process = b.command(command).start();
		process.waitFor();

	}

	/**
	 * Reads all documents for the gender corpus frequencies
	 */
	private void readCorpus(){
		File folder = new File(Parameters.genderCorpusDirectory);
	    File[] files = folder.listFiles();
	    for(File f : files){
	    	List<String> inputLines;
	    	try {
				inputLines = FileUtils.readLines(f);	
				readCorpusLines(inputLines);
			} catch (IOException e) {
				e.printStackTrace();
			}	    	
	    }
	}
	

	/**
	 * reads all lines in a document and puts the frequencies in the corpusFrequencies-Map
	 * @param inputLines the corpus lines
	 */
	private void readCorpusLines(List<String> inputLines){
		for(String s : inputLines){
			String word = "";
			String freq = "";
			for(int i = 0; i < s.length(); i++){
				if(s.charAt(i) == '\t'){
					word = s.substring(0, i);
					freq = s.substring(i + 1, s.length());
					break;
				}
			}
			Integer[] frequencies = new Integer[4];
			int startAt = 0;
			int arrayNr = 0;
			freq = freq + " ";
			for(int i = 0; i < freq.length(); i++){
				if(!Character.isDigit(freq.charAt(i))){
					String stringValue = freq.substring(startAt, i);
					frequencies[arrayNr] = Integer.parseInt(stringValue);
					startAt = i + 1;
					arrayNr++;
				}
			}
			corpusFrequencies.put(word, frequencies);
		}
	}
	

}
