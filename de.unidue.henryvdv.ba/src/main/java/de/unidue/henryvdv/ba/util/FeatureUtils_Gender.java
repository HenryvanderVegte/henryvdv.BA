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

public class FeatureUtils_Gender {
	
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
		
	//	setHardConstraintGender(a);
	}
	
	public void setHardConstraintGender(Anaphora a){
		String ante = a.getAntecedent().getCoveredText().toLowerCase();
		int mFreq = 0;
		int fFreq = 0;
		int nFreq = 0;
		int pFreq = 0;
		Integer[] freq = {0,0,0,0};
		List<Token> covTokens = AnnotationUtils.getCoveredTokens(a.getAntecedent(), tokens);
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
			Token head = getHeadNoun(a.getAntecedent());
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
		if(freq[0] > freq[1] && freq[0] > freq[2] && freq[0] > freq[3])
			mFreq = 1;
		if(freq[1] > freq[0] && freq[1] > freq[2] && freq[1] > freq[3])
			fFreq = 1;
		if(freq[2] > freq[0] && freq[2] > freq[1] && freq[2] > freq[3])
			nFreq = 1;
		if(freq[3] > freq[0] && freq[3] > freq[1] && freq[3] > freq[2])
			pFreq = 1;
		
		a.getGenderFeatures().setG_Masculine_Mean(mFreq);
		a.getGenderFeatures().setG_Masculine_Variance(0f);
		a.getGenderFeatures().setG_Feminine_Mean(fFreq);
		a.getGenderFeatures().setG_Feminine_Variance(0f);
		a.getGenderFeatures().setG_Neutral_Mean(nFreq);
		a.getGenderFeatures().setG_Neutral_Variance(0f);
		a.getGenderFeatures().setG_Plural_Mean(pFreq);
		a.getGenderFeatures().setG_Plural_Variance(0f);
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
		
		if(antecedentType == PronounType.unknown){
			return false;
		}

		
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
			if(!a.getHasCorrectAntecedent()){
				//System.out.println("Pronoun Mismatch: ");
				//System.out.println(a.getCoveredText() + " ----- " + a.getAntecedent().getCoveredText());
			}
			return true; 
		}
		if(!a.getHasCorrectAntecedent()){
			//System.out.println("Pronoun match: ");
			//System.out.println(a.getCoveredText() + " ----- " + a.getAntecedent().getCoveredText());
		}
		
		return false;
	}

	public void setMasculineDistribution(Anaphora a){
		String ante = a.getAntecedent().getCoveredText().toLowerCase();
		int alpha = 1;
		int beta = 1;
		
		List<Token> covTokens = AnnotationUtils.getCoveredTokens(a.getAntecedent(), tokens);
		String person = null;
		for(Token t : covTokens){
			if(isPerson(t)){
				//Only take the first name (e.g. token) of a person
				person = t.getCoveredText();
			}
		}
		
		if(corpusFrequencies.containsKey(ante)){
			Integer[] freq = corpusFrequencies.get(ante);
			alpha += freq[0];
			beta += freq[1] + freq[2] + freq[3];
		} else if(person != null) {
			if(corpusFrequencies.containsKey(person.toLowerCase())){
				Integer[] freq = corpusFrequencies.get(person.toLowerCase());
				alpha += freq[0];
				beta += freq[1] + freq[2] + freq[3];
			}

		} else {
			Token head = getHeadNoun(a.getAntecedent());
			if(head != null && corpusFrequencies.containsKey(head.getCoveredText().toLowerCase())){
				Integer[] freq = corpusFrequencies.get(head.getCoveredText().toLowerCase());
				alpha += freq[0];
				beta += freq[1] + freq[2] + freq[3];
			} else {
				for(Token t : covTokens){
					if(t.getPos().getPosValue().contains("NN")){
						if(corpusFrequencies.containsKey(t.getCoveredText().toLowerCase())){
							Integer[] freq = corpusFrequencies.get(t.getCoveredText().toLowerCase());
							alpha += freq[0];
							beta += freq[1] + freq[2] + freq[3];
							break;
						}
					}
				}
			}
		}
		a.getGenderFeatures().setG_Masculine_Mean(getMean(alpha, beta));
		a.getGenderFeatures().setG_Masculine_Variance(getVariance(alpha, beta));
		
	}
	
	public void setFeminineDistribution(Anaphora a){
		String ante = a.getAntecedent().getCoveredText().toLowerCase();
		int alpha = 1;
		int beta = 1;

		List<Token> covTokens = AnnotationUtils.getCoveredTokens(a.getAntecedent(), tokens);
		String person = null;
		for(Token t : covTokens){
			if(isPerson(t)){
				//Only take the first name (e.g. token) of a person
				person = t.getCoveredText();
			}
		}
		
		if(corpusFrequencies.containsKey(ante)){
			Integer[] freq = corpusFrequencies.get(ante);
			alpha += freq[1];
			beta += freq[0] + freq[2] + freq[3];
		} else if(person != null) {
			if(corpusFrequencies.containsKey(person.toLowerCase())){
				Integer[] freq = corpusFrequencies.get(person.toLowerCase());
				alpha += freq[1];
				beta += freq[0] + freq[2] + freq[3];
			}

		} else {
			Token head = getHeadNoun(a.getAntecedent());
			if(head != null && corpusFrequencies.containsKey(head.getCoveredText().toLowerCase())){
				Integer[] freq = corpusFrequencies.get(head.getCoveredText().toLowerCase());
				alpha += freq[1];
				beta += freq[0] + freq[2] + freq[3];
			} else {
				for(Token t : covTokens){
					if(t.getPos().getPosValue().contains("NN")){
						if(corpusFrequencies.containsKey(t.getCoveredText().toLowerCase())){
							Integer[] freq = corpusFrequencies.get(t.getCoveredText().toLowerCase());
							alpha += freq[1];
							beta += freq[0] + freq[2] + freq[3];
							break;
						}
					}
				}
			}	
		}
		a.getGenderFeatures().setG_Feminine_Mean(getMean(alpha, beta));
		a.getGenderFeatures().setG_Feminine_Variance(getVariance(alpha, beta));
	}
	
	public void setNeutralDistribution(Anaphora a){
		String ante = a.getAntecedent().getCoveredText().toLowerCase();
		int alpha = 1;
		int beta = 1;

		
		List<Token> covTokens = AnnotationUtils.getCoveredTokens(a.getAntecedent(), tokens);
		String person = null;
		for(Token t : covTokens){
			if(isPerson(t)){
				//Only take the first name (e.g. token) of a person
				person = t.getCoveredText();
			}
		}
		
		if(corpusFrequencies.containsKey(ante)){
			Integer[] freq = corpusFrequencies.get(ante);
			alpha += freq[2];
			beta += freq[0] + freq[1] + freq[3];
		} else if(person != null) {
			if(corpusFrequencies.containsKey(person.toLowerCase())){
				Integer[] freq = corpusFrequencies.get(person.toLowerCase());
				alpha += freq[2];
				beta += freq[0] + freq[1] + freq[3];
			}

		} else {
			Token head = getHeadNoun(a.getAntecedent());
			if(head != null && corpusFrequencies.containsKey(head.getCoveredText().toLowerCase())){
				Integer[] freq = corpusFrequencies.get(head.getCoveredText().toLowerCase());
				alpha += freq[2];
				beta += freq[0] + freq[1] + freq[3];
			}else {
				for(Token t : covTokens){
					if(t.getPos().getPosValue().contains("NN")){
						if(corpusFrequencies.containsKey(t.getCoveredText().toLowerCase())){
							Integer[] freq = corpusFrequencies.get(t.getCoveredText().toLowerCase());
							alpha += freq[2];
							beta += freq[0] + freq[1] + freq[3];
							break;
						}
					}
				}
			}	
		}
	
		a.getGenderFeatures().setG_Neutral_Mean(getMean(alpha, beta));
		a.getGenderFeatures().setG_Neutral_Variance(getVariance(alpha, beta));
	}
	
	public void setPluralDistribution(Anaphora a){
		String ante = a.getAntecedent().getCoveredText().toLowerCase();
		int alpha = 1;
		int beta = 1;

		List<Token> covTokens = AnnotationUtils.getCoveredTokens(a.getAntecedent(), tokens);
		String person = null;
		for(Token t : covTokens){
			if(isPerson(t)){
				//Only take the first name (e.g. token) of a person
				person = t.getCoveredText();
			}
		}
		
		if(corpusFrequencies.containsKey(ante)){
			Integer[] freq = corpusFrequencies.get(ante);
			alpha += freq[3];
			beta += freq[0] + freq[1] + freq[2];
		} else if(person != null) {
			if(corpusFrequencies.containsKey(person.toLowerCase())){
				Integer[] freq = corpusFrequencies.get(person.toLowerCase());
				alpha += freq[3];
				beta += freq[0] + freq[1] + freq[2];
			}

		} else {
			Token head = getHeadNoun(a.getAntecedent());
			if(head != null && corpusFrequencies.containsKey(head.getCoveredText().toLowerCase())){
				Integer[] freq = corpusFrequencies.get(head.getCoveredText().toLowerCase());
				alpha += freq[3];
				beta += freq[0] + freq[1] + freq[2];
			}else {
				for(Token t : covTokens){
					if(t.getPos().getPosValue().contains("NN")){
						if(corpusFrequencies.containsKey(t.getCoveredText().toLowerCase())){
							Integer[] freq = corpusFrequencies.get(t.getCoveredText().toLowerCase());
							alpha += freq[3];
							beta += freq[0] + freq[1] + freq[2];
							break;
						}
					}
				}
			}
		}
		
		a.getGenderFeatures().setG_Plural_Mean(getMean(alpha, beta));
		a.getGenderFeatures().setG_Plural_Variance(getVariance(alpha, beta));
	}
	
	public float getMean(int alpha, int beta){
		float fAlpha = alpha;
		float fBeta = beta;
		return (fAlpha / (fAlpha + fBeta));
		
	}
	
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
	
	private boolean isPerson(Token token){
		for(NamedEntity n : namedEntities){
			if(n.getBegin() <= token.getBegin() && n.getEnd() >= token.getEnd() && n.getValue().equals("PERSON")){
				return true;
			}
		}
		return false;
	}
	
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
