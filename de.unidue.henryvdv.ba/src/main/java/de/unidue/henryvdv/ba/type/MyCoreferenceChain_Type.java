
/* First created by JCasGen Sun Jul 17 12:54:32 CEST 2016 */
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
public class MyCoreferenceChain_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (MyCoreferenceChain_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = MyCoreferenceChain_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new MyCoreferenceChain(addr, MyCoreferenceChain_Type.this);
  			   MyCoreferenceChain_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new MyCoreferenceChain(addr, MyCoreferenceChain_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = MyCoreferenceChain.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.unidue.henryvdv.ba.type.MyCoreferenceChain");
 
  /** @generated */
  final Feature casFeat_first;
  /** @generated */
  final int     casFeatCode_first;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getFirst(int addr) {
        if (featOkTst && casFeat_first == null)
      jcas.throwFeatMissing("first", "de.unidue.henryvdv.ba.type.MyCoreferenceChain");
    return ll_cas.ll_getRefValue(addr, casFeatCode_first);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setFirst(int addr, int v) {
        if (featOkTst && casFeat_first == null)
      jcas.throwFeatMissing("first", "de.unidue.henryvdv.ba.type.MyCoreferenceChain");
    ll_cas.ll_setRefValue(addr, casFeatCode_first, v);}
    
  
 
  /** @generated */
  final Feature casFeat_corefClass;
  /** @generated */
  final int     casFeatCode_corefClass;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getCorefClass(int addr) {
        if (featOkTst && casFeat_corefClass == null)
      jcas.throwFeatMissing("corefClass", "de.unidue.henryvdv.ba.type.MyCoreferenceChain");
    return ll_cas.ll_getStringValue(addr, casFeatCode_corefClass);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setCorefClass(int addr, String v) {
        if (featOkTst && casFeat_corefClass == null)
      jcas.throwFeatMissing("corefClass", "de.unidue.henryvdv.ba.type.MyCoreferenceChain");
    ll_cas.ll_setStringValue(addr, casFeatCode_corefClass, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public MyCoreferenceChain_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_first = jcas.getRequiredFeatureDE(casType, "first", "de.unidue.henryvdv.ba.type.MyCoreferenceLink", featOkTst);
    casFeatCode_first  = (null == casFeat_first) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_first).getCode();

 
    casFeat_corefClass = jcas.getRequiredFeatureDE(casType, "corefClass", "uima.cas.String", featOkTst);
    casFeatCode_corefClass  = (null == casFeat_corefClass) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_corefClass).getCode();

  }
}



    