

/* First created by JCasGen Thu Aug 11 18:52:08 CEST 2016 */
package de.unidue.henryvdv.ba.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Sun Aug 14 23:41:59 CEST 2016
 * XML source: C:/Users/Henry/Documents/henryvdv.BA/de.unidue.henryvdv.ba/src/main/resources/desc/type/Anaphora.xml
 * @generated */
public class PronounAntecedentFeatures extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(PronounAntecedentFeatures.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated
   * @return index of the type  
   */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected PronounAntecedentFeatures() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public PronounAntecedentFeatures(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public PronounAntecedentFeatures(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public PronounAntecedentFeatures(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** 
   * <!-- begin-user-doc -->
   * Write your own initialization here
   * <!-- end-user-doc -->
   *
   * @generated modifiable 
   */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: p_A_InSameSentence

  /** getter for p_A_InSameSentence - gets 
   * @generated
   * @return value of the feature 
   */
  public boolean getP_A_InSameSentence() {
    if (PronounAntecedentFeatures_Type.featOkTst && ((PronounAntecedentFeatures_Type)jcasType).casFeat_p_A_InSameSentence == null)
      jcasType.jcas.throwFeatMissing("p_A_InSameSentence", "de.unidue.henryvdv.ba.type.PronounAntecedentFeatures");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((PronounAntecedentFeatures_Type)jcasType).casFeatCode_p_A_InSameSentence);}
    
  /** setter for p_A_InSameSentence - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setP_A_InSameSentence(boolean v) {
    if (PronounAntecedentFeatures_Type.featOkTst && ((PronounAntecedentFeatures_Type)jcasType).casFeat_p_A_InSameSentence == null)
      jcasType.jcas.throwFeatMissing("p_A_InSameSentence", "de.unidue.henryvdv.ba.type.PronounAntecedentFeatures");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((PronounAntecedentFeatures_Type)jcasType).casFeatCode_p_A_InSameSentence, v);}    
   
    
  //*--------------*
  //* Feature: p_A_InPreviousSentence

  /** getter for p_A_InPreviousSentence - gets 
   * @generated
   * @return value of the feature 
   */
  public boolean getP_A_InPreviousSentence() {
    if (PronounAntecedentFeatures_Type.featOkTst && ((PronounAntecedentFeatures_Type)jcasType).casFeat_p_A_InPreviousSentence == null)
      jcasType.jcas.throwFeatMissing("p_A_InPreviousSentence", "de.unidue.henryvdv.ba.type.PronounAntecedentFeatures");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((PronounAntecedentFeatures_Type)jcasType).casFeatCode_p_A_InPreviousSentence);}
    
  /** setter for p_A_InPreviousSentence - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setP_A_InPreviousSentence(boolean v) {
    if (PronounAntecedentFeatures_Type.featOkTst && ((PronounAntecedentFeatures_Type)jcasType).casFeat_p_A_InPreviousSentence == null)
      jcasType.jcas.throwFeatMissing("p_A_InPreviousSentence", "de.unidue.henryvdv.ba.type.PronounAntecedentFeatures");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((PronounAntecedentFeatures_Type)jcasType).casFeatCode_p_A_InPreviousSentence, v);}    
   
    
  //*--------------*
  //* Feature: p_A_SingularMatch

  /** getter for p_A_SingularMatch - gets 
   * @generated
   * @return value of the feature 
   */
  public boolean getP_A_SingularMatch() {
    if (PronounAntecedentFeatures_Type.featOkTst && ((PronounAntecedentFeatures_Type)jcasType).casFeat_p_A_SingularMatch == null)
      jcasType.jcas.throwFeatMissing("p_A_SingularMatch", "de.unidue.henryvdv.ba.type.PronounAntecedentFeatures");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((PronounAntecedentFeatures_Type)jcasType).casFeatCode_p_A_SingularMatch);}
    
  /** setter for p_A_SingularMatch - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setP_A_SingularMatch(boolean v) {
    if (PronounAntecedentFeatures_Type.featOkTst && ((PronounAntecedentFeatures_Type)jcasType).casFeat_p_A_SingularMatch == null)
      jcasType.jcas.throwFeatMissing("p_A_SingularMatch", "de.unidue.henryvdv.ba.type.PronounAntecedentFeatures");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((PronounAntecedentFeatures_Type)jcasType).casFeatCode_p_A_SingularMatch, v);}    
   
    
  //*--------------*
  //* Feature: p_A_PluralMatch

  /** getter for p_A_PluralMatch - gets 
   * @generated
   * @return value of the feature 
   */
  public boolean getP_A_PluralMatch() {
    if (PronounAntecedentFeatures_Type.featOkTst && ((PronounAntecedentFeatures_Type)jcasType).casFeat_p_A_PluralMatch == null)
      jcasType.jcas.throwFeatMissing("p_A_PluralMatch", "de.unidue.henryvdv.ba.type.PronounAntecedentFeatures");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((PronounAntecedentFeatures_Type)jcasType).casFeatCode_p_A_PluralMatch);}
    
  /** setter for p_A_PluralMatch - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setP_A_PluralMatch(boolean v) {
    if (PronounAntecedentFeatures_Type.featOkTst && ((PronounAntecedentFeatures_Type)jcasType).casFeat_p_A_PluralMatch == null)
      jcasType.jcas.throwFeatMissing("p_A_PluralMatch", "de.unidue.henryvdv.ba.type.PronounAntecedentFeatures");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((PronounAntecedentFeatures_Type)jcasType).casFeatCode_p_A_PluralMatch, v);}    
   
    
  //*--------------*
  //* Feature: p_A_IntraSentenceDiff

  /** getter for p_A_IntraSentenceDiff - gets 
   * @generated
   * @return value of the feature 
   */
  public float getP_A_IntraSentenceDiff() {
    if (PronounAntecedentFeatures_Type.featOkTst && ((PronounAntecedentFeatures_Type)jcasType).casFeat_p_A_IntraSentenceDiff == null)
      jcasType.jcas.throwFeatMissing("p_A_IntraSentenceDiff", "de.unidue.henryvdv.ba.type.PronounAntecedentFeatures");
    return jcasType.ll_cas.ll_getFloatValue(addr, ((PronounAntecedentFeatures_Type)jcasType).casFeatCode_p_A_IntraSentenceDiff);}
    
  /** setter for p_A_IntraSentenceDiff - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setP_A_IntraSentenceDiff(float v) {
    if (PronounAntecedentFeatures_Type.featOkTst && ((PronounAntecedentFeatures_Type)jcasType).casFeat_p_A_IntraSentenceDiff == null)
      jcasType.jcas.throwFeatMissing("p_A_IntraSentenceDiff", "de.unidue.henryvdv.ba.type.PronounAntecedentFeatures");
    jcasType.ll_cas.ll_setFloatValue(addr, ((PronounAntecedentFeatures_Type)jcasType).casFeatCode_p_A_IntraSentenceDiff, v);}    
   
    
  //*--------------*
  //* Feature: p_A_InterSentenceDiff

  /** getter for p_A_InterSentenceDiff - gets 
   * @generated
   * @return value of the feature 
   */
  public float getP_A_InterSentenceDiff() {
    if (PronounAntecedentFeatures_Type.featOkTst && ((PronounAntecedentFeatures_Type)jcasType).casFeat_p_A_InterSentenceDiff == null)
      jcasType.jcas.throwFeatMissing("p_A_InterSentenceDiff", "de.unidue.henryvdv.ba.type.PronounAntecedentFeatures");
    return jcasType.ll_cas.ll_getFloatValue(addr, ((PronounAntecedentFeatures_Type)jcasType).casFeatCode_p_A_InterSentenceDiff);}
    
  /** setter for p_A_InterSentenceDiff - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setP_A_InterSentenceDiff(float v) {
    if (PronounAntecedentFeatures_Type.featOkTst && ((PronounAntecedentFeatures_Type)jcasType).casFeat_p_A_InterSentenceDiff == null)
      jcasType.jcas.throwFeatMissing("p_A_InterSentenceDiff", "de.unidue.henryvdv.ba.type.PronounAntecedentFeatures");
    jcasType.ll_cas.ll_setFloatValue(addr, ((PronounAntecedentFeatures_Type)jcasType).casFeatCode_p_A_InterSentenceDiff, v);}    
   
    
  //*--------------*
  //* Feature: p_A_PrepositionalParallel

  /** getter for p_A_PrepositionalParallel - gets 
   * @generated
   * @return value of the feature 
   */
  public boolean getP_A_PrepositionalParallel() {
    if (PronounAntecedentFeatures_Type.featOkTst && ((PronounAntecedentFeatures_Type)jcasType).casFeat_p_A_PrepositionalParallel == null)
      jcasType.jcas.throwFeatMissing("p_A_PrepositionalParallel", "de.unidue.henryvdv.ba.type.PronounAntecedentFeatures");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((PronounAntecedentFeatures_Type)jcasType).casFeatCode_p_A_PrepositionalParallel);}
    
  /** setter for p_A_PrepositionalParallel - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setP_A_PrepositionalParallel(boolean v) {
    if (PronounAntecedentFeatures_Type.featOkTst && ((PronounAntecedentFeatures_Type)jcasType).casFeat_p_A_PrepositionalParallel == null)
      jcasType.jcas.throwFeatMissing("p_A_PrepositionalParallel", "de.unidue.henryvdv.ba.type.PronounAntecedentFeatures");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((PronounAntecedentFeatures_Type)jcasType).casFeatCode_p_A_PrepositionalParallel, v);}    
   
    
  //*--------------*
  //* Feature: p_A_BindingTheory

  /** getter for p_A_BindingTheory - gets 
   * @generated
   * @return value of the feature 
   */
  public boolean getP_A_BindingTheory() {
    if (PronounAntecedentFeatures_Type.featOkTst && ((PronounAntecedentFeatures_Type)jcasType).casFeat_p_A_BindingTheory == null)
      jcasType.jcas.throwFeatMissing("p_A_BindingTheory", "de.unidue.henryvdv.ba.type.PronounAntecedentFeatures");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((PronounAntecedentFeatures_Type)jcasType).casFeatCode_p_A_BindingTheory);}
    
  /** setter for p_A_BindingTheory - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setP_A_BindingTheory(boolean v) {
    if (PronounAntecedentFeatures_Type.featOkTst && ((PronounAntecedentFeatures_Type)jcasType).casFeat_p_A_BindingTheory == null)
      jcasType.jcas.throwFeatMissing("p_A_BindingTheory", "de.unidue.henryvdv.ba.type.PronounAntecedentFeatures");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((PronounAntecedentFeatures_Type)jcasType).casFeatCode_p_A_BindingTheory, v);}    
   
    
  //*--------------*
  //* Feature: p_A_ReflexiveSubjMatch

  /** getter for p_A_ReflexiveSubjMatch - gets 
   * @generated
   * @return value of the feature 
   */
  public boolean getP_A_ReflexiveSubjMatch() {
    if (PronounAntecedentFeatures_Type.featOkTst && ((PronounAntecedentFeatures_Type)jcasType).casFeat_p_A_ReflexiveSubjMatch == null)
      jcasType.jcas.throwFeatMissing("p_A_ReflexiveSubjMatch", "de.unidue.henryvdv.ba.type.PronounAntecedentFeatures");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((PronounAntecedentFeatures_Type)jcasType).casFeatCode_p_A_ReflexiveSubjMatch);}
    
  /** setter for p_A_ReflexiveSubjMatch - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setP_A_ReflexiveSubjMatch(boolean v) {
    if (PronounAntecedentFeatures_Type.featOkTst && ((PronounAntecedentFeatures_Type)jcasType).casFeat_p_A_ReflexiveSubjMatch == null)
      jcasType.jcas.throwFeatMissing("p_A_ReflexiveSubjMatch", "de.unidue.henryvdv.ba.type.PronounAntecedentFeatures");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((PronounAntecedentFeatures_Type)jcasType).casFeatCode_p_A_ReflexiveSubjMatch, v);}    
   
    
  //*--------------*
  //* Feature: p_A_RelationMatch

  /** getter for p_A_RelationMatch - gets 
   * @generated
   * @return value of the feature 
   */
  public boolean getP_A_RelationMatch() {
    if (PronounAntecedentFeatures_Type.featOkTst && ((PronounAntecedentFeatures_Type)jcasType).casFeat_p_A_RelationMatch == null)
      jcasType.jcas.throwFeatMissing("p_A_RelationMatch", "de.unidue.henryvdv.ba.type.PronounAntecedentFeatures");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((PronounAntecedentFeatures_Type)jcasType).casFeatCode_p_A_RelationMatch);}
    
  /** setter for p_A_RelationMatch - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setP_A_RelationMatch(boolean v) {
    if (PronounAntecedentFeatures_Type.featOkTst && ((PronounAntecedentFeatures_Type)jcasType).casFeat_p_A_RelationMatch == null)
      jcasType.jcas.throwFeatMissing("p_A_RelationMatch", "de.unidue.henryvdv.ba.type.PronounAntecedentFeatures");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((PronounAntecedentFeatures_Type)jcasType).casFeatCode_p_A_RelationMatch, v);}    
   
    
  //*--------------*
  //* Feature: p_A_ParentRelationMatch

  /** getter for p_A_ParentRelationMatch - gets 
   * @generated
   * @return value of the feature 
   */
  public boolean getP_A_ParentRelationMatch() {
    if (PronounAntecedentFeatures_Type.featOkTst && ((PronounAntecedentFeatures_Type)jcasType).casFeat_p_A_ParentRelationMatch == null)
      jcasType.jcas.throwFeatMissing("p_A_ParentRelationMatch", "de.unidue.henryvdv.ba.type.PronounAntecedentFeatures");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((PronounAntecedentFeatures_Type)jcasType).casFeatCode_p_A_ParentRelationMatch);}
    
  /** setter for p_A_ParentRelationMatch - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setP_A_ParentRelationMatch(boolean v) {
    if (PronounAntecedentFeatures_Type.featOkTst && ((PronounAntecedentFeatures_Type)jcasType).casFeat_p_A_ParentRelationMatch == null)
      jcasType.jcas.throwFeatMissing("p_A_ParentRelationMatch", "de.unidue.henryvdv.ba.type.PronounAntecedentFeatures");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((PronounAntecedentFeatures_Type)jcasType).casFeatCode_p_A_ParentRelationMatch, v);}    
   
    
  //*--------------*
  //* Feature: p_A_ParentCatMatch

  /** getter for p_A_ParentCatMatch - gets 
   * @generated
   * @return value of the feature 
   */
  public boolean getP_A_ParentCatMatch() {
    if (PronounAntecedentFeatures_Type.featOkTst && ((PronounAntecedentFeatures_Type)jcasType).casFeat_p_A_ParentCatMatch == null)
      jcasType.jcas.throwFeatMissing("p_A_ParentCatMatch", "de.unidue.henryvdv.ba.type.PronounAntecedentFeatures");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((PronounAntecedentFeatures_Type)jcasType).casFeatCode_p_A_ParentCatMatch);}
    
  /** setter for p_A_ParentCatMatch - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setP_A_ParentCatMatch(boolean v) {
    if (PronounAntecedentFeatures_Type.featOkTst && ((PronounAntecedentFeatures_Type)jcasType).casFeat_p_A_ParentCatMatch == null)
      jcasType.jcas.throwFeatMissing("p_A_ParentCatMatch", "de.unidue.henryvdv.ba.type.PronounAntecedentFeatures");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((PronounAntecedentFeatures_Type)jcasType).casFeatCode_p_A_ParentCatMatch, v);}    
   
    
  //*--------------*
  //* Feature: p_A_ParentWordMatch

  /** getter for p_A_ParentWordMatch - gets 
   * @generated
   * @return value of the feature 
   */
  public boolean getP_A_ParentWordMatch() {
    if (PronounAntecedentFeatures_Type.featOkTst && ((PronounAntecedentFeatures_Type)jcasType).casFeat_p_A_ParentWordMatch == null)
      jcasType.jcas.throwFeatMissing("p_A_ParentWordMatch", "de.unidue.henryvdv.ba.type.PronounAntecedentFeatures");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((PronounAntecedentFeatures_Type)jcasType).casFeatCode_p_A_ParentWordMatch);}
    
  /** setter for p_A_ParentWordMatch - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setP_A_ParentWordMatch(boolean v) {
    if (PronounAntecedentFeatures_Type.featOkTst && ((PronounAntecedentFeatures_Type)jcasType).casFeat_p_A_ParentWordMatch == null)
      jcasType.jcas.throwFeatMissing("p_A_ParentWordMatch", "de.unidue.henryvdv.ba.type.PronounAntecedentFeatures");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((PronounAntecedentFeatures_Type)jcasType).casFeatCode_p_A_ParentWordMatch, v);}    
   
    
  //*--------------*
  //* Feature: p_A_QuotationSituation

  /** getter for p_A_QuotationSituation - gets 
   * @generated
   * @return value of the feature 
   */
  public boolean getP_A_QuotationSituation() {
    if (PronounAntecedentFeatures_Type.featOkTst && ((PronounAntecedentFeatures_Type)jcasType).casFeat_p_A_QuotationSituation == null)
      jcasType.jcas.throwFeatMissing("p_A_QuotationSituation", "de.unidue.henryvdv.ba.type.PronounAntecedentFeatures");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((PronounAntecedentFeatures_Type)jcasType).casFeatCode_p_A_QuotationSituation);}
    
  /** setter for p_A_QuotationSituation - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setP_A_QuotationSituation(boolean v) {
    if (PronounAntecedentFeatures_Type.featOkTst && ((PronounAntecedentFeatures_Type)jcasType).casFeat_p_A_QuotationSituation == null)
      jcasType.jcas.throwFeatMissing("p_A_QuotationSituation", "de.unidue.henryvdv.ba.type.PronounAntecedentFeatures");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((PronounAntecedentFeatures_Type)jcasType).casFeatCode_p_A_QuotationSituation, v);}    
  }

    