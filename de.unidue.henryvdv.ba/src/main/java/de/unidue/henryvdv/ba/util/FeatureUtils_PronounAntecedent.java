package de.unidue.henryvdv.ba.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

import de.tudarmstadt.ukp.dkpro.core.api.ner.type.NamedEntity;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.constituent.Constituent;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.constituent.NP;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.dependency.Dependency;
import de.unidue.henryvdv.ba.param.Parameters;
import de.unidue.henryvdv.ba.type.Anaphora;
import de.unidue.henryvdv.ba.type.Antecedent;
import de.unidue.henryvdv.ba.type.MyNP;
import de.unidue.henryvdv.ba.type.Quotation;
import de.unidue.henryvdv.ba.util.FeatureUtils_Gender.Gender;
/**
 * Class for the assignment of all pronoun-antecedent features
 * for a given anaphora
 * @author Henry
 *
 */
public class FeatureUtils_PronounAntecedent {
	
	/********************************************
	 * 	Assigns the antecedent features:		*
	 * 											*
	 * 	-Binding Theory (bool)					*
	 * 	-Same Sentence (bool)					*
	 * 	-Previous Sentence (bool)				*
	 * 	-Inter-Sentence Difference (float)		*
	 * 	-Intra-Sentence Difference (float)		*
	 * 	-Prepositional Parallel (bool)			*
	 * 	-Singular Match (bool)					*
	 * 	-Plural Match (bool)					*
	 * 											*
	 * 	Not used:								*
 	 * 	-Parent Category Match (bool)			*
	 * 	-Parent Word Match (bool)				*
	 * 											*
	 * 	+My own features						*
	 * 	-Difference in noun phrases(float)		*
	 * 	-Difference in pronouns(float)			*
	 ********************************************/
	
	/**
	 * Required Annotations:
	 */
	private Collection<Token> tokens;
	private Collection<Sentence> sentences;
	private Collection<Dependency> dependencies;
	private Collection<Quotation> quotes;
	private Collection<Constituent> constituents;
	private Collection<NP> nps;
	private Collection<NamedEntity> namedEntities;
	private JCas aJCas;
	
	public enum Number {
	    singular, plural, unknown
	}
	
	public FeatureUtils_PronounAntecedent(JCas aJCas){
		this.aJCas = aJCas;
		sentences = JCasUtil.select(aJCas, Sentence.class);
		dependencies = JCasUtil.select(aJCas, Dependency.class);
		tokens = JCasUtil.select(aJCas, Token.class);
		quotes = JCasUtil.select(aJCas, Quotation.class);
		constituents = JCasUtil.select(aJCas, Constituent.class);
		nps = JCasUtil.select(aJCas, NP.class);
		namedEntities = JCasUtil.select(aJCas, NamedEntity.class);
	}
	
	
	public void annotateFeatures(Anaphora a){	
		//In Same Sentence:
		a.getPronounAntecedentFeatures().setP_A_BindingTheory(bindingTheory(a));
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

		//My own features:
		a.getPronounAntecedentFeatures().setP_A_NPDistance(npDistance(a));
		a.getPronounAntecedentFeatures().setP_A_IntermediatePronoun(intermediatePronouns(a));
	}
	
	/**
	 * Returns all intermediate pronouns of the same gender
	 * @param anaphora Anaphora
	 * @return value
	 */
	public float intermediatePronouns(Anaphora anaphora){
		List<Token> intermediateTokens = AnnotationUtils.getCoveredTokens(anaphora.getAntecedent().getEnd() + 1, anaphora.getBegin() - 1, tokens);
		int i = 0;
		if(Arrays.asList(Parameters.malePronouns).contains(anaphora.getCoveredText().toLowerCase())){
			for(Token t : intermediateTokens){
				if(Arrays.asList(Parameters.malePronouns).contains(t.getCoveredText().toLowerCase()))
					i++;
			}
		}
		if(Arrays.asList(Parameters.femalePronouns).contains(anaphora.getCoveredText().toLowerCase())){
			for(Token t : intermediateTokens){
				if(Arrays.asList(Parameters.femalePronouns).contains(t.getCoveredText().toLowerCase()))
					i++;
			}
		}
		if(Arrays.asList(Parameters.neutralPronouns).contains(anaphora.getCoveredText().toLowerCase())){
			for(Token t : intermediateTokens){
				if(Arrays.asList(Parameters.neutralPronouns).contains(t.getCoveredText().toLowerCase()))
					i++;
			}
		}
		if(Arrays.asList(Parameters.pluralPronouns).contains(anaphora.getCoveredText().toLowerCase())){
			for(Token t : intermediateTokens){
				if(Arrays.asList(Parameters.pluralPronouns).contains(t.getCoveredText().toLowerCase()))
					i++;
			}
		}
		return (float)i / 10f;
	}
	
