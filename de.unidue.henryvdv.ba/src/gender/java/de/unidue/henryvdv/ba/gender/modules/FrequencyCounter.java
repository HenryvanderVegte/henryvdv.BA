package de.unidue.henryvdv.ba.gender.modules;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

public class FrequencyCounter extends JCasAnnotator_ImplBase {

	private ConditionalFrequencyDistribution<String, String> reflexiveFrequencies; 
	private ConditionalFrequencyDistribution<String, String> possessiveFrequencies; 
	private ConditionalFrequencyDistribution<String, String> nominativeFrequencies; 
	private ConditionalFrequencyDistribution<String, String> predicateFrequencies; 
	private ConditionalFrequencyDistribution<String, String> designatorFrequencies; 
	
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
	
	
	
	public void initialize(UimaContext context) throws ResourceInitializationException{
		super.initialize(context);
		reflexiveFrequencies = new ConditionalFrequencyDistribution<String, String>();
		possessiveFrequencies = new ConditionalFrequencyDistribution<String, String>();
		nominativeFrequencies = new ConditionalFrequencyDistribution<String, String>();
		predicateFrequencies = new ConditionalFrequencyDistribution<String, String>();
		designatorFrequencies = new ConditionalFrequencyDistribution<String, String>();
	}
	
	
	
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {		
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
				
				reflexiveFrequencies.addSample(tokens[i].getCoveredText().toLowerCase(), tokens[i-2].getCoveredText().toLowerCase(), 1);
				//System.out.println("Add Reflexive: " + reflexiveFrequencies.getN());
				continue;
			}
			//Possessives:
			if( i > 1 &&
					Arrays.asList(possessive).contains(tokens[i].getCoveredText().toLowerCase()) &&
					tokens[i-1] != null &&
					tokens[i-1].getPos().getPosValue().contains("VB") &&
					tokens[i-2] != null &&
					tokens[i-2].getPos().getPosValue().contains("NN")){
				
				possessiveFrequencies.addSample(tokens[i].getCoveredText().toLowerCase(), tokens[i-2].getCoveredText().toLowerCase(), 1);
				//System.out.println("Add Possessive: " + possessiveFrequencies.getN());
				continue;
			}
			//Nominatives:
			if( i > 1 &&
					Arrays.asList(nominative).contains(tokens[i].getCoveredText().toLowerCase()) &&
					tokens[i-1] != null &&
					tokens[i-1].getPos().getPosValue().contains("VB") &&
					tokens[i-2] != null &&
					tokens[i-2].getPos().getPosValue().contains("NN")){
				
				nominativeFrequencies.addSample(tokens[i].getCoveredText().toLowerCase(), tokens[i-2].getCoveredText().toLowerCase(), 1);
				//System.out.println("Add Nominative: " + nominativeFrequencies.getN());
				continue;
			}
			
			//Predicates:
			if( i < tokens.length - 3 &&
					Arrays.asList(nominative).contains(tokens[i].getCoveredText().toLowerCase())&&
					tokens[i+1] != null &&
						(tokens[i+1].getCoveredText().toLowerCase().equals("is") || 
						 tokens[i+1].getCoveredText().toLowerCase().equals("are"))){
				
				if(tokens[i+2].getPos().getPosValue().contains("NN")){
					predicateFrequencies.addSample(tokens[i].getCoveredText().toLowerCase(), tokens[i+2].getCoveredText().toLowerCase(), 1);
					//System.out.println("Add Predicate: " + predicateFrequencies.getN());
					continue;
				}
				
				if(tokens[i+2].getCoveredText().toLowerCase().equals("a") || 
						tokens[i+2].getCoveredText().toLowerCase().equals("an")){
					
					if(tokens[i+3].getPos().getPosValue().contains("NN")){
						predicateFrequencies.addSample(tokens[i].getCoveredText().toLowerCase(), tokens[i+3].getCoveredText().toLowerCase(), 1);
						//System.out.println("Add Predicate: " + predicateFrequencies.getN());
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
				designatorFrequencies.addSample(tokens[i].getCoveredText().toLowerCase(), tokens[i+1].getCoveredText().toLowerCase(), 1);
				//System.out.println("Add Designator: " + designatorFrequencies.getN());
				continue;
			}
		}	
		
		System.out.println("Designators: " + designatorFrequencies.getN());
		System.out.println("Nominatives: " + nominativeFrequencies.getN());
		System.out.println("Possessives: " + possessiveFrequencies.getN());
		System.out.println("Predicates:  " + predicateFrequencies.getN());
		System.out.println("Reflexives:  " + reflexiveFrequencies.getN());
		
	}

	
	@Override
	public void collectionProcessComplete(){
		System.out.println("write output");
		writeOutput();
	}
	
