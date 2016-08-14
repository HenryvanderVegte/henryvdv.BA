package de.unidue.henryvdv.ba.modules;

import java.util.Collection;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.dependency.Dependency;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;

import de.unidue.henryvdv.ba.type.Anaphora;
import de.unidue.henryvdv.ba.type.PronounAntecedentFeatures;
import de.unidue.henryvdv.ba.type.Quotation;
import de.unidue.henryvdv.ba.util.PronounAntecedentFeatureUtils;

public class FeatureAnnotator_PronounAntecedent extends JCasAnnotator_ImplBase {
	
	/************************************************
	 *  Annotates the Pronoun-Antecedent-Features   *
	 *  -Same Sentence (bool)						*
	 *  -Intra-Sentence Difference (float)			*
	 *  -In Previous Sentence (bool)				*
	 *  -Inter-Sentence Difference (float)			*
	 *  -Prepositional Parallel (bool)				*
	 *  -Parent Category Match (bool)				*
	 *  -Parent Word Match (bool)					*
	 *  -Quotation Situation (bool)					*
	 *  -Singular Match (bool)						*
	 *  -Plural Match (bool)						*
	 *  											*
	 *  Missing:									*
	 *  -Binding Theory								*
	 *  -Reflexive Subj. Match						*
	 *  -Relation-Match								*
	 *  -Parent Relation Match						*
	 *  -MI Value									*
	 *  -MI Available								*
	 * 												*
	 ************************************************/
	
	private JCas aJCas;
	private Collection<Anaphora> anaphoras;

	
	private PronounAntecedentFeatureUtils paUtil;
	
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		this.aJCas = aJCas;
		anaphoras = JCasUtil.select(aJCas, Anaphora.class);
		
		paUtil = new PronounAntecedentFeatureUtils(aJCas);
		
		for(Anaphora anaphora : anaphoras){
			PronounAntecedentFeatures a = new PronounAntecedentFeatures(aJCas);
			anaphora.setPronounAntecedentFeatures(a);
			paUtil.annotateFeatures(anaphora);
		}
	}
	



}

