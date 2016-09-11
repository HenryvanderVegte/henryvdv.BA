

/* First created by JCasGen Sun Aug 14 23:39:14 CEST 2016 */
package de.unidue.henryvdv.ba.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Fri Sep 09 21:03:12 CEST 2016
 * XML source: C:/Users/Henry/Documents/henryvdv.BA/de.unidue.henryvdv.ba/src/main/resources/desc/type/Anaphora.xml
 * @generated */
public class PronounFeatures extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(PronounFeatures.class);
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
  protected PronounFeatures() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public PronounFeatures(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public PronounFeatures(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public PronounFeatures(JCas jcas, int begin, int end) {
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
  //* Feature: p_Masculine

  /** getter for p_Masculine - gets 
   * @generated
   * @return value of the feature 
   */
  public boolean getP_Masculine() {
    if (PronounFeatures_Type.featOkTst && ((PronounFeatures_Type)jcasType).casFeat_p_Masculine == null)
      jcasType.jcas.throwFeatMissing("p_Masculine", "de.unidue.henryvdv.ba.type.PronounFeatures");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((PronounFeatures_Type)jcasType).casFeatCode_p_Masculine);}
    
  /** setter for p_Masculine - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setP_Masculine(boolean v) {
    if (PronounFeatures_Type.featOkTst && ((PronounFeatures_Type)jcasType).casFeat_p_Masculine == null)
      jcasType.jcas.throwFeatMissing("p_Masculine", "de.unidue.henryvdv.ba.type.PronounFeatures");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((PronounFeatures_Type)jcasType).casFeatCode_p_Masculine, v);}    
   
    
  //*--------------*
  //* Feature: p_Feminine

  /** getter for p_Feminine - gets 
   * @generated
   * @return value of the feature 
   */
  public boolean getP_Feminine() {
    if (PronounFeatures_Type.featOkTst && ((PronounFeatures_Type)jcasType).casFeat_p_Feminine == null)
      jcasType.jcas.throwFeatMissing("p_Feminine", "de.unidue.henryvdv.ba.type.PronounFeatures");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((PronounFeatures_Type)jcasType).casFeatCode_p_Feminine);}
    
  /** setter for p_Feminine - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setP_Feminine(boolean v) {
    if (PronounFeatures_Type.featOkTst && ((PronounFeatures_Type)jcasType).casFeat_p_Feminine == null)
      jcasType.jcas.throwFeatMissing("p_Feminine", "de.unidue.henryvdv.ba.type.PronounFeatures");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((PronounFeatures_Type)jcasType).casFeatCode_p_Feminine, v);}    
   
    
  //*--------------*
  //* Feature: p_Neutral

  /** getter for p_Neutral - gets 
   * @generated
   * @return value of the feature 
   */
  public boolean getP_Neutral() {
    if (PronounFeatures_Type.featOkTst && ((PronounFeatures_Type)jcasType).casFeat_p_Neutral == null)
      jcasType.jcas.throwFeatMissing("p_Neutral", "de.unidue.henryvdv.ba.type.PronounFeatures");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((PronounFeatures_Type)jcasType).casFeatCode_p_Neutral);}
    
  /** setter for p_Neutral - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setP_Neutral(boolean v) {
    if (PronounFeatures_Type.featOkTst && ((PronounFeatures_Type)jcasType).casFeat_p_Neutral == null)
      jcasType.jcas.throwFeatMissing("p_Neutral", "de.unidue.henryvdv.ba.type.PronounFeatures");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((PronounFeatures_Type)jcasType).casFeatCode_p_Neutral, v);}    
   
    
  //*--------------*
  //* Feature: p_Plural

  /** getter for p_Plural - gets 
   * @generated
   * @return value of the feature 
   */
  public boolean getP_Plural() {
    if (PronounFeatures_Type.featOkTst && ((PronounFeatures_Type)jcasType).casFeat_p_Plural == null)
      jcasType.jcas.throwFeatMissing("p_Plural", "de.unidue.henryvdv.ba.type.PronounFeatures");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((PronounFeatures_Type)jcasType).casFeatCode_p_Plural);}
    
  /** setter for p_Plural - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setP_Plural(boolean v) {
    if (PronounFeatures_Type.featOkTst && ((PronounFeatures_Type)jcasType).casFeat_p_Plural == null)
      jcasType.jcas.throwFeatMissing("p_Plural", "de.unidue.henryvdv.ba.type.PronounFeatures");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((PronounFeatures_Type)jcasType).casFeatCode_p_Plural, v);}    
  }

    