	private void writeOutput(){
		String designatorFilePath = OUTPUT_DIRECTORY + "/" + DESIGNATOR_NAME;
		String nominativeFilePath = OUTPUT_DIRECTORY + "/" + NOMINATIVE_NAME;
		String possessiveFilePath = OUTPUT_DIRECTORY + "/" + POSSESSIVE_NAME;
		String predicateFilePath  = OUTPUT_DIRECTORY + "/" + PREDICATE_NAME;
		String reflexiveFilePath  = OUTPUT_DIRECTORY + "/" + REFLEXIVE_NAME;
		
		File designatorFile = new File(designatorFilePath);
		File nominativeFile = new File(nominativeFilePath);
		File possessiveFile = new File(possessiveFilePath);
		File predicateFile  = new File(predicateFilePath);
		File reflexiveFile  = new File(reflexiveFilePath);
		
		
		designatorFile.delete();
		nominativeFile.delete();
		possessiveFile.delete();
		predicateFile.delete();
		reflexiveFile.delete();		
		try {
			designatorFile.getParentFile().mkdirs();
			designatorFile.createNewFile();
			nominativeFile.getParentFile().mkdirs();
			nominativeFile.createNewFile();
			possessiveFile.getParentFile().mkdirs();
			possessiveFile.createNewFile();
			predicateFile.getParentFile().mkdirs();
			predicateFile.createNewFile();
			reflexiveFile.getParentFile().mkdirs();
			reflexiveFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		BufferedWriter writer = null;
		try {
			//Designator:
            writer = new BufferedWriter(new FileWriter(designatorFile));
            for(String condition : designatorFrequencies.getConditions()){
    			for(String sample : designatorFrequencies.getFrequencyDistribution(condition).getKeys()){
    				String outputline = condition + ";" + sample + ";" + designatorFrequencies.getCount(condition, sample);				
    				writer.write(outputline);
    				writer.newLine();
    			}
    		}	
            try {
 			   writer.close();
 		   } catch (Exception e)
 		   {
 			   e.printStackTrace();
 		   }
		    //Nominative
            writer = new BufferedWriter(new FileWriter(nominativeFile));
            for(String condition : nominativeFrequencies.getConditions()){
    			for(String sample : nominativeFrequencies.getFrequencyDistribution(condition).getKeys()){
    				String outputline = condition + ";" + sample + ";" + nominativeFrequencies.getCount(condition, sample);				
    				writer.write(outputline);
    				writer.newLine();
    			}
    		}
            try {
 			   writer.close();
 		   } catch (Exception e)
 		   {
 			   e.printStackTrace();
 		   }
            //Possessive
            writer = new BufferedWriter(new FileWriter(possessiveFile));
            for(String condition : possessiveFrequencies.getConditions()){
    			for(String sample : possessiveFrequencies.getFrequencyDistribution(condition).getKeys()){
    				String outputline = condition + ";" + sample + ";" + possessiveFrequencies.getCount(condition, sample);				
    				writer.write(outputline);
    				writer.newLine();
    			}
    		}
            try {
 			   writer.close();
 		   } catch (Exception e)
 		   {
 			   e.printStackTrace();
 		   }
            //Predicate
            writer = new BufferedWriter(new FileWriter(predicateFile));
            for(String condition : predicateFrequencies.getConditions()){
    			for(String sample : predicateFrequencies.getFrequencyDistribution(condition).getKeys()){
    				String outputline = condition + ";" + sample + ";" + predicateFrequencies.getCount(condition, sample);				
    				writer.write(outputline);
    				writer.newLine();
    			}
    		}
            try {
 			   writer.close();
 		   } catch (Exception e)
 		   {
 			   e.printStackTrace();
 		   }
          //Reflexive
            writer = new BufferedWriter(new FileWriter(reflexiveFile));
            for(String condition : reflexiveFrequencies.getConditions()){
    			for(String sample : reflexiveFrequencies.getFrequencyDistribution(condition).getKeys()){
    				String outputline = condition + ";" + sample + ";" + reflexiveFrequencies.getCount(condition, sample);				
    				writer.write(outputline);
    				writer.newLine();
    			}
    		} 
            try {
  			   writer.close();
  		   } catch (Exception e)
  		   {
  			   e.printStackTrace();
  		   }
		} catch (IOException e) {
		  e.printStackTrace();
		} finally {
		   try {
			   writer.close();
		   } catch (Exception e)
		   {
			   e.printStackTrace();
		   }
		}		
	}
}
