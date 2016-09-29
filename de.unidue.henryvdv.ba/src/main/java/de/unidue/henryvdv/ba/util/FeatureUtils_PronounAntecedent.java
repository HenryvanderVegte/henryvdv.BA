package de.unidue.henryvdv.ba.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

import com.google.common.reflect.Parameter;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.dependency.Dependency;
import de.unidue.henryvdv.ba.param.Parameters;
import de.unidue.henryvdv.ba.type.Anaphora;
import de.unidue.henryvdv.ba.type.Antecedent;
import de.unidue.henryvdv.ba.type.Quotation;

public class FeatureUtils_PronounAntecedent {
	
	private Collection<Token> tokens;
	private Collection<Sentence> sentences;
	private Collection<Dependency> dependencies;
	private Collection<Quotation> quotes;
	
	public enum Number {
	    singular, plural, unknown
	}
	
	public FeatureUtils_PronounAntecedent(JCas aJCas){
		sentences = JCasUtil.select(aJCas, Sentence.class);
		dependencies = JCasUtil.select(aJCas, Dependency.class);
		tokens = JCasUtil.select(aJCas, Token.class);
		quotes = JCasUtil.select(aJCas, Quotation.class);
	}
	
	
	public void annotateFeatures(Anaphora a){
		//In Same Sentence:
		a.getPronounAntecedentFeatures().setP_A_InSameSentence(antecedentInSameSentence(a));
		//In Previous Sentence:
		a.getPronounAntecedentFeatures().setP_A_InPreviousSentence(antecedentInPreviousSentence(a));
		//Inter-Sentence Diff:
		a.getPronounAntecedentFeatures().setP_A_InterSentenceDiff(interSentenceDiff(a));
		//Intra-Sentence Diff
		a.getPronounAntecedentFeatures().setP_A_IntraSentenceDiff(intraSentenceDiff(a));
		//Prepositional Parallel
		a.getPronounAntecedentFeatures().setP_A_PrepositionalParallel(prepositionalParallel(a, a.getAntecedent()));
		//Parent Category Match
		a.getPronounAntecedentFeatures().setP_A_ParentCatMatch(parentCategoryMatch(a, a.getAntecedent()));
		//Parent Word Match
		a.getPronounAntecedentFeatures().setP_A_ParentWordMatch(parentWordMatch(a));
		//Quotation Situation
		a.getPronounAntecedentFeatures().setP_A_QuotationSituation(quotationSituation(a));
		//Singular Match
		a.getPronounAntecedentFeatures().setP_A_SingularMatch(isBothSingular(a));
		//Plural Match
		a.getPronounAntecedentFeatures().setP_A_PluralMatch(isBothPlural(a));
	}
	
	public boolean antecedentInSameSentence(Anaphora a){
		boolean value = (getSentenceNr(a.getBegin()) == getSentenceNr(a.getAntecedent().getBegin()));
		return value;
	}
	
	public boolean antecedentInPreviousSentence(Anaphora a){
		boolean value = (getSentenceNr(a.getBegin()) -1 == getSentenceNr(a.getAntecedent().getBegin()));
		return value;
	}
	
	public float interSentenceDiff(Anaphora a){
		int anaphoraS = getSentenceNr(a.getBegin());
		int antecedentS = getSentenceNr(a.getAntecedent().getBegin());
		float value = (anaphoraS - antecedentS)/ 10.0f;
		return value;
	}
	
	public float intraSentenceDiff(Anaphora a){
		int dist = 0;
		for(Token t : tokens){
			if(t.getBegin() > a.getAntecedent().getBegin() && t.getBegin() < a.getBegin())
				dist++;
		}
		float value = (float)dist / 100.0f;
		return value;
	}
	
	public boolean parentWordMatch(Anaphora a){
		Token anaphoraparent = getParent(a);
		if(anaphoraparent == null)
			return false;
		String anaphoraParentString = anaphoraparent.getCoveredText();
		List<Token> anteTokens = getCoveredTokens(a.getAntecedent().getBegin(), a.getAntecedent().getEnd());					
		boolean value = false;
		for(Token t : anteTokens){
			Token tParent = getParent(t);
			if(tParent == null)
				continue;			
			String tParentString = tParent.getCoveredText();
			if(anaphoraParentString.equalsIgnoreCase(tParentString)){
				value = true;
			}
		}
		return value;
	}
	
