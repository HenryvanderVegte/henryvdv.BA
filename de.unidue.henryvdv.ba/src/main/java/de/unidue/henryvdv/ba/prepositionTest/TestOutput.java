package de.unidue.henryvdv.ba.prepositionTest;

import java.util.Collection;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.dependency.Dependency;

public class TestOutput	extends JCasAnnotator_ImplBase
{

	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		//Document text:
		System.out.println("Document text: " + aJCas.getDocumentText());
		
		//Tokens + POS
		System.out.print("Tokens: ");
		Collection<Token> tokens = JCasUtil.select(aJCas, Token.class);
		for(Token t : tokens){
			System.out.print(t.getCoveredText() + " [" + t.getPos().getPosValue() + "] ");
		}
		System.out.println("");
		
		//Dependencies:		
		Collection<Dependency> dependencies = JCasUtil.select(aJCas, Dependency.class);
		for(Dependency d : dependencies){
			System.out.println("Dependent: " + d.getDependent().getCoveredText() + 
								" -- Governor: " + d.getGovernor().getCoveredText() +
								" -- Type: " + d.getDependencyType());
		}
	}

}
