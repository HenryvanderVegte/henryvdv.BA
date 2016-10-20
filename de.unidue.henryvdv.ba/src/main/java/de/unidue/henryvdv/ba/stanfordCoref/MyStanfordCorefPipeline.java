package de.unidue.henryvdv.ba.stanfordCoref;

import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;

import de.tudarmstadt.ukp.dkpro.core.corenlp.CoreNlpNamedEntityRecognizer;
import de.tudarmstadt.ukp.dkpro.core.corenlp.CoreNlpParser;
import de.tudarmstadt.ukp.dkpro.core.corenlp.CoreNlpCoreferenceResolver;
import de.tudarmstadt.ukp.dkpro.core.corenlp.CoreNlpSegmenter;
import de.tudarmstadt.ukp.dkpro.core.corenlp.CoreNlpPosTagger;
import de.tudarmstadt.ukp.dkpro.core.corenlp.CoreNlpLemmatizer;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordCoreferenceResolver;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordPosTagger;
import de.unidue.henryvdv.ba.modules.AnaphoraAnnotator;
import de.unidue.henryvdv.ba.reader.WikiCoref_Reader;

/**
 * Additional pipeline to test in how many cases the stanford coref-Annotator 
 * also finds the previous instance of an anaphora
 * @author Henry
 *
 */
public class MyStanfordCorefPipeline {

	public static void main(String[] args)
			  throws Exception {	
		Integer[] allDocs = new Integer[30];
		for(int i = 0; i < allDocs.length; i++){
			allDocs[i] = i;
		}
		
		Integer[] oneDoc = new Integer[1];
		oneDoc[0] = 4;
		runPipeline(allDocs);
	}
	
	private static void runPipeline(Integer[] usedDocs) throws Exception{
		SimplePipeline.runPipeline(	                       		
	        CollectionReaderFactory.createReader(
	                      WikiCoref_Reader.class,
	                      WikiCoref_Reader.PARAM_INPUT_DIRECTORY, "src/test/resources/WikiCoref_Annotation",
	                      WikiCoref_Reader.PARAM_USED_DOCUMENT_NUMBERS, usedDocs),			   
	        AnalysisEngineFactory.createEngineDescription(CoreNlpSegmenter.class),
	        AnalysisEngineFactory.createEngineDescription(CoreNlpPosTagger.class),
	        AnalysisEngineFactory.createEngineDescription(CoreNlpLemmatizer.class),
	        AnalysisEngineFactory.createEngineDescription(CoreNlpNamedEntityRecognizer.class),
	        AnalysisEngineFactory.createEngineDescription(CoreNlpParser.class,
															CoreNlpParser.PARAM_ORIGINAL_DEPENDENCIES,
															false),
	        AnalysisEngineFactory.createEngineDescription(StanfordCoreferenceResolver.class,
	        												StanfordCoreferenceResolver.PARAM_MAXDIST,
	        												1),
	        AnalysisEngineFactory.createEngineDescription(AnaphoraAnnotator.class),
	        AnalysisEngineFactory.createEngineDescription(MyStanfordCorefEval.class)
	        
	        );  
	  }
}
