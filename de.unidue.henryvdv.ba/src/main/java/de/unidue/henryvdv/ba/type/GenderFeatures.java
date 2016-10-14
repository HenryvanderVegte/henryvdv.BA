

/* First created by JCasGen Fri Sep 09 20:58:44 CEST 2016 */
package de.unidue.henryvdv.ba.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Thu Oct 13 22:34:10 CEST 2016
 * XML source: C:/Users/Henry/Documents/henryvdv.BA/de.unidue.henryvdv.ba/src/main/resources/desc/type/GenderFeatures.xml
 * @generated */
public class GenderFeatures extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(GenderFeatures.class);
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
  protected GenderFeatures() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public GenderFeatures(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public GenderFeatures(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public GenderFeatures(JCas jcas, int begin, int end) {
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
  //* Feature: g_StdGenderMatch

  /** getter for g_StdGenderMatch - gets 
   * @generated
   * @return value of the feature 
   */
  public boolean getG_StdGenderMatch() {
    if (GenderFeatures_Type.featOkTst && ((GenderFeatures_Type)jcasType).casFeat_g_StdGenderMatch == null)
      jcasType.jcas.throwFeatMissing("g_StdGenderMatch", "de.unidue.henryvdv.ba.type.GenderFeatures");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((GenderFeatures_Type)jcasType).casFeatCode_g_StdGenderMatch);}
    
  /** setter for g_StdGenderMatch - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setG_StdGenderMatch(boolean v) {
    if (GenderFeatures_Type.featOkTst && ((GenderFeatures_Type)jcasType).casFeat_g_StdGenderMatch == null)
      jcasType.jcas.throwFeatMissing("g_StdGenderMatch", "de.unidue.henryvdv.ba.type.GenderFeatures");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((GenderFeatures_Type)jcasType).casFeatCode_g_StdGenderMatch, v);}    
   
    
  //*--------------*
  //* Feature: g_StdGenderMismatch

  /** getter for g_StdGenderMismatch - gets 
   * @generated
   * @return value of the feature 
   */
  public boolean getG_StdGenderMismatch() {
    if (GenderFeatures_Type.featOkTst && ((GenderFeatures_Type)jcasType).casFeat_g_StdGenderMismatch == null)
      jcasType.jcas.throwFeatMissing("g_StdGenderMismatch", "de.unidue.henryvdv.ba.type.GenderFeatures");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((GenderFeatures_Type)jcasType).casFeatCode_g_StdGenderMismatch);}
    
  /** setter for g_StdGenderMismatch - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setG_StdGenderMismatch(boolean v) {
    if (GenderFeatures_Type.featOkTst && ((GenderFeatures_Type)jcasType).casFeat_g_StdGenderMismatch == null)
      jcasType.jcas.throwFeatMissing("g_StdGenderMismatch", "de.unidue.henryvdv.ba.type.GenderFeatures");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((GenderFeatures_Type)jcasType).casFeatCode_g_StdGenderMismatch, v);}    
   
    
  //*--------------*
  //* Feature: g_PronounMismatch

  /** getter for g_PronounMismatch - gets 
   * @generated
   * @return value of the feature 
   */
  public boolean getG_PronounMismatch() {
    if (GenderFeatures_Type.featOkTst && ((GenderFeatures_Type)jcasType).casFeat_g_PronounMismatch == null)
      jcasType.jcas.throwFeatMissing("g_PronounMismatch", "de.unidue.henryvdv.ba.type.GenderFeatures");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((GenderFeatures_Type)jcasType).casFeatCode_g_PronounMismatch);}
    
  /** setter for g_PronounMismatch - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setG_PronounMismatch(boolean v) {
    if (GenderFeatures_Type.featOkTst && ((GenderFeatures_Type)jcasType).casFeat_g_PronounMismatch == null)
      jcasType.jcas.throwFeatMissing("g_PronounMismatch", "de.unidue.henryvdv.ba.type.GenderFeatures");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((GenderFeatures_Type)jcasType).casFeatCode_g_PronounMismatch, v);}    
   
    
  //*--------------*
  //* Feature: g_Masculine_Mean

  /** getter for g_Masculine_Mean - gets 
   * @generated
   * @return value of the feature 
   */
  public float getG_Masculine_Mean() {
    if (GenderFeatures_Type.featOkTst && ((GenderFeatures_Type)jcasType).casFeat_g_Masculine_Mean == null)
      jcasType.jcas.throwFeatMissing("g_Masculine_Mean", "de.unidue.henryvdv.ba.type.GenderFeatures");
    return jcasType.ll_cas.ll_getFloatValue(addr, ((GenderFeatures_Type)jcasType).casFeatCode_g_Masculine_Mean);}
    
  /** setter for g_Masculine_Mean - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setG_Masculine_Mean(float v) {
    if (GenderFeatures_Type.featOkTst && ((GenderFeatures_Type)jcasType).casFeat_g_Masculine_Mean == null)
      jcasType.jcas.throwFeatMissing("g_Masculine_Mean", "de.unidue.henryvdv.ba.type.GenderFeatures");
    jcasType.ll_cas.ll_setFloatValue(addr, ((GenderFeatures_Type)jcasType).casFeatCode_g_Masculine_Mean, v);}    
   
    
  //*--------------*
  //* Feature: g_Masculine_Variance

  /** getter for g_Masculine_Variance - gets 
   * @generated
   * @return value of the feature 
   */
  public float getG_Masculine_Variance() {
    if (GenderFeatures_Type.featOkTst && ((GenderFeatures_Type)jcasType).casFeat_g_Masculine_Variance == null)
      jcasType.jcas.throwFeatMissing("g_Masculine_Variance", "de.unidue.henryvdv.ba.type.GenderFeatures");
    return jcasType.ll_cas.ll_getFloatValue(addr, ((GenderFeatures_Type)jcasType).casFeatCode_g_Masculine_Variance);}
    
  /** setter for g_Masculine_Variance - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setG_Masculine_Variance(float v) {
    if (GenderFeatures_Type.featOkTst && ((GenderFeatures_Type)jcasType).casFeat_g_Masculine_Variance == null)
      jcasType.jcas.throwFeatMissing("g_Masculine_Variance", "de.unidue.henryvdv.ba.type.GenderFeatures");
    jcasType.ll_cas.ll_setFloatValue(addr, ((GenderFeatures_Type)jcasType).casFeatCode_g_Masculine_Variance, v);}    
   
    
  //*--------------*
  //* Feature: g_Feminine_Mean

  /** getter for g_Feminine_Mean - gets 
   * @generated
   * @return value of the feature 
   */
  public float getG_Feminine_Mean() {
    if (GenderFeatures_Type.featOkTst && ((GenderFeatures_Type)jcasType).casFeat_g_Feminine_Mean == null)
      jcasType.jcas.throwFeatMissing("g_Feminine_Mean", "de.unidue.henryvdv.ba.type.GenderFeatures");
    return jcasType.ll_cas.ll_getFloatValue(addr, ((GenderFeatures_Type)jcasType).casFeatCode_g_Feminine_Mean);}
    
  /** setter for g_Feminine_Mean - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setG_Feminine_Mean(float v) {
    if (GenderFeatures_Type.featOkTst && ((GenderFeatures_Type)jcasType).casFeat_g_Feminine_Mean == null)
      jcasType.jcas.throwFeatMissing("g_Feminine_Mean", "de.unidue.henryvdv.ba.type.GenderFeatures");
    jcasType.ll_cas.ll_setFloatValue(addr, ((GenderFeatures_Type)jcasType).casFeatCode_g_Feminine_Mean, v);}    
   
    
  //*--------------*
  //* Feature: g_Feminine_Variance

  /** getter for g_Feminine_Variance - gets 
   * @generated
   * @return value of the feature 
   */
  public float getG_Feminine_Variance() {
    if (GenderFeatures_Type.featOkTst && ((GenderFeatures_Type)jcasType).casFeat_g_Feminine_Variance == null)
      jcasType.jcas.throwFeatMissing("g_Feminine_Variance", "de.unidue.henryvdv.ba.type.GenderFeatures");
    return jcasType.ll_cas.ll_getFloatValue(addr, ((GenderFeatures_Type)jcasType).casFeatCode_g_Feminine_Variance);}
    
  /** setter for g_Feminine_Variance - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setG_Feminine_Variance(float v) {
    if (GenderFeatures_Type.featOkTst && ((GenderFeatures_Type)jcasType).casFeat_g_Feminine_Variance == null)
      jcasType.jcas.throwFeatMissing("g_Feminine_Variance", "de.unidue.henryvdv.ba.type.GenderFeatures");
    jcasType.ll_cas.ll_setFloatValue(addr, ((GenderFeatures_Type)jcasType).casFeatCode_g_Feminine_Variance, v);}    
   
    
  //*--------------*
  //* Feature: g_Neutral_Mean

  /** getter for g_Neutral_Mean - gets 
   * @generated
   * @return value of the feature 
   */
  public float getG_Neutral_Mean() {
    if (GenderFeatures_Type.featOkTst && ((GenderFeatures_Type)jcasType).casFeat_g_Neutral_Mean == null)
      jcasType.jcas.throwFeatMissing("g_Neutral_Mean", "de.unidue.henryvdv.ba.type.GenderFeatures");
    return jcasType.ll_cas.ll_getFloatValue(addr, ((GenderFeatures_Type)jcasType).casFeatCode_g_Neutral_Mean);}
    
  /** setter for g_Neutral_Mean - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setG_Neutral_Mean(float v) {
    if (GenderFeatures_Type.featOkTst && ((GenderFeatures_Type)jcasType).casFeat_g_Neutral_Mean == null)
      jcasType.jcas.throwFeatMissing("g_Neutral_Mean", "de.unidue.henryvdv.ba.type.GenderFeatures");
    jcasType.ll_cas.ll_setFloatValue(addr, ((GenderFeatures_Type)jcasType).casFeatCode_g_Neutral_Mean, v);}    
   
    
  //*--------------*
  //* Feature: g_Neutral_Variance

  /** getter for g_Neutral_Variance - gets 
   * @generated
   * @return value of the feature 
   */
  public float getG_Neutral_Variance() {
    if (GenderFeatures_Type.featOkTst && ((GenderFeatures_Type)jcasType).casFeat_g_Neutral_Variance == null)
      jcasType.jcas.throwFeatMissing("g_Neutral_Variance", "de.unidue.henryvdv.ba.type.GenderFeatures");
    return jcasType.ll_cas.ll_getFloatValue(addr, ((GenderFeatures_Type)jcasType).casFeatCode_g_Neutral_Variance);}
    
  /** setter for g_Neutral_Variance - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setG_Neutral_Variance(float v) {
    if (GenderFeatures_Type.featOkTst && ((GenderFeatures_Type)jcasType).casFeat_g_Neutral_Variance == null)
      jcasType.jcas.throwFeatMissing("g_Neutral_Variance", "de.unidue.henryvdv.ba.type.GenderFeatures");
    jcasType.ll_cas.ll_setFloatValue(addr, ((GenderFeatures_Type)jcasType).casFeatCode_g_Neutral_Variance, v);}    
   
    
  //*--------------*
  //* Feature: g_Plural_Mean

  /** getter for g_Plural_Mean - gets 
   * @generated
   * @return value of the feature 
   */
  public float getG_Plural_Mean() {
    if (GenderFeatures_Type.featOkTst && ((GenderFeatures_Type)jcasType).casFeat_g_Plural_Mean == null)
      jcasType.jcas.throwFeatMissing("g_Plural_Mean", "de.unidue.henryvdv.ba.type.GenderFeatures");
    return jcasType.ll_cas.ll_getFloatValue(addr, ((GenderFeatures_Type)jcasType).casFeatCode_g_Plural_Mean);}
    
  /** setter for g_Plural_Mean - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setG_Plural_Mean(float v) {
    if (GenderFeatures_Type.featOkTst && ((GenderFeatures_Type)jcasType).casFeat_g_Plural_Mean == null)
      jcasType.jcas.throwFeatMissing("g_Plural_Mean", "de.unidue.henryvdv.ba.type.GenderFeatures");
    jcasType.ll_cas.ll_setFloatValue(addr, ((GenderFeatures_Type)jcasType).casFeatCode_g_Plural_Mean, v);}    
   
    
  //*--------------*
  //* Feature: g_Plural_Variance

  /** getter for g_Plural_Variance - gets 
   * @generated
   * @return value of the feature 
   */
  public float getG_Plural_Variance() {
    if (GenderFeatures_Type.featOkTst && ((GenderFeatures_Type)jcasType).casFeat_g_Plural_Variance == null)
      jcasType.jcas.throwFeatMissing("g_Plural_Variance", "de.unidue.henryvdv.ba.type.GenderFeatures");
    return jcasType.ll_cas.ll_getFloatValue(addr, ((GenderFeatures_Type)jcasType).casFeatCode_g_Plural_Variance);}
    
  /** setter for g_Plural_Variance - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setG_Plural_Variance(float v) {
    if (GenderFeatures_Type.featOkTst && ((GenderFeatures_Type)jcasType).casFeat_g_Plural_Variance == null)
      jcasType.jcas.throwFeatMissing("g_Plural_Variance", "de.unidue.henryvdv.ba.type.GenderFeatures");
    jcasType.ll_cas.ll_setFloatValue(addr, ((GenderFeatures_Type)jcasType).casFeatCode_g_Plural_Variance, v);}    
   
    
  //*--------------*
  //* Feature: g_Masculine_HardConstraint

  /** getter for g_Masculine_HardConstraint - gets 
   * @generated
   * @return value of the feature 
   */
  public boolean getG_Masculine_HardConstraint() {
    if (GenderFeatures_Type.featOkTst && ((GenderFeatures_Type)jcasType).casFeat_g_Masculine_HardConstraint == null)
      jcasType.jcas.throwFeatMissing("g_Masculine_HardConstraint", "de.unidue.henryvdv.ba.type.GenderFeatures");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((GenderFeatures_Type)jcasType).casFeatCode_g_Masculine_HardConstraint);}
    
  /** setter for g_Masculine_HardConstraint - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setG_Masculine_HardConstraint(boolean v) {
    if (GenderFeatures_Type.featOkTst && ((GenderFeatures_Type)jcasType).casFeat_g_Masculine_HardConstraint == null)
      jcasType.jcas.throwFeatMissing("g_Masculine_HardConstraint", "de.unidue.henryvdv.ba.type.GenderFeatures");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((GenderFeatures_Type)jcasType).casFeatCode_g_Masculine_HardConstraint, v);}    
   
    
  //*--------------*
  //* Feature: g_Feminine_HardConstraint

  /** getter for g_Feminine_HardConstraint - gets 
   * @generated
   * @return value of the feature 
   */
  public boolean getG_Feminine_HardConstraint() {
    if (GenderFeatures_Type.featOkTst && ((GenderFeatures_Type)jcasType).casFeat_g_Feminine_HardConstraint == null)
      jcasType.jcas.throwFeatMissing("g_Feminine_HardConstraint", "de.unidue.henryvdv.ba.type.GenderFeatures");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((GenderFeatures_Type)jcasType).casFeatCode_g_Feminine_HardConstraint);}
    
  /** setter for g_Feminine_HardConstraint - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setG_Feminine_HardConstraint(boolean v) {
    if (GenderFeatures_Type.featOkTst && ((GenderFeatures_Type)jcasType).casFeat_g_Feminine_HardConstraint == null)
      jcasType.jcas.throwFeatMissing("g_Feminine_HardConstraint", "de.unidue.henryvdv.ba.type.GenderFeatures");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((GenderFeatures_Type)jcasType).casFeatCode_g_Feminine_HardConstraint, v);}    
   
    
  //*--------------*
  //* Feature: g_Neutral_HardConstraint

  /** getter for g_Neutral_HardConstraint - gets 
   * @generated
   * @return value of the feature 
   */
  public boolean getG_Neutral_HardConstraint() {
    if (GenderFeatures_Type.featOkTst && ((GenderFeatures_Type)jcasType).casFeat_g_Neutral_HardConstraint == null)
      jcasType.jcas.throwFeatMissing("g_Neutral_HardConstraint", "de.unidue.henryvdv.ba.type.GenderFeatures");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((GenderFeatures_Type)jcasType).casFeatCode_g_Neutral_HardConstraint);}
    
  /** setter for g_Neutral_HardConstraint - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setG_Neutral_HardConstraint(boolean v) {
    if (GenderFeatures_Type.featOkTst && ((GenderFeatures_Type)jcasType).casFeat_g_Neutral_HardConstraint == null)
      jcasType.jcas.throwFeatMissing("g_Neutral_HardConstraint", "de.unidue.henryvdv.ba.type.GenderFeatures");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((GenderFeatures_Type)jcasType).casFeatCode_g_Neutral_HardConstraint, v);}    
   
    
  //*--------------*
  //* Feature: g_Plural_HardConstraint

  /** getter for g_Plural_HardConstraint - gets 
   * @generated
   * @return value of the feature 
   */
  public boolean getG_Plural_HardConstraint() {
    if (GenderFeatures_Type.featOkTst && ((GenderFeatures_Type)jcasType).casFeat_g_Plural_HardConstraint == null)
      jcasType.jcas.throwFeatMissing("g_Plural_HardConstraint", "de.unidue.henryvdv.ba.type.GenderFeatures");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((GenderFeatures_Type)jcasType).casFeatCode_g_Plural_HardConstraint);}
    
  /** setter for g_Plural_HardConstraint - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setG_Plural_HardConstraint(boolean v) {
    if (GenderFeatures_Type.featOkTst && ((GenderFeatures_Type)jcasType).casFeat_g_Plural_HardConstraint == null)
      jcasType.jcas.throwFeatMissing("g_Plural_HardConstraint", "de.unidue.henryvdv.ba.type.GenderFeatures");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((GenderFeatures_Type)jcasType).casFeatCode_g_Plural_HardConstraint, v);}    
  }

    