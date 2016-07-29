
/* First created by JCasGen Fri Jul 29 14:01:49 CEST 2016 */
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
 * Updated by JCasGen Fri Jul 29 14:01:49 CEST 2016
 * @generated */
public class NegativeTrainingInstance_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (NegativeTrainingInstance_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = NegativeTrainingInstance_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new NegativeTrainingInstance(addr, NegativeTrainingInstance_Type.this);
  			   NegativeTrainingInstance_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new NegativeTrainingInstance(addr, NegativeTrainingInstance_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = NegativeTrainingInstance.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.unidue.henryvdv.ba.type.NegativeTrainingInstance");
 
  /** @generated */
  final Feature casFeat_anaphora;
  /** @generated */
  final int     casFeatCode_anaphora;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getAnaphora(int addr) {
        if (featOkTst && casFeat_anaphora == null)
      jcas.throwFeatMissing("anaphora", "de.unidue.henryvdv.ba.type.NegativeTrainingInstance");
    return ll_cas.ll_getRefValue(addr, casFeatCode_anaphora);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setAnaphora(int addr, int v) {
        if (featOkTst && casFeat_anaphora == null)
      jcas.throwFeatMissing("anaphora", "de.unidue.henryvdv.ba.type.NegativeTrainingInstance");
    ll_cas.ll_setRefValue(addr, casFeatCode_anaphora, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public NegativeTrainingInstance_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_anaphora = jcas.getRequiredFeatureDE(casType, "anaphora", "de.unidue.henryvdv.ba.type.Anaphora", featOkTst);
    casFeatCode_anaphora  = (null == casFeat_anaphora) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_anaphora).getCode();

  }
}



    