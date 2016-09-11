package de.unidue.henryvdv.ba.modules;

import java.util.Collection;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import de.unidue.henryvdv.ba.type.Anaphora;
import de.unidue.henryvdv.ba.type.AntecedentFeatures;
import de.unidue.henryvdv.ba.type.PronounFeatures;
import de.unidue.henryvdv.ba.util.FeatureUtils_Antecedent;
import de.unidue.henryvdv.ba.util.FeatureUtils_Pronoun;

public class FeatureAnnotator_Pronoun extends JCasAnnotator_ImplBase {

	private JCas aJCas;
	private Collection<Anaphora> anaphoras;
	private FeatureUtils_Pronoun pUtil;

	
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		this.aJCas = aJCas;
		anaphoras = JCasUtil.select(aJCas, Anaphora.class);
		pUtil = new FeatureUtils_Pronoun();
		
		for(Anaphora anaphora : anaphoras){
			PronounFeatures a = new PronounFeatures(aJCas);
			anaphora.setPronounFeatures(a);
			pUtil.annotateFeatures(anaphora);
		}
	}
}
