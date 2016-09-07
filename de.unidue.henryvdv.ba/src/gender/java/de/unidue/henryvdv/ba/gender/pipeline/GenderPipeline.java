package de.unidue.henryvdv.ba.gender.pipeline;

import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;

import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordLemmatizer;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordNamedEntityRecognizer;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordParser;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordPosTagger;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordSegmenter;
import de.unidue.henryvdv.ba.gender.modules.FrequencyCounter;
import de.unidue.henryvdv.ba.gender.reader.CorpusTextReader;
import de.unidue.henryvdv.ba.modules.AnaphoraAnnotator;
import de.unidue.henryvdv.ba.modules.FeatureAnnotator_PronounAntecedent;
import de.unidue.henryvdv.ba.modules.InformationModule;
import de.unidue.henryvdv.ba.modules.NegativeTrainingInstanceAnnotator;
import de.unidue.henryvdv.ba.modules.QuotationAnnotator;
import de.unidue.henryvdv.ba.reader.SimpleTextReader;

public class GenderPipeline {
	  public static void main(String[] args)
			  throws Exception {	
		  runGenderPipeline();
	  }
	
	  private static void runGenderPipeline() throws Exception{
		  SimplePipeline.runPipeline(
				  CollectionReaderFactory.createReader(
						  				CorpusTextReader.class,
						  				CorpusTextReader.PARAM_INPUT_DIRECTORY,
						  				"src/gender/resources/input"),
				  AnalysisEngineFactory.createEngineDescription(StanfordSegmenter.class),
				  AnalysisEngineFactory.createEngineDescription(StanfordPosTagger.class),
				  AnalysisEngineFactory.createEngineDescription(FrequencyCounter.class)
				  );
	  }
	
}
