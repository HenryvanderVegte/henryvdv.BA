package de.unidue.henryvdv.ba.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

import de.tudarmstadt.ukp.dkpro.core.api.ner.type.NamedEntity;
import de.tudarmstadt.ukp.dkpro.core.api.ner.type.Time;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.dependency.Dependency;
import de.unidue.henryvdv.ba.param.Parameters;
import de.unidue.henryvdv.ba.type.Anaphora;
import de.unidue.henryvdv.ba.type.Quotation;
/**
 * Class for the assignment of all antecedent features
 * for a given anaphora
 * @author Henry
 *
 */
public class FeatureUtils_Antecedent {
	/********************************************
	 * 	Assigns the antecedent features:		*
	 * 											*
	 * 	-Antecedent Frequency (float)			*
	 * 	-Subject (bool)							*
	 * 	-Object (bool)							*
	 * 	-Predicate (bool)						*
	 * 	-Pronominal (bool)						*
	 * 	-Head Word Emphasis (bool)				*
	 * 	-Conjunction (bool)						*
	 * 	-Prenominal Modifier (bool)				*
	 * 	-Organization (bool)					*
	 * 	-Person (bool)							*
	 * 	-Time (bool)							*
	 * 	-Date (bool)							*
	 * 	-Money (bool)							*
	 * 	-Number (bool)							*
	 * 	-Definite (bool)						*
	 * 	-His/Her (bool)							*
	 * 	-He/His (bool)							*
	 * 											*
	 * 	+My own features						*
	 * 	-CoveredTokens(float)					*
	 ********************************************/
	
	/**
	 * Required Annotations:
	 */
	private Collection<Token> tokens;
	private Collection<Sentence> sentences;
	private Collection<Dependency> dependencies;
	private Collection<NamedEntity> namedEntities;
	
	public FeatureUtils_Antecedent(JCas aJCas){
		sentences = JCasUtil.select(aJCas, Sentence.class);
		dependencies = JCasUtil.select(aJCas, Dependency.class);
		tokens = JCasUtil.select(aJCas, Token.class);
		namedEntities = JCasUtil.select(aJCas, NamedEntity.class);
	}
	
	public void annotateFeatures(Anaphora a){
		//Antecedent Frequency
		a.getAntecedentFeatures().setA_AntecedentFrequency(antecedentFrequency(a));	
		//Subject
		a.getAntecedentFeatures().setA_Subject(subject(a));
		//Object
		a.getAntecedentFeatures().setA_Object(object(a));
		//Predicate
		a.getAntecedentFeatures().setA_Predicate(predicate(a));
		//Pronominal
		a.getAntecedentFeatures().setA_Pronominal(pronominal(a));
		//Head-Word Emphasis
		a.getAntecedentFeatures().setA_HeadWordEmphasis(headWordEmphasis(a));
		//Conjunction
		a.getAntecedentFeatures().setA_Conjunction(conjunction(a));
		//Prenominal Modifier
		a.getAntecedentFeatures().setA_PrenominalModifier(prenominalModifier(a));
		//Org
		a.getAntecedentFeatures().setA_Org(organization(a));
		//Person
		a.getAntecedentFeatures().setA_Person(person(a));
		//Time
		a.getAntecedentFeatures().setA_Time(time(a));
		//Date
		a.getAntecedentFeatures().setA_Date(date(a));
		//Money
		a.getAntecedentFeatures().setA_Money(money(a));
		//Number
		a.getAntecedentFeatures().setA_Number(number(a));
		//Definite Article
		a.getAntecedentFeatures().setA_Definite(definiteArticle(a));
		// His/Her
		a.getAntecedentFeatures().setA_HisHer(hisHerPattern(a));
		// He/His
		a.getAntecedentFeatures().setA_HeHis(heHisPattern(a));
		
		//My own features:
		//How many tokens the antecedent covers / 10f :
		a.getAntecedentFeatures().setA_CovTokens(coveredTokens(a));
	}
	
	public float coveredTokens(Anaphora a){
		List<Token> covTokens = getCoveredTokens(a.getAntecedent());
		return (float)covTokens.size() / 10f;
	}
	
