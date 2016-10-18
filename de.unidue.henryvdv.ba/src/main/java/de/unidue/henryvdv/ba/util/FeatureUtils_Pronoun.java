package de.unidue.henryvdv.ba.util;

import java.util.Arrays;

import org.apache.uima.jcas.JCas;

import de.unidue.henryvdv.ba.param.Parameters;
import de.unidue.henryvdv.ba.type.Anaphora;
/**
 * Class for the assignment of all pronoun features
 * for a given anaphora
 * @author Henry
 *
 */
public class FeatureUtils_Pronoun {
	
	/************************************
	 * 	Assigns the pronoun features:	*
	 * 									*
	 * 	-Masculine(bool)				*					
	 * 	-Feminine(bool)					*
	 * 	-Neutral (bool)					*
	 * 	-Plural (bool					*
	 * 									*
	 * 	+My own features				*
	 * 	-Reflexive(float)				*
	 ************************************/

	public void annotateFeatures(Anaphora a){
		// Masculine
		a.getPronounFeatures().setP_Masculine(masculine(a));
		// Feminine
		a.getPronounFeatures().setP_Feminine(feminine(a));
		// Neutral
		a.getPronounFeatures().setP_Neutral(neutral(a));
		// Plural
		a.getPronounFeatures().setP_Plural(plural(a));	
		
		// My own features:
		// Reflexive
		a.getPronounFeatures().setP_Reflexive(isReflexive(a));
	}
	/**
	 * if the anaphora is a masculine pronoun
	 * @param anaphora Anaphora
	 * @return value
	 */
	public boolean masculine(Anaphora anaphora){
		if(Arrays.asList(Parameters.malePronouns).contains(anaphora.getCoveredText().toLowerCase())){
			return true;
		}
		return false;
	}
	/**
	 * if the anaphora is a feminine pronoun
	 * @param anaphora Anaphora
	 * @return value
	 */
	public boolean feminine(Anaphora anaphora){
		if(Arrays.asList(Parameters.femalePronouns).contains(anaphora.getCoveredText().toLowerCase())){
			return true;
		}
		return false;
	}
	/**
	 * if the anaphora is a neutral pronoun
	 * @param anaphora Anaphora
	 * @return value
	 */
	public boolean neutral(Anaphora anaphora){
		if(Arrays.asList(Parameters.neutralPronouns).contains(anaphora.getCoveredText().toLowerCase())){
			return true;
		}
		return false;
	}
	/**
	 * if the anaphora is a plural pronoun
	 * @param anaphora Anaphora
	 * @return value
	 */
	public boolean plural(Anaphora anaphora){
		if(Arrays.asList(Parameters.pluralPronouns).contains(anaphora.getCoveredText().toLowerCase())){
			return true;
		}
		return false;
	}
	/**
	 * if the anaphora is a reflexive pronoun
	 * @param anaphora Anaphora
	 * @return value
	 */
	public boolean isReflexive(Anaphora anaphora){
		if(Arrays.asList(Parameters.reflexivePronouns).contains(anaphora.getCoveredText().toLowerCase())){
			return true;
		}
		return false;
	}
}
