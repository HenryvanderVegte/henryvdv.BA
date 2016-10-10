
/* First created by JCasGen Wed Aug 10 13:44:08 CEST 2016 */
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
 * Updated by JCasGen Sun Oct 09 23:25:39 CEST 2016
 * @generated */
public class AntecedentFeatures_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (AntecedentFeatures_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = AntecedentFeatures_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new AntecedentFeatures(addr, AntecedentFeatures_Type.this);
  			   AntecedentFeatures_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new AntecedentFeatures(addr, AntecedentFeatures_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = AntecedentFeatures.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.unidue.henryvdv.ba.type.AntecedentFeatures");
 
  /** @generated */
  final Feature casFeat_a_AntecedentFrequency;
  /** @generated */
  final int     casFeatCode_a_AntecedentFrequency;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public float getA_AntecedentFrequency(int addr) {
        if (featOkTst && casFeat_a_AntecedentFrequency == null)
      jcas.throwFeatMissing("a_AntecedentFrequency", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    return ll_cas.ll_getFloatValue(addr, casFeatCode_a_AntecedentFrequency);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setA_AntecedentFrequency(int addr, float v) {
        if (featOkTst && casFeat_a_AntecedentFrequency == null)
      jcas.throwFeatMissing("a_AntecedentFrequency", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    ll_cas.ll_setFloatValue(addr, casFeatCode_a_AntecedentFrequency, v);}
    
  
 
  /** @generated */
  final Feature casFeat_a_Subject;
  /** @generated */
  final int     casFeatCode_a_Subject;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public boolean getA_Subject(int addr) {
        if (featOkTst && casFeat_a_Subject == null)
      jcas.throwFeatMissing("a_Subject", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_a_Subject);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setA_Subject(int addr, boolean v) {
        if (featOkTst && casFeat_a_Subject == null)
      jcas.throwFeatMissing("a_Subject", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_a_Subject, v);}
    
  
 
  /** @generated */
  final Feature casFeat_a_Object;
  /** @generated */
  final int     casFeatCode_a_Object;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public boolean getA_Object(int addr) {
        if (featOkTst && casFeat_a_Object == null)
      jcas.throwFeatMissing("a_Object", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_a_Object);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setA_Object(int addr, boolean v) {
        if (featOkTst && casFeat_a_Object == null)
      jcas.throwFeatMissing("a_Object", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_a_Object, v);}
    
  
 
  /** @generated */
  final Feature casFeat_a_Predicate;
  /** @generated */
  final int     casFeatCode_a_Predicate;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public boolean getA_Predicate(int addr) {
        if (featOkTst && casFeat_a_Predicate == null)
      jcas.throwFeatMissing("a_Predicate", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_a_Predicate);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setA_Predicate(int addr, boolean v) {
        if (featOkTst && casFeat_a_Predicate == null)
      jcas.throwFeatMissing("a_Predicate", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_a_Predicate, v);}
    
  
 
  /** @generated */
  final Feature casFeat_a_Pronominal;
  /** @generated */
  final int     casFeatCode_a_Pronominal;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public boolean getA_Pronominal(int addr) {
        if (featOkTst && casFeat_a_Pronominal == null)
      jcas.throwFeatMissing("a_Pronominal", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_a_Pronominal);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setA_Pronominal(int addr, boolean v) {
        if (featOkTst && casFeat_a_Pronominal == null)
      jcas.throwFeatMissing("a_Pronominal", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_a_Pronominal, v);}
    
  
 
  /** @generated */
  final Feature casFeat_a_Prepositional;
  /** @generated */
  final int     casFeatCode_a_Prepositional;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public boolean getA_Prepositional(int addr) {
        if (featOkTst && casFeat_a_Prepositional == null)
      jcas.throwFeatMissing("a_Prepositional", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_a_Prepositional);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setA_Prepositional(int addr, boolean v) {
        if (featOkTst && casFeat_a_Prepositional == null)
      jcas.throwFeatMissing("a_Prepositional", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_a_Prepositional, v);}
    
  
 
  /** @generated */
  final Feature casFeat_a_HeadWordEmphasis;
  /** @generated */
  final int     casFeatCode_a_HeadWordEmphasis;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public boolean getA_HeadWordEmphasis(int addr) {
        if (featOkTst && casFeat_a_HeadWordEmphasis == null)
      jcas.throwFeatMissing("a_HeadWordEmphasis", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_a_HeadWordEmphasis);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setA_HeadWordEmphasis(int addr, boolean v) {
        if (featOkTst && casFeat_a_HeadWordEmphasis == null)
      jcas.throwFeatMissing("a_HeadWordEmphasis", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_a_HeadWordEmphasis, v);}
    
  
 
  /** @generated */
  final Feature casFeat_a_Conjunction;
  /** @generated */
  final int     casFeatCode_a_Conjunction;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public boolean getA_Conjunction(int addr) {
        if (featOkTst && casFeat_a_Conjunction == null)
      jcas.throwFeatMissing("a_Conjunction", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_a_Conjunction);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setA_Conjunction(int addr, boolean v) {
        if (featOkTst && casFeat_a_Conjunction == null)
      jcas.throwFeatMissing("a_Conjunction", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_a_Conjunction, v);}
    
  
 
  /** @generated */
  final Feature casFeat_a_PrenominalModifier;
  /** @generated */
  final int     casFeatCode_a_PrenominalModifier;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public boolean getA_PrenominalModifier(int addr) {
        if (featOkTst && casFeat_a_PrenominalModifier == null)
      jcas.throwFeatMissing("a_PrenominalModifier", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_a_PrenominalModifier);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setA_PrenominalModifier(int addr, boolean v) {
        if (featOkTst && casFeat_a_PrenominalModifier == null)
      jcas.throwFeatMissing("a_PrenominalModifier", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_a_PrenominalModifier, v);}
    
  
 
  /** @generated */
  final Feature casFeat_a_Org;
  /** @generated */
  final int     casFeatCode_a_Org;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public boolean getA_Org(int addr) {
        if (featOkTst && casFeat_a_Org == null)
      jcas.throwFeatMissing("a_Org", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_a_Org);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setA_Org(int addr, boolean v) {
        if (featOkTst && casFeat_a_Org == null)
      jcas.throwFeatMissing("a_Org", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_a_Org, v);}
    
  
 
  /** @generated */
  final Feature casFeat_a_Person;
  /** @generated */
  final int     casFeatCode_a_Person;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public boolean getA_Person(int addr) {
        if (featOkTst && casFeat_a_Person == null)
      jcas.throwFeatMissing("a_Person", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_a_Person);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setA_Person(int addr, boolean v) {
        if (featOkTst && casFeat_a_Person == null)
      jcas.throwFeatMissing("a_Person", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_a_Person, v);}
    
  
 
  /** @generated */
  final Feature casFeat_a_Time;
  /** @generated */
  final int     casFeatCode_a_Time;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public boolean getA_Time(int addr) {
        if (featOkTst && casFeat_a_Time == null)
      jcas.throwFeatMissing("a_Time", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_a_Time);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setA_Time(int addr, boolean v) {
        if (featOkTst && casFeat_a_Time == null)
      jcas.throwFeatMissing("a_Time", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_a_Time, v);}
    
  
 
  /** @generated */
  final Feature casFeat_a_Date;
  /** @generated */
  final int     casFeatCode_a_Date;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public boolean getA_Date(int addr) {
        if (featOkTst && casFeat_a_Date == null)
      jcas.throwFeatMissing("a_Date", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_a_Date);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setA_Date(int addr, boolean v) {
        if (featOkTst && casFeat_a_Date == null)
      jcas.throwFeatMissing("a_Date", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_a_Date, v);}
    
  
 
  /** @generated */
  final Feature casFeat_a_Money;
  /** @generated */
  final int     casFeatCode_a_Money;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public boolean getA_Money(int addr) {
        if (featOkTst && casFeat_a_Money == null)
      jcas.throwFeatMissing("a_Money", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_a_Money);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setA_Money(int addr, boolean v) {
        if (featOkTst && casFeat_a_Money == null)
      jcas.throwFeatMissing("a_Money", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_a_Money, v);}
    
  
 
  /** @generated */
  final Feature casFeat_a_Price;
  /** @generated */
  final int     casFeatCode_a_Price;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public boolean getA_Price(int addr) {
        if (featOkTst && casFeat_a_Price == null)
      jcas.throwFeatMissing("a_Price", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_a_Price);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setA_Price(int addr, boolean v) {
        if (featOkTst && casFeat_a_Price == null)
      jcas.throwFeatMissing("a_Price", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_a_Price, v);}
    
  
 
  /** @generated */
  final Feature casFeat_a_Amount;
  /** @generated */
  final int     casFeatCode_a_Amount;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public boolean getA_Amount(int addr) {
        if (featOkTst && casFeat_a_Amount == null)
      jcas.throwFeatMissing("a_Amount", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_a_Amount);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setA_Amount(int addr, boolean v) {
        if (featOkTst && casFeat_a_Amount == null)
      jcas.throwFeatMissing("a_Amount", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_a_Amount, v);}
    
  
 
  /** @generated */
  final Feature casFeat_a_Number;
  /** @generated */
  final int     casFeatCode_a_Number;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public boolean getA_Number(int addr) {
        if (featOkTst && casFeat_a_Number == null)
      jcas.throwFeatMissing("a_Number", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_a_Number);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setA_Number(int addr, boolean v) {
        if (featOkTst && casFeat_a_Number == null)
      jcas.throwFeatMissing("a_Number", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_a_Number, v);}
    
  
 
  /** @generated */
  final Feature casFeat_a_Definite;
  /** @generated */
  final int     casFeatCode_a_Definite;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public boolean getA_Definite(int addr) {
        if (featOkTst && casFeat_a_Definite == null)
      jcas.throwFeatMissing("a_Definite", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_a_Definite);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setA_Definite(int addr, boolean v) {
        if (featOkTst && casFeat_a_Definite == null)
      jcas.throwFeatMissing("a_Definite", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_a_Definite, v);}
    
  
 
  /** @generated */
  final Feature casFeat_a_HisHer;
  /** @generated */
  final int     casFeatCode_a_HisHer;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public boolean getA_HisHer(int addr) {
        if (featOkTst && casFeat_a_HisHer == null)
      jcas.throwFeatMissing("a_HisHer", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_a_HisHer);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setA_HisHer(int addr, boolean v) {
        if (featOkTst && casFeat_a_HisHer == null)
      jcas.throwFeatMissing("a_HisHer", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_a_HisHer, v);}
    
  
 
  /** @generated */
  final Feature casFeat_a_HeHis;
  /** @generated */
  final int     casFeatCode_a_HeHis;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public boolean getA_HeHis(int addr) {
        if (featOkTst && casFeat_a_HeHis == null)
      jcas.throwFeatMissing("a_HeHis", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_a_HeHis);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setA_HeHis(int addr, boolean v) {
        if (featOkTst && casFeat_a_HeHis == null)
      jcas.throwFeatMissing("a_HeHis", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_a_HeHis, v);}
    
  
 
  /** @generated */
  final Feature casFeat_a_CovTokens;
  /** @generated */
  final int     casFeatCode_a_CovTokens;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public float getA_CovTokens(int addr) {
        if (featOkTst && casFeat_a_CovTokens == null)
      jcas.throwFeatMissing("a_CovTokens", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    return ll_cas.ll_getFloatValue(addr, casFeatCode_a_CovTokens);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setA_CovTokens(int addr, float v) {
        if (featOkTst && casFeat_a_CovTokens == null)
      jcas.throwFeatMissing("a_CovTokens", "de.unidue.henryvdv.ba.type.AntecedentFeatures");
    ll_cas.ll_setFloatValue(addr, casFeatCode_a_CovTokens, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public AntecedentFeatures_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_a_AntecedentFrequency = jcas.getRequiredFeatureDE(casType, "a_AntecedentFrequency", "uima.cas.Float", featOkTst);
    casFeatCode_a_AntecedentFrequency  = (null == casFeat_a_AntecedentFrequency) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_a_AntecedentFrequency).getCode();

 
    casFeat_a_Subject = jcas.getRequiredFeatureDE(casType, "a_Subject", "uima.cas.Boolean", featOkTst);
    casFeatCode_a_Subject  = (null == casFeat_a_Subject) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_a_Subject).getCode();

 
    casFeat_a_Object = jcas.getRequiredFeatureDE(casType, "a_Object", "uima.cas.Boolean", featOkTst);
    casFeatCode_a_Object  = (null == casFeat_a_Object) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_a_Object).getCode();

 
    casFeat_a_Predicate = jcas.getRequiredFeatureDE(casType, "a_Predicate", "uima.cas.Boolean", featOkTst);
    casFeatCode_a_Predicate  = (null == casFeat_a_Predicate) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_a_Predicate).getCode();

 
    casFeat_a_Pronominal = jcas.getRequiredFeatureDE(casType, "a_Pronominal", "uima.cas.Boolean", featOkTst);
    casFeatCode_a_Pronominal  = (null == casFeat_a_Pronominal) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_a_Pronominal).getCode();

 
    casFeat_a_Prepositional = jcas.getRequiredFeatureDE(casType, "a_Prepositional", "uima.cas.Boolean", featOkTst);
    casFeatCode_a_Prepositional  = (null == casFeat_a_Prepositional) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_a_Prepositional).getCode();

 
    casFeat_a_HeadWordEmphasis = jcas.getRequiredFeatureDE(casType, "a_HeadWordEmphasis", "uima.cas.Boolean", featOkTst);
    casFeatCode_a_HeadWordEmphasis  = (null == casFeat_a_HeadWordEmphasis) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_a_HeadWordEmphasis).getCode();

 
    casFeat_a_Conjunction = jcas.getRequiredFeatureDE(casType, "a_Conjunction", "uima.cas.Boolean", featOkTst);
    casFeatCode_a_Conjunction  = (null == casFeat_a_Conjunction) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_a_Conjunction).getCode();

 
    casFeat_a_PrenominalModifier = jcas.getRequiredFeatureDE(casType, "a_PrenominalModifier", "uima.cas.Boolean", featOkTst);
    casFeatCode_a_PrenominalModifier  = (null == casFeat_a_PrenominalModifier) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_a_PrenominalModifier).getCode();

 
    casFeat_a_Org = jcas.getRequiredFeatureDE(casType, "a_Org", "uima.cas.Boolean", featOkTst);
    casFeatCode_a_Org  = (null == casFeat_a_Org) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_a_Org).getCode();

 
    casFeat_a_Person = jcas.getRequiredFeatureDE(casType, "a_Person", "uima.cas.Boolean", featOkTst);
    casFeatCode_a_Person  = (null == casFeat_a_Person) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_a_Person).getCode();

 
    casFeat_a_Time = jcas.getRequiredFeatureDE(casType, "a_Time", "uima.cas.Boolean", featOkTst);
    casFeatCode_a_Time  = (null == casFeat_a_Time) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_a_Time).getCode();

 
    casFeat_a_Date = jcas.getRequiredFeatureDE(casType, "a_Date", "uima.cas.Boolean", featOkTst);
    casFeatCode_a_Date  = (null == casFeat_a_Date) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_a_Date).getCode();

 
    casFeat_a_Money = jcas.getRequiredFeatureDE(casType, "a_Money", "uima.cas.Boolean", featOkTst);
    casFeatCode_a_Money  = (null == casFeat_a_Money) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_a_Money).getCode();

 
    casFeat_a_Price = jcas.getRequiredFeatureDE(casType, "a_Price", "uima.cas.Boolean", featOkTst);
    casFeatCode_a_Price  = (null == casFeat_a_Price) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_a_Price).getCode();

 
    casFeat_a_Amount = jcas.getRequiredFeatureDE(casType, "a_Amount", "uima.cas.Boolean", featOkTst);
    casFeatCode_a_Amount  = (null == casFeat_a_Amount) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_a_Amount).getCode();

 
    casFeat_a_Number = jcas.getRequiredFeatureDE(casType, "a_Number", "uima.cas.Boolean", featOkTst);
    casFeatCode_a_Number  = (null == casFeat_a_Number) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_a_Number).getCode();

 
    casFeat_a_Definite = jcas.getRequiredFeatureDE(casType, "a_Definite", "uima.cas.Boolean", featOkTst);
    casFeatCode_a_Definite  = (null == casFeat_a_Definite) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_a_Definite).getCode();

 
    casFeat_a_HisHer = jcas.getRequiredFeatureDE(casType, "a_HisHer", "uima.cas.Boolean", featOkTst);
    casFeatCode_a_HisHer  = (null == casFeat_a_HisHer) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_a_HisHer).getCode();

 
    casFeat_a_HeHis = jcas.getRequiredFeatureDE(casType, "a_HeHis", "uima.cas.Boolean", featOkTst);
    casFeatCode_a_HeHis  = (null == casFeat_a_HeHis) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_a_HeHis).getCode();

 
    casFeat_a_CovTokens = jcas.getRequiredFeatureDE(casType, "a_CovTokens", "uima.cas.Float", featOkTst);
    casFeatCode_a_CovTokens  = (null == casFeat_a_CovTokens) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_a_CovTokens).getCode();

  }
}



    