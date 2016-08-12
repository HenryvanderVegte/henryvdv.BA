package de.unidue.henryvdv.ba.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.uima.jcas.tcas.Annotation;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.dependency.Dependency;
import de.unidue.henryvdv.ba.type.Anaphora;
import de.unidue.henryvdv.ba.type.Quotation;

public class PronounAntecedentFeatureUtils {
	
	private Collection<Token> tokens;
	private Collection<Sentence> sentences;
	private Collection<Dependency> dependencies;
	private Collection<Quotation> quotes;
	
	private static String[] pluralPronouns = {"themselves","their","they"};
	
	public PronounAntecedentFeatureUtils(Collection<Token> tokens, 
										Collection<Sentence> sentences, Collection<Dependency> dependencies,
										Collection<Quotation> quotes){
		this.tokens = tokens;
		this.sentences = sentences;
		this.dependencies = dependencies;
		this.quotes = quotes;		
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
		a.getPronounAntecedentFeatures().setP_A_SingularMatch(isBothSingular(a, a.getAntecedent()));
		//Plural Match
		a.getPronounAntecedentFeatures().setP_A_PluralMatch(isBothPlural(a, a.getAntecedent()));
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
		float value = (anaphoraS - antecedentS)/ 50.0f;
		return value;
	}
	
	public float intraSentenceDiff(Anaphora a){
		float value = (a.getBegin() - a.getAntecedent().getBegin())/ 50.0f;
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
		List<Token> anteTokens = getCoveredTokens(antecedent.getBegin(), antecedent.getEnd());
		boolean value = false;
		for(Token t : anteTokens){
			Token anteParent = getParent(t);
			if(anaphoraParent.getPos().getPosValue().equals(anteParent.getPos().getPosValue())){
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
	
	public boolean isBothSingular(Annotation anaphora, Annotation antecedent){
		boolean isSingularA = true;
		if(Arrays.asList(pluralPronouns).contains(anaphora.getCoveredText().toLowerCase())){
			isSingularA = false;
		}
		boolean isSingularB = false;
		boolean valueSet = false;
		List<Token> covTokens = getCoveredTokens(antecedent.getBegin(), antecedent.getEnd());
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
	
	public boolean isBothPlural(Annotation anaphora, Annotation antecedent){
		boolean isSingularA = true;
		if(Arrays.asList(pluralPronouns).contains(anaphora.getCoveredText().toLowerCase())){
			isSingularA = false;
		}
		boolean isSingularB = true;
		boolean valueSet = false;
		List<Token> covTokens = getCoveredTokens(antecedent.getBegin(), antecedent.getEnd());
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
	
	public int getSentenceNr(int begin){
		return AnnotationUtils.getSentenceNr(begin, sentences);
	}
	
	public List<Token> getCoveredTokens(int begin, int end){
		return AnnotationUtils.getCoveredTokens(begin, end, tokens);
	}
	
	
}
