package de.unidue.henryvdv.ba.modules;

import java.util.Collection;
import java.util.List;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.CONJ;
import de.tudarmstadt.ukp.dkpro.core.api.ner.type.NamedEntity;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.dependency.Dependency;
import de.unidue.henryvdv.ba.type.Anaphora;
import de.unidue.henryvdv.ba.type.AntecedentFeatures;
import de.unidue.henryvdv.ba.type.Quotation;
import de.unidue.henryvdv.ba.util.AnnotationUtils;
import de.unidue.henryvdv.ba.util.AntecedentFeatureUtils;
import de.unidue.henryvdv.ba.util.PronounAntecedentFeatureUtils;

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
	private Collection<Token> tokens;
	private Collection<Sentence> sentences;
	private Collection<Dependency> dependencies;
	private Collection<NamedEntity> namedEntities;
	private String documentText;
	
	private AntecedentFeatureUtils aUtil;
	
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		this.aJCas = aJCas;
		anaphoras = JCasUtil.select(aJCas, Anaphora.class);
		sentences = JCasUtil.select(aJCas, Sentence.class);
		dependencies = JCasUtil.select(aJCas, Dependency.class);
		tokens = JCasUtil.select(aJCas, Token.class);
		namedEntities = JCasUtil.select(aJCas, NamedEntity.class);
		
		aUtil = new AntecedentFeatureUtils(tokens, sentences,dependencies,namedEntities);
		
		prepareAnnotations();
		annotateAntecedentFrequencyFeature();
		annotateSubjectFeature();
		annotateObjectFeature();
		annotatePredicateFeature();
		annotatePronounFeature();
		annotateHeadWordEmphasisFeature();
		annotateConjunctionFeature();
		annotatePrenominalModifierFeature();
		annotateOrganizationFeature();
		annotatePersonFeature();
	}

	public void annotateFeatures(Anaphora a){
		
	}
	
	public void prepareAnnotations(){
		for(Anaphora anaphora : anaphoras){
			AntecedentFeatures a = new AntecedentFeatures(aJCas);
			anaphora.setAntecedentFeatures(a);
		}
	}
	
	public void annotateAntecedentFrequencyFeature(){
		for(Anaphora anaphora : anaphoras){
			float value = aUtil.antecedentFrequency(anaphora);
			anaphora.getAntecedentFeatures().setA_AntecedentFrequency(value);
		}
	}
	
	public void annotateSubjectFeature(){
		for(Anaphora anaphora : anaphoras){
			boolean value = aUtil.subject(anaphora);
			anaphora.getAntecedentFeatures().setA_Subject(value);
		}
	}
	
	public void annotateObjectFeature(){
		for(Anaphora anaphora : anaphoras){
			boolean value = aUtil.object(anaphora);
			anaphora.getAntecedentFeatures().setA_Object(value);
		}
	}

	public void annotatePredicateFeature(){
		for(Anaphora anaphora : anaphoras){
			boolean value = aUtil.predicate(anaphora);
			anaphora.getAntecedentFeatures().setA_Predicate(value);
		}
	}
	
	public void annotatePronounFeature(){
		for(Anaphora anaphora : anaphoras){
			boolean value = aUtil.pronominal(anaphora);
			anaphora.getAntecedentFeatures().setA_Pronominal(value);
		}		
	}
	
	public void annotateHeadWordEmphasisFeature(){
		for(Anaphora anaphora : anaphoras){
			boolean value = aUtil.headWordEmphasis(anaphora);
			anaphora.getAntecedentFeatures().setA_HeadWordEmphasis(value);
		}		
	}

	public void annotateConjunctionFeature(){
		for(Anaphora anaphora : anaphoras){
			boolean value = aUtil.conjunction(anaphora);
			anaphora.getAntecedentFeatures().setA_Conjunction(value);
		}
	}
	
	public void annotatePrenominalModifierFeature(){
		for(Anaphora anaphora : anaphoras){
			boolean value = aUtil.prenominalModifier(anaphora);
			anaphora.getAntecedentFeatures().setA_PrenominalModifier(value);
		}
	}
	
	public void annotateOrganizationFeature(){
		for(Anaphora anaphora : anaphoras){
			boolean value = aUtil.organization(anaphora);
			anaphora.getAntecedentFeatures().setA_Org(value);
		}		
	}
	
	public void annotatePersonFeature(){
		for(Anaphora anaphora : anaphoras){
			boolean value = aUtil.person(anaphora);
			anaphora.getAntecedentFeatures().setA_Org(value);
		}
	}

}
