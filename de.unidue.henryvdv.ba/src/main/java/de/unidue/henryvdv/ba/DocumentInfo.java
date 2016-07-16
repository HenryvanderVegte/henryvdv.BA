

/* First created by JCasGen Sat Jul 16 17:39:08 CEST 2016 */
package de.unidue.henryvdv.ba;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Sat Jul 16 17:39:08 CEST 2016
 * XML source: C:/Users/Henry/Documents/henryvdv.BA/de.unidue.henryvdv.ba/src/main/resources/desc/type/DocumentInfo.xml
 * @generated */
public class DocumentInfo extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(DocumentInfo.class);
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
  protected DocumentInfo() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public DocumentInfo(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public DocumentInfo(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public DocumentInfo(JCas jcas, int begin, int end) {
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
  //* Feature: documentName

  /** getter for documentName - gets 
   * @generated
   * @return value of the feature 
   */
  public String getDocumentName() {
    if (DocumentInfo_Type.featOkTst && ((DocumentInfo_Type)jcasType).casFeat_documentName == null)
      jcasType.jcas.throwFeatMissing("documentName", "de.unidue.henryvdv.ba.DocumentInfo");
    return jcasType.ll_cas.ll_getStringValue(addr, ((DocumentInfo_Type)jcasType).casFeatCode_documentName);}
    
  /** setter for documentName - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setDocumentName(String v) {
    if (DocumentInfo_Type.featOkTst && ((DocumentInfo_Type)jcasType).casFeat_documentName == null)
      jcasType.jcas.throwFeatMissing("documentName", "de.unidue.henryvdv.ba.DocumentInfo");
    jcasType.ll_cas.ll_setStringValue(addr, ((DocumentInfo_Type)jcasType).casFeatCode_documentName, v);}    
  }

    