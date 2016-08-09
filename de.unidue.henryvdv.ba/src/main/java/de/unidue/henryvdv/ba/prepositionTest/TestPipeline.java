package de.unidue.henryvdv.ba.prepositionTest;

import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;

import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordLemmatizer;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordNamedEntityRecognizer;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordParser;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordPosTagger;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordSegmenter;
import de.tudarmstadt.ukp.dkpro.core.corenlp.CoreNlpParser;
import de.unidue.henryvdv.ba.modules.InformationModule;
import de.unidue.henryvdv.ba.reader.SimpleTextReader;

public class TestPipeline {
	
	
	  public static void main(String[] args)throws Exception {	

		  runPipeline();
		  
	  }
	
	  public static void runPipeline() throws Exception{
		  SimplePipeline.runPipeline(
				  CollectionReaderFactory.createReader(
						  				SimpleTextReader.class,
						  				SimpleTextReader.PARAM_INPUT_DIRECTORY,
						  				"src/test/resources/prepositionTest"),
				  	AnalysisEngineFactory.createEngineDescription(StanfordSegmenter.class),
	        		AnalysisEngineFactory.createEngineDescription(StanfordPosTagger.class),
	        		AnalysisEngineFactory.createEngineDescription(StanfordLemmatizer.class),
	        		AnalysisEngineFactory.createEngineDescription(StanfordNamedEntityRecognizer.class),
	        		AnalysisEngineFactory.createEngineDescription(CoreNlpParser.class,
	        														CoreNlpParser.PARAM_ORIGINAL_DEPENDENCIES,
	        														false),
	        		
	        		/*AnalysisEngineFactory.createEngineDescription(StanfordParser.class,
	        														StanfordParser.PARAM_MODE,
	        														StanfordParser.DependenciesMode.BASIC), */ 
				  AnalysisEngineFactory.createEngineDescription(TestOutput.class)				  
				  );
	  }
	  
}
