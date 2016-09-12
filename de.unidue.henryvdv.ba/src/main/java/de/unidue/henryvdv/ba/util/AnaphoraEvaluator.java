package de.unidue.henryvdv.ba.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.unidue.henryvdv.ba.type.Anaphora;
import de.unidue.henryvdv.ba.type.DetectedNP;
import de.unidue.henryvdv.ba.type.GoldNP;
import de.unidue.henryvdv.ba.type.MyCoreferenceChain;
import de.unidue.henryvdv.ba.type.MyCoreferenceLink;

public class AnaphoraEvaluator {
	
	private static final String PRINT_FILE_NAME = "accuracyOutput.txt";

	private static final String PRINT_FILE_DIRECTORY = "src/main/resources/output";
	
	public static final boolean PARAM_PRINTFILE = true;

	private int anaphorsTotal = 0;
	private int correctAnaphorsTotal = 0;
	
	private File outputFile;
	
	public AnaphoraEvaluator(){
		super();
		outputFile = new File(PRINT_FILE_DIRECTORY + "/" + PRINT_FILE_NAME);
	}
	
	public void evaluateSameEntity(List<DetectedNP> detectedAntecedents, List<Anaphora> anaphoras, Collection<MyCoreferenceChain> corefChains, boolean printChoices){

		List<MyCoreferenceChain> anaphoraChains = new ArrayList<MyCoreferenceChain>();
		for(int i = 0; i < anaphoras.size(); i++){
			int anaphoraBegin = anaphoras.get(i).getBegin();
			int anaphoraEnd = anaphoras.get(i).getEnd();
			
			
			for(MyCoreferenceChain c : corefChains){
				MyCoreferenceLink corefLinkOld = c.getFirst();			
				if(corefLinkOld.getBegin() == anaphoraBegin && corefLinkOld.getEnd() == anaphoraEnd){
					anaphoraChains.add(c);
					break;
				}
				
				while(corefLinkOld.getNext() != null){
					MyCoreferenceLink corefLinkNew = corefLinkOld.getNext();
					
					if(corefLinkNew.getBegin() == anaphoraBegin && corefLinkNew.getEnd() == anaphoraEnd){					
						anaphoraChains.add(c);
						break;
					}			
					corefLinkOld = corefLinkNew;
				}
			}
		}
		
		if(detectedAntecedents.size() != anaphoras.size() || anaphoras.size() != anaphoraChains.size() ){
			System.out.println("Gold Antecedent List and Detected Antecedent List are not the same size - return");
			return;
		}
		int total = 0;
		int correct = 0;
		for(int i = 0; i < anaphoras.size(); i++){
			
			boolean foundIt = false;
			int anteBegin = detectedAntecedents.get(i).getBegin();
			int anteEnd = detectedAntecedents.get(i).getEnd();
			
			MyCoreferenceChain corefChain = anaphoraChains.get(i);
			
			MyCoreferenceLink corefLinkOld = corefChain.getFirst();			
			if(corefLinkOld.getBegin() <= anteBegin && corefLinkOld.getEnd() >= anteEnd){
				foundIt = true;		
			}
			
			while(!foundIt && corefLinkOld.getNext() != null){
				MyCoreferenceLink corefLinkNew = corefLinkOld.getNext();
				
				if(corefLinkNew.getBegin() <= anteBegin && corefLinkNew.getEnd() >= anteEnd){					
					foundIt = true;
				}			
				corefLinkOld = corefLinkNew;
			}
			if(foundIt)
				correct++;
			total++;
		}
		System.out.println("Correct in this Doc: " + correct);
		System.out.println("Total in this Doc: " + total + "  " + (anaphoras.size()));
		anaphorsTotal += total;
		correctAnaphorsTotal += correct;
	}
	
	public void evaluate(List<DetectedNP> detectedAntecedents, List<GoldNP> goldAntecedents, List<Anaphora> anaphoras, boolean printChoices){
		if(detectedAntecedents.size() != goldAntecedents.size() || detectedAntecedents.size() != anaphoras.size()){
			System.out.println("Gold Antecedent List and Detected Antecedent List are not the same size - return");
			return;
		}
		
		int correct = 0;
		int total = 0;
		
		for(int i = 0; i < goldAntecedents.size(); i++){
			if(goldAntecedents.get(i) == null && detectedAntecedents.get(i) == null){
				
				if(printChoices){
					System.out.println("--Correct--");
					System.out.println("Both are null");
				}

				
				correct++;	
				total++;
				continue;
			}
			if(goldAntecedents.get(i) == null || detectedAntecedents.get(i) == null){
				if(printChoices){
					System.out.println("--Wrong--");
					System.out.println("One is null");
				}
				total++;
				continue;
			}		
			if(goldAntecedents.get(i).getBegin() >= detectedAntecedents.get(i).getBegin() && 
				goldAntecedents.get(i).getEnd() <= detectedAntecedents.get(i).getEnd()){
				
				if(printChoices){
					System.out.println("--Correct--");
					System.out.println("Anaphora: " + anaphoras.get(i).getCoveredText());
					System.out.println("Gold : " + goldAntecedents.get(i).getCoveredText());
					System.out.println("Detected : " + detectedAntecedents.get(i).getCoveredText());
				}
				
				correct++;	
				total++;
				continue;
			}
			
			if(goldAntecedents.get(i).getBegin() <= detectedAntecedents.get(i).getBegin() && 
				goldAntecedents.get(i).getEnd() >= detectedAntecedents.get(i).getEnd()){
				if(printChoices){
					System.out.println("--Correct--");
					System.out.println("Anaphora: " + anaphoras.get(i).getCoveredText());
					System.out.println("Gold : " + goldAntecedents.get(i).getCoveredText());
					System.out.println("Detected : " + detectedAntecedents.get(i).getCoveredText());
				}
				
				correct++;		
				total++;
				continue;
			}
			if(printChoices){
				System.out.println("--Wrong--");
				System.out.println("Anaphora: " + anaphoras.get(i).getCoveredText());
				System.out.println("Gold : " + goldAntecedents.get(i).getCoveredText());
				System.out.println("Detected : " + detectedAntecedents.get(i).getCoveredText());
			}
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
		if(PARAM_PRINTFILE){
			
			FileWriter fw = null;
			BufferedWriter bw = null;
			PrintWriter out = null;
			try {

				fw = new FileWriter(outputFile, true);
			    bw = new BufferedWriter(fw);
			    out = new PrintWriter(bw);
			    out.println("Correct: " + correctAnaphorsTotal + "  Total: " + anaphorsTotal + "  Accuracy: " + rel);
			    out.close();
			} catch (IOException e) {
			    //exception handling left as an exercise for the reader
			}
			finally {
			    if(out != null)
				    out.close();
			    try {
			        if(bw != null)
			            bw.close();
			    } catch (IOException e) {
			        //exception handling left as an exercise for the reader
			    }
			    try {
			        if(fw != null)
			            fw.close();
			    } catch (IOException e) {
			        //exception handling left as an exercise for the reader
			    }
			}
			
			
		}
	}
	
}
