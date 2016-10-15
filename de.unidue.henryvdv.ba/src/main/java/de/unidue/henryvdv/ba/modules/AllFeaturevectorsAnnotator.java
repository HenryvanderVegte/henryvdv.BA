package de.unidue.henryvdv.ba.modules;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.constituent.NP;
import de.unidue.henryvdv.ba.type.Anaphora;
import de.unidue.henryvdv.ba.util.FeatureVectorUtils;

public class AllFeaturevectorsAnnotator extends JCasAnnotator_ImplBase{
	
	private JCas aJCas;
	private File exportFile;
	private String exportFilePath;
	private List<String> posFeatureVectors;
	private List<String> negFeatureVectors;
	private Collection<Anaphora> anaphoras;
	private Collection<NP> nps;
	private int documentCounter;
	
	private FeatureVectorUtils featureVectorUtil;
	

    public static final String PARAM_EXPORT_DIRECTORY= "ExportFileDirectory";
    @ConfigurationParameter(name = PARAM_EXPORT_DIRECTORY, mandatory = false, defaultValue = "src/main/resources/exportVectors")
    private String ExportFileDirectory;
    
    public static final String PARAM_EXPORT= "ExportFileName";
    @ConfigurationParameter(name = PARAM_EXPORT, mandatory = false, defaultValue = "allVectorsNoFilterOut.dat")
    private String exportFileName;
	
	@Override
	public void initialize(UimaContext context) throws ResourceInitializationException{
		super.initialize(context);
		exportFilePath = ExportFileDirectory + "/" + exportFileName;
		exportFile = new File(exportFilePath);
		posFeatureVectors = new ArrayList<String>();
		negFeatureVectors = new ArrayList<String>();
		featureVectorUtil = new FeatureVectorUtils();
		
		documentCounter = 0;
		
		if(exportFile.isFile()){
			exportFile.delete();
		}		
		
		try {
			exportFile.getParentFile().mkdirs();
			exportFile.createNewFile();
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
		documentCounter++;
	}
	
	@Override
	public void collectionProcessComplete() throws AnalysisEngineProcessException{
		super.collectionProcessComplete();
		writeFeaturevectorsToFile();
	}
	
	private void generateFeatureVectors(){
		for(Anaphora anaphora : anaphoras){
			String currentFeatureVector = documentCounter + ";" + featureVectorUtil.createFeatureVector(anaphora);		
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
            writer = new BufferedWriter(new FileWriter(exportFile));
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
