package de.unidue.henryvdv.ba.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.constituent.NP;
import de.unidue.henryvdv.ba.param.Parameters;
import de.unidue.henryvdv.ba.type.Anaphora;
import de.unidue.henryvdv.ba.type.DetectedNP;
import de.unidue.henryvdv.ba.type.GoldNP;
import de.unidue.henryvdv.ba.type.MyCoreferenceChain;
import de.unidue.henryvdv.ba.type.MyCoreferenceLink;
import de.unidue.henryvdv.ba.type.MyNP;
/**
 * Class that performs various 
 * @author Henry
 *
 */
public class AnaphoraEvaluator {
	
	private static final String ACCURACY_FILE_NAME = "accuracyOutput.txt";
	
	private static final String OUTPUT_FILE_NAME = "output.txt";

	private static final String PRINT_FILE_DIRECTORY = "src/main/resources/output";
	
	public static final boolean PARAM_PRINTFILE = true;
	
	/**
	 * Order: [0]=TP, [1]=TN, [2]=FP, [3]=FN
	 */
	private int[] bergsmaLowerThreshold;
	private int[] bergsmaKeepThreshold;
	private int[] bergsmaSameEntity;
	private int[] bergsmaBaseline;
	private int[] lastNSentences;
	private int[] lastNSentences_takeTheBest;
	private int[] lastNSentences_baseline;
	private int[] lastNSentences_sameEntity;
	
	private File accuracyOutputFile;
	private File outputFile;
	private List<String> output;
	private Collection<Sentence> sentences;
	
	public AnaphoraEvaluator(){
		super();
		
		accuracyOutputFile = new File(PRINT_FILE_DIRECTORY + "/" + ACCURACY_FILE_NAME);
		outputFile = new File(PRINT_FILE_DIRECTORY + "/" + OUTPUT_FILE_NAME);
		output = new ArrayList<String>();
	}
	
	public void setSentences(Collection<Sentence> sentences){
		this.sentences = sentences;
	}
	
