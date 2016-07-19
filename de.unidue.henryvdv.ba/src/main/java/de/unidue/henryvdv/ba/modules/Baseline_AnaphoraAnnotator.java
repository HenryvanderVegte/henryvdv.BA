package de.unidue.henryvdv.ba.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.POS;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.constituent.NP;
import de.unidue.henryvdv.ba.type.DetectedNP;
import de.unidue.henryvdv.ba.type.DocumentInfo;
import de.unidue.henryvdv.ba.type.GoldNP;
import de.unidue.henryvdv.ba.type.MyCoreferenceChain;
import de.unidue.henryvdv.ba.type.MyCoreferenceLink;

public class Baseline_AnaphoraAnnotator
extends JCasAnnotator_ImplBase{

	private JCas aJCas;
	
	private int anaphorsInDoc;
	private int correctAnaphorsInDoc;
	
	private int anaphorsTotal;
	private int correctAnaphorsTotal;
	
	private List<NP> nps;
	private List<NP> npsWithAnaphora;
	private List<GoldNP> goldNpsWithAnaphora;
	private List<DetectedNP> detectedNpsWithAnaphora;
	private Collection<MyCoreferenceChain> corefChains;
	
	private String[] whitelist = {"himself","herself","itself","themselves",
								"his","her","its","their",
								"he","she","it","they"};
	
	public void initialize(UimaContext context) throws ResourceInitializationException{
		super.initialize(context);
		anaphorsTotal = 0;
		correctAnaphorsTotal = 0;
	}
	
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		this.aJCas = aJCas;
		nps = new ArrayList<NP>();
		npsWithAnaphora = new ArrayList<NP>();
		goldNpsWithAnaphora = new ArrayList<GoldNP>();
		detectedNpsWithAnaphora = new ArrayList<DetectedNP>();
		correctAnaphorsInDoc = 0;
		anaphorsInDoc = 0;
		
		corefChains = JCasUtil.select(aJCas, MyCoreferenceChain.class);		
		Collection<NP> nounphrases = JCasUtil.select(aJCas, NP.class);
		
		for(NP n : nounphrases){
			List<Token> coveredTokens = getCoveredTokens(n.getBegin(), n.getEnd());		
			if(containsAnaphora(coveredTokens)){
				npsWithAnaphora.add(n);
			}		
			nps.add(n);			
		}
		SetGoldNPs();
		SetDetectedNPs();
		if(detectedNpsWithAnaphora.size() != goldNpsWithAnaphora.size()){
			System.out.println("Something wrong here");
		} else {
			evaluate();
			printResults();
		}
	}
	
	@Override
	public void collectionProcessComplete(){
		float rel = 0f;	
		if(anaphorsTotal != 0){
			rel = ((float)correctAnaphorsTotal / (float)anaphorsTotal) * 100f;
		} 
		System.out.println("Total: " + rel + " % ");
	}
	
	private void printResults(){
		//System.out.println("RESULTS:   ");
		//System.out.println("Correct (Abs): " + correctAnaphors);
		//System.out.println("Total: " + totalAnaphors);
		float rel = 0f;	
		if(anaphorsInDoc != 0){
			rel = ((float)correctAnaphorsInDoc / (float)anaphorsInDoc) * 100f;
		} 
		DocumentInfo docInfo = JCasUtil.selectSingle(aJCas, DocumentInfo.class);
		System.out.println("Document: " + docInfo.getDocumentName());
		System.out.println("Correct: " + rel + " %");
	}
	
	private void evaluate(){
		for(int i = 0; i < npsWithAnaphora.size(); i++){
			if(goldNpsWithAnaphora.get(i) == null || detectedNpsWithAnaphora.get(i) == null){
				anaphorsInDoc++;
				continue;
			}
			if(goldNpsWithAnaphora.get(i).getBegin() == detectedNpsWithAnaphora.get(i).getBegin() && 
				goldNpsWithAnaphora.get(i).getEnd() == detectedNpsWithAnaphora.get(i).getEnd()){
				correctAnaphorsInDoc++;
			}
			anaphorsInDoc++;
		}
		anaphorsTotal += anaphorsInDoc;
		correctAnaphorsTotal += correctAnaphorsInDoc;
		
	}
	
	private void SetDetectedNPs(){
		int i = 0;
		int j = 0;
		while(i < nps.size() && j < npsWithAnaphora.size()){
			if(nps.get(i).equals(npsWithAnaphora.get(j))){				
				DetectedNP n = new DetectedNP(aJCas, nps.get(i-1).getBegin(), nps.get(i-1).getEnd());
				detectedNpsWithAnaphora.add(n);
				
				j++;
			}
			i++;
		}
	}
	
	private void SetGoldNPs(){
		for(int i = 0; i < npsWithAnaphora.size(); i++){
			int begin = npsWithAnaphora.get(i).getBegin();
			int end = npsWithAnaphora.get(i).getEnd();
			Integer[] anaphoraStartEnd = getAnaphoraBound(begin, end);
			if(anaphoraStartEnd[0] == 0 && anaphoraStartEnd[1] == 0){
				goldNpsWithAnaphora.add(null);
			} else {
				GoldNP np = new GoldNP(aJCas, anaphoraStartEnd[0], anaphoraStartEnd[1]);
				goldNpsWithAnaphora.add(np);
			}
			
		}
	}
	
	private Integer[] getAnaphoraBound(int begin, int end){
		Integer[] r = {0,0};
		for(MyCoreferenceChain c : corefChains){
			MyCoreferenceLink corefLinkOld = c.getFirst();			
			if(corefLinkOld.getBegin() == begin && corefLinkOld.getEnd() == end){
				return r;
			}
			
			while(corefLinkOld.getNext() != null){
				MyCoreferenceLink corefLinkNew = corefLinkOld.getNext();
				
				if(corefLinkNew.getBegin() == begin && corefLinkNew.getEnd() == end){					
					r[0] = corefLinkOld.getBegin();
					r[1] = corefLinkOld.getEnd();
					return r;
				}
				corefLinkOld = corefLinkNew;
			}
		
		}
		return r;
	}	
	
	private boolean containsAnaphora(List<Token> tokens){
		for(Token t : tokens){
			if(t.getPos().getPosValue() == "PRP" || 
					t.getPos().getPosValue() == "PRP$" || 
					t.getPos().getPosValue() == "WP$"
			){
				if(Arrays.asList(whitelist).contains(t.getCoveredText().toLowerCase())){
					return true;
				}
			}
		}
		return false;
	}
	
	private List<Token> getCoveredTokens(int begin, int end){
		List<Token> coveredTokens = new ArrayList<Token>();
		Collection<Token> tokens = JCasUtil.select(aJCas, Token.class);
		
		for(Token t : tokens){
			if(t.getBegin() >= begin && t.getEnd() <= end){
				coveredTokens.add(t);
			}
		}
		return coveredTokens;
	}

}
