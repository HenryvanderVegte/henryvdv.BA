package de.unidue.henryvdv.ba.modules;

import java.util.Collection;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import de.unidue.henryvdv.ba.type.Anaphora;
import de.unidue.henryvdv.ba.type.AntecedentFeatures;
import de.unidue.henryvdv.ba.type.PronounFeatures;
import de.unidue.henryvdv.ba.util.AntecedentFeatureUtils;
import de.unidue.henryvdv.ba.util.PronounFeatureUtils;

public class FeatureAnnotator_Pronoun extends JCasAnnotator_ImplBase {

	private JCas aJCas;
	private Collection<Anaphora> anaphoras;
	private PronounFeatureUtils pUtil;

	
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		this.aJCas = aJCas;
		anaphoras = JCasUtil.select(aJCas, Anaphora.class);
		pUtil = new PronounFeatureUtils();
		
		for(Anaphora anaphora : anaphoras){
			PronounFeatures a = new PronounFeatures(aJCas);
			anaphora.setPronounFeatures(a);
			pUtil.annotateFeatures(anaphora);
		}
	}
}