	/**
	 * Whether Binding Principle B & C are satisfied
	 * @param anaphora Anaphora
	 * @return value
	 */
	public boolean bindingTheory(Anaphora anaphora){		
		//If Principle B is satisfied:
		if(!isFreeInBindingDomain(anaphora, anaphora.getAntecedent()))
			return false;
	
		//If Principle C is satisfied:
		Antecedent ante = anaphora.getAntecedent();
		if(!person(ante) && !organization(ante) && !definiteArticle(ante)){
			return true;
		} else {
			if(!isFreeInBindingDomain(ante, anaphora))
				return false;
		}
	
		return true;
	}
	
	/**
	 * if annotation alpha is free in its binding domain (e.g. not bound by beta)
	 * @param alpha
	 * @param beta
	 * @return value
	 */
	public boolean isFreeInBindingDomain(Annotation alpha, Annotation beta){
		if(getSentenceNr(alpha.getBegin()) == getSentenceNr(beta.getBegin())){
			//only then the anaphora could be not free in its binding domain
			if(getBindingDomain(alpha) != null){
				Annotation bindingDomain = getBindingDomain(alpha);
				if(beta.getBegin() >= bindingDomain.getBegin() && beta.getBegin() <= bindingDomain.getEnd()){
					return false;
				}
				if(beta.getEnd() <= bindingDomain.getEnd() && beta.getEnd() >= bindingDomain.getBegin()){
					return false;
				}	
			}
		}
		return true;
	}
	/**
	 * Returns the binding domain for an annotation A - either the smallest sentence (if A is a subject)
	 * or the smallest sentence with another noun phrase c-commanding A
	 * @param annotation Annotation
	 * @return binding Domain
	 */
	public Annotation getBindingDomain(Annotation annotation){
		boolean isSubject = isSubject(AnnotationUtils.getCoveredToken(annotation, tokens));
		
		//get smallest sentence if a is subject 
		if(isSubject){
			Annotation smallest = null;
			int size = Integer.MAX_VALUE;
			for(Constituent c : constituents){			
				if(c.getConstituentType().equals("S") && c.getBegin() <= annotation.getBegin() && c.getEnd() >= annotation.getEnd()){
					if((c.getEnd() - c.getBegin()) < size ){
						size = c.getEnd() - c.getBegin();
						smallest = new Annotation(aJCas, c.getBegin(), c.getEnd());
					}
				
				}
			}
			return smallest;
		}
		//get smallest sentence with a NP c-commanding the anaphora
		Sentence currentSentence = null;
		for(Sentence s : sentences){
			if(s.getBegin() <= annotation.getBegin() && s.getEnd() >= annotation.getEnd())
				currentSentence = s;
		}
			
		Map<Integer, Annotation> annotationSizes = new HashMap<Integer, Annotation>();		
		for(Constituent c : constituents){			
			if(c.getConstituentType().equals("S") && currentSentence.getBegin() <= c.getBegin() && currentSentence.getEnd() >= c.getEnd()){
				Integer size = c.getEnd() - c.getBegin();
				Annotation anno = new Annotation(aJCas, c.getBegin(), c.getEnd());
				annotationSizes.put(size, anno);
			}
		}
		while(annotationSizes.size() != 0){
			Integer smallest = Integer.MAX_VALUE;
			for(Integer i : annotationSizes.keySet()){
				if(i < smallest){
					smallest = i;
				}
			}
			if(containsCCommandingNP(annotationSizes.get(smallest), annotation)){
				return annotationSizes.get(smallest);
			} else {
				annotationSizes.remove(smallest);
			}
		}	
		return null;
	}
	
