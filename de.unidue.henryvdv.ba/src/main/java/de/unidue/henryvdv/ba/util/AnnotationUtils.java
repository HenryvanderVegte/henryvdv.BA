package de.unidue.henryvdv.ba.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.uima.jcas.tcas.Annotation;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;

public class AnnotationUtils {

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
	
	public static Token getCoveredToken(Annotation anno, Collection<Token> tokens){
		for(Token t : tokens){
			if(t.getBegin() >= anno.getBegin()){
				return t;
			}
		}
		return null;
	}
	
	public static boolean containsWord(String word, List<Token> tokens){
		for(int i = 0; i < tokens.size(); i++){
			if(tokens.get(i).getCoveredText().toLowerCase().equals(word.toLowerCase())){
				return true;
			}
		}
		return false;
	}
	
	public static List<Token> getCoveredTokens(int begin, int end, Collection<Token> tokens){
		List<Token> coveredTokens = new ArrayList<Token>();
		for(Token t : tokens){
			if(t.getBegin() >= begin && t.getEnd() <= end){
				coveredTokens.add(t);
			}
		}
		return coveredTokens;
	}
	
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
	
}
