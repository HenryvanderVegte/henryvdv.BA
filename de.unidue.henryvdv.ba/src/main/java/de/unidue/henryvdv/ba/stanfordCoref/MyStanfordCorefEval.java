package de.unidue.henryvdv.ba.stanfordCoref;

import java.util.Collection;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import de.tudarmstadt.ukp.dkpro.core.api.coref.type.CoreferenceChain;
import de.tudarmstadt.ukp.dkpro.core.api.coref.type.CoreferenceLink;
import de.unidue.henryvdv.ba.type.Anaphora;
import de.unidue.henryvdv.ba.type.MyCoreferenceLink;

public class MyStanfordCorefEval extends JCasAnnotator_ImplBase{

	private JCas aJCas;
	private Collection<CoreferenceChain> stdCorefChains;
	private Collection<Anaphora> anaphoras;
	
	private int anaphorsTotal;
	private int correctAnaphorsTotal;
	
	public void initialize(UimaContext context) throws ResourceInitializationException{
		super.initialize(context);
		anaphorsTotal = 0;
		correctAnaphorsTotal = 0;
	}
	
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		this.aJCas = aJCas;
		stdCorefChains = JCasUtil.select(aJCas, CoreferenceChain.class);
		anaphoras = JCasUtil.select(aJCas, Anaphora.class);
		int correct = 0;
		int total = 0;
		for(Anaphora a : anaphoras){
			boolean hasCorrectAnte = false;
			CoreferenceChain chainOfAnaphora = null;

			for(CoreferenceChain c : stdCorefChains){
				if(chainOfAnaphora != null)
					break;
				CoreferenceLink corefLinkOld = c.getFirst();		
				if(corefLinkOld.getBegin() <= a.getBegin() && corefLinkOld.getEnd() >= a.getEnd()){
					chainOfAnaphora = c;
					break;
				}

				while(corefLinkOld.getNext() != null){
					CoreferenceLink corefLinkNew = corefLinkOld.getNext();
					
					if(corefLinkNew.getBegin() <= a.getBegin() && corefLinkNew.getEnd() >= a.getEnd()){
						chainOfAnaphora = c;
						if(corefLinkOld.getBegin() <= a.getAntecedent().getBegin() && corefLinkOld.getEnd() >= a.getAntecedent().getEnd()){
							hasCorrectAnte = true;
						}
						
						break;
					}
					corefLinkOld = corefLinkNew;
				}
			}
			
			if(hasCorrectAnte)
				correct++;
			total++;
		}
		correctAnaphorsTotal += correct;
		anaphorsTotal += total;
		
		System.out.println(correct + " of " + total);
		System.out.println(((float)correct / (float)total )* 100f + " %");
		System.out.println("----------------");
	}
	
	@Override
	public void collectionProcessComplete(){
		float rel = 0f;	
		if(anaphorsTotal != 0){
			rel = ((float)correctAnaphorsTotal / (float)anaphorsTotal) * 100f;
		} 
		System.out.println("Total Accuracy: " + rel + " % ");
	}
	
	

}
