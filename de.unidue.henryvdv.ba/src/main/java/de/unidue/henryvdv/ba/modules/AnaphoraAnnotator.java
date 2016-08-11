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

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
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
	private Collection<Token> tokens;
	private Collection<Sentence> sentences;
	
	private String[] whitelist = {"himself","herself","itself","themselves",
			"his","her","its","their",
			"he","she","they"};

	
	
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		this.aJCas = aJCas;
		npsWithAnaphora = new ArrayList<NP>();
		sentences = JCasUtil.select(aJCas, Sentence.class);			
		corefChains = JCasUtil.select(aJCas, MyCoreferenceChain.class);	
		tokens = JCasUtil.select(aJCas, Token.class);
		
		setAnaphoras();
		
		
	}

	private void setAnaphoras(){
		List<Anaphora> anaphoras = new ArrayList<Anaphora>();
		for(Token token : tokens){
			if(token.getPos().getPosValue() == "PRP" || 
					token.getPos().getPosValue() == "PRP$" || 
					token.getPos().getPosValue() == "WP$"
			){
				if(Arrays.asList(whitelist).contains(token.getCoveredText().toLowerCase())){
					Anaphora a = new Anaphora(aJCas, token.getBegin(), token.getEnd());
					anaphoras.add(a);
				}
			}
		}

		for(int i = 0; i < anaphoras.size(); i++){
			Integer[] bound = getAntecedentBound(anaphoras.get(i));
			if(bound[0] != 0 || bound[1] != 0){
				Antecedent antecedent = new Antecedent(aJCas, bound[0], bound[1]);
				anaphoras.get(i).setAntecedent(antecedent);
				anaphoras.get(i).addToIndexes();
			}
			
			
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
				
				if(corefLinkNew.getBegin() == begin && corefLinkNew.getEnd() == end){					
					r[0] = corefLinkOld.getBegin();
					r[1] = corefLinkOld.getEnd();
					return r;
				}
				/*
				if(corefLinkNew.getBegin() >= begin && corefLinkNew.getEnd() <= end){					
					r[0] = corefLinkOld.getBegin();
					r[1] = corefLinkOld.getEnd();
					return r;
				}
				
				if(corefLinkNew.getBegin() <= begin && corefLinkNew.getEnd() >= end){					
					r[0] = corefLinkOld.getBegin();
					r[1] = corefLinkOld.getEnd();
					return r;
				}*/
				
				corefLinkOld = corefLinkNew;
			}
		
		}
		return r;
	}	
	
}