	public void evaluateBergsma_SameEntity(Map<Anaphora, List<MyNP>> allNPs, Collection<MyCoreferenceChain> corefChains, boolean printChoices){
		if(bergsmaSameEntity == null)
			bergsmaSameEntity = new int[]{0,0,0,0};
		int TP = 0;
		int FP = 0;
		int FN = 0;
		List<Anaphora> anaphoras = new ArrayList<Anaphora>();
		for(Anaphora a :allNPs.keySet()){
			anaphoras.add(a);
		}
		//Define for each anaphora its coreference chain:
		List<MyCoreferenceChain> anaphoraChains = new ArrayList<MyCoreferenceChain>();
		for(int i = 0; i < anaphoras.size(); i++){
			int anaphoraBegin = anaphoras.get(i).getBegin();
			int anaphoraEnd = anaphoras.get(i).getEnd();	
			boolean foundIt = false;
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
						foundIt = true;
						break;
					}			
					corefLinkOld = corefLinkNew;
				}
				if(foundIt)
					break;
			}
		}
		
		//Check all antecedents:
		for(int i = 0; i < anaphoras.size(); i++){
			int goldAntecedentBegin = anaphoras.get(i).getAntecedent().getBegin();
			int goldAntecedentEnd = anaphoras.get(i).getAntecedent().getEnd();
			List<MyNP> candidates = new ArrayList<MyNP>();
			for(MyNP np : allNPs.get(anaphoras.get(i))){
				candidates.add(np);				
			}
			boolean foundPossibleAntecedent = false;
			float threshold = Parameters.acceptAtThreshold;
			MyNP possibleAntecedent = null;
			//Get best candidate:
			while(!foundPossibleAntecedent){
				MyNP nearest = null;
				int dist = Integer.MAX_VALUE;
				int size = Integer.MAX_VALUE;
				for(MyNP np : candidates){
					if(anaphoras.get(i).getBegin() - np.getBegin() < dist){
						nearest = np;
						dist = anaphoras.get(i).getBegin() - np.getBegin();
						size = np.getEnd() - np.getBegin();
					} else if((anaphoras.get(i).getBegin() - np.getBegin() == dist) && (np.getEnd() - np.getBegin()) < size){
						nearest = np;
						dist = anaphoras.get(i).getBegin() - np.getBegin();
						size = np.getEnd() - np.getBegin();
					}
				}
				if(nearest == null){
					System.out.println("No possible candidates!");
					break;
				}
				if(nearest.getOutputValue() > threshold){
					foundPossibleAntecedent = true;
					possibleAntecedent = nearest;
				} else {
					candidates.remove(nearest);
					if(candidates.size() == 0){
						break;
					}
				}
			}
			if(possibleAntecedent == null){
				FN++;
				continue;
			}
			//Check if best candidate is in coreferenceChain
			int npBegin = possibleAntecedent.getBegin();
			int npEnd = possibleAntecedent.getEnd();			
			MyCoreferenceChain anaphoraCorefChain = anaphoraChains.get(i);	
			MyCoreferenceLink corefLinkOld = anaphoraCorefChain.getFirst();	
			boolean candidateInChain = false;
			if(corefLinkOld.getBegin() <= npBegin && corefLinkOld.getEnd() >= npEnd){
				candidateInChain = true;		
			}		
			while(!candidateInChain && corefLinkOld.getNext() != null){
				MyCoreferenceLink corefLinkNew = corefLinkOld.getNext();
				if(Parameters.exactBoundMatch){
					if(corefLinkNew.getBegin() == npBegin && corefLinkNew.getEnd() == npBegin){					
						candidateInChain = true;
					}	
				}else {
					if(corefLinkNew.getBegin() <= npBegin && corefLinkNew.getEnd() >= npBegin){					
						candidateInChain = true;
					}	
				}
		
				corefLinkOld = corefLinkNew;
			}
			
			if(candidateInChain){
				TP++;
			} else {
				FP++;
			}		
		}
		bergsmaSameEntity[0] += TP;
		bergsmaSameEntity[2] += FP;
		bergsmaSameEntity[3] += FN;
		
	}
	
	public void evaluateBergsma_LowerThreshold(Map<Anaphora, List<MyNP>> allNPs, boolean printChoices){
		if(bergsmaLowerThreshold == null)
			bergsmaLowerThreshold = new int[]{0,0,0,0};
		
		System.out.println("Anaphors: " + allNPs.keySet().size());
		int TP = 0;
		int FP = 0;
		for(Anaphora anaphora : allNPs.keySet()){
			int goldAntecedentBegin = anaphora.getAntecedent().getBegin();
			int goldAntecedentEnd = anaphora.getAntecedent().getEnd();
			List<MyNP> candidates = new ArrayList<MyNP>();
			for(MyNP np : allNPs.get(anaphora)){
				candidates.add(np);				
			}
			boolean foundPossibleAntecedent = false;
			float threshold = Parameters.acceptAtThreshold;
			MyNP possibleAntecedent = null;
			while(!foundPossibleAntecedent){
				MyNP nearest = null;
				int dist = Integer.MAX_VALUE;
				int size = Integer.MAX_VALUE;
				for(MyNP np : candidates){
					if(anaphora.getBegin() - np.getBegin() < dist){
						nearest = np;
						dist = anaphora.getBegin() - np.getBegin();
						size = np.getEnd() - np.getBegin();
					} else if((anaphora.getBegin() - np.getBegin() == dist) && (np.getEnd() - np.getBegin()) < size){
						nearest = np;
						dist = anaphora.getBegin() - np.getBegin();
						size = np.getEnd() - np.getBegin();
					}
				}
				if(nearest.getOutputValue() > threshold){
					foundPossibleAntecedent = true;
					possibleAntecedent = nearest;
				} else {
					candidates.remove(nearest);
					if(candidates.size() == 0){
						for(MyNP np : allNPs.get(anaphora)){
							candidates.add(np);				
						}
						threshold -= Parameters.lowerThresholdFactor;
					}
				}
			}
			if(possibleAntecedent == null){
				System.out.println("Error: No possible antecedent available");
				continue;
			}
			
			int npBegin = possibleAntecedent.getBegin();
			int npEnd = possibleAntecedent.getEnd();
			if(Parameters.exactBoundMatch){
				if(goldAntecedentBegin == npBegin && goldAntecedentEnd == npEnd){
					TP++;
				} else {
					FP++;
				}
			} else {
				if((goldAntecedentBegin >= npBegin && goldAntecedentEnd <= npEnd) || goldAntecedentBegin <= npBegin && goldAntecedentEnd >= npEnd){
					TP++;
				} else {
					FP++;
				}
			}
		}
		bergsmaLowerThreshold[0] += TP;
		bergsmaLowerThreshold[2] += FP;
	}
	
	public void evaluateBergsma_KeepThreshold(Map<Anaphora, List<MyNP>> allNPs, boolean printChoices){
		if(bergsmaKeepThreshold == null)
			bergsmaKeepThreshold = new int[]{0,0,0,0};
		int TP = 0;
		int FP = 0;
		int FN = 0;
		for(Anaphora anaphora : allNPs.keySet()){
			int goldAntecedentBegin = anaphora.getAntecedent().getBegin();
			int goldAntecedentEnd = anaphora.getAntecedent().getEnd();
			List<MyNP> candidates = new ArrayList<MyNP>();
			for(MyNP np : allNPs.get(anaphora)){
				candidates.add(np);				
			}
			boolean foundPossibleAntecedent = false;
			float threshold = Parameters.acceptAtThreshold;
			MyNP possibleAntecedent = null;
			while(!foundPossibleAntecedent){
				MyNP nearest = null;
				int dist = Integer.MAX_VALUE;
				int size = Integer.MAX_VALUE;
				for(MyNP np : candidates){
					if(anaphora.getBegin() - np.getBegin() < dist){
						nearest = np;
						dist = anaphora.getBegin() - np.getBegin();
						size = np.getEnd() - np.getBegin();
					} else if((anaphora.getBegin() - np.getBegin() == dist) && (np.getEnd() - np.getBegin()) < size){
						nearest = np;
						dist = anaphora.getBegin() - np.getBegin();
						size = np.getEnd() - np.getBegin();
					}
				}
				if(nearest == null){
					if(printChoices)
						System.out.println("No possible candidates!");
					break;
				}
				if(nearest.getOutputValue() > threshold){
					foundPossibleAntecedent = true;
					possibleAntecedent = nearest;
				} else {
					candidates.remove(nearest);
					if(candidates.size() == 0){
						break;
					}
				}
			}
			if(possibleAntecedent == null){
				if(printChoices){
					System.out.println("-->Anaphora :" + anaphora.getCoveredText() + "   " + AnnotationUtils.getSentence(anaphora.getBegin(), sentences).getCoveredText());			
					System.out.println("!!None found");
					System.out.println("Antecedent " + anaphora.getAntecedent().getCoveredText() +
							"   " + AnnotationUtils.getSentence(anaphora.getAntecedent().getBegin(), sentences).getCoveredText());
				}
				FN++;
				continue;
			}
			int npBegin = possibleAntecedent.getBegin();
			int npEnd = possibleAntecedent.getEnd();
			if(Parameters.exactBoundMatch){
				if(printChoices)
					System.out.println("-->Anaphora :" + anaphora.getCoveredText() + "   " + AnnotationUtils.getSentence(anaphora.getBegin(), sentences).getCoveredText());	
				if(goldAntecedentBegin == npBegin && goldAntecedentEnd == npEnd){
					if(printChoices)
						System.out.println("Gold detected (" + anaphora.getAntecedent().getCoveredText() + ")");
					TP++;
				} else {
					if(printChoices){
						System.out.println("!!Detected:" + possibleAntecedent.getCoveredText() );
						System.out.println("Antecedent " + anaphora.getAntecedent().getCoveredText() +
								"   " + AnnotationUtils.getSentence(anaphora.getAntecedent().getBegin(), sentences).getCoveredText());
					}
					FP++;
				}
			} else {
				if((goldAntecedentBegin >= npBegin && goldAntecedentEnd <= npEnd) || goldAntecedentBegin <= npBegin && goldAntecedentEnd >= npEnd){
					TP++;
				} else {
					FP++;	
				}
			}		
		}
		if(printChoices)
			System.out.println("TP : " + TP + "  FP : "+ FP + " FN : " + FN);
		bergsmaKeepThreshold[0] += TP;
		bergsmaKeepThreshold[2] += FP;
		bergsmaKeepThreshold[3] += FN;

	}
	
	public void evaluateBergsma_Baseline(Map<Anaphora, List<MyNP>> allNPs, boolean printChoices){
		if(bergsmaBaseline == null)
			bergsmaBaseline = new int[]{0,0,0,0};
		int TP = 0;
		int FP = 0;
		for(Anaphora anaphora : allNPs.keySet()){
			int goldAntecedentBegin = anaphora.getAntecedent().getBegin();
			int goldAntecedentEnd = anaphora.getAntecedent().getEnd();
			List<MyNP> candidates = new ArrayList<MyNP>();
			for(MyNP np : allNPs.get(anaphora)){
				candidates.add(np);				
			}
			MyNP nearest = null;
			int dist = Integer.MAX_VALUE;
			int size = Integer.MAX_VALUE;
			for(MyNP np : candidates){
				if(anaphora.getBegin() - np.getBegin() < dist){
					nearest = np;
					dist = anaphora.getBegin() - np.getBegin();
					size = np.getEnd() - np.getBegin();
				} else if((anaphora.getBegin() - np.getBegin() == dist) && (np.getEnd() - np.getBegin()) < size){
					nearest = np;
					dist = anaphora.getBegin() - np.getBegin();
					size = np.getEnd() - np.getBegin();
				}
			}
			if(nearest == null){
				System.out.println("No possible candidates!");
			}

			int npBegin = nearest.getBegin();
			int npEnd = nearest.getEnd();
			if(Parameters.exactBoundMatch){
				if(goldAntecedentBegin == npBegin && goldAntecedentEnd == npEnd){
					TP++;
				} else {
					FP++;
				}
			} else {
				if((goldAntecedentBegin >= npBegin && goldAntecedentEnd <= npEnd) || goldAntecedentBegin <= npBegin && goldAntecedentEnd >= npEnd){
					TP++;
				} else {
					FP++;	
				}
			}		
		}
		bergsmaBaseline[0] += TP;
		bergsmaBaseline[2] += FP;

	}
	

	public void evaluate_last_n_sentences(Map<Anaphora, List<MyNP>> allNPs, boolean printChoices){
		if(lastNSentences == null)
			lastNSentences = new int[]{0,0,0,0};
		int currentTN = 0;
		int currentTP = 0;
		int currentFP = 0;
		int currentFN = 0;
		for(Anaphora anaphora : allNPs.keySet()){
			int goldAntecedentBegin = anaphora.getAntecedent().getBegin();
			int goldAntecedentEnd = anaphora.getAntecedent().getEnd();
			if(printChoices){
				System.out.println("++++ANAPHORA:  " + anaphora.getCoveredText() + "    +++++++");
				System.out.println("++++GOLD ANTECEDENT:  " + anaphora.getAntecedent().getCoveredText() + "    +++++++");	
			}
			
			List<MyNP> sortedList = AnnotationUtils.sortMyNPList(allNPs.get(anaphora));
			
			for(MyNP np : sortedList){
				if(printChoices){
					System.out.println(np.getCoveredText() + "   [ " + np.getOutputValue() + " ]" );
				}
				int npBegin = np.getBegin();
				int npEnd = np.getEnd();
				
				boolean isGoldAnte = false;
				
				if(Parameters.exactBoundMatch){
					if(goldAntecedentBegin == npBegin && goldAntecedentEnd == npEnd){
						isGoldAnte = true;
					}
				} else {
					if((goldAntecedentBegin >= npBegin && goldAntecedentEnd <= npEnd) || goldAntecedentBegin <= npBegin && goldAntecedentEnd >= npEnd){
						isGoldAnte = true;
					}
				}
				
				if(isGoldAnte){
					if(np.getOutputValue() >= Parameters.acceptAtThreshold){
						currentTP++;
					} else {
						currentFN++;
					}
				} else {
					if(np.getOutputValue() < Parameters.acceptAtThreshold){
						currentTN++;
					} else {
						currentFP++;
					}
				}
			}
			
		}
		lastNSentences[0] += currentTP;
		lastNSentences[1] += currentTN;
		lastNSentences[2] += currentFP;
		lastNSentences[3] += currentFN;	
	}

	public void last_n_sentences_takeTheBest(Map<Anaphora, List<MyNP>> allNPs, boolean printChoices){
		if(lastNSentences_takeTheBest == null)
			lastNSentences_takeTheBest = new int[]{0,0,0,0};
		
		int currentTN = 0;
		int currentTP = 0;
		int currentFP = 0;
		int currentFN = 0;
		for(Anaphora anaphora : allNPs.keySet()){
			int goldAntecedentBegin = anaphora.getAntecedent().getBegin();
			int goldAntecedentEnd = anaphora.getAntecedent().getEnd();

			MyNP bestChoice = null;
			float maxOutputValue = Float.MIN_VALUE;
			for(MyNP np : allNPs.get(anaphora)){
				if(np.getOutputValue() > maxOutputValue){
					maxOutputValue = np.getOutputValue();
					bestChoice = np;
				}
			}
			for(MyNP np : allNPs.get(anaphora)){
				int npBegin = np.getBegin();
				int npEnd = np.getEnd();
				
				if(np == bestChoice){
					if((goldAntecedentBegin >= npBegin && goldAntecedentEnd <= npEnd) || goldAntecedentBegin <= npBegin && goldAntecedentEnd >= npEnd){
						currentTP++;
					} else{
						currentFP++;
					}
					
				}else {
					if((goldAntecedentBegin >= npBegin && goldAntecedentEnd <= npEnd) || goldAntecedentBegin <= npBegin && goldAntecedentEnd >= npEnd){
						currentFN++;
					} else {
						currentTN++;
					}
				}		
			}	
		}
		lastNSentences_takeTheBest[0] += currentTP;
		lastNSentences_takeTheBest[1] += currentTN;
		lastNSentences_takeTheBest[2] += currentFP;
		lastNSentences_takeTheBest[3] += currentFN;	
	}
	
	public void last_n_sentences_baseline(Map<Anaphora, List<MyNP>> allNPs, boolean printChoices){
		if(lastNSentences_baseline == null)
			lastNSentences_baseline = new int[]{0,0,0,0};
		
		int currentTN = 0;
		int currentTP = 0;
		int currentFP = 0;
		int currentFN = 0;		
		for(Anaphora anaphora : allNPs.keySet()){
			int goldAntecedentBegin = anaphora.getAntecedent().getBegin();
			int goldAntecedentEnd = anaphora.getAntecedent().getEnd();
			
			MyNP nearest = null;
			int dist = Integer.MAX_VALUE;
			for(MyNP np : allNPs.get(anaphora)){
				if(anaphora.getBegin() - np.getBegin() < dist){
					dist = anaphora.getBegin() - np.getBegin();
					nearest = np;
				}
			}
			
			for(MyNP np : allNPs.get(anaphora)){
				int npBegin = np.getBegin();
				int npEnd = np.getEnd();
				
				if(np == nearest){
					if((goldAntecedentBegin >= npBegin && goldAntecedentEnd <= npEnd) || goldAntecedentBegin <= npBegin && goldAntecedentEnd >= npEnd){
						currentTP++;
					} else{
						currentFP++;
					}
					
				}else {
					if((goldAntecedentBegin >= npBegin && goldAntecedentEnd <= npEnd) || goldAntecedentBegin <= npBegin && goldAntecedentEnd >= npEnd){
						currentFN++;
					} else {
						currentTN++;
					}
				}
			}
		}
		lastNSentences_baseline[0] += currentTP;
		lastNSentences_baseline[1] += currentTN;
		lastNSentences_baseline[2] += currentFP;
		lastNSentences_baseline[3] += currentFN;
	}
	
	public void evaluate_last_n_sentences_sameEntity(Map<Anaphora, List<MyNP>> allNPs,Collection<MyCoreferenceChain> corefChains, boolean printChoices){
		if(lastNSentences_sameEntity == null)
			lastNSentences_sameEntity = new int[]{0,0,0,0};
		
		List<Anaphora> anaphoras = new ArrayList<Anaphora>();
		for(Anaphora a :allNPs.keySet()){
			anaphoras.add(a);
		}
		List<MyCoreferenceChain> anaphoraChains = new ArrayList<MyCoreferenceChain>();
		for(int i = 0; i < anaphoras.size(); i++){
			int anaphoraBegin = anaphoras.get(i).getBegin();
			int anaphoraEnd = anaphoras.get(i).getEnd();	
			boolean foundIt = false;
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
						foundIt = true;
						break;
					}			
					corefLinkOld = corefLinkNew;
				}
				if(foundIt)
					break;
			}
		}
		
		int currentTN = 0;
		int currentTP = 0;
		int currentFP = 0;
		int currentFN = 0;
		for(int i = 0; i < anaphoras.size(); i++){			
			if(anaphoras.size() != anaphoraChains.size())
				System.out.println("Error here (1) ");
			for(MyNP np : allNPs.get(anaphoras.get(i))){
				int npBegin = np.getBegin();
				int npEnd = np.getEnd();
				
				MyCoreferenceChain anaphoraCorefChain = anaphoraChains.get(i);
				
				MyCoreferenceLink corefLinkOld = anaphoraCorefChain.getFirst();	
				boolean anteInChain = false;
				if(corefLinkOld.getBegin() <= npBegin && corefLinkOld.getEnd() >= npEnd){
					anteInChain = true;		
				}
				
				while(!anteInChain && corefLinkOld.getNext() != null){
					MyCoreferenceLink corefLinkNew = corefLinkOld.getNext();
					
					if(corefLinkNew.getBegin() <= npBegin && corefLinkNew.getEnd() >= npBegin){					
						anteInChain = true;
					}			
					corefLinkOld = corefLinkNew;
				}
						
				if(anteInChain){
					if(np.getOutputValue() >= Parameters.acceptAtThreshold){
						currentTP++;
					} else {
						currentFN++;
					}
				} else {
					if(np.getOutputValue() < Parameters.acceptAtThreshold){
						currentTN++;
					} else {
						currentFP++;
					}
				}
			}
		}
		lastNSentences_sameEntity[0] += currentTP;
		lastNSentences_sameEntity[1] += currentTN;
		lastNSentences_sameEntity[2] += currentFP;
		lastNSentences_sameEntity[3] += currentFN;
	}	

	
	public void printResults(){
		
		boolean isEmptyFile = false;
		try {
			BufferedReader br = new BufferedReader(new FileReader(accuracyOutputFile));     
			if (br.readLine() == null) {
			    isEmptyFile = true;
			}
			br.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if(PARAM_PRINTFILE){		
			FileWriter fw = null;
			BufferedWriter bw = null;
			PrintWriter out = null;
			try {
				fw = new FileWriter(accuracyOutputFile, true);
			    bw = new BufferedWriter(fw);
			    out = new PrintWriter(bw);			    
			    
			    if(isEmptyFile){
			    	createCaptions();
			    }
			    createOutput();
			    for(String s : output){
			    	out.println(s);
			    }
			    out.close();
			    bw.close();
			    fw.close();
			   	 
			} catch (IOException e) {
			    //exception handling left as an exercise for the reader
			}
			
		}
	}
	
	private void createOutput(){
		String values = "";
		if(bergsmaLowerThreshold != null){
			values += returnAllValues(bergsmaLowerThreshold);
		}
		if(bergsmaKeepThreshold != null){
			values += returnAllValues(bergsmaKeepThreshold);
		}
		if(bergsmaSameEntity != null){
			values += returnAllValues(bergsmaSameEntity);
		}
		
		if(bergsmaBaseline != null){
			values += returnAllValues(bergsmaBaseline);
		}
		if(lastNSentences != null){
			values += returnAllValues(lastNSentences);
		}
		if(lastNSentences_takeTheBest != null){
			values += returnAllValues(lastNSentences_takeTheBest);
		}
		if(lastNSentences_baseline != null){
			values += returnAllValues(lastNSentences_baseline);
		}
		if(lastNSentences_sameEntity != null){
			values += returnAllValues(lastNSentences_sameEntity);
		}

		output.add(values);	
	}
	
	private void createCaptions(){
		String caption = "";
		String semi = ";;;;;;;;;";
		String varNames = "";
		String template = "TP;TN;FP;FN;;;;;;";
		if(bergsmaLowerThreshold != null){
			caption += "bergsmaLowerThreshold" + semi;
			varNames += template;
		}
		if(bergsmaKeepThreshold != null){
			caption += "bergsmaKeepThreshold" + semi;
			varNames += template;
		}
		if(bergsmaSameEntity != null){
			caption += "bergsmaSameEntity" + semi;
			varNames += template;
		}
		if(bergsmaBaseline != null){
			caption += "bergsmaBaseline" + semi;
			varNames += template;
		}
		if(lastNSentences != null){
			caption += "lastNSentences" + semi;
			varNames += template;
		}
		if(lastNSentences_takeTheBest != null){
			caption += "lastNSentences_takeTheBest" + semi;
			varNames += template;
		}
		if(lastNSentences_baseline != null){
			caption += "lastNSentences_baseline" + semi;
			varNames += template;
		}
		if(lastNSentences_sameEntity != null){
			caption += "lastNSentences_sameEntity" + semi;
			varNames += template;
		}
		output.add(caption);
		output.add(varNames);
	}
	
	private String returnAllValues(int[] input){
		String returnString = "";
		returnString += input[0] + ";" + input[1] + ";"
						+ input[2] + ";" + input[3] + ";";
		return returnString + ";;;;;";
	}
	
	private float getFmeasure(int[] input){
		float precision = getPrecision(input);
		float recall = getRecall(input);
		float fMeasure = 0f;
		if(precision != 0f || recall != 0f){
			fMeasure = (2f * precision * recall) / (precision + recall);
		}
		return fMeasure;	
	}
	
	private float getPrecision(int[] input){
		int totalTPFN = input[0] + input[3];	
		float precision = 0f;
		if(totalTPFN != 0){
			precision = ((float)input[0] / (float)totalTPFN)*100f;
		}
		return precision;
	}
	
	private float getRecall(int[] input){
		int totalTPFP = input[0] + input[2];	
		float recall = 0f;
		if(totalTPFP != 0){
			recall = ((float)input[0] / (float)totalTPFP)*100f;
		}
		return recall;
	}

	private float getAccuracy(int[] input){
		int correct = input[0] + input[1];
		int total = input[0] + input[1] + input[2] + input[3];
		float accuracy = 0f;
		if(total != 0){
			accuracy = ((float)correct / (float)total) * 100f;
		}
		return accuracy;
	}
	
}
