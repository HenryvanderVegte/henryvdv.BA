
/* First created by JCasGen Fri Sep 09 20:58:44 CEST 2016 */
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
 * Updated by JCasGen Thu Oct 13 22:34:10 CEST 2016
 * @generated */
public class GenderFeatures_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (GenderFeatures_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = GenderFeatures_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new GenderFeatures(addr, GenderFeatures_Type.this);
  			   GenderFeatures_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new GenderFeatures(addr, GenderFeatures_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = GenderFeatures.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.unidue.henryvdv.ba.type.GenderFeatures");
 
  /** @generated */
  final Feature casFeat_g_StdGenderMatch;
  /** @generated */
  final int     casFeatCode_g_StdGenderMatch;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public boolean getG_StdGenderMatch(int addr) {
        if (featOkTst && casFeat_g_StdGenderMatch == null)
      jcas.throwFeatMissing("g_StdGenderMatch", "de.unidue.henryvdv.ba.type.GenderFeatures");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_g_StdGenderMatch);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setG_StdGenderMatch(int addr, boolean v) {
        if (featOkTst && casFeat_g_StdGenderMatch == null)
      jcas.throwFeatMissing("g_StdGenderMatch", "de.unidue.henryvdv.ba.type.GenderFeatures");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_g_StdGenderMatch, v);}
    
  
 
  /** @generated */
  final Feature casFeat_g_StdGenderMismatch;
  /** @generated */
  final int     casFeatCode_g_StdGenderMismatch;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public boolean getG_StdGenderMismatch(int addr) {
        if (featOkTst && casFeat_g_StdGenderMismatch == null)
      jcas.throwFeatMissing("g_StdGenderMismatch", "de.unidue.henryvdv.ba.type.GenderFeatures");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_g_StdGenderMismatch);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setG_StdGenderMismatch(int addr, boolean v) {
        if (featOkTst && casFeat_g_StdGenderMismatch == null)
      jcas.throwFeatMissing("g_StdGenderMismatch", "de.unidue.henryvdv.ba.type.GenderFeatures");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_g_StdGenderMismatch, v);}
    
  
 
  /** @generated */
  final Feature casFeat_g_PronounMismatch;
  /** @generated */
  final int     casFeatCode_g_PronounMismatch;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public boolean getG_PronounMismatch(int addr) {
        if (featOkTst && casFeat_g_PronounMismatch == null)
      jcas.throwFeatMissing("g_PronounMismatch", "de.unidue.henryvdv.ba.type.GenderFeatures");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_g_PronounMismatch);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setG_PronounMismatch(int addr, boolean v) {
        if (featOkTst && casFeat_g_PronounMismatch == null)
      jcas.throwFeatMissing("g_PronounMismatch", "de.unidue.henryvdv.ba.type.GenderFeatures");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_g_PronounMismatch, v);}
    
  
 
  /** @generated */
  final Feature casFeat_g_Masculine_Mean;
  /** @generated */
  final int     casFeatCode_g_Masculine_Mean;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public float getG_Masculine_Mean(int addr) {
        if (featOkTst && casFeat_g_Masculine_Mean == null)
      jcas.throwFeatMissing("g_Masculine_Mean", "de.unidue.henryvdv.ba.type.GenderFeatures");
    return ll_cas.ll_getFloatValue(addr, casFeatCode_g_Masculine_Mean);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setG_Masculine_Mean(int addr, float v) {
        if (featOkTst && casFeat_g_Masculine_Mean == null)
      jcas.throwFeatMissing("g_Masculine_Mean", "de.unidue.henryvdv.ba.type.GenderFeatures");
    ll_cas.ll_setFloatValue(addr, casFeatCode_g_Masculine_Mean, v);}
    
  
 
  /** @generated */
  final Feature casFeat_g_Masculine_Variance;
  /** @generated */
  final int     casFeatCode_g_Masculine_Variance;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public float getG_Masculine_Variance(int addr) {
        if (featOkTst && casFeat_g_Masculine_Variance == null)
      jcas.throwFeatMissing("g_Masculine_Variance", "de.unidue.henryvdv.ba.type.GenderFeatures");
    return ll_cas.ll_getFloatValue(addr, casFeatCode_g_Masculine_Variance);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setG_Masculine_Variance(int addr, float v) {
        if (featOkTst && casFeat_g_Masculine_Variance == null)
      jcas.throwFeatMissing("g_Masculine_Variance", "de.unidue.henryvdv.ba.type.GenderFeatures");
    ll_cas.ll_setFloatValue(addr, casFeatCode_g_Masculine_Variance, v);}
    
  
 
  /** @generated */
  final Feature casFeat_g_Feminine_Mean;
  /** @generated */
  final int     casFeatCode_g_Feminine_Mean;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public float getG_Feminine_Mean(int addr) {
        if (featOkTst && casFeat_g_Feminine_Mean == null)
      jcas.throwFeatMissing("g_Feminine_Mean", "de.unidue.henryvdv.ba.type.GenderFeatures");
    return ll_cas.ll_getFloatValue(addr, casFeatCode_g_Feminine_Mean);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setG_Feminine_Mean(int addr, float v) {
        if (featOkTst && casFeat_g_Feminine_Mean == null)
      jcas.throwFeatMissing("g_Feminine_Mean", "de.unidue.henryvdv.ba.type.GenderFeatures");
    ll_cas.ll_setFloatValue(addr, casFeatCode_g_Feminine_Mean, v);}
    
  
 
  /** @generated */
  final Feature casFeat_g_Feminine_Variance;
  /** @generated */
  final int     casFeatCode_g_Feminine_Variance;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public float getG_Feminine_Variance(int addr) {
        if (featOkTst && casFeat_g_Feminine_Variance == null)
      jcas.throwFeatMissing("g_Feminine_Variance", "de.unidue.henryvdv.ba.type.GenderFeatures");
    return ll_cas.ll_getFloatValue(addr, casFeatCode_g_Feminine_Variance);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setG_Feminine_Variance(int addr, float v) {
        if (featOkTst && casFeat_g_Feminine_Variance == null)
      jcas.throwFeatMissing("g_Feminine_Variance", "de.unidue.henryvdv.ba.type.GenderFeatures");
    ll_cas.ll_setFloatValue(addr, casFeatCode_g_Feminine_Variance, v);}
    
  
 
  /** @generated */
  final Feature casFeat_g_Neutral_Mean;
  /** @generated */
  final int     casFeatCode_g_Neutral_Mean;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public float getG_Neutral_Mean(int addr) {
        if (featOkTst && casFeat_g_Neutral_Mean == null)
      jcas.throwFeatMissing("g_Neutral_Mean", "de.unidue.henryvdv.ba.type.GenderFeatures");
    return ll_cas.ll_getFloatValue(addr, casFeatCode_g_Neutral_Mean);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setG_Neutral_Mean(int addr, float v) {
        if (featOkTst && casFeat_g_Neutral_Mean == null)
      jcas.throwFeatMissing("g_Neutral_Mean", "de.unidue.henryvdv.ba.type.GenderFeatures");
    ll_cas.ll_setFloatValue(addr, casFeatCode_g_Neutral_Mean, v);}
    
  
 
  /** @generated */
  final Feature casFeat_g_Neutral_Variance;
  /** @generated */
  final int     casFeatCode_g_Neutral_Variance;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public float getG_Neutral_Variance(int addr) {
        if (featOkTst && casFeat_g_Neutral_Variance == null)
      jcas.throwFeatMissing("g_Neutral_Variance", "de.unidue.henryvdv.ba.type.GenderFeatures");
    return ll_cas.ll_getFloatValue(addr, casFeatCode_g_Neutral_Variance);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setG_Neutral_Variance(int addr, float v) {
        if (featOkTst && casFeat_g_Neutral_Variance == null)
      jcas.throwFeatMissing("g_Neutral_Variance", "de.unidue.henryvdv.ba.type.GenderFeatures");
    ll_cas.ll_setFloatValue(addr, casFeatCode_g_Neutral_Variance, v);}
    
  
 
  /** @generated */
  final Feature casFeat_g_Plural_Mean;
  /** @generated */
  final int     casFeatCode_g_Plural_Mean;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public float getG_Plural_Mean(int addr) {
        if (featOkTst && casFeat_g_Plural_Mean == null)
      jcas.throwFeatMissing("g_Plural_Mean", "de.unidue.henryvdv.ba.type.GenderFeatures");
    return ll_cas.ll_getFloatValue(addr, casFeatCode_g_Plural_Mean);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setG_Plural_Mean(int addr, float v) {
        if (featOkTst && casFeat_g_Plural_Mean == null)
      jcas.throwFeatMissing("g_Plural_Mean", "de.unidue.henryvdv.ba.type.GenderFeatures");
    ll_cas.ll_setFloatValue(addr, casFeatCode_g_Plural_Mean, v);}
    
  
 
  /** @generated */
  final Feature casFeat_g_Plural_Variance;
  /** @generated */
  final int     casFeatCode_g_Plural_Variance;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public float getG_Plural_Variance(int addr) {
        if (featOkTst && casFeat_g_Plural_Variance == null)
      jcas.throwFeatMissing("g_Plural_Variance", "de.unidue.henryvdv.ba.type.GenderFeatures");
    return ll_cas.ll_getFloatValue(addr, casFeatCode_g_Plural_Variance);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setG_Plural_Variance(int addr, float v) {
        if (featOkTst && casFeat_g_Plural_Variance == null)
      jcas.throwFeatMissing("g_Plural_Variance", "de.unidue.henryvdv.ba.type.GenderFeatures");
    ll_cas.ll_setFloatValue(addr, casFeatCode_g_Plural_Variance, v);}
    
  
 
  /** @generated */
  final Feature casFeat_g_Masculine_HardConstraint;
  /** @generated */
  final int     casFeatCode_g_Masculine_HardConstraint;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public boolean getG_Masculine_HardConstraint(int addr) {
        if (featOkTst && casFeat_g_Masculine_HardConstraint == null)
      jcas.throwFeatMissing("g_Masculine_HardConstraint", "de.unidue.henryvdv.ba.type.GenderFeatures");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_g_Masculine_HardConstraint);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setG_Masculine_HardConstraint(int addr, boolean v) {
        if (featOkTst && casFeat_g_Masculine_HardConstraint == null)
      jcas.throwFeatMissing("g_Masculine_HardConstraint", "de.unidue.henryvdv.ba.type.GenderFeatures");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_g_Masculine_HardConstraint, v);}
    
  
 
  /** @generated */
  final Feature casFeat_g_Feminine_HardConstraint;
  /** @generated */
  final int     casFeatCode_g_Feminine_HardConstraint;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public boolean getG_Feminine_HardConstraint(int addr) {
        if (featOkTst && casFeat_g_Feminine_HardConstraint == null)
      jcas.throwFeatMissing("g_Feminine_HardConstraint", "de.unidue.henryvdv.ba.type.GenderFeatures");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_g_Feminine_HardConstraint);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setG_Feminine_HardConstraint(int addr, boolean v) {
        if (featOkTst && casFeat_g_Feminine_HardConstraint == null)
      jcas.throwFeatMissing("g_Feminine_HardConstraint", "de.unidue.henryvdv.ba.type.GenderFeatures");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_g_Feminine_HardConstraint, v);}
    
  
 
  /** @generated */
  final Feature casFeat_g_Neutral_HardConstraint;
  /** @generated */
  final int     casFeatCode_g_Neutral_HardConstraint;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public boolean getG_Neutral_HardConstraint(int addr) {
        if (featOkTst && casFeat_g_Neutral_HardConstraint == null)
      jcas.throwFeatMissing("g_Neutral_HardConstraint", "de.unidue.henryvdv.ba.type.GenderFeatures");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_g_Neutral_HardConstraint);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setG_Neutral_HardConstraint(int addr, boolean v) {
        if (featOkTst && casFeat_g_Neutral_HardConstraint == null)
      jcas.throwFeatMissing("g_Neutral_HardConstraint", "de.unidue.henryvdv.ba.type.GenderFeatures");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_g_Neutral_HardConstraint, v);}
    
  
 
  /** @generated */
  final Feature casFeat_g_Plural_HardConstraint;
  /** @generated */
  final int     casFeatCode_g_Plural_HardConstraint;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public boolean getG_Plural_HardConstraint(int addr) {
        if (featOkTst && casFeat_g_Plural_HardConstraint == null)
      jcas.throwFeatMissing("g_Plural_HardConstraint", "de.unidue.henryvdv.ba.type.GenderFeatures");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_g_Plural_HardConstraint);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setG_Plural_HardConstraint(int addr, boolean v) {
        if (featOkTst && casFeat_g_Plural_HardConstraint == null)
      jcas.throwFeatMissing("g_Plural_HardConstraint", "de.unidue.henryvdv.ba.type.GenderFeatures");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_g_Plural_HardConstraint, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public GenderFeatures_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_g_StdGenderMatch = jcas.getRequiredFeatureDE(casType, "g_StdGenderMatch", "uima.cas.Boolean", featOkTst);
    casFeatCode_g_StdGenderMatch  = (null == casFeat_g_StdGenderMatch) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_g_StdGenderMatch).getCode();

 
    casFeat_g_StdGenderMismatch = jcas.getRequiredFeatureDE(casType, "g_StdGenderMismatch", "uima.cas.Boolean", featOkTst);
    casFeatCode_g_StdGenderMismatch  = (null == casFeat_g_StdGenderMismatch) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_g_StdGenderMismatch).getCode();

 
    casFeat_g_PronounMismatch = jcas.getRequiredFeatureDE(casType, "g_PronounMismatch", "uima.cas.Boolean", featOkTst);
    casFeatCode_g_PronounMismatch  = (null == casFeat_g_PronounMismatch) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_g_PronounMismatch).getCode();

 
    casFeat_g_Masculine_Mean = jcas.getRequiredFeatureDE(casType, "g_Masculine_Mean", "uima.cas.Float", featOkTst);
    casFeatCode_g_Masculine_Mean  = (null == casFeat_g_Masculine_Mean) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_g_Masculine_Mean).getCode();

 
    casFeat_g_Masculine_Variance = jcas.getRequiredFeatureDE(casType, "g_Masculine_Variance", "uima.cas.Float", featOkTst);
    casFeatCode_g_Masculine_Variance  = (null == casFeat_g_Masculine_Variance) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_g_Masculine_Variance).getCode();

 
    casFeat_g_Feminine_Mean = jcas.getRequiredFeatureDE(casType, "g_Feminine_Mean", "uima.cas.Float", featOkTst);
    casFeatCode_g_Feminine_Mean  = (null == casFeat_g_Feminine_Mean) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_g_Feminine_Mean).getCode();

 
    casFeat_g_Feminine_Variance = jcas.getRequiredFeatureDE(casType, "g_Feminine_Variance", "uima.cas.Float", featOkTst);
    casFeatCode_g_Feminine_Variance  = (null == casFeat_g_Feminine_Variance) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_g_Feminine_Variance).getCode();

 
    casFeat_g_Neutral_Mean = jcas.getRequiredFeatureDE(casType, "g_Neutral_Mean", "uima.cas.Float", featOkTst);
    casFeatCode_g_Neutral_Mean  = (null == casFeat_g_Neutral_Mean) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_g_Neutral_Mean).getCode();

 
    casFeat_g_Neutral_Variance = jcas.getRequiredFeatureDE(casType, "g_Neutral_Variance", "uima.cas.Float", featOkTst);
    casFeatCode_g_Neutral_Variance  = (null == casFeat_g_Neutral_Variance) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_g_Neutral_Variance).getCode();

 
    casFeat_g_Plural_Mean = jcas.getRequiredFeatureDE(casType, "g_Plural_Mean", "uima.cas.Float", featOkTst);
    casFeatCode_g_Plural_Mean  = (null == casFeat_g_Plural_Mean) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_g_Plural_Mean).getCode();

 
    casFeat_g_Plural_Variance = jcas.getRequiredFeatureDE(casType, "g_Plural_Variance", "uima.cas.Float", featOkTst);
    casFeatCode_g_Plural_Variance  = (null == casFeat_g_Plural_Variance) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_g_Plural_Variance).getCode();

 
    casFeat_g_Masculine_HardConstraint = jcas.getRequiredFeatureDE(casType, "g_Masculine_HardConstraint", "uima.cas.Boolean", featOkTst);
    casFeatCode_g_Masculine_HardConstraint  = (null == casFeat_g_Masculine_HardConstraint) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_g_Masculine_HardConstraint).getCode();

 
    casFeat_g_Feminine_HardConstraint = jcas.getRequiredFeatureDE(casType, "g_Feminine_HardConstraint", "uima.cas.Boolean", featOkTst);
    casFeatCode_g_Feminine_HardConstraint  = (null == casFeat_g_Feminine_HardConstraint) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_g_Feminine_HardConstraint).getCode();

 
    casFeat_g_Neutral_HardConstraint = jcas.getRequiredFeatureDE(casType, "g_Neutral_HardConstraint", "uima.cas.Boolean", featOkTst);
    casFeatCode_g_Neutral_HardConstraint  = (null == casFeat_g_Neutral_HardConstraint) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_g_Neutral_HardConstraint).getCode();

 
    casFeat_g_Plural_HardConstraint = jcas.getRequiredFeatureDE(casType, "g_Plural_HardConstraint", "uima.cas.Boolean", featOkTst);
    casFeatCode_g_Plural_HardConstraint  = (null == casFeat_g_Plural_HardConstraint) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_g_Plural_HardConstraint).getCode();

  }
}



    