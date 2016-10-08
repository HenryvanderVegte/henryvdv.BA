package de.unidue.henryvdv.ba.modules;

import java.util.Collection;
import java.util.List;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.FSArray;
import org.apache.uima.jcas.tcas.Annotation;

import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.PennTree;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.constituent.Constituent;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.constituent.NP;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.dependency.Dependency;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;

import de.unidue.henryvdv.ba.type.Anaphora;
import de.unidue.henryvdv.ba.type.PronounAntecedentFeatures;
import de.unidue.henryvdv.ba.type.Quotation;
import de.unidue.henryvdv.ba.util.AnnotationUtils;
import de.unidue.henryvdv.ba.util.FeatureUtils_PronounAntecedent;

public class FeatureAnnotator_PronounAntecedent extends JCasAnnotator_ImplBase {
		
	private JCas aJCas;
	private Collection<Anaphora> anaphoras;
	
	private FeatureUtils_PronounAntecedent paUtil;
	
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		this.aJCas = aJCas;
		anaphoras = JCasUtil.select(aJCas, Anaphora.class);
		
		paUtil = new FeatureUtils_PronounAntecedent(aJCas);
		
		for(Anaphora anaphora : anaphoras){
			PronounAntecedentFeatures a = new PronounAntecedentFeatures(aJCas);
			anaphora.setPronounAntecedentFeatures(a);
			paUtil.annotateFeatures(anaphora);

		}
	}

}

