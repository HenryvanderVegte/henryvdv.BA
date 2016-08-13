package de.unidue.henryvdv.ba.util;

import de.unidue.henryvdv.ba.type.Anaphora;

public class FeatureVectorUtils {

	private Anaphora currentAnaphora;
	private int currentFeatureCount;
	private String currentFeatureVector;
	
	public String createFeatureVector(Anaphora a){
		currentFeatureCount = 1;
		currentFeatureVector = "";
		currentAnaphora = a;	
		
		if(a.getHasCorrectAntecedent()){
			currentFeatureVector += "1";
		} else {
			currentFeatureVector += "-1";
		}
		
		addPA_SameSentence();
		addPA_IntraSentenceDiff();
		addPA_InPreviousSentence();
		addPA_InterSentenceDiff();
		addPA_PrepositionalParallel();
		addPA_ParentCatMatch();
		addPA_ParentWordMatch();
		addPA_QuotationSituation();
		addPA_SingularMatch();
		addPA_PluralMatch();
		
		addA_AntecedentFrequency();
		addA_Subject();
		addA_Object();
		addA_Predicate();
		addA_Pronominal();
		addA_HeadWordEmphasis();
		addA_Conjunction();
		addA_PrenominalModifier();
		addA_Org();
		addA_Person();
		
		return currentFeatureVector;
	}
	
	private void addPA_SameSentence(){
		addBinarizedFeature(currentAnaphora.getPronounAntecedentFeatures().getP_A_InSameSentence());
	}	
	
	private void addPA_IntraSentenceDiff(){
		addFloatFeature(currentAnaphora.getPronounAntecedentFeatures().getP_A_IntraSentenceDiff());
	}
	
	private void addPA_InPreviousSentence(){
		addBinarizedFeature(currentAnaphora.getPronounAntecedentFeatures().getP_A_InPreviousSentence());
	}
	
	private void addPA_InterSentenceDiff(){
		addFloatFeature(currentAnaphora.getPronounAntecedentFeatures().getP_A_InterSentenceDiff());
	}
	
	private void addPA_PrepositionalParallel(){
		addBinarizedFeature(currentAnaphora.getPronounAntecedentFeatures().getP_A_PrepositionalParallel());
	}
	
	private void addPA_ParentCatMatch(){
		addBinarizedFeature(currentAnaphora.getPronounAntecedentFeatures().getP_A_ParentCatMatch());
	}
	
	private void addPA_ParentWordMatch(){
		addBinarizedFeature(currentAnaphora.getPronounAntecedentFeatures().getP_A_ParentWordMatch());
	}
	
	private void addPA_QuotationSituation(){
		addBinarizedFeature(currentAnaphora.getPronounAntecedentFeatures().getP_A_QuotationSituation());
	}
	
	private void addPA_SingularMatch(){
		addBinarizedFeature(currentAnaphora.getPronounAntecedentFeatures().getP_A_SingularMatch());
	}
	
	private void addPA_PluralMatch(){
		addBinarizedFeature(currentAnaphora.getPronounAntecedentFeatures().getP_A_PluralMatch());
	}
	
	private void addA_AntecedentFrequency(){
		addFloatFeature(currentAnaphora.getAntecedentFeatures().getA_AntecedentFrequency());
	}
	
	private void addA_Subject(){
		addBinarizedFeature(currentAnaphora.getAntecedentFeatures().getA_Subject());
	}	
	
	private void addA_Object(){
		addBinarizedFeature(currentAnaphora.getAntecedentFeatures().getA_Object());
	}
	
	private void addA_Predicate(){
		addBinarizedFeature(currentAnaphora.getAntecedentFeatures().getA_Predicate());
	}
	
	private void addA_Pronominal(){
		addBinarizedFeature(currentAnaphora.getAntecedentFeatures().getA_Pronominal());
	}
	
	private void addA_HeadWordEmphasis(){
		addBinarizedFeature(currentAnaphora.getAntecedentFeatures().getA_HeadWordEmphasis());
	}
	
	private void addA_Conjunction(){
		addBinarizedFeature(currentAnaphora.getAntecedentFeatures().getA_Conjunction());
	}
	
	private void addA_PrenominalModifier(){
		addBinarizedFeature(currentAnaphora.getAntecedentFeatures().getA_PrenominalModifier());
	}
	
	private void addA_Org(){
		addBinarizedFeature(currentAnaphora.getAntecedentFeatures().getA_Org());
	}
	
	private void addA_Person(){
		addBinarizedFeature(currentAnaphora.getAntecedentFeatures().getA_Person());
	}
	
	private void addBinarizedFeature(boolean value){
		currentFeatureVector += " ";
		if(value){
			currentFeatureVector += currentFeatureCount + ":1";
		} else {
			currentFeatureVector += currentFeatureCount + ":0";
		}
		currentFeatureCount++;
	}
	
	private void addFloatFeature(float value){
		currentFeatureVector += " ";  
		currentFeatureVector += currentFeatureCount + ":" + value;
		currentFeatureCount++;
	}
	
}
