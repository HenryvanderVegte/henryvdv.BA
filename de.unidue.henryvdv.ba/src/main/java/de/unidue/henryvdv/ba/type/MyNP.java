

/* First created by JCasGen Fri Sep 30 18:40:42 CEST 2016 */
package de.unidue.henryvdv.ba.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Fri Sep 30 18:44:55 CEST 2016
 * XML source: C:/Users/Henry/Documents/henryvdv.BA/de.unidue.henryvdv.ba/src/main/resources/desc/type/MyNP.xml
 * @generated */
public class MyNP extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(MyNP.class);
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
  protected MyNP() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public MyNP(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public MyNP(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public MyNP(JCas jcas, int begin, int end) {
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
  //* Feature: outputValue

  /** getter for outputValue - gets 
   * @generated
   * @return value of the feature 
   */
  public float getOutputValue() {
    if (MyNP_Type.featOkTst && ((MyNP_Type)jcasType).casFeat_outputValue == null)
      jcasType.jcas.throwFeatMissing("outputValue", "de.unidue.henryvdv.ba.type.MyNP");
    return jcasType.ll_cas.ll_getFloatValue(addr, ((MyNP_Type)jcasType).casFeatCode_outputValue);}
    
  /** setter for outputValue - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setOutputValue(float v) {
    if (MyNP_Type.featOkTst && ((MyNP_Type)jcasType).casFeat_outputValue == null)
      jcasType.jcas.throwFeatMissing("outputValue", "de.unidue.henryvdv.ba.type.MyNP");
    jcasType.ll_cas.ll_setFloatValue(addr, ((MyNP_Type)jcasType).casFeatCode_outputValue, v);}    
   
    
  //*--------------*
  //* Feature: featureVector

  /** getter for featureVector - gets 
   * @generated
   * @return value of the feature 
   */
  public String getFeatureVector() {
    if (MyNP_Type.featOkTst && ((MyNP_Type)jcasType).casFeat_featureVector == null)
      jcasType.jcas.throwFeatMissing("featureVector", "de.unidue.henryvdv.ba.type.MyNP");
    return jcasType.ll_cas.ll_getStringValue(addr, ((MyNP_Type)jcasType).casFeatCode_featureVector);}
    
  /** setter for featureVector - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setFeatureVector(String v) {
    if (MyNP_Type.featOkTst && ((MyNP_Type)jcasType).casFeat_featureVector == null)
      jcasType.jcas.throwFeatMissing("featureVector", "de.unidue.henryvdv.ba.type.MyNP");
    jcasType.ll_cas.ll_setStringValue(addr, ((MyNP_Type)jcasType).casFeatCode_featureVector, v);}    
  }

    