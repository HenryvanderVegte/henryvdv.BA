

/* First created by JCasGen Fri Jul 29 14:01:49 CEST 2016 */
package de.unidue.henryvdv.ba.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Fri Jul 29 14:01:49 CEST 2016
 * XML source: C:/Users/Henry/Documents/henryvdv.BA/de.unidue.henryvdv.ba/src/main/resources/desc/type/NegativeTrainingInstance.xml
 * @generated */
public class NegativeTrainingInstance extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(NegativeTrainingInstance.class);
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
  protected NegativeTrainingInstance() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public NegativeTrainingInstance(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public NegativeTrainingInstance(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public NegativeTrainingInstance(JCas jcas, int begin, int end) {
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
  //* Feature: anaphora

  /** getter for anaphora - gets 
   * @generated
   * @return value of the feature 
   */
  public Anaphora getAnaphora() {
    if (NegativeTrainingInstance_Type.featOkTst && ((NegativeTrainingInstance_Type)jcasType).casFeat_anaphora == null)
      jcasType.jcas.throwFeatMissing("anaphora", "de.unidue.henryvdv.ba.type.NegativeTrainingInstance");
    return (Anaphora)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((NegativeTrainingInstance_Type)jcasType).casFeatCode_anaphora)));}
    
  /** setter for anaphora - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setAnaphora(Anaphora v) {
    if (NegativeTrainingInstance_Type.featOkTst && ((NegativeTrainingInstance_Type)jcasType).casFeat_anaphora == null)
      jcasType.jcas.throwFeatMissing("anaphora", "de.unidue.henryvdv.ba.type.NegativeTrainingInstance");
    jcasType.ll_cas.ll_setRefValue(addr, ((NegativeTrainingInstance_Type)jcasType).casFeatCode_anaphora, jcasType.ll_cas.ll_getFSRef(v));}    
  }

    