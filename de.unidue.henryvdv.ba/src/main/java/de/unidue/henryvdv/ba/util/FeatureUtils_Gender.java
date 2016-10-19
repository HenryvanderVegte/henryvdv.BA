package de.unidue.henryvdv.ba.util;

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
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.dependency.Dependency;
import de.unidue.henryvdv.ba.param.Parameters;
import de.unidue.henryvdv.ba.type.Anaphora;
import de.unidue.henryvdv.ba.type.Quotation;

/**
 * Class for the assignment of all gender features
 * for a given anaphora
 * @author Henry
 *
 */
public class FeatureUtils_Gender {
	/********************************************
	 * 	Assigns the gender features:			*
	 * 											*
	 * 	-Standard Gender Match (bool)			*
	 * 	-Standard Gender Mismatch (bool)		*
	 * 	-Pronoun Mismatch (bool)				*
	 * 											*
	 *  Gender Frequency features:				*
	 *  -Masculine Mean							*
	 *  -Masculine Standard Deviation			*
	 *  -Feminine Mean							*
	 *  -Feminine Standard Deviation			*
	 *  -Neutral Mean							*
	 *  -Neutral Standard Deviation				*
	 *  -Plural Mean							*
	 *  -Plural Standard Deviation				*
	 * 											*
	 * 	Hard Constraint Gender Features:		*
	 * 	-Masculine Hard Constraint				*
	 * 	-Feminine Hard Constraint				*
	 * 	-Neutral Hard Constraint				*
	 * 	-Plural Hard Constraint					*
	 * 											*
	 ********************************************/
	
	
	/**
	 * Required information
	 */
	private Collection<Token> tokens;
	private Map<String, Integer[]> corpusFrequencies;
	private Collection<NamedEntity> namedEntities;
	private Collection<Dependency> dependencies;
	
	public FeatureUtils_Gender(JCas aJCas, Map<String, Integer[]> corpusFrequencies){
		this.corpusFrequencies = corpusFrequencies;
		tokens = JCasUtil.select(aJCas, Token.class);
		namedEntities = JCasUtil.select(aJCas, NamedEntity.class);
		dependencies = JCasUtil.select(aJCas, Dependency.class);
	}
	
	
	public enum Gender {
	    male, female, unknown
	}
	
	public enum PronounType {
		unknown, male, female, neutral, plural
	}
	
