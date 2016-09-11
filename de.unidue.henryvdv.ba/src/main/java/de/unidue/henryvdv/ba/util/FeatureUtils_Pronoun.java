package de.unidue.henryvdv.ba.util;

import org.apache.uima.jcas.JCas;

import de.unidue.henryvdv.ba.param.Parameters;
import de.unidue.henryvdv.ba.type.Anaphora;

public class FeatureUtils_Pronoun {

	public void annotateFeatures(Anaphora a){
		// Masculine
		a.getPronounFeatures().setP_Masculine(masculine(a));
		// Feminine
		a.getPronounFeatures().setP_Feminine(feminine(a));
		// Neutral
		a.getPronounFeatures().setP_Neutral(neutral(a));
		// Plural
		a.getPronounFeatures().setP_Plural(plural(a));	
	}
	
	public boolean masculine(Anaphora a){
		String text = a.getCoveredText().toLowerCase();
		
		for(int i = 0; i < Parameters.malePronouns.length; i++){
			if(text.equals(Parameters.malePronouns[i]))
				return true;
		}
		return false;
	}
	
	public boolean feminine(Anaphora a){
		String text = a.getCoveredText().toLowerCase();
		
		for(int i = 0; i < Parameters.femalePronouns.length; i++){
			if(text.equals(Parameters.femalePronouns[i]))
				return true;
		}
		return false;
	}
	
	public boolean neutral(Anaphora a){
		String text = a.getCoveredText().toLowerCase();
		for(int i = 0; i < Parameters.neutralPronouns.length; i++){
			if(text.equals(Parameters.neutralPronouns[i]))
				return true;
		}
		return false;
	}
	
	public boolean plural(Anaphora a){
		String text = a.getCoveredText().toLowerCase();
		for(int i = 0; i < Parameters.pluralPronouns.length; i++){
			if(text.equals(Parameters.pluralPronouns[i]))
				return true;
		}
		return false;
	}
}
