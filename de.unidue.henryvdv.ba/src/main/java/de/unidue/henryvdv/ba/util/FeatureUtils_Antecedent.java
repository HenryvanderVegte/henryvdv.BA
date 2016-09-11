package de.unidue.henryvdv.ba.util;

import java.util.ArrayList;
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
import de.unidue.henryvdv.ba.type.Anaphora;
import de.unidue.henryvdv.ba.type.Quotation;

public class FeatureUtils_Antecedent {

	private Collection<Token> tokens;
	private Collection<Sentence> sentences;
	private Collection<Dependency> dependencies;
	private Collection<NamedEntity> namedEntities;
	private Collection<Time> times;
	
	public FeatureUtils_Antecedent(JCas aJCas){
		sentences = JCasUtil.select(aJCas, Sentence.class);
		dependencies = JCasUtil.select(aJCas, Dependency.class);
		tokens = JCasUtil.select(aJCas, Token.class);
		namedEntities = JCasUtil.select(aJCas, NamedEntity.class);
		times = JCasUtil.select(aJCas, Time.class);
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
		//Money
		a.getAntecedentFeatures().setA_Number(number(a));
		//Definite Article
		a.getAntecedentFeatures().setA_Definite(definiteArticle(a));
		// His/Her
		a.getAntecedentFeatures().setA_HisHer(hisHerPattern(a));
		// He/His
		a.getAntecedentFeatures().setA_HeHis(heHisPattern(a));
	}
	

	
	
	public float antecedentFrequency(Anaphora a){
		List<Token> covTokens = getCoveredTokens(a.getAntecedent());		
		int count = getNrOfOccurences(covTokens);
		return (float)count  / 10f;
	}
	
	public boolean subject(Anaphora a){
		List<Token> covTokens = getCoveredTokens(a.getAntecedent());	
		boolean value = false;
		for(Token t : covTokens){				
			if(isSubject(t)){
				value = true;
			}
		}
		return value;
	}
	
	public boolean object(Anaphora a){
		List<Token> covTokens = getCoveredTokens(a.getAntecedent());	
		boolean value = false;
		for(Token t : covTokens){				
			if(isObject(t)){
				value = true;
			}
		}
		return value;
	}
	
	public boolean predicate(Anaphora a){
		List<Token> covTokens = getCoveredTokens(a.getAntecedent());	
		boolean value = false;
		for(Token t : covTokens){				
			if(isPredicate(t)){
				value = true;
			}
		}
		return value;
	}
	
	public boolean pronominal(Anaphora a){
		List<Token> covTokens = getCoveredTokens(a.getAntecedent());	
		boolean value = false;
		for(Token t : covTokens){				
			if(isPronoun(t)){
				value = true;
			}
		}
		return value;
	}
	
	public boolean headWordEmphasis(Anaphora a){
		List<Token> covTokens = getCoveredTokens(a.getAntecedent());
		boolean parentIsNoun = false;
		for(Token t : covTokens){
			Token parent = getParent(t);
			if(parent != null && isNoun(parent)){
				parentIsNoun = true;
			}
		}
		return !parentIsNoun;
	}
	
	public boolean conjunction(Anaphora a){
		List<Token> covTokens = getCoveredTokens(a.getAntecedent());
		boolean containsConjunction = false;
		for(Token t : covTokens){
			if(isConjunction(t)){
				containsConjunction = true;
			}
		}
		return !containsConjunction;
	}
	
	public boolean prenominalModifier(Anaphora a){
		List<Token> covTokens = getCoveredTokens(a.getAntecedent());
		boolean value = false;
		for(Token t : covTokens){
			if(isPrenominalModifier(t)){
				value = true;
			}
		}
		return value;
	}
	
	public boolean organization(Anaphora a){
		List<Token> covTokens = getCoveredTokens(a.getAntecedent());
		boolean value = false;			
		for(Token t : covTokens){
			String ne = getNamedEntityValue(t);
			if(ne != null && ne.equals("ORGANIZATION")){
				value = true;
			}
		}
		return value;
	}
	
	public boolean person(Anaphora a){
		List<Token> covTokens = getCoveredTokens(a.getAntecedent());
		boolean value = false;			
		for(Token t : covTokens){
			String ne = getNamedEntityValue(t);
			if(ne != null && ne.equals("PERSON")){
				value = true;
			}
		}
		return value;
	}
	
	public boolean time(Anaphora a){
		List<Token> covTokens = getCoveredTokens(a.getAntecedent());
		boolean value = false;			
		for(Token t : covTokens){
			String ne = getNamedEntityValue(t);
			if(ne != null && ne.equals("TIME")){
				value = true;
			}
		}
		return value;
	}
	
	public boolean date(Anaphora a){
		List<Token> covTokens = getCoveredTokens(a.getAntecedent());
		boolean value = false;			
		for(Token t : covTokens){
			String ne = getNamedEntityValue(t);
			if(ne != null && ne.equals("DATE")){
				value = true;
			}
		}
		return value;
	}
	
	public boolean money(Anaphora a){
		List<Token> covTokens = getCoveredTokens(a.getAntecedent());
		boolean value = false;			
		for(Token t : covTokens){
			String ne = getNamedEntityValue(t);
			if(ne != null && ne.equals("MONEY")){
				value = true;
			}
		}
		return value;
	}
	
	public boolean number(Anaphora a){
		List<Token> covTokens = getCoveredTokens(a.getAntecedent());
		boolean value = false;			
		for(Token t : covTokens){
			String ne = getNamedEntityValue(t);
			if(ne != null && ne.equals("NUMBER")){
				value = true;
			}
		}
		return value;
	}
	
	public boolean definiteArticle(Anaphora a){
		List<Token> covTokens = getCoveredTokens(a.getAntecedent());
		boolean value = false;			
		for(Token t : covTokens){
			if(t.getCoveredText().equalsIgnoreCase("the")){
				value = true;
			}
		}
		return value;
	}
	
	public boolean hisHerPattern(Anaphora a){
		List<Token> covTokens = getCoveredTokens(a.getAntecedent());
		boolean value = false;			
		for(Token t : covTokens){
			if(t.getCoveredText().equalsIgnoreCase("his")||t.getCoveredText().equalsIgnoreCase("her")){
				value = true;
			}
		}
		return value;
	}
	
	public boolean heHisPattern(Anaphora a){
		List<Token> covTokens = getCoveredTokens(a.getAntecedent());
		boolean value = false;			
		for(Token t : covTokens){
			if(t.getCoveredText().equalsIgnoreCase("he")||t.getCoveredText().equalsIgnoreCase("his")){
				value = true;
			}
		}
		return value;
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
}
