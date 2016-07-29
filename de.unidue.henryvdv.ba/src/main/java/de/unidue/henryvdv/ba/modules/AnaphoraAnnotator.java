package de.unidue.henryvdv.ba.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.constituent.NP;
import de.unidue.henryvdv.ba.type.Anaphora;
import de.unidue.henryvdv.ba.type.Antecedent;
import de.unidue.henryvdv.ba.type.GoldNP;
import de.unidue.henryvdv.ba.type.MyCoreferenceChain;
import de.unidue.henryvdv.ba.type.MyCoreferenceLink;

public class AnaphoraAnnotator 
extends JCasAnnotator_ImplBase{

	JCas aJCas;
	
	private List<NP> npsWithAnaphora;
	private Collection<MyCoreferenceChain> corefChains;
	
	private String[] whitelist = {"himself","herself","itself","themselves",
			"his","her","its","their",
			"he","she","they"};

	
	
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		this.aJCas = aJCas;
		npsWithAnaphora = new ArrayList<NP>();
		
		corefChains = JCasUtil.select(aJCas, MyCoreferenceChain.class);	
		Collection<NP> nounphrases = JCasUtil.select(aJCas, NP.class);
		
		for(NP n : nounphrases){
			List<Token> coveredTokens = getCoveredTokens(n.getBegin(), n.getEnd());		
			if(containsAnaphora(coveredTokens)){
				npsWithAnaphora.add(n);
			}			
		}
		
		setAnaphors();
		
		
	}

	private void setAnaphors(){
		for(int i = 0; i < npsWithAnaphora.size(); i++){
			int begin = npsWithAnaphora.get(i).getBegin();
			int end = npsWithAnaphora.get(i).getEnd();
			Integer[] antecedentStartEnd = getAntecedentBound(npsWithAnaphora.get(i));

			Anaphora n = new Anaphora(aJCas, begin, end);
			
			Antecedent a = null;
			
			if(antecedentStartEnd[0] != 0 || antecedentStartEnd[1] != 0){			
				a = new Antecedent(aJCas, antecedentStartEnd[0], antecedentStartEnd[1]);
			}
			n.setAntecedent(a);
			if(a != null)
				n.addToIndexes();
		}
		
	}
	
	private Integer[] getAntecedentBound(Annotation anno){
		
		int begin = anno.getBegin(); 
		int end = anno.getEnd();
		
		Integer[] r = {0,0};
		for(MyCoreferenceChain c : corefChains){
			MyCoreferenceLink corefLinkOld = c.getFirst();			
			if(corefLinkOld.getBegin() == begin && corefLinkOld.getEnd() == end){
				return r;
			}
			
			while(corefLinkOld.getNext() != null){
				MyCoreferenceLink corefLinkNew = corefLinkOld.getNext();
				
				if(corefLinkNew.getBegin() >= begin && corefLinkNew.getEnd() <= end){					
					r[0] = corefLinkOld.getBegin();
					r[1] = corefLinkOld.getEnd();
					return r;
				}
				
				if(corefLinkNew.getBegin() <= begin && corefLinkNew.getEnd() >= end){					
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
