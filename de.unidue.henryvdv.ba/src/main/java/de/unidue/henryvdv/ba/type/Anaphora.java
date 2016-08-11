

/* First created by JCasGen Thu Jul 28 18:45:35 CEST 2016 */
package de.unidue.henryvdv.ba.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Thu Aug 11 18:56:41 CEST 2016
 * XML source: C:/Users/Henry/Documents/henryvdv.BA/de.unidue.henryvdv.ba/src/main/resources/desc/type/Anaphora.xml
 * @generated */
public class Anaphora extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Anaphora.class);
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
  protected Anaphora() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Anaphora(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Anaphora(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Anaphora(JCas jcas, int begin, int end) {
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
  //* Feature: antecedent

  /** getter for antecedent - gets 
   * @generated
   * @return value of the feature 
   */
  public Antecedent getAntecedent() {
    if (Anaphora_Type.featOkTst && ((Anaphora_Type)jcasType).casFeat_antecedent == null)
      jcasType.jcas.throwFeatMissing("antecedent", "de.unidue.henryvdv.ba.type.Anaphora");
    return (Antecedent)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Anaphora_Type)jcasType).casFeatCode_antecedent)));}
    
  /** setter for antecedent - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setAntecedent(Antecedent v) {
    if (Anaphora_Type.featOkTst && ((Anaphora_Type)jcasType).casFeat_antecedent == null)
      jcasType.jcas.throwFeatMissing("antecedent", "de.unidue.henryvdv.ba.type.Anaphora");
    jcasType.ll_cas.ll_setRefValue(addr, ((Anaphora_Type)jcasType).casFeatCode_antecedent, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: antecedentFeatures

  /** getter for antecedentFeatures - gets 
   * @generated
   * @return value of the feature 
   */
  public AntecedentFeatures getAntecedentFeatures() {
    if (Anaphora_Type.featOkTst && ((Anaphora_Type)jcasType).casFeat_antecedentFeatures == null)
      jcasType.jcas.throwFeatMissing("antecedentFeatures", "de.unidue.henryvdv.ba.type.Anaphora");
    return (AntecedentFeatures)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Anaphora_Type)jcasType).casFeatCode_antecedentFeatures)));}
    
  /** setter for antecedentFeatures - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setAntecedentFeatures(AntecedentFeatures v) {
    if (Anaphora_Type.featOkTst && ((Anaphora_Type)jcasType).casFeat_antecedentFeatures == null)
      jcasType.jcas.throwFeatMissing("antecedentFeatures", "de.unidue.henryvdv.ba.type.Anaphora");
    jcasType.ll_cas.ll_setRefValue(addr, ((Anaphora_Type)jcasType).casFeatCode_antecedentFeatures, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: pronounAntecedentFeatures

  /** getter for pronounAntecedentFeatures - gets 
   * @generated
   * @return value of the feature 
   */
  public PronounAntecedentFeatures getPronounAntecedentFeatures() {
    if (Anaphora_Type.featOkTst && ((Anaphora_Type)jcasType).casFeat_pronounAntecedentFeatures == null)
      jcasType.jcas.throwFeatMissing("pronounAntecedentFeatures", "de.unidue.henryvdv.ba.type.Anaphora");
    return (PronounAntecedentFeatures)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Anaphora_Type)jcasType).casFeatCode_pronounAntecedentFeatures)));}
    
  /** setter for pronounAntecedentFeatures - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setPronounAntecedentFeatures(PronounAntecedentFeatures v) {
    if (Anaphora_Type.featOkTst && ((Anaphora_Type)jcasType).casFeat_pronounAntecedentFeatures == null)
      jcasType.jcas.throwFeatMissing("pronounAntecedentFeatures", "de.unidue.henryvdv.ba.type.Anaphora");
    jcasType.ll_cas.ll_setRefValue(addr, ((Anaphora_Type)jcasType).casFeatCode_pronounAntecedentFeatures, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: hasCorrectAntecedent

  /** getter for hasCorrectAntecedent - gets Determines if it is a positive or negative instance
   * @generated
   * @return value of the feature 
   */
  public boolean getHasCorrectAntecedent() {
    if (Anaphora_Type.featOkTst && ((Anaphora_Type)jcasType).casFeat_hasCorrectAntecedent == null)
      jcasType.jcas.throwFeatMissing("hasCorrectAntecedent", "de.unidue.henryvdv.ba.type.Anaphora");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((Anaphora_Type)jcasType).casFeatCode_hasCorrectAntecedent);}
    
  /** setter for hasCorrectAntecedent - sets Determines if it is a positive or negative instance 
   * @generated
   * @param v value to set into the feature 
   */
  public void setHasCorrectAntecedent(boolean v) {
    if (Anaphora_Type.featOkTst && ((Anaphora_Type)jcasType).casFeat_hasCorrectAntecedent == null)
      jcasType.jcas.throwFeatMissing("hasCorrectAntecedent", "de.unidue.henryvdv.ba.type.Anaphora");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((Anaphora_Type)jcasType).casFeatCode_hasCorrectAntecedent, v);}    
  }

    