package de.unidue.henryvdv.ba.prepositionTest;

import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;

import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordLemmatizer;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordNamedEntityRecognizer;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordParser;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordPosTagger;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordSegmenter;
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
	        		AnalysisEngineFactory.createEngineDescription(StanfordParser.class),  
				  AnalysisEngineFactory.createEngineDescription(TestOutput.class)				  
				  );
	  }
	  
}
