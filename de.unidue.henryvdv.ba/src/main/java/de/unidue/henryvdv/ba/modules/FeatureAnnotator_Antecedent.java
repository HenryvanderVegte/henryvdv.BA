package de.unidue.henryvdv.ba.modules;

import java.util.Collection;
import java.util.List;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.CONJ;
import de.tudarmstadt.ukp.dkpro.core.api.ner.type.NamedEntity;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.dependency.Dependency;
import de.unidue.henryvdv.ba.type.Anaphora;
import de.unidue.henryvdv.ba.type.AntecedentFeatures;
import de.unidue.henryvdv.ba.type.Quotation;
import de.unidue.henryvdv.ba.util.AnnotationUtils;

public class FeatureAnnotator_Antecedent extends JCasAnnotator_ImplBase {

	/************************************************
	 *  Annotates the Antecedent-Features   		*
	 *  -Antecedent Frequency (float)				*
	 *  -Subject (bool)								*
	 *  -Object (bool)								*
	 *  -Predicate (bool)							*
	 *  -Pronominal (bool)							*
	 *  -Head-Word Emphasis (bool)					*
	 *  -Conjunction (bool)							*
	 *  -Prenominal Modifier (bool)					*
	 *  -Organization (bool)						*
	 *  -Person (bool)								*
	 *  											*
	 *  Missing:									*
	 *  ...											*
	 * 												*
	 ************************************************/
	
	
	private JCas aJCas;
	private Collection<Anaphora> anaphoras;
	private Collection<Token> tokens;
	private Collection<Sentence> sentences;
	private Collection<Dependency> dependencies;
	private Collection<Quotation> quotes;
	private Collection<NamedEntity> namedEntities;
	private String documentText;
	
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		this.aJCas = aJCas;
		anaphoras = JCasUtil.select(aJCas, Anaphora.class);
		sentences = JCasUtil.select(aJCas, Sentence.class);
		dependencies = JCasUtil.select(aJCas, Dependency.class);
		tokens = JCasUtil.select(aJCas, Token.class);
		quotes = JCasUtil.select(aJCas, Quotation.class);
		namedEntities = JCasUtil.select(aJCas, NamedEntity.class);
		prepareAnnotations();
		annotateAntecedentFrequencyFeature();
		annotateSubjectFeature();
		annotateObjectFeature();
		annotatePredicateFeature();
		annotatePronounFeature();
		annotateHeadWordEmphasisFeature();
		annotateConjunctionFeature();
		annotatePrenominalModifierFeature();
		annotateOrganizationFeature();
		annotatePersonFeature();
	}

	public void prepareAnnotations(){
		for(Anaphora anaphora : anaphoras){
			AntecedentFeatures a = new AntecedentFeatures(aJCas);
			anaphora.setAntecedentFeatures(a);
		}
	}
	
	public void annotateAntecedentFrequencyFeature(){
		for(Anaphora anaphora : anaphoras){
			List<Token> covTokens = getCoveredTokens(anaphora.getAntecedent());		
			int count = getNrOfOccurences(covTokens);
			anaphora.getAntecedentFeatures().setA_AntecedentFrequency((float)count / 10f);
		}
	}
	
	public void annotateSubjectFeature(){
		for(Anaphora anaphora : anaphoras){
			List<Token> covTokens = getCoveredTokens(anaphora.getAntecedent());	
			boolean containsSubj = false;
			for(Token t : covTokens){				
				if(isSubject(t)){
					containsSubj = true;
				}
			}
			anaphora.getAntecedentFeatures().setA_Subject(containsSubj);
		}
	}
	
	public void annotateObjectFeature(){
		for(Anaphora anaphora : anaphoras){
			List<Token> covTokens = getCoveredTokens(anaphora.getAntecedent());	
			boolean containsObj = false;
			for(Token t : covTokens){				
				if(isObject(t)){
					containsObj = true;
				}
			}
			anaphora.getAntecedentFeatures().setA_Object(containsObj);
		}
	}

	public void annotatePredicateFeature(){
		for(Anaphora anaphora : anaphoras){
			List<Token> covTokens = getCoveredTokens(anaphora.getAntecedent());	
			boolean containsPredicate = false;
			for(Token t : covTokens){				
				if(isPredicate(t)){
					containsPredicate = true;
				}
			}
			anaphora.getAntecedentFeatures().setA_Predicate(containsPredicate);
		}
	}
	
	public void annotatePronounFeature(){
		for(Anaphora anaphora : anaphoras){
			List<Token> covTokens = getCoveredTokens(anaphora.getAntecedent());	
			boolean containsPronoun = false;
			for(Token t : covTokens){				
				if(isPronoun(t)){
					containsPronoun = true;
				}
			}
			anaphora.getAntecedentFeatures().setA_Pronominal(containsPronoun);
		}		
	}
	
	public void annotateHeadWordEmphasisFeature(){
		for(Anaphora anaphora : anaphoras){
			List<Token> covTokens = getCoveredTokens(anaphora.getAntecedent());
			boolean parentIsNoun = false;
			for(Token t : covTokens){
				Token parent = getParent(t);
				if(parent != null && isNoun(parent)){
					parentIsNoun = true;
				}
			}
			anaphora.getAntecedentFeatures().setA_HeadWordEmphasis(!parentIsNoun);
		}		
	}

	public void annotateConjunctionFeature(){
		for(Anaphora anaphora : anaphoras){
			List<Token> covTokens = getCoveredTokens(anaphora.getAntecedent());
			boolean containsConjunction = false;
			for(Token t : covTokens){
				if(isConjunction(t)){
					containsConjunction = true;
				}
			}
			anaphora.getAntecedentFeatures().setA_Conjunction(!containsConjunction);
		}
	}
	
	public void annotatePrenominalModifierFeature(){
		for(Anaphora anaphora : anaphoras){
			List<Token> covTokens = getCoveredTokens(anaphora.getAntecedent());
			boolean value = false;
			for(Token t : covTokens){
				if(isPrenominalModifier(t)){
					value = true;
				}
			}
			anaphora.getAntecedentFeatures().setA_PrenominalModifier(value);
		}
	}
	
	public void annotateOrganizationFeature(){
		for(Anaphora anaphora : anaphoras){
			List<Token> covTokens = getCoveredTokens(anaphora.getAntecedent());
			boolean value = false;			
			for(Token t : covTokens){
				String ne = getNamedEntityValue(t);
				if(ne != null && ne.equals("ORGANIZATION")){
					value = true;
				}
			}
			anaphora.getAntecedentFeatures().setA_Org(value);
		}		
	}
	
	public void annotatePersonFeature(){
		for(Anaphora anaphora : anaphoras){
			List<Token> covTokens = getCoveredTokens(anaphora.getAntecedent());
			boolean value = false;			
			for(Token t : covTokens){
				String ne = getNamedEntityValue(t);
				if(ne != null && ne.equals("PERSON")){
					value = true;
				}
			}
			anaphora.getAntecedentFeatures().setA_Org(value);
		}
	}
	
	private int getNrOfOccurences(List<Token> checkTokens){
		Token[] tokenArray = tokens.toArray(new Token[tokens.size()]);
		int nrOfOcc = 0;
		boolean isSame = false;		
		for(int i = 0; i < tokenArray.length; i++){
			isSame = true;
			for(int j = 0; j < checkTokens.size(); j++){
				if(j+i >= tokenArray.length){
					isSame = false;
					break;
				}
				if(!tokenArray[j+i].getCoveredText().equalsIgnoreCase(checkTokens.get(j).getCoveredText())){
					isSame = false;
					break;
				}
			}
			if(isSame){
				nrOfOcc++;
				i += checkTokens.size();
			}
		}	
		return nrOfOcc;		
	}
		
	private boolean isSubject(Token token){
		for(Dependency d : dependencies){
			if(d.getDependent() == token && d.getDependencyType().contains("subj")){
				return true;
			}
		}	
		return false;
	}
	
	private boolean isObject(Token token){
		for(Dependency d : dependencies){
			if(d.getDependent() == token && d.getDependencyType().contains("obj")){
				return true;
			}
		}	
		return false;
	}
	
	private boolean isPredicate(Token token){
		if(token.getPos().getPosValue().length() >= 2 && token.getPos().getPosValue().substring(0, 2).equals("VB"))
			return true;
		return false;
	}
	
	private boolean isPronoun(Token token){
		if(token.getPos().getPosValue().length() >= 3 && 
				token.getPos().getPosValue().substring(0, 3).equals("PRP")) //TODO: Think about implementing WP & WP$		
			return true;
		return false;
	}
	
	private boolean isNoun(Token token){
		if(token.getPos().getPosValue().length() >= 2 && 
				token.getPos().getPosValue().substring(0, 2).equals("NN")) 		
			return true;
		return false;
	}
	
	private boolean isConjunction(Token token){
		if(token.getPos().getPosValue().length() >= 2 && 
				token.getPos().getPosValue().substring(0, 2).equals("CC")) 	//TODO: Maybe Check for subordinating conjunctions	
			return true;
		return false;
	}

	private boolean isPrenominalModifier(Token token){
		if(isNoun(token) && isModifier(token) && getParent(token) != null && isNoun(getParent(token)))
			return true;
		return false;
	}
	
	private boolean isModifier(Token token){
		for(Dependency d : dependencies){
			if(d.getDependent() == token && d.getDependencyType().contains("mod")){
				return true;
			}
		}
		return false;
	}
	
	private String getNamedEntityValue(Token token){
		for(NamedEntity n : namedEntities){
			if(n.getBegin() <= token.getBegin() && n.getEnd() >= token.getEnd()){
				return n.getValue();
			}
		}
		return null;
	}
	
	private List<Token> getCoveredTokens(Annotation anno){
		return AnnotationUtils.getCoveredTokens(anno, tokens);
	}
	
	private Token getParent(Token token){
		return AnnotationUtils.getParent(token, dependencies);
	}
	
	private Sentence getSentence(int begin){
		return AnnotationUtils.getSentence(begin, sentences);
	}

}
