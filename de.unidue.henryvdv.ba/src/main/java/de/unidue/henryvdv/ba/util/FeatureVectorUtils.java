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
		
		//Pronoun-Antecedent-Features (10)
		
		addPA_SameSentence(); 		
		addPA_IntraSentenceDiff(); 
		addPA_InPreviousSentence();
		addPA_InterSentenceDiff();
		addPA_PrepositionalParallel();   //5
//->überarbeiten		addPA_ParentCatMatch(); 
//->überarbeiten		addPA_ParentWordMatch();
		addPA_QuotationSituation();
		addPA_SingularMatch();
		addPA_PluralMatch();			//10
		
		//Antecedent Features (17)
		
		addA_AntecedentFrequency();
		addA_Subject();
		addA_Object();
		addA_Predicate();
		addA_Pronominal();				//15
		addA_HeadWordEmphasis();
		addA_Conjunction();
		addA_PrenominalModifier();
		addA_Org();
		addA_Person();					//20
		addA_Time();
		addA_Date();
		addA_Money();
		addA_Number();
		addA_Definite();				//25
		addA_HisHer();
		addA_HeHis();

		//Pronoun Features (4)
		addP_Masculine();
		addP_Feminine();
		addP_Neutral();					//30
		addP_Plural();
//		addP_Reflexive();
		
		//Gender Features (11)
	
		addG_StdGenderMatch();
		addG_StdGenderMismatch();
		addG_PronounMismatch();
		addG_MasculineMean();			//35
		addG_MasculineVariance();
		addG_FeminineMean();
		addG_FeminineVariance();
		addG_NeutralMean();
		addG_NeutralVariance();			//40
		addG_PluralMean();
		addG_PluralVariance();
		
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
	
	private void addA_Time(){
		addBinarizedFeature(currentAnaphora.getAntecedentFeatures().getA_Time());
	}
	
	private void addA_Date(){
		addBinarizedFeature(currentAnaphora.getAntecedentFeatures().getA_Date());
	}
	
	private void addA_Money(){
		addBinarizedFeature(currentAnaphora.getAntecedentFeatures().getA_Money());
	}
	
	private void addA_Number(){
		addBinarizedFeature(currentAnaphora.getAntecedentFeatures().getA_Number());
	}
	
	private void addA_Definite(){
		addBinarizedFeature(currentAnaphora.getAntecedentFeatures().getA_Definite());
	}
	
	private void addA_HisHer(){
		addBinarizedFeature(currentAnaphora.getAntecedentFeatures().getA_HisHer());
	}
	
	private void addA_HeHis(){
		addBinarizedFeature(currentAnaphora.getAntecedentFeatures().getA_HeHis());
	}
	
	private void addP_Masculine(){
		addBinarizedFeature(currentAnaphora.getPronounFeatures().getP_Masculine());
	}
	
	private void addP_Feminine(){
		addBinarizedFeature(currentAnaphora.getPronounFeatures().getP_Feminine());
	}
	
	private void addP_Neutral(){
		addBinarizedFeature(currentAnaphora.getPronounFeatures().getP_Neutral());
	}
	
	private void addP_Plural(){
		addBinarizedFeature(currentAnaphora.getPronounFeatures().getP_Plural());
	}
	
	private void addP_Reflexive(){
		addBinarizedFeature(currentAnaphora.getPronounFeatures().getP_Reflexive());
	}
	
	
	private void addG_StdGenderMatch(){
		addBinarizedFeature(currentAnaphora.getGenderFeatures().getG_StdGenderMatch());
	}
	private void addG_StdGenderMismatch(){
		addBinarizedFeature(currentAnaphora.getGenderFeatures().getG_StdGenderMismatch());
	}
	private void addG_PronounMismatch(){
		addBinarizedFeature(currentAnaphora.getGenderFeatures().getG_PronounMismatch());
	}
	
	private void addG_MasculineMean(){
		addFloatFeature(currentAnaphora.getGenderFeatures().getG_Masculine_Mean());
	}
	
	private void addG_MasculineVariance(){
		addFloatFeature(currentAnaphora.getGenderFeatures().getG_Masculine_Variance());
	}
	
	private void addG_FeminineMean(){
		addFloatFeature(currentAnaphora.getGenderFeatures().getG_Feminine_Mean());
	}
	
	private void addG_FeminineVariance(){
		addFloatFeature(currentAnaphora.getGenderFeatures().getG_Feminine_Variance());
	}
	
	private void addG_NeutralMean(){
		addFloatFeature(currentAnaphora.getGenderFeatures().getG_Neutral_Mean());
	}
	
	private void addG_NeutralVariance(){
		addFloatFeature(currentAnaphora.getGenderFeatures().getG_Neutral_Variance());
	}
	
	private void addG_PluralMean(){
		addFloatFeature(currentAnaphora.getGenderFeatures().getG_Plural_Mean());
	}
	
	private void addG_PluralVariance(){
		addFloatFeature(currentAnaphora.getGenderFeatures().getG_Plural_Variance());
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
