package de.unidue.henryvdv.ba.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

/**
 * Class that reads all Vectors from a .dat-file
 * and writes the required ones to the train-file
 * (so that the same feature vectors don't need to be created
 * every time the pipeline is executed)
 * @author Henry
 *
 */
public class SavedVectorWriter {

	/**
	 * Folder of all vectors
	 */
	private static final String INPUT_FOLDER= "src/main/resources/exportVectors";
	
	/**
	 * File storing all vectors
	 */
	private static final String INPUT_FILE = "current.dat";
	
	/**
	 * train.dat folder
	 */
	private static final String OUTPUT_FOLDER= "src/main/resources/svm/dat";
	
	/**
	 * train.dat file
	 */
	private static final String OUTPUT_FILE = "train.dat";
	
	private List<String> inputLines;
	private List<String> posUsedVectors;
	private List<String> negUsedVectors;
	
	private File exportFile;
	
	/**
	 * Constructor for the Writer
	 * @param usedDocs Integer[] with all document numbers the system should be trained on
	 */
	public SavedVectorWriter(Integer[] usedDocs){
		File inputFile = new File(INPUT_FOLDER + "/" + INPUT_FILE);
		exportFile = new File(OUTPUT_FOLDER + "/" + OUTPUT_FILE);
		posUsedVectors = new ArrayList<String>();
		negUsedVectors = new ArrayList<String>();
		try {
			inputLines = FileUtils.readLines(inputFile);
		} catch (IOException e) {
			System.out.println("Failed to read input file");
			e.printStackTrace();
		}
		
		for(int i = 0; i < usedDocs.length; i++){
			saveUsedVectors(usedDocs[i]);
		}
		
	}
    
	/**
	 * Stores all vectors (positive as well as negative) of a given document
	 * to a list 
	 * @param documentNr 
	 */
	public void saveUsedVectors(int documentNr){
		for(int i = 1; i < inputLines.size(); i++){
			if(inputLines.get(i).length() == 0)
				continue;
			
			if(documentNr == getDocNumber(inputLines.get(i))){
				String line = inputLines.get(i);
				line = line.substring(String.valueOf(documentNr).length() + 1, line.length());
				if(line.charAt(0) == '-'){
					negUsedVectors.add(line);
				} else {
					posUsedVectors.add(line);
				}
			}
			
			
		}
	}
	
	/**
	 * Returns the document number of a feature vector
	 * (since the stored vectors are sorted by their document number)
	 * @param line
	 * @return
	 */
	private int getDocNumber(String line){
		int breakAt = 0;
		for(int i = 0; i < line.length(); i++){
			if(line.charAt(i) == ';'){
				breakAt = i;
				break;
			}
		}
		String nr = line.substring(0,breakAt);
		int returnValue = -1;
		returnValue = Integer.parseInt(nr);
		return returnValue;
	}
	
	/**
	 * Writes all train vectors to train file
	 */
	public void write(){
		if(exportFile.isFile()){
			exportFile.delete();
		}		
		
		try {
			exportFile.getParentFile().mkdirs();
			exportFile.createNewFile();
		} catch (IOException e) {
			System.out.println("Error while creating the file.");
			e.printStackTrace();
		}
		
		BufferedWriter writer = null;
		try {
            writer = new BufferedWriter(new FileWriter(exportFile));
		    writer.write("# svm train \"WikiCoref\" (training examples: " + posUsedVectors.size() + 
		    				" positive / " + negUsedVectors.size() + " negative)");
		    
		    
		    for(int i = 0; i < posUsedVectors.size(); i++){
		    	writer.newLine();
		    	writer.write(posUsedVectors.get(i));
		    }

		    for(int i = 0; i < negUsedVectors.size(); i++){
		    	writer.newLine();
		    	writer.write(negUsedVectors.get(i));
		    }
		    writer.newLine();
		    
		} catch (IOException e) {
		  System.out.println("Failed to write feature vectors to train file.");
		  e.printStackTrace();
		} finally {
		   try {
			   writer.close();
		   } catch (Exception e)
		   {
			   System.out.println("Failed to close writer.");
			   e.printStackTrace();
		   }
		}
		
	}
	/**
	 * Useful to see how many percent of all used vectors achieve the best result
	 * @param percent percentage of used vectors
	 */
	public void write(int percent){
		if(exportFile.isFile()){
			exportFile.delete();
		}		
		
		int posStop = (int)((float)posUsedVectors.size() * ((float)percent/100));
		int negStop = (int)((float)negUsedVectors.size() * ((float)percent/100));
		System.out.println("** pos: " + posStop + " of " + posUsedVectors.size());
		System.out.println("** neg: " + negStop + " of " + negUsedVectors.size());
		try {
			exportFile.getParentFile().mkdirs();
			exportFile.createNewFile();
		} catch (IOException e) {
			System.out.println("Error while creating the file.");
			e.printStackTrace();
		}
		
		BufferedWriter writer = null;
		try {
            writer = new BufferedWriter(new FileWriter(exportFile));
		    writer.write("# svm train \"WikiCoref\" (training examples: " + posUsedVectors.size() + 
		    				" positive / " + negUsedVectors.size() + " negative)");
		    
		    
		    for(int i = 0; i < posStop; i++){
		    	writer.newLine();
		    	writer.write(posUsedVectors.get(i));
		    }

		    for(int i = 0; i < negStop; i++){
		    	writer.newLine();
		    	writer.write(negUsedVectors.get(i));
		    }
		    writer.newLine();
		    
		} catch (IOException e) {
		  System.out.println("Failed to write feature vectors to train file.");
		  e.printStackTrace();
		} finally {
		   try {
			   writer.close();
		   } catch (Exception e)
		   {
			   System.out.println("Failed to close writer.");
			   e.printStackTrace();
		   }
		}
		
	}
	
}
