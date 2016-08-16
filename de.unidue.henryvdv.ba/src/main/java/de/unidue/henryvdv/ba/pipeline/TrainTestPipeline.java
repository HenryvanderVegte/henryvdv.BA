package de.unidue.henryvdv.ba.pipeline;

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
import de.unidue.henryvdv.ba.modules.FeatureAnnotator_Pronoun;
import de.unidue.henryvdv.ba.modules.FeatureAnnotator_PronounAntecedent;
import de.unidue.henryvdv.ba.modules.InformationModule;
import de.unidue.henryvdv.ba.modules.NegativeTrainingInstanceAnnotator;
import de.unidue.henryvdv.ba.modules.SVMClassifier;
import de.unidue.henryvdv.ba.modules.SVMLearn;
import de.unidue.henryvdv.ba.modules.SVMTrainingInstanceCreator;
import de.unidue.henryvdv.ba.reader.WikiCoref_Reader;

public class TrainTestPipeline {

	public static void main(String[] args)
			  throws Exception {	
		
		for(int i = 26; i < 30; i++){
			long start = System.currentTimeMillis();
			System.out.println("Nr." + i + ":");
			System.out.print("Train on:");
			
			trainPipeline(i, -1);
			
			System.out.println("");
			
			SVMLearn svmLearn = new SVMLearn();
			svmLearn.learn();
			
			System.out.print("Test on:");
			
			testPipeline(-1,i);
			long elapsedTimeMillis = System.currentTimeMillis()-start;
			float elapsedTimeMin = elapsedTimeMillis/(60*1000F);
			System.out.println("Elapsed Time: " + elapsedTimeMin);
			System.out.println("");
		}
	}
	
	private static void trainTest(){
		
	}
	
	  private static void trainPipeline(int leaveOut, int useThis) throws Exception{
		  SimplePipeline.runPipeline(	               
	        		
	        		CollectionReaderFactory.createReader(
	                        WikiCoref_Reader.class,
	                        WikiCoref_Reader.PARAM_INPUT_DIRECTORY, "src/test/resources/WikiCoref_Annotation",
	                        WikiCoref_Reader.PARAM_MAX_DOCUMENTS, 30,
	                        WikiCoref_Reader.PARAM_LEAVE_OUT, leaveOut,
	                        WikiCoref_Reader.PARAM_USE_ONLY_THIS, useThis
	                ),			   
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
	        		AnalysisEngineFactory.createEngineDescription(SVMTrainingInstanceCreator.class),
	        		AnalysisEngineFactory.createEngineDescription(InformationModule.class)
	        );  
	  }
	  
	  private static void testPipeline(int leaveOut, int useThis) throws Exception{
		  SimplePipeline.runPipeline(	        		
	        		CollectionReaderFactory.createReader(
	                        WikiCoref_Reader.class,
	                        WikiCoref_Reader.PARAM_INPUT_DIRECTORY, "src/test/resources/WikiCoref_Annotation",
	                        WikiCoref_Reader.PARAM_MAX_DOCUMENTS, 30,
	                        WikiCoref_Reader.PARAM_LEAVE_OUT, leaveOut,
	                        WikiCoref_Reader.PARAM_USE_ONLY_THIS, useThis
	                ),			   
	        		AnalysisEngineFactory.createEngineDescription(StanfordPosTagger.class),
	        		AnalysisEngineFactory.createEngineDescription(StanfordLemmatizer.class),
	        		AnalysisEngineFactory.createEngineDescription(CoreNlpNamedEntityRecognizer.class),
	        		AnalysisEngineFactory.createEngineDescription(CoreNlpParser.class,
																	CoreNlpParser.PARAM_ORIGINAL_DEPENDENCIES,
																	false),
	        		AnalysisEngineFactory.createEngineDescription(AnaphoraAnnotator.class),
	        		AnalysisEngineFactory.createEngineDescription(NegativeTrainingInstanceAnnotator.class),
	        		AnalysisEngineFactory.createEngineDescription(SVMClassifier.class),   		
	        		AnalysisEngineFactory.createEngineDescription(InformationModule.class)
	        );  
	  }
	
}
