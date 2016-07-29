
/* First created by JCasGen Fri Jul 29 14:01:49 CEST 2016 */
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
 * Updated by JCasGen Fri Jul 29 15:25:09 CEST 2016
 * @generated */
public class NegativeTrainingInstance_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (NegativeTrainingInstance_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = NegativeTrainingInstance_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new NegativeTrainingInstance(addr, NegativeTrainingInstance_Type.this);
  			   NegativeTrainingInstance_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new NegativeTrainingInstance(addr, NegativeTrainingInstance_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = NegativeTrainingInstance.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.unidue.henryvdv.ba.type.NegativeTrainingInstance");
 
  /** @generated */
  final Feature casFeat_anaphora;
  /** @generated */
  final int     casFeatCode_anaphora;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getAnaphora(int addr) {
        if (featOkTst && casFeat_anaphora == null)
      jcas.throwFeatMissing("anaphora", "de.unidue.henryvdv.ba.type.NegativeTrainingInstance");
    return ll_cas.ll_getRefValue(addr, casFeatCode_anaphora);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setAnaphora(int addr, int v) {
        if (featOkTst && casFeat_anaphora == null)
      jcas.throwFeatMissing("anaphora", "de.unidue.henryvdv.ba.type.NegativeTrainingInstance");
    ll_cas.ll_setRefValue(addr, casFeatCode_anaphora, v);}
    
  
 
  /** @generated */
  final Feature casFeat_p_A_InSameSentence;
  /** @generated */
  final int     casFeatCode_p_A_InSameSentence;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public boolean getP_A_InSameSentence(int addr) {
        if (featOkTst && casFeat_p_A_InSameSentence == null)
      jcas.throwFeatMissing("p_A_InSameSentence", "de.unidue.henryvdv.ba.type.NegativeTrainingInstance");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_p_A_InSameSentence);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setP_A_InSameSentence(int addr, boolean v) {
        if (featOkTst && casFeat_p_A_InSameSentence == null)
      jcas.throwFeatMissing("p_A_InSameSentence", "de.unidue.henryvdv.ba.type.NegativeTrainingInstance");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_p_A_InSameSentence, v);}
    
  
 
  /** @generated */
  final Feature casFeat_p_A_InPreviousSentence;
  /** @generated */
  final int     casFeatCode_p_A_InPreviousSentence;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public boolean getP_A_InPreviousSentence(int addr) {
        if (featOkTst && casFeat_p_A_InPreviousSentence == null)
      jcas.throwFeatMissing("p_A_InPreviousSentence", "de.unidue.henryvdv.ba.type.NegativeTrainingInstance");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_p_A_InPreviousSentence);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setP_A_InPreviousSentence(int addr, boolean v) {
        if (featOkTst && casFeat_p_A_InPreviousSentence == null)
      jcas.throwFeatMissing("p_A_InPreviousSentence", "de.unidue.henryvdv.ba.type.NegativeTrainingInstance");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_p_A_InPreviousSentence, v);}
    
  
 
  /** @generated */
  final Feature casFeat_p_A_SingularMatch;
  /** @generated */
  final int     casFeatCode_p_A_SingularMatch;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public boolean getP_A_SingularMatch(int addr) {
        if (featOkTst && casFeat_p_A_SingularMatch == null)
      jcas.throwFeatMissing("p_A_SingularMatch", "de.unidue.henryvdv.ba.type.NegativeTrainingInstance");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_p_A_SingularMatch);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setP_A_SingularMatch(int addr, boolean v) {
        if (featOkTst && casFeat_p_A_SingularMatch == null)
      jcas.throwFeatMissing("p_A_SingularMatch", "de.unidue.henryvdv.ba.type.NegativeTrainingInstance");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_p_A_SingularMatch, v);}
    
  
 
  /** @generated */
  final Feature casFeat_p_A_PluralMatch;
  /** @generated */
  final int     casFeatCode_p_A_PluralMatch;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public boolean getP_A_PluralMatch(int addr) {
        if (featOkTst && casFeat_p_A_PluralMatch == null)
      jcas.throwFeatMissing("p_A_PluralMatch", "de.unidue.henryvdv.ba.type.NegativeTrainingInstance");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_p_A_PluralMatch);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setP_A_PluralMatch(int addr, boolean v) {
        if (featOkTst && casFeat_p_A_PluralMatch == null)
      jcas.throwFeatMissing("p_A_PluralMatch", "de.unidue.henryvdv.ba.type.NegativeTrainingInstance");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_p_A_PluralMatch, v);}
    
  
 
  /** @generated */
  final Feature casFeat_p_A_IntraSentenceDiff;
  /** @generated */
  final int     casFeatCode_p_A_IntraSentenceDiff;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public float getP_A_IntraSentenceDiff(int addr) {
        if (featOkTst && casFeat_p_A_IntraSentenceDiff == null)
      jcas.throwFeatMissing("p_A_IntraSentenceDiff", "de.unidue.henryvdv.ba.type.NegativeTrainingInstance");
    return ll_cas.ll_getFloatValue(addr, casFeatCode_p_A_IntraSentenceDiff);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setP_A_IntraSentenceDiff(int addr, float v) {
        if (featOkTst && casFeat_p_A_IntraSentenceDiff == null)
      jcas.throwFeatMissing("p_A_IntraSentenceDiff", "de.unidue.henryvdv.ba.type.NegativeTrainingInstance");
    ll_cas.ll_setFloatValue(addr, casFeatCode_p_A_IntraSentenceDiff, v);}
    
  
 
  /** @generated */
  final Feature casFeat_p_A_InterSentenceDiff;
  /** @generated */
  final int     casFeatCode_p_A_InterSentenceDiff;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public float getP_A_InterSentenceDiff(int addr) {
        if (featOkTst && casFeat_p_A_InterSentenceDiff == null)
      jcas.throwFeatMissing("p_A_InterSentenceDiff", "de.unidue.henryvdv.ba.type.NegativeTrainingInstance");
    return ll_cas.ll_getFloatValue(addr, casFeatCode_p_A_InterSentenceDiff);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setP_A_InterSentenceDiff(int addr, float v) {
        if (featOkTst && casFeat_p_A_InterSentenceDiff == null)
      jcas.throwFeatMissing("p_A_InterSentenceDiff", "de.unidue.henryvdv.ba.type.NegativeTrainingInstance");
    ll_cas.ll_setFloatValue(addr, casFeatCode_p_A_InterSentenceDiff, v);}
    
  
 
  /** @generated */
  final Feature casFeat_p_A_PrepositionalParallel;
  /** @generated */
  final int     casFeatCode_p_A_PrepositionalParallel;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public boolean getP_A_PrepositionalParallel(int addr) {
        if (featOkTst && casFeat_p_A_PrepositionalParallel == null)
      jcas.throwFeatMissing("p_A_PrepositionalParallel", "de.unidue.henryvdv.ba.type.NegativeTrainingInstance");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_p_A_PrepositionalParallel);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setP_A_PrepositionalParallel(int addr, boolean v) {
        if (featOkTst && casFeat_p_A_PrepositionalParallel == null)
      jcas.throwFeatMissing("p_A_PrepositionalParallel", "de.unidue.henryvdv.ba.type.NegativeTrainingInstance");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_p_A_PrepositionalParallel, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public NegativeTrainingInstance_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_anaphora = jcas.getRequiredFeatureDE(casType, "anaphora", "de.unidue.henryvdv.ba.type.Anaphora", featOkTst);
    casFeatCode_anaphora  = (null == casFeat_anaphora) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_anaphora).getCode();

 
    casFeat_p_A_InSameSentence = jcas.getRequiredFeatureDE(casType, "p_A_InSameSentence", "uima.cas.Boolean", featOkTst);
    casFeatCode_p_A_InSameSentence  = (null == casFeat_p_A_InSameSentence) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_p_A_InSameSentence).getCode();

 
    casFeat_p_A_InPreviousSentence = jcas.getRequiredFeatureDE(casType, "p_A_InPreviousSentence", "uima.cas.Boolean", featOkTst);
    casFeatCode_p_A_InPreviousSentence  = (null == casFeat_p_A_InPreviousSentence) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_p_A_InPreviousSentence).getCode();

 
    casFeat_p_A_SingularMatch = jcas.getRequiredFeatureDE(casType, "p_A_SingularMatch", "uima.cas.Boolean", featOkTst);
    casFeatCode_p_A_SingularMatch  = (null == casFeat_p_A_SingularMatch) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_p_A_SingularMatch).getCode();

 
    casFeat_p_A_PluralMatch = jcas.getRequiredFeatureDE(casType, "p_A_PluralMatch", "uima.cas.Boolean", featOkTst);
    casFeatCode_p_A_PluralMatch  = (null == casFeat_p_A_PluralMatch) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_p_A_PluralMatch).getCode();

 
    casFeat_p_A_IntraSentenceDiff = jcas.getRequiredFeatureDE(casType, "p_A_IntraSentenceDiff", "uima.cas.Float", featOkTst);
    casFeatCode_p_A_IntraSentenceDiff  = (null == casFeat_p_A_IntraSentenceDiff) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_p_A_IntraSentenceDiff).getCode();

 
    casFeat_p_A_InterSentenceDiff = jcas.getRequiredFeatureDE(casType, "p_A_InterSentenceDiff", "uima.cas.Float", featOkTst);
    casFeatCode_p_A_InterSentenceDiff  = (null == casFeat_p_A_InterSentenceDiff) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_p_A_InterSentenceDiff).getCode();

 
    casFeat_p_A_PrepositionalParallel = jcas.getRequiredFeatureDE(casType, "p_A_PrepositionalParallel", "uima.cas.Boolean", featOkTst);
    casFeatCode_p_A_PrepositionalParallel  = (null == casFeat_p_A_PrepositionalParallel) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_p_A_PrepositionalParallel).getCode();

  }
}



    