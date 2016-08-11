package de.unidue.henryvdv.ba.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.constituent.PP;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.dependency.Dependency;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.dependency.PREP;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.constituent.Constituent;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.constituent.NP;
import de.unidue.henryvdv.ba.type.Anaphora;
import de.unidue.henryvdv.ba.type.AntecedentFeatures;
import de.unidue.henryvdv.ba.type.PronounAntecedentFeatures;
import de.unidue.henryvdv.ba.type.Quotation;
import de.unidue.henryvdv.ba.util.AnnotationUtils;

public class FeatureAnnotator_PronounAntecedent extends JCasAnnotator_ImplBase {
	
	/************************************************
	 *  Annotates the Pronoun-Antecedent-Features   *
	 *  -Same Sentence (bool)						*
	 *  -Intra-Sentence Difference (float)			*
	 *  -In Previous Sentence (bool)				*
	 *  -Inter-Sentence Difference (float)			*
	 *  -Prepositional Parallel (bool)				*
	 *  -Parent Category Match (bool)				*
	 *  -Parent Word Match (bool)					*
	 *  -Quotation Situation (bool)					*
	 *  -Singular Match (bool)						*
	 *  -Plural Match (bool)						*
	 *  											*
	 *  Missing:									*
	 *  -Binding Theory								*
	 *  -Reflexive Subj. Match						*
	 *  -Relation-Match								*
	 *  -Parent Relation Match						*
	 *  -MI Value									*
	 *  -MI Available								*
	 * 												*
	 ************************************************/
	
	private JCas aJCas;
	private Collection<Anaphora> anaphoras;
	private Collection<Token> tokens;
	private Collection<Sentence> sentences;
	private Collection<Dependency> dependencies;
	private Collection<Quotation> quotes;
	
	private String[] singularPronouns = {"himself","herself","itself",
			"his","her","its","he","she"}; //it?
	