	/**
	 * Get antecedent Frequency
	 * @param anaphora Anaphora
	 * @return frequency value
	 */
	public float antecedentFrequency(Anaphora anaphora){
		List<Token> covTokens = getCoveredTokens(anaphora.getAntecedent());		
		int count = getNrOfOccurences(covTokens);
		return (float)count  / 100f;
	}
	
	/**
	 * Get containsSubject
	 * @param anaphora Anaphora
	 * @return subject value
	 */
	public boolean subject(Anaphora anaphora){
		List<Token> covTokens = getCoveredTokens(anaphora.getAntecedent());	
		boolean value = false;
		for(Token t : covTokens){				
			if(isSubject(t)){
				value = true;
			}
		}
		return value;
	}
	
	/**
	 * Get containsObject
	 * @param anaphora Anaphora
	 * @return object value
	 */
	public boolean object(Anaphora anaphora){
		List<Token> covTokens = getCoveredTokens(anaphora.getAntecedent());	
		boolean value = false;
		for(Token t : covTokens){				
			if(isObject(t)){
				value = true;
			}
		}
		return value;
	}
	
	/**
	 * Get containsPredicate
	 * @param anaphora Anaphora
	 * @return predicate value
	 */
	public boolean predicate(Anaphora anaphora){
		List<Token> covTokens = getCoveredTokens(anaphora.getAntecedent());	
		boolean value = false;
		for(Token t : covTokens){				
			if(isPredicate(t)){
				value = true;
			}
		}
		return value;
	}
	
	/**
	 * Get containsPronominal
	 * @param anaphora Anaphora
	 * @return pronominal value
	 */
	public boolean pronominal(Anaphora anaphora){
		List<Token> covTokens = getCoveredTokens(anaphora.getAntecedent());	
		boolean value = false;
		for(Token t : covTokens){				
			if(isPronoun(t)){
				value = true;
			}
		}
		return value;
	}

	/**
	 * Get Head-Word Emphasis (if parent is no noun)
	 * @param anaphora Anaphora
	 * @return Head-Word-Emphasis Value
	 */
	public boolean headWordEmphasis(Anaphora anaphora){
		List<Token> covTokens = getCoveredTokens(anaphora.getAntecedent());
		boolean value = false;
		int nounsTotal = 0;
		for(Token t : covTokens){
			if(isNoun(t))
				nounsTotal++;
		}
		if(nounsTotal > 1){
			Token headNoun = getHeadNoun(anaphora.getAntecedent());	
			if(headNoun != null && headNoun.getBegin() < anaphora.getAntecedent().getBegin()){
				if(!isParentNoun(headNoun)){
					value = true;
				}
			}
		} else {
			for(Token t : covTokens){
				if(isNoun(t) && !isParentNoun(t))
					value = true;
			}
		}
		return value;
	}
	
	/**
	 * Get containsConjunction
	 * @param anaphora Anaphora
	 * @return conjunction value
	 */
	public boolean conjunction(Anaphora anaphora){
		List<Token> covTokens = getCoveredTokens(anaphora.getAntecedent());
		boolean containsConjunction = false;
		for(Token t : covTokens){
			if(isConjunction(t)){
				containsConjunction = true;
			}
		}
		return !containsConjunction;
	}
	/**
	 * Get containsPrenominalModifier
	 * @param anaphora Anaphora
	 * @return prenominal Modifier value
	 */
	public boolean prenominalModifier(Anaphora anaphora){	
        List<Token> covTokens = getCoveredTokens(anaphora.getAntecedent());
        boolean value = false;
        for(Token t : covTokens){
            if(isPrenominalModifier(t)){
                value = true;
            }
        }
        return value;
	}
	/**
	 * modifier value for single token
	 * @param token Token
	 * @return value
	 */
    private boolean isPrenominalModifier(Token token){
        if(isNoun(token) && isModifier(token) && getParent(token) != null && isNoun(getParent(token)))
            return true;
        return false;
    }
    /**
     * whether the antecedent contains an organization
     * @param anaphora Anaphora
     * @return value
     */
	public boolean organization(Anaphora anaphora){
		List<Token> covTokens = getCoveredTokens(anaphora.getAntecedent());
		boolean value = false;			
		for(Token t : covTokens){
			String ne = getNamedEntityValue(t);
			if(ne != null && ne.equals("ORGANIZATION")){
				value = true;
			}
		}
		return value;
	}
	
