package de.unidue.henryvdv.ba.pipeline;

import java.util.ArrayList;
import java.util.List;

import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;

import de.tudarmstadt.ukp.dkpro.core.corenlp.CoreNlpNamedEntityRecognizer;
import de.tudarmstadt.ukp.dkpro.core.corenlp.CoreNlpParser;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordLemmatizer;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordNamedEntityRecognizer;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordPosTagger;
import de.unidue.henryvdv.ba.modules.AnaphoraAnnotator;
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
	
	private final static Integer[] usedDocs = {0,1,2,3,4,5,6};

	public static void main(String[] args)
			  throws Exception {	
		/*
		Integer[] docs = new Integer[30];
		for(int i = 0; i < docs.length; i++){
			docs[i] = i;
		}
		trainPipeline(docs);
		*/
		cv(10, 30, true);
	}
	
	private static void cv(int folds, int docSize, boolean randomFolds) throws Exception{
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
			/*
			System.out.println("Train On: ");
			for(Integer x : trainOnArray){
				System.out.print(x + "  ");
			}
			System.out.println("");
			System.out.println("Test On: ");
			for(Integer x : testOnArray){
				System.out.print(x + "  ");
			}
			System.out.println("");
			System.out.println("---------------------------------");
			*/
			

			trainPipeline(trainOnArray);
			
			SVMLearn svmLearn = new SVMLearn();
			svmLearn.learn();
			
			testPipeline(testOnArray);
			
		}
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