	/**
	 * Annotates all gender features to an anaphora
	 * @param a Anaphora
	 */
	public void annotateFeatures(Anaphora a){
		// Gender known and matches
		a.getGenderFeatures().setG_StdGenderMatch(stdGenderMatch(a));
		// Gender known and mismatches
		a.getGenderFeatures().setG_StdGenderMismatch(stdGenderMismatch(a));
		// Both pronouns and mismatch
		a.getGenderFeatures().setG_PronounMismatch(pronounMismatch(a));
		
		// Mean & standard deviation of masculine Beta Distribution
		setMasculineDistribution(a);
		
		// Mean & standard deviation of feminine Beta Distribution
		setFeminineDistribution(a);
		
		// Mean & standard deviation of neutral Beta Distribution
		setNeutralDistribution(a);
		
		// Mean & standard deviation of plural Beta Distribution
		setPluralDistribution(a);
		
		//The hard constraint gender:
		setHardConstraintGender(a);
	}
	/**
	 * Sets the most frequent gender outcome of antecedent as the predicted gender
	 * @param anaphora Anaphora
	 */
	public void setHardConstraintGender(Anaphora anaphora){
		String ante = anaphora.getAntecedent().getCoveredText().toLowerCase();
		boolean masculine = false;
		boolean feminine = false;
		boolean neutral = false;
		boolean plural = false;

		Integer[] freq = {0,0,0,0};
		
		List<Token> covTokens = AnnotationUtils.getCoveredTokens(anaphora.getAntecedent(), tokens);
		String person = null;
		for(Token t : covTokens){
			if(isPerson(t)){
				//Only take the first name (e.g. token) of a person
				person = t.getCoveredText();
			}
		}
		
		if(corpusFrequencies.containsKey(ante)){
			freq = corpusFrequencies.get(ante);
		} else if(person != null) {
			if(corpusFrequencies.containsKey(person.toLowerCase())){
				freq = corpusFrequencies.get(person.toLowerCase());
			}

		} else {
			Token head = getHeadNoun(anaphora.getAntecedent());
			if(head != null && corpusFrequencies.containsKey(head.getCoveredText().toLowerCase())){
				freq = corpusFrequencies.get(head.getCoveredText().toLowerCase());
			} else {
				for(Token t : covTokens){
					if(t.getPos().getPosValue().contains("NN")){
						if(corpusFrequencies.containsKey(t.getCoveredText().toLowerCase())){
							freq = corpusFrequencies.get(t.getCoveredText().toLowerCase());
							break;
						}
					}
				}
			}
		}
		/*
		 * A different, but still valid style of getting the frequencies, 
		 * therefore it is excluded:
		 */
		/*
		List<Token> covTokens = AnnotationUtils.getCoveredTokens(a.getAntecedent(), tokens);
		if(corpusFrequencies.containsKey(ante)){
			Integer[] freq1 = corpusFrequencies.get(ante);
			freq[0] += freq1[0];
			freq[1] += freq1[1];
			freq[2] += freq1[2];
			freq[3] += freq1[3];
			}
		String firstWord = covTokens.get(0).getCoveredText().toLowerCase() + " !";
		if(corpusFrequencies.containsKey(firstWord)){
			Integer[] freq1 = corpusFrequencies.get(firstWord);
			freq[0] += freq1[0];
			freq[1] += freq1[1];
			freq[2] += freq1[2];
			freq[3] += freq1[3];
		}
		String lastWord = "! " + covTokens.get(covTokens.size() - 1).getCoveredText().toLowerCase();
		if(corpusFrequencies.containsKey(lastWord)){
			Integer[] freq1 = corpusFrequencies.get(lastWord);
			freq[0] += freq1[0];
			freq[1] += freq1[1];
			freq[2] += freq1[2];
			freq[3] += freq1[3];
		}
		*/
		
		if(freq[0] > freq[1] && freq[0] >  freq[2] && freq[0] > freq[3])
			masculine = true;
		if(freq[1] > freq[0]&& freq[1] >  freq[2] && freq[1] > freq[3])
			feminine = true;
		if(freq[2] > freq[0] && freq[2] >  freq[1]  && freq[2] > freq[3])
			neutral = true;
		if(freq[3] > freq[0] && freq[3] >  freq[1] && freq[3] >  freq[2])
			plural = true;
		
		
		anaphora.getGenderFeatures().setG_Masculine_HardConstraint(masculine);
		anaphora.getGenderFeatures().setG_Feminine_HardConstraint(feminine);
		anaphora.getGenderFeatures().setG_Neutral_HardConstraint(neutral);
		anaphora.getGenderFeatures().setG_Plural_HardConstraint(plural);
	}
	
	/**
	 * If the gender (obtained through designators) of the antecedent is known and mismatches 
	 * with the pronoun
	 * @param anaphora Anaphora
	 * @return value
	 */
	public boolean stdGenderMatch(Anaphora anaphora){
		Gender anteGender = Gender.unknown;
		
		List<Token> covTokens = AnnotationUtils.getCoveredTokens(anaphora.getAntecedent(), tokens);
		String first = covTokens.get(0).getCoveredText().toLowerCase();
		if(Arrays.asList(Parameters.maleDesignators).contains(first)){
			anteGender = Gender.male;
		}
		if(Arrays.asList(Parameters.femaleDesignators).contains(first)){
			anteGender = Gender.female;
		}

		Gender anaphoraGender = Gender.unknown;
		if(Arrays.asList(Parameters.malePronouns).contains(anaphora.getCoveredText().toLowerCase())){
			anaphoraGender = Gender.male;
		}
		if(Arrays.asList(Parameters.femalePronouns).contains(anaphora.getCoveredText().toLowerCase())){
			anaphoraGender = Gender.female;
		}
		
		if(anteGender == anaphoraGender && anteGender != Gender.unknown){
			return true;
		}
		return false;
	}
	
