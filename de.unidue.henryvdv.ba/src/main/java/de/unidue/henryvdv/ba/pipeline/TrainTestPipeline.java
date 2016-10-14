package de.unidue.henryvdv.ba.pipeline;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.uima.UIMAException;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.resource.ResourceInitializationException;

import de.tudarmstadt.ukp.dkpro.core.corenlp.CoreNlpNamedEntityRecognizer;
import de.tudarmstadt.ukp.dkpro.core.corenlp.CoreNlpParser;
import de.tudarmstadt.ukp.dkpro.core.corenlp.CoreNlpPosTagger;
import de.tudarmstadt.ukp.dkpro.core.corenlp.CoreNlpLemmatizer;
import de.unidue.henryvdv.ba.modules.AllFeaturevectorsAnnotator;
import de.unidue.henryvdv.ba.modules.AnaphoraAnnotator;
import de.unidue.henryvdv.ba.modules.Baseline_Evaluator;
import de.unidue.henryvdv.ba.modules.FeatureAnnotator;
import de.unidue.henryvdv.ba.modules.InformationModule;
import de.unidue.henryvdv.ba.modules.NegativeTrainingInstanceAnnotator;
import de.unidue.henryvdv.ba.modules.SVMClassifier;
import de.unidue.henryvdv.ba.modules.SVMTrainingInstanceCreator;
import de.unidue.henryvdv.ba.reader.WikiCoref_Reader;
import de.unidue.henryvdv.ba.util.SVMLearn;
import de.unidue.henryvdv.ba.util.SavedVectorWriter;

import java.util.concurrent.ThreadLocalRandom;

/**
 * The main file in order to perform the complete evaluation.
 * @author Henry
 *
 */
public class TrainTestPipeline {

	public static void main(String[] args)
			  throws Exception {	
		Integer[] allDocs = new Integer[30];
		for(int i = 0; i < allDocs.length; i++){
			allDocs[i] = i;
		}
	//	trainPipeline(allDocs);
		
		crossvalidation(10,30, false);
	}
	
	/**
	 * Performs a crossvalidation. Results will be written into the console
	 * @param folds the amount of folds used in crossvalidation
	 * @param docSize on how many documents the crossvalidation will be performed
	 * @param randomFolds if true, the documents will be randomly split on all folds
	 * @throws Exception
	 */
	private static void crossvalidation(int folds, int docSize, boolean randomFolds) throws Exception{
		List<Integer> allDocs = new ArrayList<Integer>();
		for(int i = 0; i < docSize; i++){
			allDocs.add(i);
		}	
		
		List<List<Integer>> foldsList = new ArrayList<List<Integer>>();
		for(int i = 0; i < folds; i++){
			foldsList.add(new ArrayList<Integer>());
		}
		
		
		int c = 0;
		while(allDocs.size() != 0){
			int removeAt = 0;
			if(randomFolds){
				removeAt = ThreadLocalRandom.current().nextInt(0, allDocs.size());
			}
			int docNr = allDocs.get(removeAt);
			allDocs.remove(removeAt);
			
			foldsList.get(c % (folds)).add(docNr);
			c++;
		}
		
		for(int i = 0; i < folds; i++){
			List<Integer> trainOnList = new ArrayList<Integer>();
			for(int j = 0; j < folds; j++){
				if(j != i){
					trainOnList.addAll(foldsList.get(j));
				}
			}
			Integer[] trainOnArray = trainOnList.toArray(new Integer[trainOnList.size()]);
			Integer[] testOnArray = foldsList.get(i).toArray(new Integer[foldsList.get(i).size()]);			
			
			System.out.println("Fold nr " + i);
			System.out.print("Training on  [");
			for(Integer t : trainOnArray){
				System.out.print("  " + t );
			}
			
			System.out.println("  ]");
			
			System.out.print("Testing  on  [");
			for(Integer t : testOnArray){
				System.out.print("  " + t );
			}
			
			System.out.println("  ]");
			
			System.out.println("Train: ");
			//trainPipeline(trainOnArray);
			SavedVectorWriter s = new SavedVectorWriter(trainOnArray);
			s.write();
			
			SVMLearn svmLearn = new SVMLearn();
			svmLearn.learn();
			
			System.out.println("Test: ");
			testPipeline(testOnArray);
			
		}
	}
	
	
	
	
	
