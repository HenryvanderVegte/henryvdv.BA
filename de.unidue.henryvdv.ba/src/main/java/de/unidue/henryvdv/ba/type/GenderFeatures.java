

/* First created by JCasGen Fri Sep 09 20:58:44 CEST 2016 */
package de.unidue.henryvdv.ba.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Sun Sep 11 09:45:22 CEST 2016
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
  public double getG_Masculine_Mean() {
    if (GenderFeatures_Type.featOkTst && ((GenderFeatures_Type)jcasType).casFeat_g_Masculine_Mean == null)
      jcasType.jcas.throwFeatMissing("g_Masculine_Mean", "de.unidue.henryvdv.ba.type.GenderFeatures");
    return jcasType.ll_cas.ll_getDoubleValue(addr, ((GenderFeatures_Type)jcasType).casFeatCode_g_Masculine_Mean);}
    
  /** setter for g_Masculine_Mean - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setG_Masculine_Mean(double v) {
    if (GenderFeatures_Type.featOkTst && ((GenderFeatures_Type)jcasType).casFeat_g_Masculine_Mean == null)
      jcasType.jcas.throwFeatMissing("g_Masculine_Mean", "de.unidue.henryvdv.ba.type.GenderFeatures");
    jcasType.ll_cas.ll_setDoubleValue(addr, ((GenderFeatures_Type)jcasType).casFeatCode_g_Masculine_Mean, v);}    
   
    
  //*--------------*
  //* Feature: g_Masculine_StdDev

  /** getter for g_Masculine_StdDev - gets 
   * @generated
   * @return value of the feature 
   */
  public double getG_Masculine_StdDev() {
    if (GenderFeatures_Type.featOkTst && ((GenderFeatures_Type)jcasType).casFeat_g_Masculine_StdDev == null)
      jcasType.jcas.throwFeatMissing("g_Masculine_StdDev", "de.unidue.henryvdv.ba.type.GenderFeatures");
    return jcasType.ll_cas.ll_getDoubleValue(addr, ((GenderFeatures_Type)jcasType).casFeatCode_g_Masculine_StdDev);}
    
  /** setter for g_Masculine_StdDev - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setG_Masculine_StdDev(double v) {
    if (GenderFeatures_Type.featOkTst && ((GenderFeatures_Type)jcasType).casFeat_g_Masculine_StdDev == null)
      jcasType.jcas.throwFeatMissing("g_Masculine_StdDev", "de.unidue.henryvdv.ba.type.GenderFeatures");
    jcasType.ll_cas.ll_setDoubleValue(addr, ((GenderFeatures_Type)jcasType).casFeatCode_g_Masculine_StdDev, v);}    
   
    
  //*--------------*
  //* Feature: g_Feminine_Mean

  /** getter for g_Feminine_Mean - gets 
   * @generated
   * @return value of the feature 
   */
  public double getG_Feminine_Mean() {
    if (GenderFeatures_Type.featOkTst && ((GenderFeatures_Type)jcasType).casFeat_g_Feminine_Mean == null)
      jcasType.jcas.throwFeatMissing("g_Feminine_Mean", "de.unidue.henryvdv.ba.type.GenderFeatures");
    return jcasType.ll_cas.ll_getDoubleValue(addr, ((GenderFeatures_Type)jcasType).casFeatCode_g_Feminine_Mean);}
    
  /** setter for g_Feminine_Mean - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setG_Feminine_Mean(double v) {
    if (GenderFeatures_Type.featOkTst && ((GenderFeatures_Type)jcasType).casFeat_g_Feminine_Mean == null)
      jcasType.jcas.throwFeatMissing("g_Feminine_Mean", "de.unidue.henryvdv.ba.type.GenderFeatures");
    jcasType.ll_cas.ll_setDoubleValue(addr, ((GenderFeatures_Type)jcasType).casFeatCode_g_Feminine_Mean, v);}    
   
    
  //*--------------*
  //* Feature: g_Feminine_StdDev

  /** getter for g_Feminine_StdDev - gets 
   * @generated
   * @return value of the feature 
   */
  public double getG_Feminine_StdDev() {
    if (GenderFeatures_Type.featOkTst && ((GenderFeatures_Type)jcasType).casFeat_g_Feminine_StdDev == null)
      jcasType.jcas.throwFeatMissing("g_Feminine_StdDev", "de.unidue.henryvdv.ba.type.GenderFeatures");
    return jcasType.ll_cas.ll_getDoubleValue(addr, ((GenderFeatures_Type)jcasType).casFeatCode_g_Feminine_StdDev);}
    
  /** setter for g_Feminine_StdDev - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setG_Feminine_StdDev(double v) {
    if (GenderFeatures_Type.featOkTst && ((GenderFeatures_Type)jcasType).casFeat_g_Feminine_StdDev == null)
      jcasType.jcas.throwFeatMissing("g_Feminine_StdDev", "de.unidue.henryvdv.ba.type.GenderFeatures");
    jcasType.ll_cas.ll_setDoubleValue(addr, ((GenderFeatures_Type)jcasType).casFeatCode_g_Feminine_StdDev, v);}    
   
    
  //*--------------*
  //* Feature: g_Neutral_Mean

  /** getter for g_Neutral_Mean - gets 
   * @generated
   * @return value of the feature 
   */
  public double getG_Neutral_Mean() {
    if (GenderFeatures_Type.featOkTst && ((GenderFeatures_Type)jcasType).casFeat_g_Neutral_Mean == null)
      jcasType.jcas.throwFeatMissing("g_Neutral_Mean", "de.unidue.henryvdv.ba.type.GenderFeatures");
    return jcasType.ll_cas.ll_getDoubleValue(addr, ((GenderFeatures_Type)jcasType).casFeatCode_g_Neutral_Mean);}
    
  /** setter for g_Neutral_Mean - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setG_Neutral_Mean(double v) {
    if (GenderFeatures_Type.featOkTst && ((GenderFeatures_Type)jcasType).casFeat_g_Neutral_Mean == null)
      jcasType.jcas.throwFeatMissing("g_Neutral_Mean", "de.unidue.henryvdv.ba.type.GenderFeatures");
    jcasType.ll_cas.ll_setDoubleValue(addr, ((GenderFeatures_Type)jcasType).casFeatCode_g_Neutral_Mean, v);}    
   
    
  //*--------------*
  //* Feature: g_Neutral_StdDev

  /** getter for g_Neutral_StdDev - gets 
   * @generated
   * @return value of the feature 
   */
  public double getG_Neutral_StdDev() {
    if (GenderFeatures_Type.featOkTst && ((GenderFeatures_Type)jcasType).casFeat_g_Neutral_StdDev == null)
      jcasType.jcas.throwFeatMissing("g_Neutral_StdDev", "de.unidue.henryvdv.ba.type.GenderFeatures");
    return jcasType.ll_cas.ll_getDoubleValue(addr, ((GenderFeatures_Type)jcasType).casFeatCode_g_Neutral_StdDev);}
    
  /** setter for g_Neutral_StdDev - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setG_Neutral_StdDev(double v) {
    if (GenderFeatures_Type.featOkTst && ((GenderFeatures_Type)jcasType).casFeat_g_Neutral_StdDev == null)
      jcasType.jcas.throwFeatMissing("g_Neutral_StdDev", "de.unidue.henryvdv.ba.type.GenderFeatures");
    jcasType.ll_cas.ll_setDoubleValue(addr, ((GenderFeatures_Type)jcasType).casFeatCode_g_Neutral_StdDev, v);}    
   
    
  //*--------------*
  //* Feature: g_Plural_Mean

  /** getter for g_Plural_Mean - gets 
   * @generated
   * @return value of the feature 
   */
  public double getG_Plural_Mean() {
    if (GenderFeatures_Type.featOkTst && ((GenderFeatures_Type)jcasType).casFeat_g_Plural_Mean == null)
      jcasType.jcas.throwFeatMissing("g_Plural_Mean", "de.unidue.henryvdv.ba.type.GenderFeatures");
    return jcasType.ll_cas.ll_getDoubleValue(addr, ((GenderFeatures_Type)jcasType).casFeatCode_g_Plural_Mean);}
    
  /** setter for g_Plural_Mean - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setG_Plural_Mean(double v) {
    if (GenderFeatures_Type.featOkTst && ((GenderFeatures_Type)jcasType).casFeat_g_Plural_Mean == null)
      jcasType.jcas.throwFeatMissing("g_Plural_Mean", "de.unidue.henryvdv.ba.type.GenderFeatures");
    jcasType.ll_cas.ll_setDoubleValue(addr, ((GenderFeatures_Type)jcasType).casFeatCode_g_Plural_Mean, v);}    
   
    
  //*--------------*
  //* Feature: g_Plural_StdDev

  /** getter for g_Plural_StdDev - gets 
   * @generated
   * @return value of the feature 
   */
  public double getG_Plural_StdDev() {
    if (GenderFeatures_Type.featOkTst && ((GenderFeatures_Type)jcasType).casFeat_g_Plural_StdDev == null)
      jcasType.jcas.throwFeatMissing("g_Plural_StdDev", "de.unidue.henryvdv.ba.type.GenderFeatures");
    return jcasType.ll_cas.ll_getDoubleValue(addr, ((GenderFeatures_Type)jcasType).casFeatCode_g_Plural_StdDev);}
    
  /** setter for g_Plural_StdDev - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setG_Plural_StdDev(double v) {
    if (GenderFeatures_Type.featOkTst && ((GenderFeatures_Type)jcasType).casFeat_g_Plural_StdDev == null)
      jcasType.jcas.throwFeatMissing("g_Plural_StdDev", "de.unidue.henryvdv.ba.type.GenderFeatures");
    jcasType.ll_cas.ll_setDoubleValue(addr, ((GenderFeatures_Type)jcasType).casFeatCode_g_Plural_StdDev, v);}    
  }

    