    /**
     * whether the antecedent contains a person
     * @param anaphora Anaphora
     * @return value
     */
	public boolean person(Anaphora anaphora){
		List<Token> covTokens = getCoveredTokens(anaphora.getAntecedent());
		boolean value = false;			
		for(Token t : covTokens){
			String ne = getNamedEntityValue(t);
			if(ne != null && ne.equals("PERSON")){
				value = true;
			}
		}
		return value;
	}
    /**
     * whether the antecedent contains a time unit
     * @param anaphora Anaphora
     * @return value
     */
	public boolean time(Anaphora anaphora){
		List<Token> covTokens = getCoveredTokens(anaphora.getAntecedent());
		boolean value = false;			
		for(Token t : covTokens){
			String ne = getNamedEntityValue(t);
			if(ne != null && ne.equals("TIME")){
				value = true;
			}
		}
		return value;
	}
    /**
     * whether the antecedent contains a date
     * @param anaphora Anaphora
     * @return value
     */
	public boolean date(Anaphora anaphora){
		List<Token> covTokens = getCoveredTokens(anaphora.getAntecedent());
		boolean value = false;			
		for(Token t : covTokens){
			String ne = getNamedEntityValue(t);
			if(ne != null && ne.equals("DATE")){
				value = true;
			}
		}
		return value;
	}
    /**
     * whether the antecedent contains a monetary denomination
     * @param anaphora Anaphora
     * @return value
     */
	public boolean money(Anaphora anaphora){
		List<Token> covTokens = getCoveredTokens(anaphora.getAntecedent());
		boolean value = false;			
		for(Token t : covTokens){
			String ne = getNamedEntityValue(t);
			if(ne != null && ne.equals("MONEY")){
				value = true;
			}
		}
		return value;
	}
    /**
     * whether the antecedent contains a number
     * @param anaphora Anaphora
     * @return value
     */
	public boolean number(Anaphora anaphora){
		List<Token> covTokens = getCoveredTokens(anaphora.getAntecedent());
		boolean value = false;			
		for(Token t : covTokens){
			String ne = getNamedEntityValue(t);
			if(ne != null && ne.equals("NUMBER")){
				value = true;
			}
		}
		return value;
	}
    /**
     * whether the antecedent begins with "the"
     * @param anaphora Anaphora
     * @return value
     */
	public boolean definiteArticle(Anaphora anaphora){
		List<Token> covTokens = getCoveredTokens(anaphora.getAntecedent());
		if(covTokens.get(0).getCoveredText().equalsIgnoreCase("the"))
			return true;
		return false;
	}
    /**
     * whether the antecedent contains his or her
     * @param anaphora Anaphora
     * @return value
     */
	public boolean hisHerPattern(Anaphora anaphora){
		List<Token> covTokens = getCoveredTokens(anaphora.getAntecedent());
		boolean value = false;			
		for(Token t : covTokens){
			if(t.getCoveredText().equalsIgnoreCase("his")||t.getCoveredText().equalsIgnoreCase("her")){
				value = true;
			}
		}
		return value;
	}
    /**
     * whether the antecedent contains he or his
     * @param anaphora Anaphora
     * @return value
     */
	public boolean heHisPattern(Anaphora anaphora){
		List<Token> covTokens = getCoveredTokens(anaphora.getAntecedent());
		boolean value = false;			
		for(Token t : covTokens){
			if(t.getCoveredText().equalsIgnoreCase("he")||t.getCoveredText().equalsIgnoreCase("his")){
				value = true;
			}
		}
		return value;
	}
	
	/**
	 * how often a given part of a text is contained in the document
	 * @param checkTokens list of successive tokens
	 * @return number of occurences
	 */
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
	
