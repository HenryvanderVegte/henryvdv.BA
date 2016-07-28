package de.unidue.henryvdv.ba.modules;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

public class SVMTrainingInstanceCreator 
extends JCasAnnotator_ImplBase{
	
	private JCas aJCas;
	private File trainFile;
	private String trainFilePath;
	private String[] posFeatureVectors;
	private String[] negFeatureVectors;

    public static final String PARAM_TRAINFILE_DIRECTORY= "TrainFileDirectory";
    @ConfigurationParameter(name = PARAM_TRAINFILE_DIRECTORY, mandatory = true, defaultValue = "src/main/resources/svm/train")
    private String TrainFileDirectory;
	
	@Override
	public void initialize(UimaContext context) throws ResourceInitializationException{
		super.initialize(context);
		trainFilePath = TrainFileDirectory + "/" +"train.dat";
		trainFile = new File(trainFilePath);
		
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
		generatePosFeatureVectors();
		
	}
	
	@Override
	public void collectionProcessComplete() throws AnalysisEngineProcessException{
		super.collectionProcessComplete();
		writeFeaturevectorsToFile();
	}
	
	private void generatePosFeatureVectors(){
		
	}
	
	
	
	
	private void writeFeaturevectorsToFile(){
		BufferedWriter writer = null;
		try {
            writer = new BufferedWriter(new FileWriter(trainFile));
		    writer.write("Something");
		    writer.write("Something");
		    writer.write("Something");
		    
		    
		    
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
