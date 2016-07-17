

/* First created by JCasGen Sun Jul 17 12:54:32 CEST 2016 */
package de.unidue.henryvdv.ba.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Sun Jul 17 12:54:32 CEST 2016
 * XML source: C:/Users/Henry/Documents/henryvdv.BA/de.unidue.henryvdv.ba/src/main/resources/desc/type/MyCoreferenceChain.xml
 * @generated */
public class MyCoreferenceChain extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(MyCoreferenceChain.class);
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
  protected MyCoreferenceChain() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public MyCoreferenceChain(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public MyCoreferenceChain(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public MyCoreferenceChain(JCas jcas, int begin, int end) {
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
  //* Feature: first

  /** getter for first - gets 
   * @generated
   * @return value of the feature 
   */
  public MyCoreferenceLink getFirst() {
    if (MyCoreferenceChain_Type.featOkTst && ((MyCoreferenceChain_Type)jcasType).casFeat_first == null)
      jcasType.jcas.throwFeatMissing("first", "de.unidue.henryvdv.ba.type.MyCoreferenceChain");
    return (MyCoreferenceLink)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((MyCoreferenceChain_Type)jcasType).casFeatCode_first)));}
    
  /** setter for first - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setFirst(MyCoreferenceLink v) {
    if (MyCoreferenceChain_Type.featOkTst && ((MyCoreferenceChain_Type)jcasType).casFeat_first == null)
      jcasType.jcas.throwFeatMissing("first", "de.unidue.henryvdv.ba.type.MyCoreferenceChain");
    jcasType.ll_cas.ll_setRefValue(addr, ((MyCoreferenceChain_Type)jcasType).casFeatCode_first, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: corefClass

  /** getter for corefClass - gets 
   * @generated
   * @return value of the feature 
   */
  public String getCorefClass() {
    if (MyCoreferenceChain_Type.featOkTst && ((MyCoreferenceChain_Type)jcasType).casFeat_corefClass == null)
      jcasType.jcas.throwFeatMissing("corefClass", "de.unidue.henryvdv.ba.type.MyCoreferenceChain");
    return jcasType.ll_cas.ll_getStringValue(addr, ((MyCoreferenceChain_Type)jcasType).casFeatCode_corefClass);}
    
  /** setter for corefClass - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setCorefClass(String v) {
    if (MyCoreferenceChain_Type.featOkTst && ((MyCoreferenceChain_Type)jcasType).casFeat_corefClass == null)
      jcasType.jcas.throwFeatMissing("corefClass", "de.unidue.henryvdv.ba.type.MyCoreferenceChain");
    jcasType.ll_cas.ll_setStringValue(addr, ((MyCoreferenceChain_Type)jcasType).casFeatCode_corefClass, v);}    
  }

    