	/**
	 * If the gender (obtained through designators) of the antecedent is known and matches 
	 * with the pronoun
	 * @param anaphora Anaphora
	 * @return value
	 */
	public boolean stdGenderMismatch(Anaphora anaphora){
		Gender anteGender = Gender.unknown;
		
		List<Token> covTokens = AnnotationUtils.getCoveredTokens(anaphora.getAntecedent(), tokens);
		String first = covTokens.get(0).getCoveredText().toLowerCase();
		if(Arrays.asList(Parameters.maleDesignators).contains(first)){
			anteGender = Gender.male;
		}
		if(Arrays.asList(Parameters.femaleDesignators).contains(first)){
			anteGender = Gender.female;
		}

		Gender anaphoraGender = Gender.unknown;
		if(Arrays.asList(Parameters.malePronouns).contains(anaphora.getCoveredText().toLowerCase())){
			anaphoraGender = Gender.male;
		}
		if(Arrays.asList(Parameters.femalePronouns).contains(anaphora.getCoveredText().toLowerCase())){
			anaphoraGender = Gender.female;
		}
		
		if(anteGender == anaphoraGender && anteGender != Gender.unknown){
			return true;
		}
		return false;
	}

	/**
	 * If both (antecedent and anaphora) are pronouns and mismatch in gender
	 * @param anaphora
	 * @return true if both are pronouns and mismatch, else false
	 */
	public boolean pronounMismatch(Anaphora anaphora){
		List<Token> covTokens = AnnotationUtils.getCoveredTokens(anaphora.getAntecedent(), tokens);
		PronounType antecedentType = PronounType.unknown;
		
		for(Token token : covTokens){
			String tokenText = token.getCoveredText().toLowerCase();
			if(Arrays.asList(Parameters.allPronouns).contains(tokenText)){
				if(antecedentType != PronounType.unknown){
					return false;
				}				

				if(Arrays.asList(Parameters.malePronouns).contains(tokenText)){
					antecedentType = PronounType.male;
				}
				if(Arrays.asList(Parameters.femalePronouns).contains(tokenText)){
					antecedentType = PronounType.female;
				}
				if(Arrays.asList(Parameters.neutralPronouns).contains(tokenText)){
					antecedentType = PronounType.neutral;
				}
				if(Arrays.asList(Parameters.pluralPronouns).contains(tokenText)){
					antecedentType = PronounType.plural;
				}
			}
		}
		
		if(antecedentType == PronounType.unknown){
			return false;
		}

		
		PronounType anaphoraType = PronounType.unknown;
		String anaphoraText = anaphora.getCoveredText().toLowerCase();
		
		if(Arrays.asList(Parameters.malePronouns).contains(anaphoraText)){
			anaphoraType = PronounType.male;
		}
		if(Arrays.asList(Parameters.femalePronouns).contains(anaphoraText)){
			anaphoraType = PronounType.female;
		}
		if(Arrays.asList(Parameters.neutralPronouns).contains(anaphoraText)){
			anaphoraType = PronounType.neutral;
		}
		if(Arrays.asList(Parameters.pluralPronouns).contains(anaphoraText)){
			anaphoraType = PronounType.plural;
		}
				
		if(anaphoraType != antecedentType){
			return true; 
		}
		
		return false;
		
	}
	/**
	 * Gets the output of alpha and beta values for all genders
	 * @param anaphora Anaphora
	 * @param alpha The alpha gender: whether masculine, feminine, neutral, or plural
	 * @return Array with [0] = alpha and [1] = beta
	 */
	private Integer[] getDistribution(Anaphora anaphora, PronounType alpha){
		int a = 0;
		int b1 = 0;
		int b2= 0;
		int b3 = 0;
		if(alpha == PronounType.male){
			a = 0;
			b1 = 1;
			b2 = 2;
			b3 = 3;
		}
		if(alpha == PronounType.female){
			a = 1;
			b1 = 0;
			b2 = 2;
			b3 = 3;
		}
		if(alpha == PronounType.neutral){
			a = 2;
			b1 = 0;
			b2 = 1;
			b3 = 3;
		}
		if(alpha == PronounType.plural){
			a = 3;
			b1 = 0;
			b2 = 1;
			b3 = 2;
		}
		
		String ante = anaphora.getAntecedent().getCoveredText().toLowerCase();
		int alphaV = 1;
		int betaV = 1;
		
		/*
		 * A different, but still valid style of getting the frequencies, 
		 * therefore it is excluded:
		 */
		/*
		List<Token> covTokens = AnnotationUtils.getCoveredTokens(anaphora.getAntecedent(), tokens);		
		if(corpusFrequencies.containsKey(ante)){
			Integer[] freq = corpusFrequencies.get(ante);
			alphaV += freq[a];
			betaV += freq[b1] + freq[b2] + freq[b3];
		}
		String firstWord = covTokens.get(0).getCoveredText().toLowerCase() + " !";
		if(corpusFrequencies.containsKey(firstWord)){
			Integer[] freq = corpusFrequencies.get(firstWord);
			alphaV += freq[a];
			betaV += freq[b1] + freq[b2] + freq[b3];
		}
		String lastWord = "! " + covTokens.get(covTokens.size() - 1).getCoveredText().toLowerCase();
		if(corpusFrequencies.containsKey(lastWord)){
			Integer[] freq = corpusFrequencies.get(lastWord);
			alphaV += freq[a];
			betaV += freq[b1] + freq[b2] + freq[b3];
		}*/
		
		
		List<Token> covTokens = AnnotationUtils.getCoveredTokens(anaphora.getAntecedent(), tokens);
		String person = null;
		for(Token t : covTokens){
			if(isPerson(t)){
				//Only take the first name (e.g. token) of a person
				person = t.getCoveredText();
			}
		}
		Integer[] freq = new Integer[]{0,0,0,0};
		if(corpusFrequencies.containsKey(ante)){
			freq = corpusFrequencies.get(ante);
		} else if(person != null) {
			if(corpusFrequencies.containsKey(person.toLowerCase())){
				freq = corpusFrequencies.get(person.toLowerCase());
			}

		} else {
			Token head = getHeadNoun(anaphora.getAntecedent());
			if(head != null && corpusFrequencies.containsKey(head.getCoveredText().toLowerCase())){
				freq = corpusFrequencies.get(head.getCoveredText().toLowerCase());
			} else {
				for(Token t : covTokens){
					if(t.getPos().getPosValue().contains("NN")){
						if(corpusFrequencies.containsKey(t.getCoveredText().toLowerCase())){
							freq = corpusFrequencies.get(t.getCoveredText().toLowerCase());
							break;
						}
					}
				}
			}
		}
		alphaV += freq[a];
		betaV += freq[b1] + freq[b2] + freq[b3];

		return new Integer[]{alphaV,betaV};
	}

