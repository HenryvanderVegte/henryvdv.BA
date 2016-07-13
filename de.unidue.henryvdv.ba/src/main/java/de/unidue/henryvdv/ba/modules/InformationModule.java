package de.unidue.henryvdv.ba.modules;

import java.util.Collection;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.api.coref.type.CoreferenceChain;
import de.tudarmstadt.ukp.dkpro.core.api.coref.type.CoreferenceLink;
import de.tudarmstadt.ukp.dkpro.core.api.frequency.util.FrequencyDistribution;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;

public class InformationModule 
	extends JCasAnnotator_ImplBase
{

	JCas aJCas;
	
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		this.aJCas = aJCas;
		
		
		printCorefChains();
	//	printDocText();
	//	printTokens();
	//	printSentences();
		
		
	//	explorePOS(10);
	}
	
	private void printCorefChains(){
		Collection<CoreferenceChain> corefChains = JCasUtil.select(aJCas, CoreferenceChain.class);
		for(CoreferenceChain cChain : corefChains){
			System.out.println("-----------------------------");
			CoreferenceLink cLink = cChain.getFirst();
			while(cLink.getNext() != null){
				System.out.println("Text: " + cLink.getCoveredText());
				cLink = cLink.getNext();
			}
			System.out.println("Text: " + cLink.getCoveredText());
		}
	}
	
	private void printDocText(){
		System.out.println("DocText:" + aJCas.getDocumentText());
	}
	
	
	private void explorePOS(int n){
		Collection<Token> tokens = JCasUtil.select(aJCas, Token.class);
		FrequencyDistribution<String> fd = new FrequencyDistribution<String>();
		for(Token t : tokens){
			fd.inc(t.getPos().getPosValue());
		}
		for(String s : fd.getMostFrequentSamples(n)){
			System.out.println("POS : " + s + " --- " + "Frequency: " + fd.getCount(s));
		}
	}
	
	private void printTokens(){
		Collection<Token> tokens = JCasUtil.select(aJCas, Token.class);
		for(Token t : tokens){
			System.out.println("++++++++++++++++++++++++++");	
			System.out.println("Token : " +  t.getCoveredText());
			System.out.println("Lemma : " +  t.getLemma().getValue());			
			System.out.println("POS : " +  t.getPos().getPosValue());			
		}
	}
	
	private void printSentences(){
		Collection<Sentence> sentences = JCasUtil.select(aJCas, Sentence.class);
		for(Sentence s : sentences){
			System.out.println(s.getCoveredText());
		}
	}

}