	/**
	 * The baseline classifier (see Baseline_Evaluator.class for further information)
	 * @param _usedDocs the documents used, each cell in the array contains the number of a document
	 * @throws Exception
	 */
	private static void baseline(Integer[] usedDocs) throws Exception{
		SimplePipeline.runPipeline(    		
		        CollectionReaderFactory.createReader(
		                      WikiCoref_Reader.class,
		                      WikiCoref_Reader.PARAM_INPUT_DIRECTORY, "src/test/resources/WikiCoref_Annotation",
		                      WikiCoref_Reader.PARAM_USED_DOCUMENT_NUMBERS, usedDocs),			   
		        AnalysisEngineFactory.createEngineDescription(CoreNlpPosTagger.class),
		        AnalysisEngineFactory.createEngineDescription(CoreNlpLemmatizer.class),
		        AnalysisEngineFactory.createEngineDescription(CoreNlpNamedEntityRecognizer.class),
		        AnalysisEngineFactory.createEngineDescription(CoreNlpParser.class,
																CoreNlpParser.PARAM_ORIGINAL_DEPENDENCIES,
																false),
		        AnalysisEngineFactory.createEngineDescription(AnaphoraAnnotator.class),
		        AnalysisEngineFactory.createEngineDescription(NegativeTrainingInstanceAnnotator.class),
		        AnalysisEngineFactory.createEngineDescription(Baseline_Evaluator.class),
		        AnalysisEngineFactory.createEngineDescription(InformationModule.class)	        
		        );
	}
	
	/**
	 * Creates the modelfile with all features used - training performed on all usedDocs - documents
	 * @param _usedDocs the documents used, each cell in the array contains the number of a document
	 * @throws Exception
	 */
	private static void trainPipeline(Integer[] usedDocs) throws Exception{
		SimplePipeline.runPipeline(	                       		
	        CollectionReaderFactory.createReader(
	                      WikiCoref_Reader.class,
	                      WikiCoref_Reader.PARAM_INPUT_DIRECTORY, "src/test/resources/WikiCoref_Annotation",
	                      WikiCoref_Reader.PARAM_USED_DOCUMENT_NUMBERS, usedDocs),			   
	        AnalysisEngineFactory.createEngineDescription(CoreNlpPosTagger.class),
	        AnalysisEngineFactory.createEngineDescription(CoreNlpLemmatizer.class),
	        AnalysisEngineFactory.createEngineDescription(CoreNlpNamedEntityRecognizer.class),
	        AnalysisEngineFactory.createEngineDescription(CoreNlpParser.class,
															CoreNlpParser.PARAM_ORIGINAL_DEPENDENCIES,
															false),
	        AnalysisEngineFactory.createEngineDescription(AnaphoraAnnotator.class),
	        AnalysisEngineFactory.createEngineDescription(NegativeTrainingInstanceAnnotator.class),
	        AnalysisEngineFactory.createEngineDescription(FeatureAnnotator.class),
	        AnalysisEngineFactory.createEngineDescription(AllFeaturevectorsAnnotator.class)
	       // AnalysisEngineFactory.createEngineDescription(SVMTrainingInstanceCreator.class)    
	        );  
	  }
	
	  /**
	   * Classifies all anaphors found in the usedDocs - documents
	   * @param _usedDocs the documents used, each cell in the array contains the number of a document
	   * @throws Exception
	   */
	  private static void testPipeline(Integer[] usedDocs) throws Exception{
		  SimplePipeline.runPipeline(	        		
	        		CollectionReaderFactory.createReader(
	                        WikiCoref_Reader.class,
	                        WikiCoref_Reader.PARAM_INPUT_DIRECTORY, "src/test/resources/WikiCoref_Annotation",
	                        WikiCoref_Reader.PARAM_USED_DOCUMENT_NUMBERS, usedDocs),	   
	        		AnalysisEngineFactory.createEngineDescription(CoreNlpPosTagger.class),
	        		AnalysisEngineFactory.createEngineDescription(CoreNlpLemmatizer.class),
	        		AnalysisEngineFactory.createEngineDescription(CoreNlpNamedEntityRecognizer.class),
	        		AnalysisEngineFactory.createEngineDescription(CoreNlpParser.class,
																	CoreNlpParser.PARAM_ORIGINAL_DEPENDENCIES,
																	false),
	        		AnalysisEngineFactory.createEngineDescription(AnaphoraAnnotator.class),
	        		AnalysisEngineFactory.createEngineDescription(NegativeTrainingInstanceAnnotator.class),
	        		AnalysisEngineFactory.createEngineDescription(SVMClassifier.class)
	        );  
	  }
	

	  
}
