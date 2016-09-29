package de.unidue.henryvdv.ba.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.constituent.NP;
import de.unidue.henryvdv.ba.type.Anaphora;
import de.unidue.henryvdv.ba.type.DetectedNP;
import de.unidue.henryvdv.ba.type.GoldNP;
import de.unidue.henryvdv.ba.type.MyCoreferenceChain;
import de.unidue.henryvdv.ba.type.MyCoreferenceLink;

public class AnaphoraEvaluator {
	
	private static final String ACCURACY_FILE_NAME = "accuracyOutput.txt";
	
	private static final String OUTPUT_FILE_NAME = "output.txt";

	private static final String PRINT_FILE_DIRECTORY = "src/main/resources/output";
	
	public static final boolean PARAM_PRINTFILE = true;

	private int anaphorsTotal = 0;
	private int correctAnaphorsTotal = 0;
	
	private File accuracyOutputFile;
	private File outputFile;
	private List<String> output;
	
	public AnaphoraEvaluator(){
		super();
		accuracyOutputFile = new File(PRINT_FILE_DIRECTORY + "/" + ACCURACY_FILE_NAME);
		outputFile = new File(PRINT_FILE_DIRECTORY + "/" + OUTPUT_FILE_NAME);
		
		output = new ArrayList<String>();
		output.add("-----------------------------------------NEW EVALUATION----------------------------------");
	}
	
	public void evaluateSameEntity_Bergsma(List<DetectedNP> detectedAntecedents, List<Anaphora> anaphoras, Collection<MyCoreferenceChain> corefChains, boolean printChoices, String documentName){

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
		anaphorsTotal += total;
		correctAnaphorsTotal += correct;
	}
	
	public void evaluate_Bergsma(List<DetectedNP> detectedAntecedents, List<GoldNP> goldAntecedents, List<Anaphora> anaphoras, boolean printChoices, String documentName){
		if(detectedAntecedents.size() != goldAntecedents.size() || detectedAntecedents.size() != anaphoras.size()){
			System.out.println("Gold Antecedent List and Detected Antecedent List are not the same size - return");
			return;
		}
		output.add("********  Document:  " + documentName + "  ********");
		
		int correct = 0;
		int total = 0;
		
		for(int i = 0; i < goldAntecedents.size(); i++){
			if(goldAntecedents.get(i) == null && detectedAntecedents.get(i) == null){
				output.add("->Correct:");
				output.add("  Both are null");
				
				correct++;	
				total++;
				continue;
			}
			if(goldAntecedents.get(i) == null || detectedAntecedents.get(i) == null){
				output.add("-> Wrong:");
				output.add("  One is null");
				total++;
				continue;
			}		
			if(goldAntecedents.get(i).getBegin() >= detectedAntecedents.get(i).getBegin() && 
				goldAntecedents.get(i).getEnd() <= detectedAntecedents.get(i).getEnd()){
				output.add("-> Correct:");
				output.add("  Anaphora: " + anaphoras.get(i).getCoveredText());
				output.add("  Gold : " + goldAntecedents.get(i).getCoveredText());
				output.add("  Detected : " + detectedAntecedents.get(i).getCoveredText());
				
				
				correct++;	
				total++;
				continue;
			}
			
			if(goldAntecedents.get(i).getBegin() <= detectedAntecedents.get(i).getBegin() && 
				goldAntecedents.get(i).getEnd() >= detectedAntecedents.get(i).getEnd()){
				output.add("->Correct:");
				output.add("  Anaphora: " + anaphoras.get(i).getCoveredText());
				output.add("  Gold : " + goldAntecedents.get(i).getCoveredText());
				output.add("  Detected : " + detectedAntecedents.get(i).getCoveredText());
				
				correct++;		
				total++;
				continue;
			}
			output.add("->Wrong:");
			output.add("  Anaphora: " + anaphoras.get(i).getCoveredText());
			output.add("  Gold : " + goldAntecedents.get(i).getCoveredText());
			output.add("  Detected : " + detectedAntecedents.get(i).getCoveredText());
			total++;
			
		}
		output.add("");
		anaphorsTotal += total;
		correctAnaphorsTotal += correct;
	}
	
	public void evaluate_last_n_sentences(Map<Anaphora, List<NP>> acceptedNPs, boolean printChoices){
		int correct = 0;
		int total = 0;
		
		for(Anaphora anaphora : acceptedNPs.keySet()){
			int goldAntecedentBegin = anaphora.getAntecedent().getBegin();
			int goldAntecedentEnd = anaphora.getAntecedent().getEnd();
			
			if(printChoices)
				System.out.println("Anaphora: " + anaphora.getCoveredText());
			if(acceptedNPs.get(anaphora).size() == 0){
				total++;
			}
			
			for(NP np : acceptedNPs.get(anaphora)){
				int npBegin = np.getBegin();
				int npEnd = np.getEnd();
				
				if(printChoices)
					System.out.print("Possible antecedent: " + np.getCoveredText());
				
				if((goldAntecedentBegin >= npBegin && goldAntecedentEnd <= npEnd) || goldAntecedentBegin <= npBegin && goldAntecedentEnd >= npEnd){
					correct++;
					if(printChoices)
						System.out.println(" - Correct");
				} else if(printChoices){
					System.out.println(" - Wrong");
				}
				total++;
			}
			
		}
		anaphorsTotal += total;
		correctAnaphorsTotal += correct;
	}
	
	
	public void printResults(){
		output.add("-----------------------------------------EVALUATION END----------------------------------");
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

				fw = new FileWriter(accuracyOutputFile, true);
			    bw = new BufferedWriter(fw);
			    out = new PrintWriter(bw);
			    out.println("Correct: " + correctAnaphorsTotal + "  Total: " + anaphorsTotal + "  Accuracy: " + rel);
			    out.close();
			    
			    fw = new FileWriter(outputFile, true);
			    bw = new BufferedWriter(fw);
			    out = new PrintWriter(bw);
			    for(String s : output){
				    out.println(s);
			    }
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

	public void printResults(List<String> featureVectorsUsed){
		output.add("-----------------------------------------EVALUATION END----------------------------------");
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

				fw = new FileWriter(accuracyOutputFile, true);
			    bw = new BufferedWriter(fw);
			    out = new PrintWriter(bw);
			    out.println("Correct: " + correctAnaphorsTotal + "  Total: " + anaphorsTotal + "  Accuracy: " + rel);
			    out.close();
			    
			    fw = new FileWriter(outputFile, true);
			    bw = new BufferedWriter(fw);
			    out = new PrintWriter(bw);
			    int i = 0;
			    for(String s : output){
				    if(s.length() > 1 && s.substring(0, 2).equals("->")){
				    	out.println(featureVectorsUsed.get(i));
					    i++;
				    }
			    	out.println(s);
				    
			    }
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
