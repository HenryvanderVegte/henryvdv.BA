package de.unidue.henryvdv.ba.gender.modules;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import de.tudarmstadt.ukp.dkpro.core.api.frequency.util.FrequencyDistribution;
import de.tudarmstadt.ukp.dkpro.core.api.frequency.util.ConditionalFrequencyDistribution;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.unidue.henryvdv.ba.gender.param.GenderParam;
import de.unidue.henryvdv.ba.type.DocumentInfo;

public class FrequencyCounter2 extends JCasAnnotator_ImplBase {

	private List<String> reflexiveFrequencies; 
	private List<String> possessiveFrequencies; 
	private List<String> nominativeFrequencies; 
	private List<String> predicateFrequencies; 
	private List<String> designatorFrequencies; 
	
	private String[] reflexive = GenderParam.reflexive;
	private String[] possessive = GenderParam.possessive;
	private String[] nominative = GenderParam.nominative;
	private String[] designators = GenderParam.designators;
	
	private static final String OUTPUT_DIRECTORY = "src/gender/resources/output";
	
	private static final String DESIGNATOR_NAME = "designatorFrequencies.txt";
	private static final String NOMINATIVE_NAME = "nominativeFrequencies.txt";
	private static final String POSSESSIVE_NAME = "possessiveFrequencies.txt";
	private static final String PREDICATE_NAME = "predicateFrequencies.txt";
	private static final String REFLEXIVE_NAME = "reflexiveFrequencies.txt";
	
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {		
		reflexiveFrequencies = new ArrayList<String>();
		possessiveFrequencies = new ArrayList<String>();
		nominativeFrequencies = new ArrayList<String>();
		predicateFrequencies = new ArrayList<String>();
		designatorFrequencies = new ArrayList<String>();
		Collection<Token> tokensColl = JCasUtil.select(aJCas, Token.class);	
		
		Token[] tokens = new Token[tokensColl.size()];
		int c = 0;
		for(Token t : tokensColl){
			tokens[c] = t;
			c++;
		}
		
		for(int i = 0; i < tokens.length; i++){
			//Reflexives:
			if( i > 1 &&
					Arrays.asList(reflexive).contains(tokens[i].getCoveredText().toLowerCase()) &&
					tokens[i-1] != null &&
					tokens[i-1].getPos().getPosValue().contains("VB") &&
					tokens[i-2] != null &&
					tokens[i-2].getPos().getPosValue().contains("NN")){
				
				reflexiveFrequencies.add(tokens[i].getCoveredText().toLowerCase() + "  " + tokens[i-2].getCoveredText().toLowerCase());
				continue;
			}
			//Possessives:
			if( i > 1 &&
					Arrays.asList(possessive).contains(tokens[i].getCoveredText().toLowerCase()) &&
					tokens[i-1] != null &&
					tokens[i-1].getPos().getPosValue().contains("VB") &&
					tokens[i-2] != null &&
					tokens[i-2].getPos().getPosValue().contains("NN")){
				
				
				possessiveFrequencies.add(tokens[i].getCoveredText().toLowerCase() + "  " + tokens[i-2].getCoveredText().toLowerCase());
				continue;
			}
			//Nominatives:
			if( i > 1 &&
					Arrays.asList(nominative).contains(tokens[i].getCoveredText().toLowerCase()) &&
					tokens[i-1] != null &&
					tokens[i-1].getPos().getPosValue().contains("VB") &&
					tokens[i-2] != null &&
					tokens[i-2].getPos().getPosValue().contains("NN")){
				
				nominativeFrequencies.add(tokens[i].getCoveredText().toLowerCase() + "  " + tokens[i-2].getCoveredText().toLowerCase());

				continue;
			}
			
			//Predicates:
			if( i < tokens.length - 3 &&
					Arrays.asList(nominative).contains(tokens[i].getCoveredText().toLowerCase())&&
					tokens[i+1] != null &&
						(tokens[i+1].getCoveredText().toLowerCase().equals("is") || 
						 tokens[i+1].getCoveredText().toLowerCase().equals("are"))){
				
				if(tokens[i+2].getPos().getPosValue().contains("NN")){
					predicateFrequencies.add(tokens[i].getCoveredText().toLowerCase() + "  " + tokens[i+2].getCoveredText().toLowerCase());				
					continue;
				}
				
				if(tokens[i+2].getCoveredText().toLowerCase().equals("a") || 
						tokens[i+2].getCoveredText().toLowerCase().equals("an")){
					
					if(tokens[i+3].getPos().getPosValue().contains("NN")){
						predicateFrequencies.add(tokens[i].getCoveredText().toLowerCase() + "  " + tokens[i+3].getCoveredText().toLowerCase());
						continue;
					}				
				}	
			}
			//Designators:
			if(i < tokens.length - 1 &&
					Arrays.asList(designators).contains(tokens[i].getCoveredText().toLowerCase()) &&
					tokens[i+1] != null &&
					tokens[i+1].getPos().getPosValue().contains("NN")
					){
				designatorFrequencies.add(tokens[i].getCoveredText().toLowerCase() + "  " + tokens[i+1].getCoveredText().toLowerCase());
				continue;
			}
		}	
		
		System.out.println("Designators: " + designatorFrequencies.size());
		System.out.println("Nominatives: " + nominativeFrequencies.size());
		System.out.println("Possessives: " + possessiveFrequencies.size());
		System.out.println("Predicates:  " + predicateFrequencies.size());
		System.out.println("Reflexives:  " + reflexiveFrequencies.size());
		
		writeOutput();
	}

	/*
	@Override
	public void collectionProcessComplete(){
		System.out.print("Write to files");
		writeOutput();
		System.out.print("Finished writing");
	} */
	
	private void writeOutput(){
		String designatorFilePath = OUTPUT_DIRECTORY + "/" + DESIGNATOR_NAME;
		String nominativeFilePath = OUTPUT_DIRECTORY + "/" + NOMINATIVE_NAME;
		String possessiveFilePath = OUTPUT_DIRECTORY + "/" + POSSESSIVE_NAME;
		String predicateFilePath  = OUTPUT_DIRECTORY + "/" + PREDICATE_NAME;
		String reflexiveFilePath  = OUTPUT_DIRECTORY + "/" + REFLEXIVE_NAME;	
		
		//Designator
		try {
		    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(designatorFilePath, true)));
		    for(String s : designatorFrequencies){
			    out.println(s);
		    }
		    out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//Nominative
		try {
		    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(nominativeFilePath, true)));
		    for(String s : nominativeFrequencies){
			    out.println(s);
		    }
		    out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Possessive
		try {
		    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(possessiveFilePath, true)));
		    for(String s : possessiveFrequencies){
			    out.println(s);
		    }
		    out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Predicate
		try {
		    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(predicateFilePath, true)));
		    for(String s : predicateFrequencies){
			    out.println(s);
		    }
		    out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Reflexive
		try {
		    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(reflexiveFilePath, true)));
		    for(String s : reflexiveFrequencies){
			    out.println(s);
		    }
		    out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