	private String[] pluralPronouns = {"themselves","their","they"};
	
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		this.aJCas = aJCas;
		anaphoras = JCasUtil.select(aJCas, Anaphora.class);
		sentences = JCasUtil.select(aJCas, Sentence.class);
		dependencies = JCasUtil.select(aJCas, Dependency.class);
		tokens = JCasUtil.select(aJCas, Token.class);
		quotes = JCasUtil.select(aJCas, Quotation.class);
		prepareAnnotations();
		annotateInSameSentenceFeature();
		annotateIntraSentenceDiffFeature();
		annotateInPreviousSentenceFeature();
		annotateInterSentenceDiffFeature();
		annotatePrepositionalParallelFeature();
		//annotateRelationMatchFeature();
		annotateParentWordMatchFeature();
		annotateQuotationSituationFeature();
		annotateSingularMatchFeature();
		annotatePluralMatchFeature();
	}
	
	public void prepareAnnotations(){
		for(Anaphora anaphora : anaphoras){
			PronounAntecedentFeatures a = new PronounAntecedentFeatures(aJCas);
			anaphora.setPronounAntecedentFeatures(a);
		}
	}

	public void annotateInSameSentenceFeature(){
		for(Anaphora a : anaphoras){
			boolean value = (getSentenceNr(a.getBegin()) == getSentenceNr(a.getAntecedent().getBegin()));
			a.getPronounAntecedentFeatures().setP_A_InSameSentence(value);
		}	
	}
	
	public void annotateInPreviousSentenceFeature(){
		for(Anaphora a : anaphoras){
			boolean value = (getSentenceNr(a.getBegin()) -1 == getSentenceNr(a.getAntecedent().getBegin()));
			a.getPronounAntecedentFeatures().setP_A_InPreviousSentence(value);
		}
	}
	
	public void annotateInterSentenceDiffFeature(){
		for(Anaphora a : anaphoras){
			int anaphoraS = getSentenceNr(a.getBegin());
			int antecedentS = getSentenceNr(a.getAntecedent().getBegin());
			float value = (anaphoraS - antecedentS)/ 50.0f;
			a.getPronounAntecedentFeatures().setP_A_InterSentenceDiff(value);
		}
	}
	
	public void annotateIntraSentenceDiffFeature(){
		for(Anaphora a : anaphoras){
			float value = (a.getBegin() - a.getAntecedent().getBegin())/ 50.0f;
			a.getPronounAntecedentFeatures().setP_A_IntraSentenceDiff(value);
		}
	}
	
	public void annotatePrepositionalParallelFeature(){
		for(Anaphora a : anaphoras){
			boolean value = prepositionalParallel(a, a.getAntecedent());
			a.getPronounAntecedentFeatures().setP_A_PrepositionalParallel(value);
		}	
	}
	
	public void annotateRelationMatchFeature(){
		//TODO: FIX
		for(Anaphora a : anaphoras){
			System.out.println("Instance:");
			boolean value = relationMatch(a, a.getAntecedent());
			System.out.println("Value: " + value);
			a.getPronounAntecedentFeatures().setP_A_RelationMatch(value);
		}
	}
		
	public void annotateParentCategoryMatch(){
		for(Anaphora a : anaphoras){
			boolean value = parentCategoryMatch(a, a.getAntecedent());
			a.getPronounAntecedentFeatures().setP_A_ParentCatMatch(value);
		}
	}

	public void annotateParentWordMatchFeature(){
		for(Anaphora a : anaphoras){
			Token anaphoraparent = getParent(a);
			if(anaphoraparent == null){
				a.getPronounAntecedentFeatures().setP_A_ParentWordMatch(false);
				break;			
			}
			String anaphoraParentString = anaphoraparent.getCoveredText();
			List<Token> anteTokens = getCoveredTokens(a.getAntecedent().getBegin(), a.getAntecedent().getEnd());					
			boolean value = false;
			for(Token t : anteTokens){
				Token tParent = getParent(t);
				if(tParent == null)
					break;			
				String tParentString = tParent.getCoveredText();
				if(anaphoraParentString.equalsIgnoreCase(tParentString)){
					value = true;
				}
			}
			a.getPronounAntecedentFeatures().setP_A_ParentWordMatch(value);
		}
	}
	
	public void annotateQuotationSituationFeature(){
		for(Anaphora a : anaphoras){
			boolean valueA = isInQuotes(a);
			boolean valueB = isInQuotes(a.getAntecedent());
			boolean returnValue = false;
			if(valueA == valueB){
				returnValue = true;
			}
			a.getPronounAntecedentFeatures().setP_A_QuotationSituation(returnValue);
		}
	}

	public void annotateSingularMatchFeature(){
		for(Anaphora a : anaphoras){
			boolean value = isBothSingular(a, a.getAntecedent());
			a.getPronounAntecedentFeatures().setP_A_SingularMatch(value);
		}
	}
	
	public void annotatePluralMatchFeature(){
		for(Anaphora a : anaphoras){
			boolean value = isBothPlural(a, a.getAntecedent());
			a.getPronounAntecedentFeatures().setP_A_PluralMatch(value);
		}
	}

	private String getPrepositionText(Annotation anno){
		Token govToken = null;
		for(Dependency d : dependencies){
			if(d.getDependent().getBegin() == anno.getBegin() && 
					d.getDependent().getEnd() == anno.getEnd()){
				govToken = d.getGovernor();
			}
		}
		if(govToken == null)
			return null;
		for(Dependency d : dependencies){
			if(d.getGovernor() == govToken && d.getDependencyType().equals("case")){
				return d.getCoveredText();
			}
		}		
		return null;
	}
	

	
	private Token getParent(Annotation anno){
		for(Dependency d : dependencies){
			if(d.getDependent().getBegin() == anno.getBegin() && 
					d.getDependent().getEnd() == anno.getEnd()){
				return d.getGovernor();
			}
		}
		return null;
	}
	
	private boolean prepositionalParallel(Annotation anaphora, Annotation antecedent){
		String prepText = getPrepositionText(anaphora);		
		List<Token> antecedentTokens = getCoveredTokens(antecedent.getBegin(), antecedent.getEnd());		
		boolean value = false;
		if(prepText != null){
			for(Token token : antecedentTokens){
				String currentText = getPrepositionText(token);
				if(currentText != null){
					if(currentText.equalsIgnoreCase(prepText)){
						value = true;
					}
				}
			}
		}	
		return value;
	}
	
	private boolean relationMatch(Annotation anno1, Annotation anno2){
		String type1 = null;
		String type2 = null;
		System.out.println("1: " + anno1.getCoveredText());
		System.out.println("2: " + anno2.getCoveredText());
		
		for(Dependency d : dependencies){
			if(d.getBegin()== anno1.getBegin() && d.getEnd() == anno1.getEnd()){
				type1 = d.getDependencyType();
				System.out.println("Type1: " + type1);
			}
			if(d.getBegin() == anno2.getBegin() && d.getEnd() ==anno2.getEnd()){
				type2 = d.getDependencyType();
				System.out.println("Type1: " + type2);
			}
		}
		
		if(type1 != null && type2 != null){
			boolean value = type1.equals(type2);
			return value;
		}		
		return false;		
	}

	private boolean parentCategoryMatch(Annotation anaphora, Annotation antecedent){
		Token anaphoraParent = getParent(anaphora);	
		List<Token> anteTokens = getCoveredTokens(antecedent.getBegin(), antecedent.getEnd());
		boolean value = false;
		for(Token t : anteTokens){
			Token anteParent = getParent(t);
			if(anaphoraParent.getPos().getPosValue().equals(anteParent.getPos().getPosValue())){
				value = true;
			}
		}
		return value;
	}

	private boolean isInQuotes(Annotation anno){
		boolean value = false;
		for(Quotation q : quotes){
			if(q.getBegin() <= anno.getBegin() && q.getEnd() >= anno.getEnd()){
				value = true;
			}
		}	
		return value;
	}
	
	private boolean isBothSingular(Annotation anaphora, Annotation antecedent){
		boolean isSingularA = true;
		if(Arrays.asList(pluralPronouns).contains(anaphora.getCoveredText().toLowerCase())){
			isSingularA = false;
		}
		boolean isSingularB = false;
		boolean valueSet = false;
		List<Token> covTokens = getCoveredTokens(antecedent.getBegin(), antecedent.getEnd());
		for(Token t : covTokens){
			if(t.getPos().getPosValue().equalsIgnoreCase("NN") || t.getPos().getPosValue().equalsIgnoreCase("NNP")){
				isSingularB = true;
				valueSet = true;
			}
			if(t.getPos().getPosValue().equalsIgnoreCase("NNS") || t.getPos().getPosValue().equalsIgnoreCase("NNPS")){
				valueSet = true;
			}
		}
		if(isSingularA && isSingularB && valueSet)
			return true;
		
		return false;
	}
	
	private boolean isBothPlural(Annotation anaphora, Annotation antecedent){
		boolean isSingularA = true;
		if(Arrays.asList(pluralPronouns).contains(anaphora.getCoveredText().toLowerCase())){
			isSingularA = false;
		}
		boolean isSingularB = true;
		boolean valueSet = false;
		List<Token> covTokens = getCoveredTokens(antecedent.getBegin(), antecedent.getEnd());
		for(Token t : covTokens){
			if(t.getPos().getPosValue().equalsIgnoreCase("NNS") || t.getPos().getPosValue().equalsIgnoreCase("NNPS")){
				isSingularB = false;
				valueSet = true;
			}
			if(t.getPos().getPosValue().equalsIgnoreCase("NN") || t.getPos().getPosValue().equalsIgnoreCase("NNP")){
				valueSet = true;
			}
		}
		if(!isSingularA && !isSingularB && valueSet)
			return true;
		
		return false;
	}
	
	private int getSentenceNr(int begin){
		return AnnotationUtils.getSentenceNr(begin, sentences);
	}
	
	private List<Token> getCoveredTokens(int begin, int end){
		return AnnotationUtils.getCoveredTokens(begin, end, tokens);
	}
}

