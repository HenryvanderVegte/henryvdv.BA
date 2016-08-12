package de.unidue.henryvdv.ba.modules;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.constituent.NP;
import de.unidue.henryvdv.ba.type.Anaphora;
import de.unidue.henryvdv.ba.type.Antecedent;

public class SVMTrainingInstanceCreator 
extends JCasAnnotator_ImplBase{
	
	private JCas aJCas;
	private File trainFile;
	private String trainFilePath;
	private List<String> posFeatureVectors;
	private List<String> negFeatureVectors;
	private Collection<Anaphora> anaphoras;
	private Collection<NP> nps;
	
	private int currentFeatureCount;
	private String currentFeatureVector;
	private Anaphora currentAnaphora;
	

    public static final String PARAM_TRAINFILE_DIRECTORY= "TrainFileDirectory";
    @ConfigurationParameter(name = PARAM_TRAINFILE_DIRECTORY, mandatory = true, defaultValue = "src/main/resources/svm/dat")
    private String trainFileDirectory;
	
	@Override
	public void initialize(UimaContext context) throws ResourceInitializationException{
		super.initialize(context);
		trainFilePath = trainFileDirectory + "/" +"train.dat";
		trainFile = new File(trainFilePath);
		posFeatureVectors = new ArrayList<String>();
		negFeatureVectors = new ArrayList<String>();
		
		if(trainFile.isFile()){
			trainFile.delete();
		}		
		
		try {
			trainFile.getParentFile().mkdirs();
			trainFile.createNewFile();
		} catch (IOException e) {
			System.out.println("Error while creating the file.");
			e.printStackTrace();
		}
		
	}
    
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		this.aJCas = aJCas;
		anaphoras = JCasUtil.select(aJCas, Anaphora.class);
		nps = JCasUtil.select(aJCas, NP.class);
		generateFeatureVectors();
	}
	
	@Override
	public void collectionProcessComplete() throws AnalysisEngineProcessException{
		super.collectionProcessComplete();
		writeFeaturevectorsToFile();
	}
	
	private void generateFeatureVectors(){

		for(Anaphora anaphora : anaphoras){
			currentFeatureCount = 1;
			currentFeatureVector = "";
			currentAnaphora = anaphora;
			if(anaphora.getHasCorrectAntecedent()){
				currentFeatureVector += "1";
			} else {
				currentFeatureVector += "-1";
			}
			
			addPA_SameSentence();
			addPA_IntraSentenceDiff();
			addPA_InPreviousSentence();
			addPA_InterSentenceDiff();
			addPA_PrepositionalParallel();
			addPA_ParentCatMatch();
			addPA_ParentWordMatch();
			addPA_QuotationSituation();
			addPA_SingularMatch();
			addPA_PluralMatch();
			
			addA_AntecedentFrequency();
			addA_Subject();
			addA_Object();
			addA_Predicate();
			addA_Pronominal();
			addA_HeadWordEmphasis();
			addA_Conjunction();
			addA_PrenominalModifier();
			addA_Org();
			addA_Person();
			
			if(anaphora.getHasCorrectAntecedent()){
				posFeatureVectors.add(currentFeatureVector);
			} else {
				negFeatureVectors.add(currentFeatureVector);
			}
			
		}
	}
	
	
	private void writeFeaturevectorsToFile(){
		BufferedWriter writer = null;
		try {
            writer = new BufferedWriter(new FileWriter(trainFile));
		    writer.write("# svm train \"WikiCoref\" (training examples: " + posFeatureVectors.size() + 
		    				" positive / " + negFeatureVectors.size() + " negative)");
		    
		    
		    for(int i = 0; i < posFeatureVectors.size(); i++){
		    	writer.newLine();
		    	writer.write(posFeatureVectors.get(i));
		    }

		    for(int i = 0; i < negFeatureVectors.size(); i++){
		    	writer.newLine();
		    	writer.write(negFeatureVectors.get(i));
		    }
		    
		    
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
	
	private void addPA_SameSentence(){
		addBinarizedFeature(currentAnaphora.getPronounAntecedentFeatures().getP_A_InSameSentence());
	}	
	
	private void addPA_IntraSentenceDiff(){
		addFloatFeature(currentAnaphora.getPronounAntecedentFeatures().getP_A_IntraSentenceDiff());
	}
	
	private void addPA_InPreviousSentence(){
		addBinarizedFeature(currentAnaphora.getPronounAntecedentFeatures().getP_A_InPreviousSentence());
	}
	
	private void addPA_InterSentenceDiff(){
		addFloatFeature(currentAnaphora.getPronounAntecedentFeatures().getP_A_InterSentenceDiff());
	}
	
	private void addPA_PrepositionalParallel(){
		addBinarizedFeature(currentAnaphora.getPronounAntecedentFeatures().getP_A_PrepositionalParallel());
	}
	
	private void addPA_ParentCatMatch(){
		addBinarizedFeature(currentAnaphora.getPronounAntecedentFeatures().getP_A_ParentCatMatch());
	}
	
	private void addPA_ParentWordMatch(){
		addBinarizedFeature(currentAnaphora.getPronounAntecedentFeatures().getP_A_ParentWordMatch());
	}
	
	private void addPA_QuotationSituation(){
		addBinarizedFeature(currentAnaphora.getPronounAntecedentFeatures().getP_A_QuotationSituation());
	}
	
	private void addPA_SingularMatch(){
		addBinarizedFeature(currentAnaphora.getPronounAntecedentFeatures().getP_A_SingularMatch());
	}
	
	private void addPA_PluralMatch(){
		addBinarizedFeature(currentAnaphora.getPronounAntecedentFeatures().getP_A_PluralMatch());
	}
	
	private void addA_AntecedentFrequency(){
		addFloatFeature(currentAnaphora.getAntecedentFeatures().getA_AntecedentFrequency());
	}
	
	private void addA_Subject(){
		addBinarizedFeature(currentAnaphora.getAntecedentFeatures().getA_Subject());
	}	
	
	private void addA_Object(){
		addBinarizedFeature(currentAnaphora.getAntecedentFeatures().getA_Object());
	}
	
	private void addA_Predicate(){
		addBinarizedFeature(currentAnaphora.getAntecedentFeatures().getA_Predicate());
	}
	
	private void addA_Pronominal(){
		addBinarizedFeature(currentAnaphora.getAntecedentFeatures().getA_Pronominal());
	}
	
	private void addA_HeadWordEmphasis(){
		addBinarizedFeature(currentAnaphora.getAntecedentFeatures().getA_HeadWordEmphasis());
	}
	
	private void addA_Conjunction(){
		addBinarizedFeature(currentAnaphora.getAntecedentFeatures().getA_Conjunction());
	}
	
	private void addA_PrenominalModifier(){
		addBinarizedFeature(currentAnaphora.getAntecedentFeatures().getA_PrenominalModifier());
	}
	
	private void addA_Org(){
		addBinarizedFeature(currentAnaphora.getAntecedentFeatures().getA_Org());
	}
	
	private void addA_Person(){
		addBinarizedFeature(currentAnaphora.getAntecedentFeatures().getA_Person());
	}
	
	private void addBinarizedFeature(boolean value){
		currentFeatureVector += " ";
		if(value){
			currentFeatureVector += currentFeatureCount + ":1";
		} else {
			currentFeatureVector += currentFeatureCount + ":0";
		}
		currentFeatureCount++;
	}
	
	private void addFloatFeature(float value){
		currentFeatureVector += " ";  
		currentFeatureVector += currentFeatureCount + ":" + value;
		currentFeatureCount++;
	}
	
}
