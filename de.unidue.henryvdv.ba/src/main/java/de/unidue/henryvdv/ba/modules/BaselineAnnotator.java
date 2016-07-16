package de.unidue.henryvdv.ba.modules;

import java.util.Collection;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;

public class BaselineAnnotator 
extends JCasAnnotator_ImplBase
{

	private JCas aJcas;
	
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		this.aJcas = aJCas;
		/*
		Collection<Token> tokens = JCasUtil.select(aJCas, Token.class);
		for(Token t : tokens){
			//System.out.println(t.getPos().getPosValue());
			if(t.getPos().getPosValue().equals("PRP")){
				
			}
		}
		*/
	}

}
