package de.unidue.henryvdv.ba.modules;

import java.util.Collection;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.unidue.henryvdv.ba.type.Anaphora;
import de.unidue.henryvdv.ba.type.Quotation;

public class QuotationAnnotator  extends JCasAnnotator_ImplBase{

	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {	
		int quotBegin = 0;
		int quotEnd = 0;
		Collection<Token> tokens = JCasUtil.select(aJCas, Token.class);
		for(Token t : tokens){
			if(t.getCoveredText().contains("``") || t.getCoveredText().contains("''")|| t.getCoveredText().contains("\"")){
				if(quotBegin == 0){
					quotBegin = t.getBegin();				
				} else {
					quotEnd = t.getEnd();
					Quotation q = new Quotation(aJCas, quotBegin, quotEnd);
					q.addToIndexes();
					quotBegin = 0;
					quotEnd = 0;
				}
			}
		}
	}

}
