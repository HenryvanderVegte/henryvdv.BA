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
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordLemmatizer;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordNamedEntityRecognizer;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordPosTagger;
import de.unidue.henryvdv.ba.modules.AnaphoraAnnotator;
import de.unidue.henryvdv.ba.modules.Baseline_Evaluator;
import de.unidue.henryvdv.ba.modules.FeatureAnnotator_Antecedent;
import de.unidue.henryvdv.ba.modules.FeatureAnnotator_Gender;
import de.unidue.henryvdv.ba.modules.FeatureAnnotator_Pronoun;
import de.unidue.henryvdv.ba.modules.FeatureAnnotator_PronounAntecedent;
import de.unidue.henryvdv.ba.modules.InformationModule;
import de.unidue.henryvdv.ba.modules.NegativeTrainingInstanceAnnotator;
import de.unidue.henryvdv.ba.modules.SVMClassifier;
import de.unidue.henryvdv.ba.modules.SVMLearn;
import de.unidue.henryvdv.ba.modules.SVMTrainingInstanceCreator;
import de.unidue.henryvdv.ba.reader.WikiCoref_Reader;
import java.util.concurrent.ThreadLocalRandom;

public class TrainTestPipeline {

	public static void main(String[] args)
			  throws Exception {	

		crossvalidation(10, 30, true);
		
		/*
		Integer[] allDocs = new Integer[30];
		for(int i = 0; i < 30; i++){
			allDocs[i] = i;
		}
		
		trainPipeline(allDocs);
		*/
		//baseline(allDocs);
	}
	
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
			
			System.out.print("Training - Fold nr " + i + "  (");
			for(Integer t : trainOnArray){
				System.out.print("  " + t );
			}
			
			System.out.println(")");
			trainPipeline(trainOnArray);
			
			SVMLearn svmLearn = new SVMLearn();
			svmLearn.learn();
			
			System.out.print("Testing - Fold nr " + i + "  (");
			for(Integer t : testOnArray){
				System.out.print("  " + t );
			}
			
			System.out.println(")");
			testPipeline(testOnArray);
			
		}
	}
	
	private static void baseline(Integer[] _usedDocs) throws Exception{
		SimplePipeline.runPipeline(	               
        		
		        CollectionReaderFactory.createReader(
		                      WikiCoref_Reader.class,
		                      WikiCoref_Reader.PARAM_INPUT_DIRECTORY, "src/test/resources/WikiCoref_Annotation",
		                      WikiCoref_Reader.PARAM_USED_DOCUMENT_NUMBERS, _usedDocs),			   
		        AnalysisEngineFactory.createEngineDescription(StanfordPosTagger.class),
		        AnalysisEngineFactory.createEngineDescription(StanfordLemmatizer.class),
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
	
	private static void trainTest(){
	}
	
	private static void trainPipeline(Integer[] _usedDocs) throws Exception{
		SimplePipeline.runPipeline(	               
	        		
	        CollectionReaderFactory.createReader(
	                      WikiCoref_Reader.class,
	                      WikiCoref_Reader.PARAM_INPUT_DIRECTORY, "src/test/resources/WikiCoref_Annotation",
	                      WikiCoref_Reader.PARAM_USED_DOCUMENT_NUMBERS, _usedDocs),			   
	        AnalysisEngineFactory.createEngineDescription(StanfordPosTagger.class),
	        AnalysisEngineFactory.createEngineDescription(StanfordLemmatizer.class),
	        AnalysisEngineFactory.createEngineDescription(CoreNlpNamedEntityRecognizer.class),
	        AnalysisEngineFactory.createEngineDescription(CoreNlpParser.class,
															CoreNlpParser.PARAM_ORIGINAL_DEPENDENCIES,
															false),
	        AnalysisEngineFactory.createEngineDescription(AnaphoraAnnotator.class),
	        AnalysisEngineFactory.createEngineDescription(NegativeTrainingInstanceAnnotator.class),
	        AnalysisEngineFactory.createEngineDescription(FeatureAnnotator_PronounAntecedent.class),
	        AnalysisEngineFactory.createEngineDescription(FeatureAnnotator_Antecedent.class),    
	        AnalysisEngineFactory.createEngineDescription(FeatureAnnotator_Pronoun.class), 
	        AnalysisEngineFactory.createEngineDescription(FeatureAnnotator_Gender.class), 
	        AnalysisEngineFactory.createEngineDescription(SVMTrainingInstanceCreator.class)
	        );  
	  }
	  
	  private static void testPipeline(Integer[] _usedDocs) throws Exception{
		  SimplePipeline.runPipeline(	        		
	        		CollectionReaderFactory.createReader(
	                        WikiCoref_Reader.class,
	                        WikiCoref_Reader.PARAM_INPUT_DIRECTORY, "src/test/resources/WikiCoref_Annotation",
	                        WikiCoref_Reader.PARAM_USED_DOCUMENT_NUMBERS, _usedDocs),	   
	        		AnalysisEngineFactory.createEngineDescription(StanfordPosTagger.class),
	        		AnalysisEngineFactory.createEngineDescription(StanfordLemmatizer.class),
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
