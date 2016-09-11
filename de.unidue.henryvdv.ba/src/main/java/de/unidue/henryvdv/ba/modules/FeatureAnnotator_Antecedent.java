package de.unidue.henryvdv.ba.modules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;

import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.CONJ;
import de.tudarmstadt.ukp.dkpro.core.api.ner.type.NamedEntity;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.dependency.Dependency;
import de.unidue.henryvdv.ba.type.Anaphora;
import de.unidue.henryvdv.ba.type.AntecedentFeatures;
import de.unidue.henryvdv.ba.type.Quotation;
import de.unidue.henryvdv.ba.util.AnnotationUtils;
import de.unidue.henryvdv.ba.util.FeatureUtils_Antecedent;
import de.unidue.henryvdv.ba.util.FeatureUtils_PronounAntecedent;

public class FeatureAnnotator_Antecedent extends JCasAnnotator_ImplBase {

	/************************************************
	 *  Annotates the Antecedent-Features   		*
	 *  -Antecedent Frequency (float)				*
	 *  -Subject (bool)								*
	 *  -Object (bool)								*
	 *  -Predicate (bool)							*
	 *  -Pronominal (bool)							*
	 *  -Head-Word Emphasis (bool)					*
	 *  -Conjunction (bool)							*
	 *  -Prenominal Modifier (bool)					*
	 *  -Organization (bool)						*
	 *  -Person (bool)								*
	 *  											*
	 *  Missing:									*
	 *  ...											*
	 * 												*
	 ************************************************/
	
	
	private JCas aJCas;
	private Collection<Anaphora> anaphoras;
	private FeatureUtils_Antecedent aUtil;

	

	
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		this.aJCas = aJCas;
		anaphoras = JCasUtil.select(aJCas, Anaphora.class);
		aUtil = new FeatureUtils_Antecedent(aJCas);
		
		for(Anaphora anaphora : anaphoras){
			AntecedentFeatures a = new AntecedentFeatures(aJCas);
			anaphora.setAntecedentFeatures(a);
			aUtil.annotateFeatures(anaphora);
		}
		
	}
	



}
