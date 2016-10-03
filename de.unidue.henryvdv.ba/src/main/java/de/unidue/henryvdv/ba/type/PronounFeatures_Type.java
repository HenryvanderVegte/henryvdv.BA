
/* First created by JCasGen Sun Aug 14 23:39:14 CEST 2016 */
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
 * Updated by JCasGen Mon Oct 03 11:55:09 CEST 2016
 * @generated */
public class PronounFeatures_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (PronounFeatures_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = PronounFeatures_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new PronounFeatures(addr, PronounFeatures_Type.this);
  			   PronounFeatures_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new PronounFeatures(addr, PronounFeatures_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = PronounFeatures.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.unidue.henryvdv.ba.type.PronounFeatures");
 
  /** @generated */
  final Feature casFeat_p_Masculine;
  /** @generated */
  final int     casFeatCode_p_Masculine;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public boolean getP_Masculine(int addr) {
        if (featOkTst && casFeat_p_Masculine == null)
      jcas.throwFeatMissing("p_Masculine", "de.unidue.henryvdv.ba.type.PronounFeatures");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_p_Masculine);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setP_Masculine(int addr, boolean v) {
        if (featOkTst && casFeat_p_Masculine == null)
      jcas.throwFeatMissing("p_Masculine", "de.unidue.henryvdv.ba.type.PronounFeatures");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_p_Masculine, v);}
    
  
 
  /** @generated */
  final Feature casFeat_p_Feminine;
  /** @generated */
  final int     casFeatCode_p_Feminine;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public boolean getP_Feminine(int addr) {
        if (featOkTst && casFeat_p_Feminine == null)
      jcas.throwFeatMissing("p_Feminine", "de.unidue.henryvdv.ba.type.PronounFeatures");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_p_Feminine);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setP_Feminine(int addr, boolean v) {
        if (featOkTst && casFeat_p_Feminine == null)
      jcas.throwFeatMissing("p_Feminine", "de.unidue.henryvdv.ba.type.PronounFeatures");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_p_Feminine, v);}
    
  
 
  /** @generated */
  final Feature casFeat_p_Neutral;
  /** @generated */
  final int     casFeatCode_p_Neutral;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public boolean getP_Neutral(int addr) {
        if (featOkTst && casFeat_p_Neutral == null)
      jcas.throwFeatMissing("p_Neutral", "de.unidue.henryvdv.ba.type.PronounFeatures");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_p_Neutral);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setP_Neutral(int addr, boolean v) {
        if (featOkTst && casFeat_p_Neutral == null)
      jcas.throwFeatMissing("p_Neutral", "de.unidue.henryvdv.ba.type.PronounFeatures");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_p_Neutral, v);}
    
  
 
  /** @generated */
  final Feature casFeat_p_Plural;
  /** @generated */
  final int     casFeatCode_p_Plural;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public boolean getP_Plural(int addr) {
        if (featOkTst && casFeat_p_Plural == null)
      jcas.throwFeatMissing("p_Plural", "de.unidue.henryvdv.ba.type.PronounFeatures");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_p_Plural);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setP_Plural(int addr, boolean v) {
        if (featOkTst && casFeat_p_Plural == null)
      jcas.throwFeatMissing("p_Plural", "de.unidue.henryvdv.ba.type.PronounFeatures");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_p_Plural, v);}
    
  
 
  /** @generated */
  final Feature casFeat_p_Reflexive;
  /** @generated */
  final int     casFeatCode_p_Reflexive;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public boolean getP_Reflexive(int addr) {
        if (featOkTst && casFeat_p_Reflexive == null)
      jcas.throwFeatMissing("p_Reflexive", "de.unidue.henryvdv.ba.type.PronounFeatures");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_p_Reflexive);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setP_Reflexive(int addr, boolean v) {
        if (featOkTst && casFeat_p_Reflexive == null)
      jcas.throwFeatMissing("p_Reflexive", "de.unidue.henryvdv.ba.type.PronounFeatures");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_p_Reflexive, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public PronounFeatures_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_p_Masculine = jcas.getRequiredFeatureDE(casType, "p_Masculine", "uima.cas.Boolean", featOkTst);
    casFeatCode_p_Masculine  = (null == casFeat_p_Masculine) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_p_Masculine).getCode();

 
    casFeat_p_Feminine = jcas.getRequiredFeatureDE(casType, "p_Feminine", "uima.cas.Boolean", featOkTst);
    casFeatCode_p_Feminine  = (null == casFeat_p_Feminine) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_p_Feminine).getCode();

 
    casFeat_p_Neutral = jcas.getRequiredFeatureDE(casType, "p_Neutral", "uima.cas.Boolean", featOkTst);
    casFeatCode_p_Neutral  = (null == casFeat_p_Neutral) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_p_Neutral).getCode();

 
    casFeat_p_Plural = jcas.getRequiredFeatureDE(casType, "p_Plural", "uima.cas.Boolean", featOkTst);
    casFeatCode_p_Plural  = (null == casFeat_p_Plural) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_p_Plural).getCode();

 
    casFeat_p_Reflexive = jcas.getRequiredFeatureDE(casType, "p_Reflexive", "uima.cas.Boolean", featOkTst);
    casFeatCode_p_Reflexive  = (null == casFeat_p_Reflexive) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_p_Reflexive).getCode();

  }
}



    