	/**
	 * Annotates the masculine mean and std. dev.
	 * @param anaphora Anaphora
	 */
	public void setMasculineDistribution(Anaphora anaphora){
		Integer[] val = getDistribution(anaphora, PronounType.male);	
		int alpha = val[0];
		int beta = val[1];
		anaphora.getGenderFeatures().setG_Masculine_Mean(getMean(alpha, beta));
		anaphora.getGenderFeatures().setG_Masculine_Variance(getStdDeviation(alpha, beta));
		
	}
	
	/**
	 * Annotates the feminine mean and std. dev.
	 * @param anaphora Anaphora
	 */
	public void setFeminineDistribution(Anaphora anaphora){
		Integer[] val = getDistribution(anaphora, PronounType.female);	
		int alpha = val[0];
		int beta = val[1];
		anaphora.getGenderFeatures().setG_Feminine_Mean(getMean(alpha, beta));
		anaphora.getGenderFeatures().setG_Feminine_Variance(getStdDeviation(alpha, beta));
	}
	
	/**
	 * Annotates the neutral mean and std. dev.
	 * @param anaphora Anaphora
	 */
	public void setNeutralDistribution(Anaphora anaphora){
		Integer[] val = getDistribution(anaphora, PronounType.neutral);	
		int alpha = val[0];
		int beta = val[1];	
		anaphora.getGenderFeatures().setG_Neutral_Mean(getMean(alpha, beta));
		anaphora.getGenderFeatures().setG_Neutral_Variance(getStdDeviation(alpha, beta));
	}
	