	/**
	 * if a given Token is tagged as Subject
	 * @param token Token
	 * @return value
	 */
	private boolean isSubject(Token token){
		for(Dependency d : dependencies){
			if(d.getDependent() == token && d.getDependencyType().contains("subj")){
				return true;
			}
		}	
		return false;
	}
	
	/**
	 * if a given Token is tagged as Object
	 * @param token Token
	 * @return value
	 */
	private boolean isObject(Token token){
		for(Dependency d : dependencies){
			if(d.getDependent() == token && d.getDependencyType().contains("obj")){
				return true;
			}
		}	
		return false;
	}
	
	/**
	 * if a given Token is tagged as predicate
	 * @param token Token
	 * @return value
	 */
	private boolean isPredicate(Token token){
		if(token.getPos().getPosValue().length() >= 2 && token.getPos().getPosValue().substring(0, 2).equals("VB"))
			return true;
		return false;
	}
	
	/**
	 * if a given Token is a pronoun
	 * @param token Token
	 * @return value
	 */
	private boolean isPronoun(Token token){
		if(Arrays.asList(Parameters.allPronouns).contains(token.getCoveredText())){
			return true;
		}
		return false;
	}
	/**
	 * if a given Token is a noun
	 * @param token Token
	 * @return value
	 */
	private boolean isNoun(Token token){
		if(token.getPos().getPosValue().length() >= 2 && 
				token.getPos().getPosValue().substring(0, 2).equals("NN")) 		
			return true;
		return false;
	}	
	
	/**
	 * if a given Token is a conjunction
	 * @param token Token
	 * @return value
	 */
	private boolean isConjunction(Token token){
		if(token.getPos().getPosValue().length() >= 2 && 
				token.getPos().getPosValue().substring(0, 2).equals("CC")){ 	//TODO: Maybe Check for subordinating conjunctions	
			return true;
		}
		return false;
	}
	
	/**
	 * if the parent of a given Token is a noun
	 * @param token Token
	 * @return value
	 */
	private boolean isParentNoun(Token token){
		if(getParent(token) != null && isNoun(getParent(token)))
			return true;
		return false;
	}
	
	/**
	 * If a given Token is a modifier
	 * @param token Token
	 * @return value
	 */
	private boolean isModifier(Token token){
		for(Dependency d : dependencies){
			if(d.getDependent() == token && d.getDependencyType().contains("mod")){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Get the named Entity category (if available) for a token
	 * @param token Token
	 * @return if available: value, else null
	 */
	private String getNamedEntityValue(Token token){
		for(NamedEntity n : namedEntities){
			if(n.getBegin() <= token.getBegin() && n.getEnd() >= token.getEnd()){
				return n.getValue();
			}
		}
		return null;
	}
	
	/**
	 * Gets all covered Tokens
	 * @param annotation Annotation
	 * @return list of covered Tokens
	 */
	private List<Token> getCoveredTokens(Annotation annotation){
		return AnnotationUtils.getCoveredTokens(annotation, tokens);
	}
	
	/**
	 * Gets the parent token of a token
	 * @param token Token
	 * @return if availabe: parent token, else null
	 */
	private Token getParent(Token token){
		return AnnotationUtils.getParent(token, dependencies);
	}
	/**
	 * Gets the head noun of a nounphrase
	 * @param nounphrase Nounphrase
	 * @return if available: the head noun, else null
	 */
	private Token getHeadNoun(Annotation nounphrase){
		List<Token> covTokens = AnnotationUtils.getCoveredTokens(nounphrase, tokens);
		
		for(Token t : covTokens){
			if(t.getPos().getPosValue().contains("NN")){
				for(Dependency d : dependencies){
					if(d.getDependent() == t && d.getGovernor().getPos().getPosValue().contains("NN")){
						Token gov = d.getGovernor();
						if(nounphrase.getBegin() <= gov.getBegin() && nounphrase.getEnd() >= gov.getEnd())
							return d.getGovernor();					
					}
				}
			}
		}
		
		return null;
	}
}
