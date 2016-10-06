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
		
		Collection<Constituent> constituents = JCasUtil.select(aJCas, Constituent.class);
		Collection<Token> tokens = JCasUtil.select(aJCas, Token.class);
		Collection<Dependency> dependencies = JCasUtil.select(aJCas, Dependency.class);
		/*
		for(Constituent c : constituents){
			System.out.println(c.getCoveredText() + "   " + c.getConstituentType());
		}
		Collection<NP> nps = JCasUtil.select(aJCas, NP.class);
		
		System.out.println("*******************");
		for(NP c : nps){
			System.out.println(c.getCoveredText());
		}
		
		System.out.println("*******************");
		for(Constituent c : constituents){
		
			if(c.getConstituentType().equals("S")){
				System.out.println("Sentence:" + c.getCoveredText());
				for(int i = 0; i < c.getChildren().size(); i++){
					System.out.println(c.getChildren(i).getCoveredText());
				}
			}
			
			//System.out.println(c.getCoveredText() + "   " + c.getConstituentType());
		}
		System.out.println("*******************");
*/
		for(Anaphora anaphora : anaphoras){
			PronounAntecedentFeatures a = new PronounAntecedentFeatures(aJCas);
			anaphora.setPronounAntecedentFeatures(a);
			paUtil.annotateFeatures(anaphora);
		}
	}
	
	public Annotation getBindingDomain(Annotation a){
		Collection<Constituent> constituents = JCasUtil.select(aJCas, Constituent.class);
		Collection<Token> tokens = JCasUtil.select(aJCas, Token.class);	
		boolean anaphoraIsSubject = isSubject(AnnotationUtils.getCoveredToken(a, tokens));

		if(anaphoraIsSubject){
			Annotation smallest = null;
			int size = Integer.MAX_VALUE;
			for(Constituent c : constituents){			
				if(c.getConstituentType().equals("S") && c.getBegin() <= a.getBegin() && c.getEnd() >= a.getEnd()){
					if((c.getEnd() - c.getBegin()) < size ){
						size = c.getEnd() - c.getBegin();
						smallest = new Annotation(aJCas, c.getBegin(), c.getEnd());
					}
				
				}
			}
			System.out.println("Anaphora: " + a.getCoveredText());
			System.out.println("Smallest: " + smallest.getCoveredText());
		}
		
		return null;
	}

	private boolean isSubject(Token token){
		Collection<Dependency> dependencies = JCasUtil.select(aJCas, Dependency.class);
		for(Dependency d : dependencies){
			if(d.getDependent() == token && d.getDependencyType().contains("subj")){
				return true;
			}
		}	
		return false;
	}


}

