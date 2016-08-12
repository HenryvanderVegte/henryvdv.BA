package de.unidue.henryvdv.ba.pipeline;

import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;

import de.tudarmstadt.ukp.dkpro.core.corenlp.CoreNlpParser;
import de.tudarmstadt.ukp.dkpro.core.snowball.SnowballStemmer;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordLemmatizer;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordNamedEntityRecognizer;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordParser;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordPosTagger;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordSegmenter;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordCoreferenceResolver;
import de.unidue.henryvdv.ba.modules.Baseline_Evaluator;
import de.unidue.henryvdv.ba.modules.FeatureAnnotator_Antecedent;
import de.unidue.henryvdv.ba.modules.FeatureAnnotator_PronounAntecedent;
import de.unidue.henryvdv.ba.modules.AnaphoraAnnotator;
import de.unidue.henryvdv.ba.modules.InformationModule;
import de.unidue.henryvdv.ba.modules.NegativeTrainingInstanceAnnotator;
import de.unidue.henryvdv.ba.modules.QuotationAnnotator;
import de.unidue.henryvdv.ba.modules.SVMLearn;
import de.unidue.henryvdv.ba.modules.SVMTrainingInstanceCreator;
import de.unidue.henryvdv.ba.reader.SimpleTextReader;
import de.unidue.henryvdv.ba.reader.WikiCoref_DCorefReader;
import de.unidue.henryvdv.ba.reader.WikiCoref_Reader;

public class BasicPipeline {

	  public static void main(String[] args)
			  throws Exception {	

     		runWikiCorefReader();
    		//  SVMLearn svmLearn = new SVMLearn();
    		//  svmLearn.learn();
	  }
	  
	  
	  
	  private static void runSimpleTextReader() throws Exception{
		  SimplePipeline.runPipeline(
				  CollectionReaderFactory.createReader(
						  				SimpleTextReader.class,
						  				SimpleTextReader.PARAM_INPUT_DIRECTORY,
						  				"src/test/resources/prepositionTest"),
				  AnalysisEngineFactory.createEngineDescription(StanfordSegmenter.class),
				  AnalysisEngineFactory.createEngineDescription(StanfordPosTagger.class),
				  AnalysisEngineFactory.createEngineDescription(StanfordLemmatizer.class),
				  AnalysisEngineFactory.createEngineDescription(StanfordNamedEntityRecognizer.class),
				  AnalysisEngineFactory.createEngineDescription(StanfordParser.class, 
	        													StanfordParser.PARAM_MODE,
	        													StanfordParser.DependenciesMode.BASIC),
	        	AnalysisEngineFactory.createEngineDescription(QuotationAnnotator.class),
	        	AnalysisEngineFactory.createEngineDescription(AnaphoraAnnotator.class),
	        	AnalysisEngineFactory.createEngineDescription(NegativeTrainingInstanceAnnotator.class),  
	        	AnalysisEngineFactory.createEngineDescription(FeatureAnnotator_PronounAntecedent.class),
	        	AnalysisEngineFactory.createEngineDescription(InformationModule.class)	        	
				  );
	  }
	  
	  private static void runWikiCorefReader() throws Exception{
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
        		
	        		AnalysisEngineFactory.createEngineDescription(SVMTrainingInstanceCreator.class),       		
	        		//AnalysisEngineFactory.createEngineDescription(Baseline_Evaluator.class),
	                AnalysisEngineFactory.createEngineDescription(InformationModule.class)
	        );
		  
	  }
	  
	  private static void runWikiCoref_DCorefReader() throws Exception{
		  SimplePipeline.runPipeline(
	                CollectionReaderFactory.createReader(
	                        WikiCoref_DCorefReader.class,
	                        WikiCoref_DCorefReader.PARAM_INPUT_DIRECTORY, "src/test/resources/WikiCoref_DCoref"
	                ), 
	                AnalysisEngineFactory.createEngineDescription(InformationModule.class)
	            //    AnalysisEngineFactory.createEngineDescription(BaselineAnnotator.class)
	        );
	  }

}
