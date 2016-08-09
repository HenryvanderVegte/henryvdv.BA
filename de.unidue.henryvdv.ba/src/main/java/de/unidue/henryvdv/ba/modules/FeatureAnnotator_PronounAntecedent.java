package de.unidue.henryvdv.ba.modules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.constituent.PP;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.dependency.Dependency;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.dependency.PREP;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.constituent.Constituent;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.constituent.NP;
import de.unidue.henryvdv.ba.type.Anaphora;
import de.unidue.henryvdv.ba.type.NegativeTrainingInstance;

public class FeatureAnnotator_PronounAntecedent extends JCasAnnotator_ImplBase {
	
	/************************************************
	 *  Annotates the Pronoun-Antecedent-Features   *
	 *  -Same Sentence (bool)
	 *  -In Previous Sentence (bool)
	 *  
	 * 
	 * 
	 ************************************************/
	
	private JCas aJCas;
	private Collection<NegativeTrainingInstance> negInstances;
	private Collection<Anaphora> anaphoras;
	private Collection<Token> tokens;
	private Collection<Sentence> sentences;
	private Collection<Dependency> dependencies;
	
	
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		this.aJCas = aJCas;
		anaphoras = JCasUtil.select(aJCas, Anaphora.class);
		negInstances = JCasUtil.select(aJCas, NegativeTrainingInstance.class);
		sentences = JCasUtil.select(aJCas, Sentence.class);
		dependencies = JCasUtil.select(aJCas, Dependency.class);
		tokens = JCasUtil.select(aJCas, Token.class);
		annotateInSameSentenceFeature();
		annotateIntraSentenceDiffFeature();
		annotateInPreviousSentenceFeature();
		annotateInterSentenceDiffFeature();
		annotatePrepositionalParallelFeature();
	}

	public void annotateInSameSentenceFeature(){
		for(Anaphora a : anaphoras){
			boolean value = (getSentenceNr(a.getBegin()) == getSentenceNr(a.getAntecedent().getBegin()));
			a.setP_A_InSameSentence(value);
		}
		for(NegativeTrainingInstance n : negInstances){
			boolean value = (getSentenceNr(n.getBegin()) == getSentenceNr(n.getAnaphora().getBegin()));
			n.setP_A_InSameSentence(value);
		}	
	}
	
	public void annotateInPreviousSentenceFeature(){
		for(Anaphora a : anaphoras){
			boolean value = (getSentenceNr(a.getBegin()) -1 == getSentenceNr(a.getAntecedent().getBegin()));
			a.setP_A_InPreviousSentence(value);
		}
		for(NegativeTrainingInstance n : negInstances){
			boolean value = (getSentenceNr(n.getBegin()) == getSentenceNr(n.getAnaphora().getBegin()) -1 );
			n.setP_A_InPreviousSentence(value);
		}	
	}
	
	public void annotateInterSentenceDiffFeature(){
		for(Anaphora a : anaphoras){
			int anaphoraS = getSentenceNr(a.getBegin());
			int antecedentS = getSentenceNr(a.getAntecedent().getBegin());
			float value = (anaphoraS - antecedentS)/ 50.0f;
			a.setP_A_InterSentenceDiff(value);
		}
		for(NegativeTrainingInstance n : negInstances){
			int anaphoraS = getSentenceNr(n.getAnaphora().getBegin());
			int negS = getSentenceNr(n.getBegin());
			float value = (anaphoraS - negS)/ 50.0f;
			n.setP_A_InterSentenceDiff(value);
		}
	}
	
	public void annotateIntraSentenceDiffFeature(){
		for(Anaphora a : anaphoras){
			float value = (a.getBegin() - a.getAntecedent().getBegin())/ 50.0f;
			a.setP_A_IntraSentenceDiff(value);
		}
		for(NegativeTrainingInstance n : negInstances){
			float value = (n.getAnaphora().getBegin() - n.getBegin())/ 50.0f;
			n.setP_A_IntraSentenceDiff(value);
		}
	}
	
	public void annotatePrepositionalParallelFeature(){	
		Collection<Constituent> c = JCasUtil.select(aJCas, Constituent.class);
		for(Constituent co : c){
			System.out.println("CO : " + co.getCoveredText() + " Type: " +  co.getConstituentType());
		}
		/*
		for(Dependency d : dependencies){
			System.out.println("Dependency: " + d.getDependent().getCoveredText() + "  Governor: " + d.getGovernor().getCoveredText() + " Type: " + d.getDependencyType());
		}
		*/
		System.out.println("///////////////////////////////////");
		for(Anaphora a : anaphoras){
			String prepText = getPrepositionText(a);
			/*
			if(prepText != null){
		     	System.out.println("Prep: " + prepText + " A: " + a.getCoveredText());
			} else {
				System.out.println("No Prep. " + a.getCoveredText() );
			}
			
			int nr = getTokenNr(a);
			Token t = getToken(nr -1);
			
			if(t == null){
				System.out.println("Pronoun: " + a.getCoveredText());
			} else {
				System.out.println("Pronoun: " + a.getCoveredText() + "   Before: " + t.getCoveredText() + "["+ t.getPos().getPosValue() + "]");
			}
			*/
			//List<Token> anteTokens = getCoveredTokens(a.getAntecedent().getBegin(), a.getAntecedent().getEnd());
			
		}
		
		
		/*
		for(Anaphora a : anaphoras){		
			List<Token> covTokens = getCoveredTokens(a.getAntecedent().getBegin(),a.getAntecedent().getEnd());
			boolean r = containsWord(a.getCoveredText(), covTokens);		
			a.setP_A_PrepositionalParallel(r);		
		}
		for(NegativeTrainingInstance n : negInstances){
			List<Token> covTokens = getCoveredTokens(n.getBegin(),n.getEnd());
			boolean r = containsWord(n.getAnaphora().getCoveredText(), covTokens);			
			n.setP_A_PrepositionalParallel(r);
		}
		*/
		
		
		
	}
	
	
	private int getSentenceNr(int begin){
		int sentenceNr = 1;
		for(Sentence s : sentences){
			if(s.getEnd() > begin){
				break;
			}
			sentenceNr++;
		}	
		return sentenceNr;
	}
	
	private boolean containsWord(String word, List<Token> tokens){
		for(int i = 0; i < tokens.size(); i++){
			if(tokens.get(i).getCoveredText().toLowerCase().equals(word.toLowerCase())){
				return true;
			}
		}
		return false;
	}
	
	private List<Token> getCoveredTokens(int begin, int end){
		List<Token> coveredTokens = new ArrayList<Token>();
		Collection<Token> tokens = JCasUtil.select(aJCas, Token.class);
		
		for(Token t : tokens){
			if(t.getBegin() >= begin && t.getEnd() <= end){
				coveredTokens.add(t);
			}
		}
		return coveredTokens;
	}
	
	private String getPrepositionText(Annotation anno){
		Token govToken = null;
		for(Dependency d : dependencies){
			if(d.getDependent().getBegin() == anno.getBegin() && 
					d.getDependent().getEnd() == anno.getEnd()){
				govToken = d.getGovernor();
			}
		}
		if(govToken == null)
			return null;
		for(Dependency d : dependencies){
			if(d.getGovernor() == govToken && d.getDependencyType().contains("prep_")){
				return d.getDependencyType();
			}
		}		
		return null;
	}
	
	private int getTokenNr(Annotation anno){
		int i = 1;
		for(Token t : tokens){
			if(anno.getBegin() <= t.getBegin()){
				break;
			}
			i++;
		}
		return i;
	}
	
	private Token getToken(int nr){
		int i = 1;
		for(Token t : tokens){
			if(i == nr){
				return t;
			}
			i++;
		}
		return null;
	}
}