	public boolean quotationSituation(Anaphora a){
		boolean valueA = isInQuotes(a);
		boolean valueB = isInQuotes(a.getAntecedent());
		boolean value = false;
		if(valueA == valueB){
			value = true;
		}
		return value;
	}
	
	public String getPrepositionText(Annotation anno){
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
			if(d.getGovernor() == govToken && d.getDependencyType().equals("case")){
				return d.getCoveredText();
			}
		}		
		return null;
	}
	

	
	public Token getParent(Annotation anno){
		for(Dependency d : dependencies){
			if(d.getDependent().getBegin() == anno.getBegin() && 
					d.getDependent().getEnd() == anno.getEnd()){
				return d.getGovernor();
			}
		}
		return null;
	}
	
	public boolean prepositionalParallel(Annotation anaphora, Annotation antecedent){
		String prepText = getPrepositionText(anaphora);		
		List<Token> antecedentTokens = getCoveredTokens(antecedent.getBegin(), antecedent.getEnd());		
		boolean value = false;
		if(prepText != null){
			for(Token token : antecedentTokens){
				String currentText = getPrepositionText(token);
				if(currentText != null){
					if(currentText.equalsIgnoreCase(prepText)){
						value = true;
					}
				}
			}
		}	
		return value;
	}
	
	public boolean relationMatch(Annotation anno1, Annotation anno2){
		String type1 = null;
		String type2 = null;
		System.out.println("1: " + anno1.getCoveredText());
		System.out.println("2: " + anno2.getCoveredText());
		
		for(Dependency d : dependencies){
			if(d.getBegin()== anno1.getBegin() && d.getEnd() == anno1.getEnd()){
				type1 = d.getDependencyType();
				System.out.println("Type1: " + type1);
			}
			if(d.getBegin() == anno2.getBegin() && d.getEnd() ==anno2.getEnd()){
				type2 = d.getDependencyType();
				System.out.println("Type1: " + type2);
			}
		}
		
		if(type1 != null && type2 != null){
			boolean value = type1.equals(type2);
			return value;
		}		
		return false;		
	}

	public boolean parentCategoryMatch(Annotation anaphora, Annotation antecedent){
		Token anaphoraParent = getParent(anaphora);	
		if(anaphoraParent == null)
			return false;
		List<Token> anteTokens = getCoveredTokens(antecedent.getBegin(), antecedent.getEnd());
		boolean value = false;
		for(Token t : anteTokens){
			Token anteParent = getParent(t);		
			if(anteParent != null && anaphoraParent.getPos().getPosValue().equals(anteParent.getPos().getPosValue())){
				value = true;
			}
		}
		return value;
	}

	public boolean isInQuotes(Annotation anno){
		boolean value = false;
		for(Quotation q : quotes){
			if(q.getBegin() <= anno.getBegin() && q.getEnd() >= anno.getEnd()){
				value = true;
			}
		}	
		return value;
	}
	
	public boolean isBothSingular(Anaphora anaphora){
		Number anaphoraNumber = Number.unknown;
		Number antecedentNumber = Number.unknown;
		
		if(Arrays.asList(Parameters.pluralPronouns).contains(anaphora.getCoveredText().toLowerCase())){
			anaphoraNumber = Number.plural;
		} else {
			anaphoraNumber = Number.singular;
		}
		
		if(Arrays.asList(Parameters.allPronouns).contains(anaphora.getAntecedent().getCoveredText().toLowerCase())){			
			if(Arrays.asList(Parameters.pluralPronouns).contains(anaphora.getAntecedent().getCoveredText().toLowerCase())){
				antecedentNumber = Number.plural;
			} else {
				antecedentNumber = Number.singular;
			}
		}
		
		if(antecedentNumber == Number.unknown){
			antecedentNumber = getAntecedentNumber(anaphora.getAntecedent());
		}
		
		if(anaphoraNumber == Number.singular && antecedentNumber == Number.singular){
			return true;
		}
	
		return false;

	}
	
	public boolean isBothPlural(Anaphora anaphora){
		Number anaphoraNumber = Number.unknown;
		Number antecedentNumber = Number.unknown;
		
		if(Arrays.asList(Parameters.pluralPronouns).contains(anaphora.getCoveredText().toLowerCase())){
			anaphoraNumber = Number.plural;
		} else {
			anaphoraNumber = Number.singular;
		}
		
		if(Arrays.asList(Parameters.allPronouns).contains(anaphora.getAntecedent().getCoveredText().toLowerCase())){			
			if(Arrays.asList(Parameters.pluralPronouns).contains(anaphora.getAntecedent().getCoveredText().toLowerCase())){
				antecedentNumber = Number.plural;
			} else {
				antecedentNumber = Number.singular;
			}
		}
		
		if(antecedentNumber == Number.unknown){
			antecedentNumber = getAntecedentNumber(anaphora.getAntecedent());
		}
		
		if(anaphoraNumber == Number.plural && antecedentNumber == Number.plural){
			System.out.println("Both plural:");
			System.out.println(anaphora.getCoveredText());
			System.out.println(anaphora.getAntecedent().getCoveredText());
			
			return true;
		}
	
		return false;
	}
	
	public int getSentenceNr(int begin){
		return AnnotationUtils.getSentenceNr(begin, sentences);
	}
	
	public List<Token> getCoveredTokens(int begin, int end){
		return AnnotationUtils.getCoveredTokens(begin, end, tokens);
	}
	
	public List<Token> getCoveredTokens(Annotation anno){
		return AnnotationUtils.getCoveredTokens(anno.getBegin(), anno.getEnd(), tokens);
	}
	
	public Number getAntecedentNumber(Antecedent antecedent){
		Number returnNumber = Number.unknown;
		
		List<Token> anteTokens = getCoveredTokens(antecedent);
		int nouns = 0;
		for(Token t : anteTokens){
			if(isNoun(t))
				nouns++;
		}
		if(nouns == 0)
			return Number.unknown;
		
		if(nouns == 1){
			for(Token t : anteTokens){
				if(isSingularNoun(t))
					returnNumber = Number.singular;
				if(isPluralNoun(t))
					returnNumber = Number.plural;
			}
		} else {
			Token relevantToken = null;
			for(Token t : anteTokens){
				if(isNoun(t)){
					Token parent = AnnotationUtils.getParent(t, dependencies);
					if(parent != null && isNoun(parent) && parent.getBegin() < antecedent.getEnd()){
						relevantToken = parent;
						break;
					}
				}
			}
			if(relevantToken == null){
				for(Token t : anteTokens){
					if(isNoun(t)){
						relevantToken = t;
					}
				}
			}		
			if(relevantToken != null){
				if(isSingularNoun(relevantToken))
					returnNumber = Number.singular;
				if(isPluralNoun(relevantToken))
					returnNumber = Number.plural;
			}
			
		}
		return returnNumber;
		
	}
	
	
	public boolean isNoun(Token token){
		String posValue = token.getPos().getPosValue();
		if(posValue.equalsIgnoreCase("NN") || 
				posValue.equalsIgnoreCase("NNP") ||
				posValue.equalsIgnoreCase("NNS") ||
				posValue.equalsIgnoreCase("NNPS"))
		{
			return true;
		}
		return false;
	}
	
	public boolean isSingularNoun(Token token){
		String posValue = token.getPos().getPosValue();
		if(posValue.equalsIgnoreCase("NN") || 
				posValue.equalsIgnoreCase("NNP"))
		{
			return true;
		}
		return false;
	}
	
	public boolean isPluralNoun(Token token){
		String posValue = token.getPos().getPosValue();
		if(posValue.equalsIgnoreCase("NNS") || 
				posValue.equalsIgnoreCase("NNPS"))
		{
			return true;
		}
		return false;
	}
}
