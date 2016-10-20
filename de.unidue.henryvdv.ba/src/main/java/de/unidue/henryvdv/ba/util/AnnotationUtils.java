package de.unidue.henryvdv.ba.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.uima.jcas.tcas.Annotation;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.dependency.Dependency;
import de.unidue.henryvdv.ba.type.MyNP;

/**
 * Class that stores several methods that are used by all kind of different annotators or similar
 * @author Henry
 *
 */
public class AnnotationUtils {

	/**
	 * Returns the "position" of a token in a document
	 * @param anno
	 * @param tokens all Tokens
	 * @return
	 */
	public static int getTokenNr(Annotation anno, Collection<Token> tokens){
		int i = 1;
		for(Token t : tokens){
			if(anno.getBegin() <= t.getBegin()){
				break;
			}
			i++;
		}
		return i;
	}
	
	/**
	 * Returns token at nr
	 * @param nr
	 * @param tokens all Tokens
	 * @return
	 */
	public static Token getToken(int nr, Collection<Token> tokens){
		int i = 1;
		for(Token t : tokens){
			if(i == nr){
				return t;
			}
			i++;
		}
		return null;
	}
	
	/**
	 * Returns the first Token covered by an annotation
	 * @param anno
	 * @param tokens
	 * @return
	 */
	public static Token getCoveredToken(Annotation anno, Collection<Token> tokens){
		for(Token t : tokens){
			if(t.getBegin() >= anno.getBegin()){
				return t;
			}
		}
		return null;
	}
	
	/**
	 * Whether a list of tokens contains a specific word
	 * @param word
	 * @param tokens
	 * @return
	 */
	public static boolean containsWord(String word, List<Token> tokens){
		for(int i = 0; i < tokens.size(); i++){
			if(tokens.get(i).getCoveredText().toLowerCase().equals(word.toLowerCase())){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns all tokens in a specific span
	 * @param begin 
	 * @param end
	 * @param tokens
	 * @return
	 */
	public static List<Token> getCoveredTokens(int begin, int end, Collection<Token> tokens){
		List<Token> coveredTokens = new ArrayList<Token>();
		for(Token t : tokens){
			if(t.getBegin() >= begin && t.getEnd() <= end){
				coveredTokens.add(t);
			}
		}
		return coveredTokens;
	}
	
	/**
	 * Returns all tokens covered by an annotation
	 * @param anno
	 * @param tokens
	 * @return
	 */
	public static List<Token> getCoveredTokens(Annotation anno, Collection<Token> tokens){
		List<Token> coveredTokens = new ArrayList<Token>();
		for(Token t : tokens){
			if(t.getBegin() >= anno.getBegin() && t.getEnd() <= anno.getEnd()){
				coveredTokens.add(t);
			}
		}
		return coveredTokens;
	}
	
	/**
	 * Returns the number of the sentence at a specific index
	 * @param begin index
	 * @param sentences all sentences
	 * @return
	 */
	public static int getSentenceNr(int begin, Collection<Sentence> sentences){
		int sentenceNr = 1;
		for(Sentence s : sentences){
			if(s.getEnd() > begin){
				break;
			}
			sentenceNr++;
		}	
		return sentenceNr;
	}
	
	/**
	 * Returns the sentence at a specific index
	 * @param begin index
	 * @param sentences all sentences
	 * @return
	 */
	public static Sentence getSentence(int begin, Collection<Sentence> sentences){
		for(Sentence s : sentences){
			if(s.getEnd() > begin){
				return s;
			}
		}	
		return null;
	}
	
	/**
	 * Returns the parent of a specific token
	 * @param token
	 * @param dependencies all dependencies
	 * @return
	 */
	public static Token getParent(Token token, Collection<Dependency> dependencies){
		for(Dependency d : dependencies){
			if(d.getDependent() == token){
				return d.getGovernor();
			}
		}
		return null;
	}

	/**
	 * Sorts a list of MyNP - noun phrases according to their begin
	 * @param unsortedList
	 * @return sorted List
	 */
	public static List<MyNP> sortMyNPList(List<MyNP> unsortedList){
		List<MyNP> returnList = new ArrayList<MyNP>();
		List<MyNP> helpList = new ArrayList<MyNP>();
		for(MyNP np : unsortedList){
			helpList.add(np);
		}
		int currentMaxValue = 0;
		while(returnList.size() != unsortedList.size()){
			currentMaxValue = 0;
			for(MyNP np : helpList){
				if(np.getEnd() > currentMaxValue){
					currentMaxValue = np.getEnd();
				}
			}
			for(MyNP np : unsortedList){
				if(np.getEnd() == currentMaxValue){
					returnList.add(np);
					helpList.remove(np);
				}
			}
		}		
		return returnList;
		
	}
	
}
