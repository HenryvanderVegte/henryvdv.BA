package de.unidue.henryvdv.ba.modules;

import java.util.Collection;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.dependency.Dependency;
import de.unidue.henryvdv.ba.type.Anaphora;
import de.unidue.henryvdv.ba.type.NegativeTrainingInstance;
import de.unidue.henryvdv.ba.type.Quotation;

public class FeatureAnnotator_Antecedent extends JCasAnnotator_ImplBase {

	private JCas aJCas;
	private Collection<NegativeTrainingInstance> negInstances;
	private Collection<Anaphora> anaphoras;
	private Collection<Token> tokens;
	private Collection<Sentence> sentences;
	private Collection<Dependency> dependencies;
	private Collection<Quotation> quotes;
	private String documentText;
	
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		this.aJCas = aJCas;
		anaphoras = JCasUtil.select(aJCas, Anaphora.class);
		negInstances = JCasUtil.select(aJCas, NegativeTrainingInstance.class);
		sentences = JCasUtil.select(aJCas, Sentence.class);
		dependencies = JCasUtil.select(aJCas, Dependency.class);
		tokens = JCasUtil.select(aJCas, Token.class);
		quotes = JCasUtil.select(aJCas, Quotation.class);
		annotateAntecedentFrequency();
	}

	public void annotateAntecedentFrequency(){
		for(Anaphora anaphora : anaphoras){
			System.out.println(anaphora.getAntecedent().getCoveredText());
		}
	}
	
	
}
