package de.unidue.henryvdv.ba.util;

import java.util.List;

import de.unidue.henryvdv.ba.type.DetectedNP;
import de.unidue.henryvdv.ba.type.GoldNP;

public class AnaphoraEvaluator {

	private int anaphorsTotal = 0;
	private int correctAnaphorsTotal = 0;
	
	public void evaluate(List<DetectedNP> detectedAntecedents, List<GoldNP> goldAntecedents){
		if(detectedAntecedents.size() != goldAntecedents.size()){
			System.out.println("Gold Antecedent List and Detected Antecedent List are not the same size - return");
			return;
		}
		
		int correct = 0;
		int total = 0;
		
		for(int i = 0; i < goldAntecedents.size(); i++){
			if(goldAntecedents.get(i) == null && detectedAntecedents.get(i) == null){
				/*
				System.out.println("--Correct--");
				System.out.println("Both are null");
				*/
				correct++;	
				total++;
				continue;
			}
			if(goldAntecedents.get(i) == null || detectedAntecedents.get(i) == null){
				/*
				System.out.println("--Wrong--");
				System.out.println("One is null");
				*/
				total++;
				continue;
			}		
			if(goldAntecedents.get(i).getBegin() >= detectedAntecedents.get(i).getBegin() && 
				goldAntecedents.get(i).getEnd() <= detectedAntecedents.get(i).getEnd()){
				
				/*
				System.out.println("--Correct--");
				System.out.println("Anaphora: " + npsWithAnaphora.get(i).getCoveredText() + "(Sentence: " + getSentenceNr(npsWithAnaphora.get(i).getBegin()) + ")" );
				System.out.println("Gold : " + goldAntecedent.get(i).getCoveredText() + "(Sentence: " + getSentenceNr(goldAntecedent.get(i).getBegin()) + ")");
				System.out.println("Detected : " + detectedAntecedent.get(i).getCoveredText() + "(Sentence: " + getSentenceNr(detectedAntecedent.get(i).getBegin()) + ")");
				*/
				
				correct++;	
				total++;
				continue;
			}
			
			if(goldAntecedents.get(i).getBegin() <= detectedAntecedents.get(i).getBegin() && 
				goldAntecedents.get(i).getEnd() >= detectedAntecedents.get(i).getEnd()){
				/*
				System.out.println("--Correct--");
				System.out.println("Anaphora: " + npsWithAnaphora.get(i).getCoveredText() + "(Sentence: " + getSentenceNr(npsWithAnaphora.get(i).getBegin()) + ")" );
				System.out.println("Gold : " + goldAntecedent.get(i).getCoveredText() + "(Sentence: " + getSentenceNr(goldAntecedent.get(i).getBegin()) + ")");
				System.out.println("Detected : " + detectedAntecedent.get(i).getCoveredText() + "(Sentence: " + getSentenceNr(detectedAntecedent.get(i).getBegin()) + ")");
				*/
				
				
				correct++;		
				total++;
				continue;
			}
			/*
			System.out.println("--Wrong--");
			System.out.println("Anaphora: " + npsWithAnaphora.get(i).getCoveredText() + "(Sentence: " + getSentenceNr(npsWithAnaphora.get(i).getBegin()) + ")" );
			System.out.println("Gold : " + goldAntecedent.get(i).getCoveredText() + "(Sentence: " + getSentenceNr(goldAntecedent.get(i).getBegin()) + ")");
			System.out.println("Detected : " + detectedAntecedent.get(i).getCoveredText() + "(Sentence: " + getSentenceNr(detectedAntecedent.get(i).getBegin()) + ")");
			*/
			total++;
			
		}
		anaphorsTotal += total;
		correctAnaphorsTotal += correct;
	}
	
	public void printResults(){
		float rel = 0f;	
		if(anaphorsTotal != 0){
			rel = ((float)correctAnaphorsTotal / (float)anaphorsTotal) * 100f;
		} 
		System.out.println("Accuracy: " + rel + " % ");
	}
	
}
