package de.unidue.henryvdv.ba.pipeline;

import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import de.unidue.henryvdv.ba.modules.InformationModule;
import de.unidue.henryvdv.ba.reader.WikiCorefReader;

public class BasicPipeline {

	  public static void main(String[] args)
		        throws Exception
		    {
		        SimplePipeline.runPipeline(
		                CollectionReaderFactory.createReader(
		                        WikiCorefReader.class,
		                        WikiCorefReader.PARAM_INPUT_FILE, "src/test/resources/corefTest/Battle of Kosovo.xml"
		                ),
		                AnalysisEngineFactory.createEngineDescription(InformationModule.class)
		        );
		    }

}
