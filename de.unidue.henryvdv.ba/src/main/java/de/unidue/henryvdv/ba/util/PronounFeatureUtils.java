package de.unidue.henryvdv.ba.util;

import org.apache.uima.jcas.JCas;

import de.unidue.henryvdv.ba.type.Anaphora;

public class PronounFeatureUtils {

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
		if(text.equals("himself") || text.equals("his") || text.equals("he")){
			return true;
		}
		return false;
	}
	
	public boolean feminine(Anaphora a){
		String text = a.getCoveredText().toLowerCase();
		if(text.equals("herself") || text.equals("her") || text.equals("she")){
			return true;
		}
		return false;
	}
	
	public boolean neutral(Anaphora a){
		String text = a.getCoveredText().toLowerCase();
		if(text.equals("itself") || text.equals("its") || text.equals("it")){
			return true;
		}
		return false;
	}
	
	public boolean plural(Anaphora a){
		String text = a.getCoveredText().toLowerCase();
		if(text.equals("themselves") || text.equals("their") || text.equals("they")){
			return true;
		}
		return false;
	}
}
