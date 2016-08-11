
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
 * Updated by JCasGen Wed Aug 10 13:46:48 CEST 2016
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
  final Feature casFeat_p_A_InSameSentence;
  /** @generated */
  final int     casFeatCode_p_A_InSameSentence;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public boolean getP_A_InSameSentence(int addr) {
        if (featOkTst && casFeat_p_A_InSameSentence == null)
      jcas.throwFeatMissing("p_A_InSameSentence", "de.unidue.henryvdv.ba.type.Anaphora");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_p_A_InSameSentence);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setP_A_InSameSentence(int addr, boolean v) {
        if (featOkTst && casFeat_p_A_InSameSentence == null)
      jcas.throwFeatMissing("p_A_InSameSentence", "de.unidue.henryvdv.ba.type.Anaphora");
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
      jcas.throwFeatMissing("p_A_InPreviousSentence", "de.unidue.henryvdv.ba.type.Anaphora");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_p_A_InPreviousSentence);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setP_A_InPreviousSentence(int addr, boolean v) {
        if (featOkTst && casFeat_p_A_InPreviousSentence == null)
      jcas.throwFeatMissing("p_A_InPreviousSentence", "de.unidue.henryvdv.ba.type.Anaphora");
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
      jcas.throwFeatMissing("p_A_SingularMatch", "de.unidue.henryvdv.ba.type.Anaphora");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_p_A_SingularMatch);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setP_A_SingularMatch(int addr, boolean v) {
        if (featOkTst && casFeat_p_A_SingularMatch == null)
      jcas.throwFeatMissing("p_A_SingularMatch", "de.unidue.henryvdv.ba.type.Anaphora");
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
      jcas.throwFeatMissing("p_A_PluralMatch", "de.unidue.henryvdv.ba.type.Anaphora");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_p_A_PluralMatch);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setP_A_PluralMatch(int addr, boolean v) {
        if (featOkTst && casFeat_p_A_PluralMatch == null)
      jcas.throwFeatMissing("p_A_PluralMatch", "de.unidue.henryvdv.ba.type.Anaphora");
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
      jcas.throwFeatMissing("p_A_IntraSentenceDiff", "de.unidue.henryvdv.ba.type.Anaphora");
    return ll_cas.ll_getFloatValue(addr, casFeatCode_p_A_IntraSentenceDiff);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setP_A_IntraSentenceDiff(int addr, float v) {
        if (featOkTst && casFeat_p_A_IntraSentenceDiff == null)
      jcas.throwFeatMissing("p_A_IntraSentenceDiff", "de.unidue.henryvdv.ba.type.Anaphora");
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
      jcas.throwFeatMissing("p_A_InterSentenceDiff", "de.unidue.henryvdv.ba.type.Anaphora");
    return ll_cas.ll_getFloatValue(addr, casFeatCode_p_A_InterSentenceDiff);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setP_A_InterSentenceDiff(int addr, float v) {
        if (featOkTst && casFeat_p_A_InterSentenceDiff == null)
      jcas.throwFeatMissing("p_A_InterSentenceDiff", "de.unidue.henryvdv.ba.type.Anaphora");
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
      jcas.throwFeatMissing("p_A_PrepositionalParallel", "de.unidue.henryvdv.ba.type.Anaphora");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_p_A_PrepositionalParallel);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setP_A_PrepositionalParallel(int addr, boolean v) {
        if (featOkTst && casFeat_p_A_PrepositionalParallel == null)
      jcas.throwFeatMissing("p_A_PrepositionalParallel", "de.unidue.henryvdv.ba.type.Anaphora");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_p_A_PrepositionalParallel, v);}
    
  
 
  /** @generated */
  final Feature casFeat_p_A_BindingTheory;
  /** @generated */
  final int     casFeatCode_p_A_BindingTheory;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public boolean getP_A_BindingTheory(int addr) {
        if (featOkTst && casFeat_p_A_BindingTheory == null)
      jcas.throwFeatMissing("p_A_BindingTheory", "de.unidue.henryvdv.ba.type.Anaphora");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_p_A_BindingTheory);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setP_A_BindingTheory(int addr, boolean v) {
        if (featOkTst && casFeat_p_A_BindingTheory == null)
      jcas.throwFeatMissing("p_A_BindingTheory", "de.unidue.henryvdv.ba.type.Anaphora");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_p_A_BindingTheory, v);}
    
  
 
  /** @generated */
  final Feature casFeat_p_A_ReflexiveSubjMatch;
  /** @generated */
  final int     casFeatCode_p_A_ReflexiveSubjMatch;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public boolean getP_A_ReflexiveSubjMatch(int addr) {
        if (featOkTst && casFeat_p_A_ReflexiveSubjMatch == null)
      jcas.throwFeatMissing("p_A_ReflexiveSubjMatch", "de.unidue.henryvdv.ba.type.Anaphora");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_p_A_ReflexiveSubjMatch);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setP_A_ReflexiveSubjMatch(int addr, boolean v) {
        if (featOkTst && casFeat_p_A_ReflexiveSubjMatch == null)
      jcas.throwFeatMissing("p_A_ReflexiveSubjMatch", "de.unidue.henryvdv.ba.type.Anaphora");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_p_A_ReflexiveSubjMatch, v);}
    
  
 
  /** @generated */
  final Feature casFeat_p_A_RelationMatch;
  /** @generated */
  final int     casFeatCode_p_A_RelationMatch;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public boolean getP_A_RelationMatch(int addr) {
        if (featOkTst && casFeat_p_A_RelationMatch == null)
      jcas.throwFeatMissing("p_A_RelationMatch", "de.unidue.henryvdv.ba.type.Anaphora");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_p_A_RelationMatch);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setP_A_RelationMatch(int addr, boolean v) {
        if (featOkTst && casFeat_p_A_RelationMatch == null)
      jcas.throwFeatMissing("p_A_RelationMatch", "de.unidue.henryvdv.ba.type.Anaphora");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_p_A_RelationMatch, v);}
    
  
 
  /** @generated */
  final Feature casFeat_p_A_ParentRelationMatch;
  /** @generated */
  final int     casFeatCode_p_A_ParentRelationMatch;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public boolean getP_A_ParentRelationMatch(int addr) {
        if (featOkTst && casFeat_p_A_ParentRelationMatch == null)
      jcas.throwFeatMissing("p_A_ParentRelationMatch", "de.unidue.henryvdv.ba.type.Anaphora");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_p_A_ParentRelationMatch);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setP_A_ParentRelationMatch(int addr, boolean v) {
        if (featOkTst && casFeat_p_A_ParentRelationMatch == null)
      jcas.throwFeatMissing("p_A_ParentRelationMatch", "de.unidue.henryvdv.ba.type.Anaphora");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_p_A_ParentRelationMatch, v);}
    
  
 
  /** @generated */
  final Feature casFeat_p_A_ParentCatMatch;
  /** @generated */
  final int     casFeatCode_p_A_ParentCatMatch;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public boolean getP_A_ParentCatMatch(int addr) {
        if (featOkTst && casFeat_p_A_ParentCatMatch == null)
      jcas.throwFeatMissing("p_A_ParentCatMatch", "de.unidue.henryvdv.ba.type.Anaphora");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_p_A_ParentCatMatch);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setP_A_ParentCatMatch(int addr, boolean v) {
        if (featOkTst && casFeat_p_A_ParentCatMatch == null)
      jcas.throwFeatMissing("p_A_ParentCatMatch", "de.unidue.henryvdv.ba.type.Anaphora");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_p_A_ParentCatMatch, v);}
    
  
 
  /** @generated */
  final Feature casFeat_p_A_ParentWordMatch;
  /** @generated */
  final int     casFeatCode_p_A_ParentWordMatch;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public boolean getP_A_ParentWordMatch(int addr) {
        if (featOkTst && casFeat_p_A_ParentWordMatch == null)
      jcas.throwFeatMissing("p_A_ParentWordMatch", "de.unidue.henryvdv.ba.type.Anaphora");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_p_A_ParentWordMatch);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setP_A_ParentWordMatch(int addr, boolean v) {
        if (featOkTst && casFeat_p_A_ParentWordMatch == null)
      jcas.throwFeatMissing("p_A_ParentWordMatch", "de.unidue.henryvdv.ba.type.Anaphora");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_p_A_ParentWordMatch, v);}
    
  
 
  /** @generated */
  final Feature casFeat_p_A_QuotationSituation;
  /** @generated */
  final int     casFeatCode_p_A_QuotationSituation;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public boolean getP_A_QuotationSituation(int addr) {
        if (featOkTst && casFeat_p_A_QuotationSituation == null)
      jcas.throwFeatMissing("p_A_QuotationSituation", "de.unidue.henryvdv.ba.type.Anaphora");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_p_A_QuotationSituation);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setP_A_QuotationSituation(int addr, boolean v) {
        if (featOkTst && casFeat_p_A_QuotationSituation == null)
      jcas.throwFeatMissing("p_A_QuotationSituation", "de.unidue.henryvdv.ba.type.Anaphora");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_p_A_QuotationSituation, v);}
    
  
 
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

 
    casFeat_p_A_BindingTheory = jcas.getRequiredFeatureDE(casType, "p_A_BindingTheory", "uima.cas.Boolean", featOkTst);
    casFeatCode_p_A_BindingTheory  = (null == casFeat_p_A_BindingTheory) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_p_A_BindingTheory).getCode();

 
    casFeat_p_A_ReflexiveSubjMatch = jcas.getRequiredFeatureDE(casType, "p_A_ReflexiveSubjMatch", "uima.cas.Boolean", featOkTst);
    casFeatCode_p_A_ReflexiveSubjMatch  = (null == casFeat_p_A_ReflexiveSubjMatch) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_p_A_ReflexiveSubjMatch).getCode();

 
    casFeat_p_A_RelationMatch = jcas.getRequiredFeatureDE(casType, "p_A_RelationMatch", "uima.cas.Boolean", featOkTst);
    casFeatCode_p_A_RelationMatch  = (null == casFeat_p_A_RelationMatch) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_p_A_RelationMatch).getCode();

 
    casFeat_p_A_ParentRelationMatch = jcas.getRequiredFeatureDE(casType, "p_A_ParentRelationMatch", "uima.cas.Boolean", featOkTst);
    casFeatCode_p_A_ParentRelationMatch  = (null == casFeat_p_A_ParentRelationMatch) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_p_A_ParentRelationMatch).getCode();

 
    casFeat_p_A_ParentCatMatch = jcas.getRequiredFeatureDE(casType, "p_A_ParentCatMatch", "uima.cas.Boolean", featOkTst);
    casFeatCode_p_A_ParentCatMatch  = (null == casFeat_p_A_ParentCatMatch) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_p_A_ParentCatMatch).getCode();

 
    casFeat_p_A_ParentWordMatch = jcas.getRequiredFeatureDE(casType, "p_A_ParentWordMatch", "uima.cas.Boolean", featOkTst);
    casFeatCode_p_A_ParentWordMatch  = (null == casFeat_p_A_ParentWordMatch) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_p_A_ParentWordMatch).getCode();

 
    casFeat_p_A_QuotationSituation = jcas.getRequiredFeatureDE(casType, "p_A_QuotationSituation", "uima.cas.Boolean", featOkTst);
    casFeatCode_p_A_QuotationSituation  = (null == casFeat_p_A_QuotationSituation) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_p_A_QuotationSituation).getCode();

 
    casFeat_antecedentFeatures = jcas.getRequiredFeatureDE(casType, "antecedentFeatures", "de.unidue.henryvdv.ba.type.AntecedentFeatures", featOkTst);
    casFeatCode_antecedentFeatures  = (null == casFeat_antecedentFeatures) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_antecedentFeatures).getCode();

  }
}



    