	/**
	 * Annotates the plural mean and std. dev.
	 * @param anaphora Anaphora
	 */
	public void setPluralDistribution(Anaphora anaphora){
		Integer[] val = getDistribution(anaphora, PronounType.plural);	
		int alpha = val[0];
		int beta = val[1];		
		anaphora.getGenderFeatures().setG_Plural_Mean(getMean(alpha, beta));
		anaphora.getGenderFeatures().setG_Plural_Variance(getStdDeviation(alpha, beta));
	}
	
	/**
	 * Mean of alpha / beta distribution
	 * @param alpha
	 * @param beta
	 * @return
	 */
	public float getMean(int alpha, int beta){
		float fAlpha = alpha;
		float fBeta = beta;
		return (fAlpha / (fAlpha + fBeta));
		
	}
	
	/**
	 * Standard deviation of alpha / beta distribution
	 * @param alpha
	 * @param beta
	 * @return
	 */
	public float getStdDeviation(int alpha, int beta){
		float var = getVariance(alpha, beta);
		double stdDev = Math.sqrt((double)var);	
		return (float)stdDev;
	}
	
	/**
	 * Variance of alpha / beta distribution
	 * @param alpha
	 * @param beta
	 * @return
	 */
	public float getVariance(int alpha, int beta){
		float fAlpha = alpha;
		float fBeta = beta;
		float sum = fAlpha + fBeta;
		float product = fAlpha * fBeta;
	
		float variance = product / (sum * sum * (sum + 1));
		
		if(variance < 0){
			System.out.println("Variance cant be < 0");
		}
		return variance;
	}
	
	/**
	 * Whether the token "contains" a person
	 * @param token
	 * @return
	 */
	private boolean isPerson(Token token){
		for(NamedEntity n : namedEntities){
			if(n.getBegin() <= token.getBegin() && n.getEnd() >= token.getEnd() && n.getValue().equals("PERSON")){
				return true;
			}
		}
		return false;
	}
	/**
	 * Returns the head noun for a given noun phrase
	 * @param nounphrase
	 * @return head noun, else null
	 */
	private Token getHeadNoun(Annotation nounphrase){
		List<Token> covTokens = AnnotationUtils.getCoveredTokens(nounphrase, tokens);
		
		for(Token t : covTokens){
			if(t.getPos().getPosValue().contains("NN")){
				for(Dependency d : dependencies){
					if(d.getDependent() == t && d.getGovernor().getPos().getPosValue().contains("NN")){
						Token gov = d.getGovernor();
						if(nounphrase.getBegin() <= gov.getBegin() && nounphrase.getEnd() >= gov.getEnd())
							return d.getGovernor();					
					}
				}
			}
		}
		
		return null;
	}
}
