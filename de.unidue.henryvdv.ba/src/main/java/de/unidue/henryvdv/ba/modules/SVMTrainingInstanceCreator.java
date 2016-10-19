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
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.constituent.NP;
import de.unidue.henryvdv.ba.type.Anaphora;
import de.unidue.henryvdv.ba.util.FeatureVectorUtils;

/**
 * Adds all created feature vectors to the svm train file 
 * @author Henry
 *
 */
@TypeCapability(
        inputs = {"de.unidue.henryvdv.ba.type.Anaphora",
                "de.unidue.henryvdv.ba.util.FeatureUtils_Antecedent",
                "de.unidue.henryvdv.ba.util.FeatureUtils_Gender",
                "de.unidue.henryvdv.ba.util.FeatureUtils_Pronoun",
                "de.unidue.henryvdv.ba.util.FeatureUtils_PronounAntecedent"
        },
        outputs = {})
public class SVMTrainingInstanceCreator 
extends JCasAnnotator_ImplBase{
	
	private File trainFile;
	private String trainFilePath;
	private List<String> posFeatureVectors;
	private List<String> negFeatureVectors;
	private Collection<Anaphora> anaphoras;
	
	private FeatureVectorUtils featureVectorUtil;
	
	/**
	 * Training file direction
	 */
    public static final String PARAM_TRAINFILE_DIRECTORY= "TrainFileDirectory";
    @ConfigurationParameter(name = PARAM_TRAINFILE_DIRECTORY, mandatory = true, defaultValue = "src/main/resources/svm/dat")
    private String trainFileDirectory;
    /**
     * Training file name
     */
    public static final String PARAM_TRAINFILE= "TrainFileName";
    @ConfigurationParameter(name = PARAM_TRAINFILE, mandatory = false, defaultValue = "train.dat")
    private String trainFileName;
    
	@Override
	public void initialize(UimaContext context) throws ResourceInitializationException{
		super.initialize(context);
		trainFilePath = trainFileDirectory + "/" + trainFileName;
		trainFile = new File(trainFilePath);
		posFeatureVectors = new ArrayList<String>();
		negFeatureVectors = new ArrayList<String>();
		featureVectorUtil = new FeatureVectorUtils();
		//So that no old vectors are in the file
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
		anaphoras = JCasUtil.select(aJCas, Anaphora.class);
		generateFeatureVectors();
	}
	
	@Override
	public void collectionProcessComplete() throws AnalysisEngineProcessException{
		super.collectionProcessComplete();
		writeFeaturevectorsToFile();
	}
	/**
	 * Creates for each anaphora (and negative training instance) the feature vector
	 */
	private void generateFeatureVectors(){
		for(Anaphora anaphora : anaphoras){
			String currentFeatureVector = featureVectorUtil.createFeatureVector(anaphora);		
			if(anaphora.getHasCorrectAntecedent()){
				posFeatureVectors.add(currentFeatureVector);
			} else {
				negFeatureVectors.add(currentFeatureVector);
			}
			
		}
	}
	
	/**
	 * Writes all feature vectors to the train file
	 */
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
	
	
	
}
