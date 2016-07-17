

/* First created by JCasGen Sun Jul 17 12:51:16 CEST 2016 */
package de.unidue.henryvdv.ba.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Sun Jul 17 12:54:32 CEST 2016
 * XML source: C:/Users/Henry/Documents/henryvdv.BA/de.unidue.henryvdv.ba/src/main/resources/desc/type/MyCoreferenceChain.xml
 * @generated */
public class MyCoreferenceLink extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(MyCoreferenceLink.class);
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
  protected MyCoreferenceLink() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public MyCoreferenceLink(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public MyCoreferenceLink(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public MyCoreferenceLink(JCas jcas, int begin, int end) {
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
  //* Feature: next

  /** getter for next - gets 
   * @generated
   * @return value of the feature 
   */
  public MyCoreferenceLink getNext() {
    if (MyCoreferenceLink_Type.featOkTst && ((MyCoreferenceLink_Type)jcasType).casFeat_next == null)
      jcasType.jcas.throwFeatMissing("next", "de.unidue.henryvdv.ba.type.MyCoreferenceLink");
    return (MyCoreferenceLink)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((MyCoreferenceLink_Type)jcasType).casFeatCode_next)));}
    
  /** setter for next - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setNext(MyCoreferenceLink v) {
    if (MyCoreferenceLink_Type.featOkTst && ((MyCoreferenceLink_Type)jcasType).casFeat_next == null)
      jcasType.jcas.throwFeatMissing("next", "de.unidue.henryvdv.ba.type.MyCoreferenceLink");
    jcasType.ll_cas.ll_setRefValue(addr, ((MyCoreferenceLink_Type)jcasType).casFeatCode_next, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: corefType

  /** getter for corefType - gets 
   * @generated
   * @return value of the feature 
   */
  public String getCorefType() {
    if (MyCoreferenceLink_Type.featOkTst && ((MyCoreferenceLink_Type)jcasType).casFeat_corefType == null)
      jcasType.jcas.throwFeatMissing("corefType", "de.unidue.henryvdv.ba.type.MyCoreferenceLink");
    return jcasType.ll_cas.ll_getStringValue(addr, ((MyCoreferenceLink_Type)jcasType).casFeatCode_corefType);}
    
  /** setter for corefType - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setCorefType(String v) {
    if (MyCoreferenceLink_Type.featOkTst && ((MyCoreferenceLink_Type)jcasType).casFeat_corefType == null)
      jcasType.jcas.throwFeatMissing("corefType", "de.unidue.henryvdv.ba.type.MyCoreferenceLink");
    jcasType.ll_cas.ll_setStringValue(addr, ((MyCoreferenceLink_Type)jcasType).casFeatCode_corefType, v);}    
   
    
  //*--------------*
  //* Feature: mentionType

  /** getter for mentionType - gets 
   * @generated
   * @return value of the feature 
   */
  public String getMentionType() {
    if (MyCoreferenceLink_Type.featOkTst && ((MyCoreferenceLink_Type)jcasType).casFeat_mentionType == null)
      jcasType.jcas.throwFeatMissing("mentionType", "de.unidue.henryvdv.ba.type.MyCoreferenceLink");
    return jcasType.ll_cas.ll_getStringValue(addr, ((MyCoreferenceLink_Type)jcasType).casFeatCode_mentionType);}
    
  /** setter for mentionType - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setMentionType(String v) {
    if (MyCoreferenceLink_Type.featOkTst && ((MyCoreferenceLink_Type)jcasType).casFeat_mentionType == null)
      jcasType.jcas.throwFeatMissing("mentionType", "de.unidue.henryvdv.ba.type.MyCoreferenceLink");
    jcasType.ll_cas.ll_setStringValue(addr, ((MyCoreferenceLink_Type)jcasType).casFeatCode_mentionType, v);}    
  }

    