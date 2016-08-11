package de.unidue.henryvdv.ba.modules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.constituent.NP;
import de.unidue.henryvdv.ba.type.Anaphora;
import de.unidue.henryvdv.ba.type.Antecedent;

public class NegativeTrainingInstanceAnnotator extends JCasAnnotator_ImplBase{

	private JCas aJCas;
	
	Collection<Anaphora> anaphoras;
	Collection<NP> nps;
	
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		this.aJCas = aJCas;
		anaphoras = JCasUtil.select(aJCas, Anaphora.class);
		nps = JCasUtil.select(aJCas, NP.class);
		generateNegativeTrainingInstances();
	}

	public void generateNegativeTrainingInstances(){
		List<Anaphora> negAnaphoras = new ArrayList<Anaphora>();
		for(Anaphora an : anaphoras){
			for(NP np : getNPSBetween(an, an.getAntecedent())){
				
				Anaphora negInst = new Anaphora(aJCas, an.getBegin(), an.getEnd());				
				Antecedent antecedent = new Antecedent(aJCas, np.getBegin(), np.getEnd());				
				negInst.setAntecedent(antecedent);
				negInst.setHasCorrectAntecedent(false);
				negAnaphoras.add(negInst);
			}
		}
		for(Anaphora a : negAnaphoras){
			a.addToIndexes();
		}
		
	}
	
	private List<NP> getNPSBetween(Anaphora an, Antecedent ant){
		List<NP> npsBetween = new ArrayList<NP>();
		
		for(NP np : nps){
			if(np.getBegin() > ant.getEnd() && np.getEnd() > ant.getEnd() &&
				np.getBegin() < an.getBegin() && np.getEnd() < an.getBegin()){
					
					npsBetween.add(np);
				
				}
		}
		//Delete nps which cover other nps
		List<NP> fixedNpsBetween = new ArrayList<NP>();
		for(NP np1 : npsBetween){
			boolean addIt = true;
			for(NP np2 : npsBetween){
				if(np1 != np2){
					if(np1.getBegin() <=  np2.getBegin() && np1.getEnd() >= np2.getEnd()){
						addIt = false;
					}
				}
			}
			if(addIt){
				fixedNpsBetween.add(np1);
			}
		}
		
		return fixedNpsBetween;
	}
	
}
