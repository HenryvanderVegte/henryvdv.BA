
/* First created by JCasGen Sun Jul 17 12:51:16 CEST 2016 */
package de.unidue.henryvdv.ba.type;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** 
 * Updated by JCasGen Sun Jul 17 12:54:32 CEST 2016
 * @generated */
public class MyCoreferenceLink_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (MyCoreferenceLink_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = MyCoreferenceLink_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new MyCoreferenceLink(addr, MyCoreferenceLink_Type.this);
  			   MyCoreferenceLink_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new MyCoreferenceLink(addr, MyCoreferenceLink_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = MyCoreferenceLink.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.unidue.henryvdv.ba.type.MyCoreferenceLink");
 
  /** @generated */
  final Feature casFeat_next;
  /** @generated */
  final int     casFeatCode_next;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getNext(int addr) {
        if (featOkTst && casFeat_next == null)
      jcas.throwFeatMissing("next", "de.unidue.henryvdv.ba.type.MyCoreferenceLink");
    return ll_cas.ll_getRefValue(addr, casFeatCode_next);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setNext(int addr, int v) {
        if (featOkTst && casFeat_next == null)
      jcas.throwFeatMissing("next", "de.unidue.henryvdv.ba.type.MyCoreferenceLink");
    ll_cas.ll_setRefValue(addr, casFeatCode_next, v);}
    
  
 
  /** @generated */
  final Feature casFeat_corefType;
  /** @generated */
  final int     casFeatCode_corefType;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getCorefType(int addr) {
        if (featOkTst && casFeat_corefType == null)
      jcas.throwFeatMissing("corefType", "de.unidue.henryvdv.ba.type.MyCoreferenceLink");
    return ll_cas.ll_getStringValue(addr, casFeatCode_corefType);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setCorefType(int addr, String v) {
        if (featOkTst && casFeat_corefType == null)
      jcas.throwFeatMissing("corefType", "de.unidue.henryvdv.ba.type.MyCoreferenceLink");
    ll_cas.ll_setStringValue(addr, casFeatCode_corefType, v);}
    
  
 
  /** @generated */
  final Feature casFeat_mentionType;
  /** @generated */
  final int     casFeatCode_mentionType;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getMentionType(int addr) {
        if (featOkTst && casFeat_mentionType == null)
      jcas.throwFeatMissing("mentionType", "de.unidue.henryvdv.ba.type.MyCoreferenceLink");
    return ll_cas.ll_getStringValue(addr, casFeatCode_mentionType);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setMentionType(int addr, String v) {
        if (featOkTst && casFeat_mentionType == null)
      jcas.throwFeatMissing("mentionType", "de.unidue.henryvdv.ba.type.MyCoreferenceLink");
    ll_cas.ll_setStringValue(addr, casFeatCode_mentionType, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public MyCoreferenceLink_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_next = jcas.getRequiredFeatureDE(casType, "next", "de.unidue.henryvdv.ba.type.MyCoreferenceLink", featOkTst);
    casFeatCode_next  = (null == casFeat_next) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_next).getCode();

 
    casFeat_corefType = jcas.getRequiredFeatureDE(casType, "corefType", "uima.cas.String", featOkTst);
    casFeatCode_corefType  = (null == casFeat_corefType) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_corefType).getCode();

 
    casFeat_mentionType = jcas.getRequiredFeatureDE(casType, "mentionType", "uima.cas.String", featOkTst);
    casFeatCode_mentionType  = (null == casFeat_mentionType) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_mentionType).getCode();

  }
}



    