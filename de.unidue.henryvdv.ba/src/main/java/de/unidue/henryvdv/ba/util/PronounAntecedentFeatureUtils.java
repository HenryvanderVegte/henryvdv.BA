package de.unidue.henryvdv.ba.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.uima.jcas.tcas.Annotation;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.dependency.Dependency;
import de.unidue.henryvdv.ba.type.Quotation;

public class PronounAntecedentFeatureUtils {
	
	private static String[] pluralPronouns = {"themselves","their","they"};
	
	public static String getPrepositionText(Annotation anno, Collection<Dependency> dependencies){
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
	

	
	public static Token getParent(Annotation anno, Collection<Dependency> dependencies){
		for(Dependency d : dependencies){
			if(d.getDependent().getBegin() == anno.getBegin() && 
					d.getDependent().getEnd() == anno.getEnd()){
				return d.getGovernor();
			}
		}
		return null;
	}
	
	public static boolean prepositionalParallel(Annotation anaphora, Annotation antecedent, Collection<Token> tokens){
		String prepText = getPrepositionText(anaphora);		
		List<Token> antecedentTokens = AnnotationUtils.getCoveredTokens(antecedent.getBegin(), antecedent.getEnd(), tokens);		
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
	
	public static boolean relationMatch(Annotation anno1, Annotation anno2, Collection<Dependency> dependencies){
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

	public static boolean parentCategoryMatch(Annotation anaphora, Annotation antecedent, Collection<Token> tokens, Collection<Dependency> dependencies){
		Token anaphoraParent = getParent(anaphora);	
		List<Token> anteTokens = AnnotationUtils.getCoveredTokens(antecedent.getBegin(), antecedent.getEnd(), tokens);
		boolean value = false;
		for(Token t : anteTokens){
			Token anteParent = getParent(t);
			if(anaphoraParent.getPos().getPosValue().equals(anteParent.getPos().getPosValue())){
				value = true;
			}
		}
		return value;
	}

	public static boolean isInQuotes(Annotation anno, Collection<Quotation> quotes){
		boolean value = false;
		for(Quotation q : quotes){
			if(q.getBegin() <= anno.getBegin() && q.getEnd() >= anno.getEnd()){
				value = true;
			}
		}	
		return value;
	}
	
	public static boolean isBothSingular(Annotation anaphora, Annotation antecedent, Collection<Token> tokens){
		boolean isSingularA = true;
		if(Arrays.asList(pluralPronouns).contains(anaphora.getCoveredText().toLowerCase())){
			isSingularA = false;
		}
		boolean isSingularB = false;
		boolean valueSet = false;
		List<Token> covTokens = AnnotationUtils.getCoveredTokens(antecedent.getBegin(), antecedent.getEnd(), tokens);
		for(Token t : covTokens){
			if(t.getPos().getPosValue().equalsIgnoreCase("NN") || t.getPos().getPosValue().equalsIgnoreCase("NNP")){
				isSingularB = true;
				valueSet = true;
			}
			if(t.getPos().getPosValue().equalsIgnoreCase("NNS") || t.getPos().getPosValue().equalsIgnoreCase("NNPS")){
				valueSet = true;
			}
		}
		if(isSingularA && isSingularB && valueSet)
			return true;
		
		return false;
	}
	
	public static boolean isBothPlural(Annotation anaphora, Annotation antecedent, Collection<Token> tokens){
		boolean isSingularA = true;
		if(Arrays.asList(pluralPronouns).contains(anaphora.getCoveredText().toLowerCase())){
			isSingularA = false;
		}
		boolean isSingularB = true;
		boolean valueSet = false;
		List<Token> covTokens = AnnotationUtils.getCoveredTokens(antecedent.getBegin(), antecedent.getEnd(), tokens);
		for(Token t : covTokens){
			if(t.getPos().getPosValue().equalsIgnoreCase("NNS") || t.getPos().getPosValue().equalsIgnoreCase("NNPS")){
				isSingularB = false;
				valueSet = true;
			}
			if(t.getPos().getPosValue().equalsIgnoreCase("NN") || t.getPos().getPosValue().equalsIgnoreCase("NNP")){
				valueSet = true;
			}
		}
		if(!isSingularA && !isSingularB && valueSet)
			return true;
		
		return false;
	}
	
}
