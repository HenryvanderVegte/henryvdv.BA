package de.unidue.henryvdv.ba.modules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.constituent.Constituent;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.constituent.NP;
import de.unidue.henryvdv.ba.type.DetectedNP;
import de.unidue.henryvdv.ba.type.GoldNP;
import de.unidue.henryvdv.ba.type.MyCoreferenceChain;
import de.unidue.henryvdv.ba.type.MyCoreferenceLink;

public class Baseline_CorefAnnotator 
extends JCasAnnotator_ImplBase
{

	private JCas aJCas;
	
	private int totalNPs = 0;
	private int correctNPs = 0;
	
	private List<NP> nps;
	private List<GoldNP> goldnps;
	private List<DetectedNP> detectednps;
	
	private Collection<MyCoreferenceChain> corefChains;
	
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		this.aJCas = aJCas;
		
		totalNPs = 0;
		correctNPs = 0;
		nps = new ArrayList<NP>();
		goldnps = new ArrayList<GoldNP>();
		detectednps = new ArrayList<DetectedNP>();
		corefChains = JCasUtil.select(aJCas, MyCoreferenceChain.class);
		
		nps = new ArrayList<NP>();
		Collection<NP> nounphrases = JCasUtil.select(aJCas, NP.class);
		
		for(NP n : nounphrases){
			nps.add(n);
			
		}
		
		setGoldNPs();
		setDetectedNPs();
		evaluate();
		printResults();
	}
	
	private void printResults(){
		System.out.println("RESULTS:   ");
		System.out.println("Correct (Abs): " + correctNPs);
	//	System.out.println("Incorrect (Abs): " + (totalNPs - correctNPs));
		System.out.println("Total: " + totalNPs);
		float rel = ((float)correctNPs / (float)totalNPs) * 100f;
		System.out.println("Correct (Rel): " + rel + " %");
	}
	
	private void evaluate(){
		for(int i = 0; i < nps.size(); i++){
			if(goldnps.get(i) == null || detectednps.get(i) == null){
				totalNPs++;
				continue;
			}
			if(goldnps.get(i).getBegin() == detectednps.get(i).getBegin() && 
				goldnps.get(i).getEnd() == detectednps.get(i).getEnd()){
				correctNPs++;
			}
			totalNPs++;
		}
	}
	
	private void setDetectedNPs(){
		detectednps.add(null);
		for(int i = 1; i < nps.size(); i++){
			//Baseline: Set the last NP as Anaphora:
			DetectedNP n = new DetectedNP(aJCas, nps.get(i-1).getBegin(), nps.get(i-1).getEnd());
			detectednps.add(n);
		}
		
	}
	
	
	private void setGoldNPs(){
		for(int i = 0; i < nps.size(); i++){
			int begin = nps.get(i).getBegin();
			int end = nps.get(i).getEnd();
			Integer[] anaphoraStartEnd = getAnaphoraBound(begin, end);
			if(anaphoraStartEnd[0] == 0 && anaphoraStartEnd[1] == 0){
				goldnps.add(null);
			} else {
				GoldNP np = new GoldNP(aJCas, anaphoraStartEnd[0], anaphoraStartEnd[1]);
				goldnps.add(np);
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
	

}
