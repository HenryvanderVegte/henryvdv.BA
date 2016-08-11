

/* First created by JCasGen Wed Aug 10 13:44:08 CEST 2016 */
package de.unidue.henryvdv.ba.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Wed Aug 10 13:46:48 CEST 2016
 * XML source: C:/Users/Henry/Documents/henryvdv.BA/de.unidue.henryvdv.ba/src/main/resources/desc/type/Anaphora.xml
 * @generated */
public class AntecedentFeatures extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(AntecedentFeatures.class);
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
  protected AntecedentFeatures() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public AntecedentFeatures(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public AntecedentFeatures(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public AntecedentFeatures(JCas jcas, int begin, int end) {
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
  //* Feature: a_AntecedentFrequency

  /** getter for a_AntecedentFrequency - gets 
   * @generated
   * @return value of the feature 
   */
  public float getA_AntecedentFrequency() {
    if (AntecedentFeatures_Type.featOkTst && ((AntecedentFeatures_Type)jcasType).casFeat_a_AntecedentFrequency == null)
      jcasType.jcas.throwFeatMissing("a_AntecedentFrequency", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    return jcasType.ll_cas.ll_getFloatValue(addr, ((AntecedentFeatures_Type)jcasType).casFeatCode_a_AntecedentFrequency);}
    
  /** setter for a_AntecedentFrequency - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setA_AntecedentFrequency(float v) {
    if (AntecedentFeatures_Type.featOkTst && ((AntecedentFeatures_Type)jcasType).casFeat_a_AntecedentFrequency == null)
      jcasType.jcas.throwFeatMissing("a_AntecedentFrequency", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    jcasType.ll_cas.ll_setFloatValue(addr, ((AntecedentFeatures_Type)jcasType).casFeatCode_a_AntecedentFrequency, v);}    
   
    
  //*--------------*
  //* Feature: a_Subject

  /** getter for a_Subject - gets 
   * @generated
   * @return value of the feature 
   */
  public boolean getA_Subject() {
    if (AntecedentFeatures_Type.featOkTst && ((AntecedentFeatures_Type)jcasType).casFeat_a_Subject == null)
      jcasType.jcas.throwFeatMissing("a_Subject", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((AntecedentFeatures_Type)jcasType).casFeatCode_a_Subject);}
    
  /** setter for a_Subject - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setA_Subject(boolean v) {
    if (AntecedentFeatures_Type.featOkTst && ((AntecedentFeatures_Type)jcasType).casFeat_a_Subject == null)
      jcasType.jcas.throwFeatMissing("a_Subject", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((AntecedentFeatures_Type)jcasType).casFeatCode_a_Subject, v);}    
   
    
  //*--------------*
  //* Feature: a_Object

  /** getter for a_Object - gets 
   * @generated
   * @return value of the feature 
   */
  public boolean getA_Object() {
    if (AntecedentFeatures_Type.featOkTst && ((AntecedentFeatures_Type)jcasType).casFeat_a_Object == null)
      jcasType.jcas.throwFeatMissing("a_Object", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((AntecedentFeatures_Type)jcasType).casFeatCode_a_Object);}
    
  /** setter for a_Object - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setA_Object(boolean v) {
    if (AntecedentFeatures_Type.featOkTst && ((AntecedentFeatures_Type)jcasType).casFeat_a_Object == null)
      jcasType.jcas.throwFeatMissing("a_Object", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((AntecedentFeatures_Type)jcasType).casFeatCode_a_Object, v);}    
   
    
  //*--------------*
  //* Feature: a_Predicate

  /** getter for a_Predicate - gets 
   * @generated
   * @return value of the feature 
   */
  public boolean getA_Predicate() {
    if (AntecedentFeatures_Type.featOkTst && ((AntecedentFeatures_Type)jcasType).casFeat_a_Predicate == null)
      jcasType.jcas.throwFeatMissing("a_Predicate", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((AntecedentFeatures_Type)jcasType).casFeatCode_a_Predicate);}
    
  /** setter for a_Predicate - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setA_Predicate(boolean v) {
    if (AntecedentFeatures_Type.featOkTst && ((AntecedentFeatures_Type)jcasType).casFeat_a_Predicate == null)
      jcasType.jcas.throwFeatMissing("a_Predicate", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((AntecedentFeatures_Type)jcasType).casFeatCode_a_Predicate, v);}    
   
    
  //*--------------*
  //* Feature: a_Pronominal

  /** getter for a_Pronominal - gets 
   * @generated
   * @return value of the feature 
   */
  public boolean getA_Pronominal() {
    if (AntecedentFeatures_Type.featOkTst && ((AntecedentFeatures_Type)jcasType).casFeat_a_Pronominal == null)
      jcasType.jcas.throwFeatMissing("a_Pronominal", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((AntecedentFeatures_Type)jcasType).casFeatCode_a_Pronominal);}
    
  /** setter for a_Pronominal - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setA_Pronominal(boolean v) {
    if (AntecedentFeatures_Type.featOkTst && ((AntecedentFeatures_Type)jcasType).casFeat_a_Pronominal == null)
      jcasType.jcas.throwFeatMissing("a_Pronominal", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((AntecedentFeatures_Type)jcasType).casFeatCode_a_Pronominal, v);}    
   
    
  //*--------------*
  //* Feature: a_Prepositional

  /** getter for a_Prepositional - gets 
   * @generated
   * @return value of the feature 
   */
  public boolean getA_Prepositional() {
    if (AntecedentFeatures_Type.featOkTst && ((AntecedentFeatures_Type)jcasType).casFeat_a_Prepositional == null)
      jcasType.jcas.throwFeatMissing("a_Prepositional", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((AntecedentFeatures_Type)jcasType).casFeatCode_a_Prepositional);}
    
  /** setter for a_Prepositional - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setA_Prepositional(boolean v) {
    if (AntecedentFeatures_Type.featOkTst && ((AntecedentFeatures_Type)jcasType).casFeat_a_Prepositional == null)
      jcasType.jcas.throwFeatMissing("a_Prepositional", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((AntecedentFeatures_Type)jcasType).casFeatCode_a_Prepositional, v);}    
   
    
  //*--------------*
  //* Feature: a_HeadWordEmphasis

  /** getter for a_HeadWordEmphasis - gets 
   * @generated
   * @return value of the feature 
   */
  public boolean getA_HeadWordEmphasis() {
    if (AntecedentFeatures_Type.featOkTst && ((AntecedentFeatures_Type)jcasType).casFeat_a_HeadWordEmphasis == null)
      jcasType.jcas.throwFeatMissing("a_HeadWordEmphasis", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((AntecedentFeatures_Type)jcasType).casFeatCode_a_HeadWordEmphasis);}
    
  /** setter for a_HeadWordEmphasis - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setA_HeadWordEmphasis(boolean v) {
    if (AntecedentFeatures_Type.featOkTst && ((AntecedentFeatures_Type)jcasType).casFeat_a_HeadWordEmphasis == null)
      jcasType.jcas.throwFeatMissing("a_HeadWordEmphasis", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((AntecedentFeatures_Type)jcasType).casFeatCode_a_HeadWordEmphasis, v);}    
   
    
  //*--------------*
  //* Feature: a_Conjunction

  /** getter for a_Conjunction - gets 
   * @generated
   * @return value of the feature 
   */
  public boolean getA_Conjunction() {
    if (AntecedentFeatures_Type.featOkTst && ((AntecedentFeatures_Type)jcasType).casFeat_a_Conjunction == null)
      jcasType.jcas.throwFeatMissing("a_Conjunction", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((AntecedentFeatures_Type)jcasType).casFeatCode_a_Conjunction);}
    
  /** setter for a_Conjunction - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setA_Conjunction(boolean v) {
    if (AntecedentFeatures_Type.featOkTst && ((AntecedentFeatures_Type)jcasType).casFeat_a_Conjunction == null)
      jcasType.jcas.throwFeatMissing("a_Conjunction", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((AntecedentFeatures_Type)jcasType).casFeatCode_a_Conjunction, v);}    
   
    
  //*--------------*
  //* Feature: a_PrenominalModifier

  /** getter for a_PrenominalModifier - gets 
   * @generated
   * @return value of the feature 
   */
  public boolean getA_PrenominalModifier() {
    if (AntecedentFeatures_Type.featOkTst && ((AntecedentFeatures_Type)jcasType).casFeat_a_PrenominalModifier == null)
      jcasType.jcas.throwFeatMissing("a_PrenominalModifier", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((AntecedentFeatures_Type)jcasType).casFeatCode_a_PrenominalModifier);}
    
  /** setter for a_PrenominalModifier - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setA_PrenominalModifier(boolean v) {
    if (AntecedentFeatures_Type.featOkTst && ((AntecedentFeatures_Type)jcasType).casFeat_a_PrenominalModifier == null)
      jcasType.jcas.throwFeatMissing("a_PrenominalModifier", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((AntecedentFeatures_Type)jcasType).casFeatCode_a_PrenominalModifier, v);}    
   
    
  //*--------------*
  //* Feature: a_Org

  /** getter for a_Org - gets 
   * @generated
   * @return value of the feature 
   */
  public boolean getA_Org() {
    if (AntecedentFeatures_Type.featOkTst && ((AntecedentFeatures_Type)jcasType).casFeat_a_Org == null)
      jcasType.jcas.throwFeatMissing("a_Org", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((AntecedentFeatures_Type)jcasType).casFeatCode_a_Org);}
    
  /** setter for a_Org - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setA_Org(boolean v) {
    if (AntecedentFeatures_Type.featOkTst && ((AntecedentFeatures_Type)jcasType).casFeat_a_Org == null)
      jcasType.jcas.throwFeatMissing("a_Org", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((AntecedentFeatures_Type)jcasType).casFeatCode_a_Org, v);}    
   
    
  //*--------------*
  //* Feature: a_Person

  /** getter for a_Person - gets 
   * @generated
   * @return value of the feature 
   */
  public boolean getA_Person() {
    if (AntecedentFeatures_Type.featOkTst && ((AntecedentFeatures_Type)jcasType).casFeat_a_Person == null)
      jcasType.jcas.throwFeatMissing("a_Person", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((AntecedentFeatures_Type)jcasType).casFeatCode_a_Person);}
    
  /** setter for a_Person - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setA_Person(boolean v) {
    if (AntecedentFeatures_Type.featOkTst && ((AntecedentFeatures_Type)jcasType).casFeat_a_Person == null)
      jcasType.jcas.throwFeatMissing("a_Person", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((AntecedentFeatures_Type)jcasType).casFeatCode_a_Person, v);}    
   
    
  //*--------------*
  //* Feature: a_Time

  /** getter for a_Time - gets 
   * @generated
   * @return value of the feature 
   */
  public boolean getA_Time() {
    if (AntecedentFeatures_Type.featOkTst && ((AntecedentFeatures_Type)jcasType).casFeat_a_Time == null)
      jcasType.jcas.throwFeatMissing("a_Time", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((AntecedentFeatures_Type)jcasType).casFeatCode_a_Time);}
    
  /** setter for a_Time - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setA_Time(boolean v) {
    if (AntecedentFeatures_Type.featOkTst && ((AntecedentFeatures_Type)jcasType).casFeat_a_Time == null)
      jcasType.jcas.throwFeatMissing("a_Time", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((AntecedentFeatures_Type)jcasType).casFeatCode_a_Time, v);}    
   
    
  //*--------------*
  //* Feature: a_Date

  /** getter for a_Date - gets 
   * @generated
   * @return value of the feature 
   */
  public boolean getA_Date() {
    if (AntecedentFeatures_Type.featOkTst && ((AntecedentFeatures_Type)jcasType).casFeat_a_Date == null)
      jcasType.jcas.throwFeatMissing("a_Date", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((AntecedentFeatures_Type)jcasType).casFeatCode_a_Date);}
    
  /** setter for a_Date - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setA_Date(boolean v) {
    if (AntecedentFeatures_Type.featOkTst && ((AntecedentFeatures_Type)jcasType).casFeat_a_Date == null)
      jcasType.jcas.throwFeatMissing("a_Date", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((AntecedentFeatures_Type)jcasType).casFeatCode_a_Date, v);}    
   
    
  //*--------------*
  //* Feature: a_Money

  /** getter for a_Money - gets 
   * @generated
   * @return value of the feature 
   */
  public boolean getA_Money() {
    if (AntecedentFeatures_Type.featOkTst && ((AntecedentFeatures_Type)jcasType).casFeat_a_Money == null)
      jcasType.jcas.throwFeatMissing("a_Money", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((AntecedentFeatures_Type)jcasType).casFeatCode_a_Money);}
    
  /** setter for a_Money - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setA_Money(boolean v) {
    if (AntecedentFeatures_Type.featOkTst && ((AntecedentFeatures_Type)jcasType).casFeat_a_Money == null)
      jcasType.jcas.throwFeatMissing("a_Money", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((AntecedentFeatures_Type)jcasType).casFeatCode_a_Money, v);}    
   
    
  //*--------------*
  //* Feature: a_Price

  /** getter for a_Price - gets 
   * @generated
   * @return value of the feature 
   */
  public boolean getA_Price() {
    if (AntecedentFeatures_Type.featOkTst && ((AntecedentFeatures_Type)jcasType).casFeat_a_Price == null)
      jcasType.jcas.throwFeatMissing("a_Price", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((AntecedentFeatures_Type)jcasType).casFeatCode_a_Price);}
    
  /** setter for a_Price - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setA_Price(boolean v) {
    if (AntecedentFeatures_Type.featOkTst && ((AntecedentFeatures_Type)jcasType).casFeat_a_Price == null)
      jcasType.jcas.throwFeatMissing("a_Price", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((AntecedentFeatures_Type)jcasType).casFeatCode_a_Price, v);}    
   
    
  //*--------------*
  //* Feature: a_Amount

  /** getter for a_Amount - gets 
   * @generated
   * @return value of the feature 
   */
  public boolean getA_Amount() {
    if (AntecedentFeatures_Type.featOkTst && ((AntecedentFeatures_Type)jcasType).casFeat_a_Amount == null)
      jcasType.jcas.throwFeatMissing("a_Amount", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((AntecedentFeatures_Type)jcasType).casFeatCode_a_Amount);}
    
  /** setter for a_Amount - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setA_Amount(boolean v) {
    if (AntecedentFeatures_Type.featOkTst && ((AntecedentFeatures_Type)jcasType).casFeat_a_Amount == null)
      jcasType.jcas.throwFeatMissing("a_Amount", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((AntecedentFeatures_Type)jcasType).casFeatCode_a_Amount, v);}    
   
    
  //*--------------*
  //* Feature: a_Number

  /** getter for a_Number - gets 
   * @generated
   * @return value of the feature 
   */
  public boolean getA_Number() {
    if (AntecedentFeatures_Type.featOkTst && ((AntecedentFeatures_Type)jcasType).casFeat_a_Number == null)
      jcasType.jcas.throwFeatMissing("a_Number", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((AntecedentFeatures_Type)jcasType).casFeatCode_a_Number);}
    
  /** setter for a_Number - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setA_Number(boolean v) {
    if (AntecedentFeatures_Type.featOkTst && ((AntecedentFeatures_Type)jcasType).casFeat_a_Number == null)
      jcasType.jcas.throwFeatMissing("a_Number", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((AntecedentFeatures_Type)jcasType).casFeatCode_a_Number, v);}    
   
    
  //*--------------*
  //* Feature: a_Definite

  /** getter for a_Definite - gets 
   * @generated
   * @return value of the feature 
   */
  public boolean getA_Definite() {
    if (AntecedentFeatures_Type.featOkTst && ((AntecedentFeatures_Type)jcasType).casFeat_a_Definite == null)
      jcasType.jcas.throwFeatMissing("a_Definite", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((AntecedentFeatures_Type)jcasType).casFeatCode_a_Definite);}
    
  /** setter for a_Definite - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setA_Definite(boolean v) {
    if (AntecedentFeatures_Type.featOkTst && ((AntecedentFeatures_Type)jcasType).casFeat_a_Definite == null)
      jcasType.jcas.throwFeatMissing("a_Definite", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((AntecedentFeatures_Type)jcasType).casFeatCode_a_Definite, v);}    
   
    
  //*--------------*
  //* Feature: a_HisHer

  /** getter for a_HisHer - gets 
   * @generated
   * @return value of the feature 
   */
  public boolean getA_HisHer() {
    if (AntecedentFeatures_Type.featOkTst && ((AntecedentFeatures_Type)jcasType).casFeat_a_HisHer == null)
      jcasType.jcas.throwFeatMissing("a_HisHer", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((AntecedentFeatures_Type)jcasType).casFeatCode_a_HisHer);}
    
  /** setter for a_HisHer - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setA_HisHer(boolean v) {
    if (AntecedentFeatures_Type.featOkTst && ((AntecedentFeatures_Type)jcasType).casFeat_a_HisHer == null)
      jcasType.jcas.throwFeatMissing("a_HisHer", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((AntecedentFeatures_Type)jcasType).casFeatCode_a_HisHer, v);}    
   
    
  //*--------------*
  //* Feature: a_HeHis

  /** getter for a_HeHis - gets 
   * @generated
   * @return value of the feature 
   */
  public boolean getA_HeHis() {
    if (AntecedentFeatures_Type.featOkTst && ((AntecedentFeatures_Type)jcasType).casFeat_a_HeHis == null)
      jcasType.jcas.throwFeatMissing("a_HeHis", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((AntecedentFeatures_Type)jcasType).casFeatCode_a_HeHis);}
    
  /** setter for a_HeHis - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setA_HeHis(boolean v) {
    if (AntecedentFeatures_Type.featOkTst && ((AntecedentFeatures_Type)jcasType).casFeat_a_HeHis == null)
      jcasType.jcas.throwFeatMissing("a_HeHis", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((AntecedentFeatures_Type)jcasType).casFeatCode_a_HeHis, v);}    
  }

    