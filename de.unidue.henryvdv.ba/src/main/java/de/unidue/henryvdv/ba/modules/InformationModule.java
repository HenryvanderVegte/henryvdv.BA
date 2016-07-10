package de.unidue.henryvdv.ba.modules;

import java.util.Collection;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.api.frequency.util.FrequencyDistribution;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;

public class InformationModule 
	extends JCasAnnotator_ImplBase
{

	
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		System.out.println("DocText:" + aJCas.getDocumentText());
		
		Collection<Token> tokens = JCasUtil.select(aJCas, Token.class);
		
		
		for(Token t : tokens){
			System.out.println("++++++++++++++++++++++++++");
		
			System.out.println("Token : " +  t.getCoveredText());
			System.out.println("Lemma : " +  t.getLemma().getValue());			
			System.out.println("POS : " +  t.getPos().getPosValue());			
		}
		/*
		Collection<Sentence> sentences = JCasUtil.select(aJCas, Sentence.class);
		for(Sentence s : sentences){
			System.out.println(s.getCoveredText());
		}
		*/
		
		explorePOS(tokens, 10);
	}
	
	private void explorePOS(Collection<Token> tokens, int n){
		FrequencyDistribution<String> fd = new FrequencyDistribution<String>();
		for(Token t : tokens){
			fd.inc(t.getPos().getPosValue());
		}
		for(String s : fd.getMostFrequentSamples(n)){
			System.out.println("POS : " + s + " --- " + "Frequency: " + fd.getCount(s));
		}
	}

}
