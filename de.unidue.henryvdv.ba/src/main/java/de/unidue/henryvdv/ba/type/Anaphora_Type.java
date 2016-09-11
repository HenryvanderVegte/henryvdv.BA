
/* First created by JCasGen Thu Jul 28 18:45:35 CEST 2016 */
package de.unidue.henryvdv.ba.type;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** 
 * Updated by JCasGen Fri Sep 09 21:03:11 CEST 2016
 * @generated */
public class Anaphora_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Anaphora_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Anaphora_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Anaphora(addr, Anaphora_Type.this);
  			   Anaphora_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Anaphora(addr, Anaphora_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Anaphora.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.unidue.henryvdv.ba.type.Anaphora");
 
  /** @generated */
  final Feature casFeat_antecedent;
  /** @generated */
  final int     casFeatCode_antecedent;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getAntecedent(int addr) {
        if (featOkTst && casFeat_antecedent == null)
      jcas.throwFeatMissing("antecedent", "de.unidue.henryvdv.ba.type.Anaphora");
    return ll_cas.ll_getRefValue(addr, casFeatCode_antecedent);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setAntecedent(int addr, int v) {
        if (featOkTst && casFeat_antecedent == null)
      jcas.throwFeatMissing("antecedent", "de.unidue.henryvdv.ba.type.Anaphora");
    ll_cas.ll_setRefValue(addr, casFeatCode_antecedent, v);}
    
  
 
  /** @generated */
  final Feature casFeat_antecedentFeatures;
  /** @generated */
  final int     casFeatCode_antecedentFeatures;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getAntecedentFeatures(int addr) {
        if (featOkTst && casFeat_antecedentFeatures == null)
      jcas.throwFeatMissing("antecedentFeatures", "de.unidue.henryvdv.ba.type.Anaphora");
    return ll_cas.ll_getRefValue(addr, casFeatCode_antecedentFeatures);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setAntecedentFeatures(int addr, int v) {
        if (featOkTst && casFeat_antecedentFeatures == null)
      jcas.throwFeatMissing("antecedentFeatures", "de.unidue.henryvdv.ba.type.Anaphora");
    ll_cas.ll_setRefValue(addr, casFeatCode_antecedentFeatures, v);}
    
  
 
  /** @generated */
  final Feature casFeat_pronounAntecedentFeatures;
  /** @generated */
  final int     casFeatCode_pronounAntecedentFeatures;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getPronounAntecedentFeatures(int addr) {
        if (featOkTst && casFeat_pronounAntecedentFeatures == null)
      jcas.throwFeatMissing("pronounAntecedentFeatures", "de.unidue.henryvdv.ba.type.Anaphora");
    return ll_cas.ll_getRefValue(addr, casFeatCode_pronounAntecedentFeatures);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setPronounAntecedentFeatures(int addr, int v) {
        if (featOkTst && casFeat_pronounAntecedentFeatures == null)
      jcas.throwFeatMissing("pronounAntecedentFeatures", "de.unidue.henryvdv.ba.type.Anaphora");
    ll_cas.ll_setRefValue(addr, casFeatCode_pronounAntecedentFeatures, v);}
    
  
 
  /** @generated */
  final Feature casFeat_hasCorrectAntecedent;
  /** @generated */
  final int     casFeatCode_hasCorrectAntecedent;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public boolean getHasCorrectAntecedent(int addr) {
        if (featOkTst && casFeat_hasCorrectAntecedent == null)
      jcas.throwFeatMissing("hasCorrectAntecedent", "de.unidue.henryvdv.ba.type.Anaphora");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_hasCorrectAntecedent);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setHasCorrectAntecedent(int addr, boolean v) {
        if (featOkTst && casFeat_hasCorrectAntecedent == null)
      jcas.throwFeatMissing("hasCorrectAntecedent", "de.unidue.henryvdv.ba.type.Anaphora");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_hasCorrectAntecedent, v);}
    
  
 
  /** @generated */
  final Feature casFeat_pronounFeatures;
  /** @generated */
  final int     casFeatCode_pronounFeatures;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getPronounFeatures(int addr) {
        if (featOkTst && casFeat_pronounFeatures == null)
      jcas.throwFeatMissing("pronounFeatures", "de.unidue.henryvdv.ba.type.Anaphora");
    return ll_cas.ll_getRefValue(addr, casFeatCode_pronounFeatures);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setPronounFeatures(int addr, int v) {
        if (featOkTst && casFeat_pronounFeatures == null)
      jcas.throwFeatMissing("pronounFeatures", "de.unidue.henryvdv.ba.type.Anaphora");
    ll_cas.ll_setRefValue(addr, casFeatCode_pronounFeatures, v);}
    
  
 
  /** @generated */
  final Feature casFeat_genderFeatures;
  /** @generated */
  final int     casFeatCode_genderFeatures;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getGenderFeatures(int addr) {
        if (featOkTst && casFeat_genderFeatures == null)
      jcas.throwFeatMissing("genderFeatures", "de.unidue.henryvdv.ba.type.Anaphora");
    return ll_cas.ll_getRefValue(addr, casFeatCode_genderFeatures);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setGenderFeatures(int addr, int v) {
        if (featOkTst && casFeat_genderFeatures == null)
      jcas.throwFeatMissing("genderFeatures", "de.unidue.henryvdv.ba.type.Anaphora");
    ll_cas.ll_setRefValue(addr, casFeatCode_genderFeatures, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public Anaphora_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_antecedent = jcas.getRequiredFeatureDE(casType, "antecedent", "de.unidue.henryvdv.ba.type.Antecedent", featOkTst);
    casFeatCode_antecedent  = (null == casFeat_antecedent) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_antecedent).getCode();

 
    casFeat_antecedentFeatures = jcas.getRequiredFeatureDE(casType, "antecedentFeatures", "de.unidue.henryvdv.ba.type.AntecedentFeatures", featOkTst);
    casFeatCode_antecedentFeatures  = (null == casFeat_antecedentFeatures) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_antecedentFeatures).getCode();

 
    casFeat_pronounAntecedentFeatures = jcas.getRequiredFeatureDE(casType, "pronounAntecedentFeatures", "de.unidue.henryvdv.ba.type.PronounAntecedentFeatures", featOkTst);
    casFeatCode_pronounAntecedentFeatures  = (null == casFeat_pronounAntecedentFeatures) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_pronounAntecedentFeatures).getCode();

 
    casFeat_hasCorrectAntecedent = jcas.getRequiredFeatureDE(casType, "hasCorrectAntecedent", "uima.cas.Boolean", featOkTst);
    casFeatCode_hasCorrectAntecedent  = (null == casFeat_hasCorrectAntecedent) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_hasCorrectAntecedent).getCode();

 
    casFeat_pronounFeatures = jcas.getRequiredFeatureDE(casType, "pronounFeatures", "de.unidue.henryvdv.ba.type.PronounFeatures", featOkTst);
    casFeatCode_pronounFeatures  = (null == casFeat_pronounFeatures) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_pronounFeatures).getCode();

 
    casFeat_genderFeatures = jcas.getRequiredFeatureDE(casType, "genderFeatures", "de.unidue.henryvdv.ba.type.GenderFeatures", featOkTst);
    casFeatCode_genderFeatures  = (null == casFeat_genderFeatures) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_genderFeatures).getCode();

  }
}



    