package de.unidue.henryvdv.ba.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.dependency.Dependency;
import de.unidue.henryvdv.ba.param.Parameters;
import de.unidue.henryvdv.ba.type.Anaphora;
import de.unidue.henryvdv.ba.type.Quotation;

public class FeatureUtils_Gender {
	
	private Collection<Token> tokens;
	private Map<String, Integer[]> corpusFrequencies;
	
	public FeatureUtils_Gender(JCas aJCas, Map<String, Integer[]> corpusFrequencies){
		this.corpusFrequencies = corpusFrequencies;
		tokens = JCasUtil.select(aJCas, Token.class);
	}
	
	
	public enum Gender {
	    male, female, unknown
	}
	
	public enum PronounType {
		unknown, male, female, neutral, plural
	}

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
	}
	
	public boolean stdGenderMatch(Anaphora a){
		Gender anteGender = Gender.unknown;
		
		List<Token> covTokens = AnnotationUtils.getCoveredTokens(a.getAntecedent(), tokens);
		
		for(Token token : covTokens){
			if(Arrays.asList(Parameters.maleDesignators).contains(token.getCoveredText().toLowerCase())){
				//Gender could be male or female - too unspecific
				if(anteGender == Gender.female)
					return false;
				anteGender = Gender.male;
			}
			if(Arrays.asList(Parameters.femaleDesignators).contains(token.getCoveredText().toLowerCase())){
				//Gender could be male or female - too unspecific
				if(anteGender == Gender.male)
					return false;
				anteGender = Gender.female;
			}
		}
		
		Gender anaphoraGender = Gender.unknown;
		if(Arrays.asList(Parameters.malePronouns).contains(a.getCoveredText().toLowerCase())){
			anaphoraGender = Gender.male;
		}
		if(Arrays.asList(Parameters.femalePronouns).contains(a.getCoveredText().toLowerCase())){
			anaphoraGender = Gender.female;
		}
		
		if(anteGender == anaphoraGender && anteGender != Gender.unknown){
			return true;
		}
		return false;
	}
	
	public boolean stdGenderMismatch(Anaphora a){
		Gender anteGender = Gender.unknown;
		
		List<Token> covTokens = AnnotationUtils.getCoveredTokens(a.getAntecedent(), tokens);
		
		for(Token token : covTokens){
			if(Arrays.asList(Parameters.maleDesignators).contains(token.getCoveredText().toLowerCase())){
				//Gender could be male or female - too unspecific
				if(anteGender == Gender.female)
					return false;
				anteGender = Gender.male;
			}
			if(Arrays.asList(Parameters.femaleDesignators).contains(token.getCoveredText().toLowerCase())){
				//Gender could be male or female - too unspecific
				if(anteGender == Gender.male)
					return false;
				anteGender = Gender.female;
			}
		}
		
		Gender anaphoraGender = Gender.unknown;
		if(Arrays.asList(Parameters.malePronouns).contains(a.getCoveredText().toLowerCase())){
			anaphoraGender = Gender.male;
		}
		if(Arrays.asList(Parameters.femalePronouns).contains(a.getCoveredText().toLowerCase())){
			anaphoraGender = Gender.female;
		}
		
		if(anteGender != anaphoraGender && anteGender != Gender.unknown){
			return true;
		}
		
		return false;
	}
	
	public boolean pronounMismatch(Anaphora a){
		List<Token> covTokens = AnnotationUtils.getCoveredTokens(a.getAntecedent(), tokens);
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
		
		if(antecedentType == PronounType.unknown)
			return false;
		
		PronounType anaphoraType = PronounType.unknown;
		String anaphoraText = a.getCoveredText().toLowerCase();
		
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

	public void setMasculineDistribution(Anaphora a){
		String ante = a.getAntecedent().getCoveredText().toLowerCase();
		int alpha = 1;
		int beta = 1;
		if(corpusFrequencies.containsKey(ante)){
			Integer[] freq = corpusFrequencies.get(ante);
			alpha += freq[0];
			beta += freq[1] + freq[2] + freq[3];
		}
		a.getGenderFeatures().setG_Masculine_Mean(getMean(alpha, beta));
		a.getGenderFeatures().setG_Masculine_StdDev(getStandardDeviation(alpha, beta));
		
	}
	
	public void setFeminineDistribution(Anaphora a){
		String ante = a.getAntecedent().getCoveredText().toLowerCase();
		int alpha = 1;
		int beta = 1;
		if(corpusFrequencies.containsKey(ante)){			
			Integer[] freq = corpusFrequencies.get(ante);
			alpha += freq[1];
			beta += freq[0] + freq[2] + freq[3];
		}
		a.getGenderFeatures().setG_Feminine_Mean(getMean(alpha, beta));
		a.getGenderFeatures().setG_Feminine_StdDev(getStandardDeviation(alpha, beta));
	}
	
	public void setNeutralDistribution(Anaphora a){
		String ante = a.getAntecedent().getCoveredText().toLowerCase();
		int alpha = 1;
		int beta = 1;
		if(corpusFrequencies.containsKey(ante)){			
			Integer[] freq = corpusFrequencies.get(ante);
			alpha += freq[2];
			beta += freq[0] + freq[1] + freq[3];
		}
		a.getGenderFeatures().setG_Neutral_Mean(getMean(alpha, beta));
		a.getGenderFeatures().setG_Neutral_StdDev(getStandardDeviation(alpha, beta));
	}
	
	public void setPluralDistribution(Anaphora a){
		String ante = a.getAntecedent().getCoveredText().toLowerCase();
		int alpha = 1;
		int beta = 1;
		if(corpusFrequencies.containsKey(ante)){			
			Integer[] freq = corpusFrequencies.get(ante);
			alpha += freq[3];
			beta += freq[0] + freq[1] + freq[2];
		}
		a.getGenderFeatures().setG_Plural_Mean(getMean(alpha, beta));
		a.getGenderFeatures().setG_Plural_StdDev(getStandardDeviation(alpha, beta));
	}
	
	public double getMean(int alpha, int beta){
		double r = (double)alpha / (double)(alpha + beta);
		return r;
		
	}
	
	public double getStandardDeviation(int alpha, int beta){
		double dAlpha = alpha;
		double dBeta = beta;
		double sum = dAlpha + dBeta;
		double product = dAlpha * dBeta;
		double a = sum * sum * (sum + 1);
	
		double variance = product /a;
		
		double stdDev = Math.sqrt(variance);	
		
		return stdDev;
	}
	
}
