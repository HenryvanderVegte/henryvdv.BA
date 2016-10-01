
/* First created by JCasGen Fri Sep 30 18:40:42 CEST 2016 */
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
 * Updated by JCasGen Fri Sep 30 18:44:55 CEST 2016
 * @generated */
public class MyNP_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (MyNP_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = MyNP_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new MyNP(addr, MyNP_Type.this);
  			   MyNP_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new MyNP(addr, MyNP_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = MyNP.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.unidue.henryvdv.ba.type.MyNP");
 
  /** @generated */
  final Feature casFeat_outputValue;
  /** @generated */
  final int     casFeatCode_outputValue;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public float getOutputValue(int addr) {
        if (featOkTst && casFeat_outputValue == null)
      jcas.throwFeatMissing("outputValue", "de.unidue.henryvdv.ba.type.MyNP");
    return ll_cas.ll_getFloatValue(addr, casFeatCode_outputValue);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setOutputValue(int addr, float v) {
        if (featOkTst && casFeat_outputValue == null)
      jcas.throwFeatMissing("outputValue", "de.unidue.henryvdv.ba.type.MyNP");
    ll_cas.ll_setFloatValue(addr, casFeatCode_outputValue, v);}
    
  
 
  /** @generated */
  final Feature casFeat_featureVector;
  /** @generated */
  final int     casFeatCode_featureVector;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getFeatureVector(int addr) {
        if (featOkTst && casFeat_featureVector == null)
      jcas.throwFeatMissing("featureVector", "de.unidue.henryvdv.ba.type.MyNP");
    return ll_cas.ll_getStringValue(addr, casFeatCode_featureVector);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setFeatureVector(int addr, String v) {
        if (featOkTst && casFeat_featureVector == null)
      jcas.throwFeatMissing("featureVector", "de.unidue.henryvdv.ba.type.MyNP");
    ll_cas.ll_setStringValue(addr, casFeatCode_featureVector, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public MyNP_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_outputValue = jcas.getRequiredFeatureDE(casType, "outputValue", "uima.cas.Float", featOkTst);
    casFeatCode_outputValue  = (null == casFeat_outputValue) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_outputValue).getCode();

 
    casFeat_featureVector = jcas.getRequiredFeatureDE(casType, "featureVector", "uima.cas.String", featOkTst);
    casFeatCode_featureVector  = (null == casFeat_featureVector) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_featureVector).getCode();

  }
}



    