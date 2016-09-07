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

public class FrequencyCounter extends JCasAnnotator_ImplBase {

	private ConditionalFrequencyDistribution<String, String> reflexiveFrequencies; 
	private ConditionalFrequencyDistribution<String, String> possessiveFrequencies; 
	private ConditionalFrequencyDistribution<String, String> nominativeFrequencies; 
	private ConditionalFrequencyDistribution<String, String> predicateFrequencies; 
	private ConditionalFrequencyDistribution<String, String> designatorFrequencies; 
	
	private String[] reflexive = {"himself","herself","itself","themselves"};
	private String[] possessive = {"his","her","its","their"};
	private String[] nominative = {"he","she","it","they"};
	private String[] designators = {"mr","mrs","miss","lady","lord","mr.","mrs.","mister",};
	
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
		Collection<Sentence> sentences = JCasUtil.select(aJCas, Sentence.class);
		
		List<Token> tokens = new ArrayList(tokensColl);
		
		for(int i = 0; i < tokens.size(); i++){
			//Reflexives:
			if( i > 1 &&
					Arrays.asList(reflexive).contains(tokens.get(i).getCoveredText().toLowerCase()) &&
					tokens.get(i-1) != null &&
					tokens.get(i-1).getPos().getPosValue().contains("VB") &&
					tokens.get(i-2) != null &&
					tokens.get(i-2).getPos().getPosValue().contains("NN")){
				
				reflexiveFrequencies.addSample(tokens.get(i).getCoveredText().toLowerCase(), tokens.get(i-2).getCoveredText().toLowerCase(), 1);
				continue;
			}
			//Possessives:
			if( i > 1 &&
					Arrays.asList(possessive).contains(tokens.get(i).getCoveredText().toLowerCase()) &&
					tokens.get(i-1) != null &&
					tokens.get(i-1).getPos().getPosValue().contains("VB") &&
					tokens.get(i-2) != null &&
					tokens.get(i-2).getPos().getPosValue().contains("NN")){
				
				possessiveFrequencies.addSample(tokens.get(i).getCoveredText().toLowerCase(), tokens.get(i-2).getCoveredText().toLowerCase(), 1);
				continue;
			}
			//Nominatives:
			if( i > 1 &&
					Arrays.asList(nominative).contains(tokens.get(i).getCoveredText().toLowerCase()) &&
					tokens.get(i-1) != null &&
					tokens.get(i-1).getPos().getPosValue().contains("VB") &&
					tokens.get(i-2) != null &&
					tokens.get(i-2).getPos().getPosValue().contains("NN")){
				
				possessiveFrequencies.addSample(tokens.get(i).getCoveredText().toLowerCase(), tokens.get(i-2).getCoveredText().toLowerCase(), 1);
				continue;
			}
			
			//Predicates:
			if( i < tokens.size() - 3 &&
					Arrays.asList(nominative).contains(tokens.get(i).getCoveredText().toLowerCase())&&
					tokens.get(i+1) != null &&
						(tokens.get(i+1).getCoveredText().toLowerCase().equals("is") || 
						 tokens.get(i+1).getCoveredText().toLowerCase().equals("are"))){
				
				if(tokens.get(i+2).getPos().getPosValue().contains("NN")){
					predicateFrequencies.addSample(tokens.get(i).getCoveredText().toLowerCase(), tokens.get(i+2).getCoveredText().toLowerCase(), 1);
					continue;
				}
				
				if(tokens.get(i+2).getCoveredText().toLowerCase().equals("a") || 
						tokens.get(i+2).getCoveredText().toLowerCase().equals("an")){
					
					if(tokens.get(i+3).getPos().getPosValue().contains("NN")){
						predicateFrequencies.addSample(tokens.get(i).getCoveredText().toLowerCase(), tokens.get(i+3).getCoveredText().toLowerCase(), 1);
						continue;
					}				
				}
				//Designators:
				if(i < tokens.size() - 1 &&
						Arrays.asList(designators).contains(tokens.get(i).getCoveredText().toLowerCase()) &&
						tokens.get(i+1) != null &&
						tokens.get(i+1).getPos().getPosValue().contains("NN")
						){
					designatorFrequencies.addSample(tokens.get(i).getCoveredText().toLowerCase(), tokens.get(i+1).getCoveredText().toLowerCase(), 1);
				}
				
			}	
		}	
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
