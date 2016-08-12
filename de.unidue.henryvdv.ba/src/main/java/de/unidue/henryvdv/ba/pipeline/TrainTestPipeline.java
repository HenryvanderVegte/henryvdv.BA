package de.unidue.henryvdv.ba.pipeline;

import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;

import de.tudarmstadt.ukp.dkpro.core.corenlp.CoreNlpParser;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordLemmatizer;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordNamedEntityRecognizer;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordPosTagger;
import de.unidue.henryvdv.ba.modules.AnaphoraAnnotator;
import de.unidue.henryvdv.ba.modules.FeatureAnnotator_Antecedent;
import de.unidue.henryvdv.ba.modules.FeatureAnnotator_PronounAntecedent;
import de.unidue.henryvdv.ba.modules.InformationModule;
import de.unidue.henryvdv.ba.modules.NegativeTrainingInstanceAnnotator;
import de.unidue.henryvdv.ba.modules.SVMLearn;
import de.unidue.henryvdv.ba.modules.SVMTrainingInstanceCreator;
import de.unidue.henryvdv.ba.reader.WikiCoref_Reader;

public class TrainTestPipeline {

	public static void main(String[] args)
			  throws Exception {	
		
		trainPipeline();
		SVMLearn svmLearn = new SVMLearn();
		svmLearn.learn();
	}
	
	  private static void trainPipeline() throws Exception{
		  SimplePipeline.runPipeline(	               
	        		
	        		CollectionReaderFactory.createReader(
	                        WikiCoref_Reader.class,
	                        WikiCoref_Reader.PARAM_INPUT_DIRECTORY, "src/test/resources/WikiCoref_Annotation",
	                        WikiCoref_Reader.PARAM_MAX_DOCUMENTS, 30
	                ),			   
	        		AnalysisEngineFactory.createEngineDescription(StanfordPosTagger.class),
	        		AnalysisEngineFactory.createEngineDescription(StanfordLemmatizer.class),
	        		AnalysisEngineFactory.createEngineDescription(StanfordNamedEntityRecognizer.class),
	        		AnalysisEngineFactory.createEngineDescription(CoreNlpParser.class,
																	CoreNlpParser.PARAM_ORIGINAL_DEPENDENCIES,
																	false),
	        		AnalysisEngineFactory.createEngineDescription(AnaphoraAnnotator.class),
	        		AnalysisEngineFactory.createEngineDescription(NegativeTrainingInstanceAnnotator.class),
	        		AnalysisEngineFactory.createEngineDescription(FeatureAnnotator_PronounAntecedent.class),
	        		AnalysisEngineFactory.createEngineDescription(FeatureAnnotator_Antecedent.class),      		
	        		AnalysisEngineFactory.createEngineDescription(SVMTrainingInstanceCreator.class)      		
	        );  
	  }
	  
	  private static void testPipeline() throws Exception{
		  SimplePipeline.runPipeline(	        		
	        		CollectionReaderFactory.createReader(
	                        WikiCoref_Reader.class,
	                        WikiCoref_Reader.PARAM_INPUT_DIRECTORY, "src/test/resources/WikiCoref_Annotation",
	                        WikiCoref_Reader.PARAM_MAX_DOCUMENTS, 30
	                ),			   
	        		AnalysisEngineFactory.createEngineDescription(StanfordPosTagger.class),
	        		AnalysisEngineFactory.createEngineDescription(StanfordLemmatizer.class),
	        		AnalysisEngineFactory.createEngineDescription(StanfordNamedEntityRecognizer.class),
	        		AnalysisEngineFactory.createEngineDescription(CoreNlpParser.class,
																	CoreNlpParser.PARAM_ORIGINAL_DEPENDENCIES,
																	false),
	        		AnalysisEngineFactory.createEngineDescription(AnaphoraAnnotator.class),
	        		AnalysisEngineFactory.createEngineDescription(NegativeTrainingInstanceAnnotator.class),
	        		AnalysisEngineFactory.createEngineDescription(FeatureAnnotator_PronounAntecedent.class),
	        		AnalysisEngineFactory.createEngineDescription(FeatureAnnotator_Antecedent.class)
	        		
	        );  
	  }
	
}