	public boolean containsCCommandingNP(Annotation anno, Annotation a){
		List<NP> containedNPs = new ArrayList<NP>();
		for(NP  np : nps){
			if(anno.getBegin() < np.getBegin() && anno.getEnd() >= np.getEnd()){
				if(!(np.getBegin() <= a.getBegin() && np.getEnd() >= a.getEnd()))
						containedNPs.add(np);
			}
		}
		
		for(NP np : containedNPs){
			if(cCommands(np, a)){
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * If alpha c-commmands beta
	 * @param np alpha
	 * @param annotation beta
	 * @return value
	 */
	public boolean cCommands(NP np, Annotation annotation){
		Sentence currentSentence = null;
		for(Sentence s : sentences){
			if(s.getBegin() <= annotation.getBegin() && s.getEnd() >= annotation.getEnd())
				currentSentence = s;
		}
		Constituent alphaParent = getConstituent(np.getParent());
		Constituent beta = getConstituent(annotation);
		int i = 0;
		while(beta.getParent() != null){
			if(beta.getParent().getBegin() == alphaParent.getBegin() && 
					beta.getParent().getEnd() == alphaParent.getEnd()){
				return true;
			} else {
				beta = getConstituent(beta.getParent());
			}
			if(beta.getBegin() <= currentSentence.getBegin() && beta.getEnd() >= currentSentence.getEnd())
				break;
			if(getConstituent(beta) == getConstituent(beta.getParent()))
				break;
			
		}
		return false;
	}
	
	/**
	 * Returns the smallest constituent containing the annotation
	 * @param annotation Annotation
	 * @return value
	 */
	public Constituent getConstituent(Annotation annotation){
		Constituent smallest = null;
		int size = Integer.MAX_VALUE;
		for(Constituent c : constituents){			
			if(c.getBegin() <= annotation.getBegin() && c.getEnd() >= annotation.getEnd()){
				if((c.getEnd() - c.getBegin()) < size ){
					size = c.getEnd() - c.getBegin();
					smallest = c;
				}		
			}
		}
		return smallest;
	}
	/**
	 * Returns the distance of nps between the anaphora and its antecedent
	 * @param anaphora Anaphora
	 * @return value
	 */
	public float npDistance(Anaphora anaphora){
		int count = 0;
		for(NP np : nps){
			if(np.getBegin() > anaphora.getAntecedent().getBegin() && np.getEnd() < anaphora.getEnd())
				count++;
		}		
		return (float)count / 10f;
	}
	
	/**
	 * Whether the anaphora and its antecedent are in the same sentence
	 * @param anaphora Anaphora
	 * @return value
	 */
	public boolean antecedentInSameSentence(Anaphora anaphora){
		boolean value = (getSentenceNr(anaphora.getBegin()) == getSentenceNr(anaphora.getAntecedent().getBegin()));
		return value;
	}
	
	/**
	 * Whether the antecedent is in the previous sentence
	 * @param anaphora Anaphora
	 * @return value
	 */
	public boolean antecedentInPreviousSentence(Anaphora anaphora){
		boolean value = (getSentenceNr(anaphora.getBegin()) -1 == getSentenceNr(anaphora.getAntecedent().getBegin()));
		return value;
	}
	
	/**
	 * Difference between anaphora and antecedent in sentences
	 * @param anaphora Anaphora
	 * @return value
	 */
	public float interSentenceDiff(Anaphora anaphora){
		int anaphoraS = getSentenceNr(anaphora.getBegin());
		int antecedentS = getSentenceNr(anaphora.getAntecedent().getBegin());
		float value = (anaphoraS - antecedentS)/ 50.0f;
		return value;
	}
	/**
	 * Difference between anaphora and antecedent in tokens
	 * @param anaphora Anaphora
	 * @return value
	 */
	public float intraSentenceDiff(Anaphora anaphora){
		int dist = 0;
		for(Token t : tokens){
			if(t.getBegin() > anaphora.getAntecedent().getBegin() && t.getBegin() < anaphora.getBegin())
				dist++;
		}
		float value = (float)dist / 50.0f;
		return value;
	}
	/**
	 * Whether the antecedent and the anaphora parent words match
	 * @param anaphora Anaphora
	 * @return value
	 */
	public boolean parentWordMatch(Anaphora anaphora){
		Token anaphoraparent = getParent(anaphora);
		if(anaphoraparent == null)
			return false;
		String anaphoraParentString = anaphoraparent.getCoveredText();
		List<Token> anteTokens = getCoveredTokens(anaphora.getAntecedent().getBegin(), anaphora.getAntecedent().getEnd());					
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
	/**
	 * Whether anaphora and antecedent are in quotes
	 * @param anaphora Anaphora
	 * @return value
	 */
	public boolean quotationSituation(Anaphora anaphora){
		boolean valueA = isInQuotes(anaphora);
		boolean valueB = isInQuotes(anaphora.getAntecedent());
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
		
		if(Arrays.asList(Parameters.thirdPersonPronouns).contains(anaphora.getAntecedent().getCoveredText().toLowerCase())){			
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
		
		if(Arrays.asList(Parameters.thirdPersonPronouns).contains(anaphora.getAntecedent().getCoveredText().toLowerCase())){			
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
	
	private boolean isSubject(Token token){
		for(Dependency d : dependencies){
			if(d.getDependent() == token && d.getDependencyType().contains("subj")){
				return true;
			}
		}	
		return false;
	}
	
	public boolean organization(Annotation annotation){
		List<Token> covTokens = getCoveredTokens(annotation);
		boolean value = false;			
		for(Token t : covTokens){
			String ne = getNamedEntityValue(t);
			if(ne != null && ne.equals("ORGANIZATION")){
				value = true;
			}
		}
		return value;
	}
	
	public boolean person(Annotation annotation){
		List<Token> covTokens = getCoveredTokens(annotation);
		boolean value = false;			
		for(Token t : covTokens){
			String ne = getNamedEntityValue(t);
			if(ne != null && ne.equals("PERSON")){
				value = true;
			}
		}
		return value;
	}
	
	public boolean definiteArticle(Annotation annotation){
		List<Token> covTokens = getCoveredTokens(annotation);
		boolean value = false;			
		for(Token t : covTokens){
			if(t.getCoveredText().equalsIgnoreCase("the")){
				value = true;
			}
		}
		return value;
	}
	
	private String getNamedEntityValue(Token token){
		for(NamedEntity n : namedEntities){
			if(n.getBegin() <= token.getBegin() && n.getEnd() >= token.getEnd()){
				return n.getValue();
			}
		}
		return null;
	}
}
