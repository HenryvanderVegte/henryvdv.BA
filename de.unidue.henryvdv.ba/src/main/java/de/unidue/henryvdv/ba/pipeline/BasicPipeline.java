package de.unidue.henryvdv.ba.pipeline;

import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;

import de.tudarmstadt.ukp.dkpro.core.corenlp.CoreNlpLemmatizer;
import de.tudarmstadt.ukp.dkpro.core.corenlp.CoreNlpNamedEntityRecognizer;
import de.tudarmstadt.ukp.dkpro.core.corenlp.CoreNlpParser;
import de.tudarmstadt.ukp.dkpro.core.corenlp.CoreNlpPosTagger;
import de.tudarmstadt.ukp.dkpro.core.corenlp.CoreNlpSegmenter;

import de.tudarmstadt.ukp.dkpro.core.snowball.SnowballStemmer;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordLemmatizer;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordNamedEntityRecognizer;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordParser;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordPosTagger;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordSegmenter;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordCoreferenceResolver;
import de.unidue.henryvdv.ba.modules.Baseline_Evaluator;
import de.unidue.henryvdv.ba.modules.FeatureAnnotator;
import de.unidue.henryvdv.ba.modules.AnaphoraAnnotator;
import de.unidue.henryvdv.ba.modules.InformationModule;
import de.unidue.henryvdv.ba.modules.NegativeTrainingInstanceAnnotator;
import de.unidue.henryvdv.ba.modules.QuotationAnnotator;
import de.unidue.henryvdv.ba.modules.SVMTrainingInstanceCreator;
import de.unidue.henryvdv.ba.reader.SimpleTextReader;
import de.unidue.henryvdv.ba.reader.WikiCoref_Reader;
import de.unidue.henryvdv.ba.util.SVMLearn;
/**
 * Pipelines for basic testing (no crossvalidation)
 * @author Henry
 *
 */
public class BasicPipeline {
	
	private static Integer[] usedDocs;

	  public static void main(String[] args)
			  throws Exception {	
		  
		  	usedDocs = new Integer[30];
		  	for(int i = 0; i < usedDocs.length; i++){
		  		usedDocs[i] = i;
		  	}
			usedDocs = new Integer[]{0};
     	//	runSimpleTextReader();
		  	runWikiCorefReader();
    		//  SVMLearn svmLearn = new SVMLearn();
    		//  svmLearn.learn();
	  }
	  
	  
	  
	  private static void runSimpleTextReader() throws Exception{
		  SimplePipeline.runPipeline(
				  CollectionReaderFactory.createReader(
						  				SimpleTextReader.class,
						  				SimpleTextReader.PARAM_INPUT_DIRECTORY,"src/test/resources/testDocuments"),
				  AnalysisEngineFactory.createEngineDescription(CoreNlpSegmenter.class),
			        AnalysisEngineFactory.createEngineDescription(CoreNlpPosTagger.class),
			        AnalysisEngineFactory.createEngineDescription(CoreNlpLemmatizer.class),
			        AnalysisEngineFactory.createEngineDescription(CoreNlpNamedEntityRecognizer.class),
			        AnalysisEngineFactory.createEngineDescription(CoreNlpParser.class,
																	CoreNlpParser.PARAM_ORIGINAL_DEPENDENCIES,
																	false,
																	CoreNlpParser.PARAM_WRITE_PENN_TREE,
																	true),
			       // AnalysisEngineFactory.createEngineDescription(AnaphoraAnnotator.class),
			       // AnalysisEngineFactory.createEngineDescription(NegativeTrainingInstanceAnnotator.class),
			       // AnalysisEngineFactory.createEngineDescription(FeatureAnnotator.class),        	
			        AnalysisEngineFactory.createEngineDescription(InformationModule.class)      	
				  );
	  }
	  
	  private static void runWikiCorefReader() throws Exception{
		  SimplePipeline.runPipeline(	               
	        		
	        		CollectionReaderFactory.createReader(
	                        WikiCoref_Reader.class,
	                        WikiCoref_Reader.PARAM_INPUT_DIRECTORY, "src/test/resources/WikiCoref_Annotation",
	                        WikiCoref_Reader.PARAM_USED_DOCUMENT_NUMBERS, usedDocs),   
	        		AnalysisEngineFactory.createEngineDescription(CoreNlpPosTagger.class),
	        		AnalysisEngineFactory.createEngineDescription(CoreNlpNamedEntityRecognizer.class),
	        		AnalysisEngineFactory.createEngineDescription(CoreNlpParser.class,
																	CoreNlpParser.PARAM_ORIGINAL_DEPENDENCIES,
																	false),
	        		AnalysisEngineFactory.createEngineDescription(AnaphoraAnnotator.class),
	        		AnalysisEngineFactory.createEngineDescription(NegativeTrainingInstanceAnnotator.class),
	        		AnalysisEngineFactory.createEngineDescription(FeatureAnnotator.class),      		
	        		//AnalysisEngineFactory.createEngineDescription(SVMTrainingInstanceCreator.class),       		
	        		//AnalysisEngineFactory.createEngineDescription(Baseline_Evaluator.class),
	                AnalysisEngineFactory.createEngineDescription(InformationModule.class)
	        );
		  
	  }
	 

}
