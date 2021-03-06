package de.unidue.henryvdv.ba.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import de.tudarmstadt.ukp.dkpro.core.api.frequency.util.FrequencyDistribution;

/**
 * Prints out the mean values of all feature vectors received from train file. Useful to have a closer look on the data
 * (maybe for error detection)
 * @author Henry
 *
 */
public class SVMTrainEvaluator {

	/**
	 * SVM Training file
	 */
	private static final String TRAIN_NAME = "train.dat";
	
	/**
	 * SVM Training file direction
	 */
	private static final String TRAIN_DIRECTORY = "src/main/resources/svm/dat";
	
	/**
	 * Training file lines
	 */
	private List<String> train;
	
	public static void main(String[] args){
		SVMTrainEvaluator s = new SVMTrainEvaluator();
		s.evaluate();
	}
	
	/**
	 * Evaluate and print the results:
	 */
	public void evaluate(){
		File trainFile = new File(TRAIN_DIRECTORY + "/" + TRAIN_NAME);
		
		try {
			train = FileUtils.readLines(trainFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<Float> pos = new ArrayList<Float>();
		List<Float> neg = new ArrayList<Float>();
		
		int posSamples = 0;
		int negSamples = 0;
		int posMatches =0;
		int negMatches = 0;
		int gender = -1;
		
		for(String line : train){
			line += " ";
			if(line.charAt(0) == '#')
				continue;
			if(line.charAt(0) == '1'){
				posSamples++;
				int nr = 1;
				for(int i = 0; i < line.length(); i++){
					if(line.charAt(i) == ':'){
						if(pos.size() < nr){
							pos.add(0f);
						}

						for(int j = i; j < line.length();j++){
							if(line.charAt(j) == ' '){
								String value = line.substring(i + 1, j);
								pos.set(nr - 1, pos.get(nr - 1) + Float.parseFloat(value));
								break;
							}
						}
						nr++;
					}
				}
				
			}
			if(line.charAt(0) == '-'){
				negSamples++;
				int nr = 1;
				for(int i = 0; i < line.length(); i++){
					if(line.charAt(i) == ':'){
						if(neg.size() < nr){
							neg.add(0f);
						}
						
						for(int j = i; j < line.length();j++){
							if(line.charAt(j) == ' '){
								String value = line.substring(i + 1, j);
								neg.set(nr - 1, neg.get(nr - 1) + Float.parseFloat(value));
								break;
							}
						}
						nr++;
					}
				}
				
			}
		}
		System.out.println("POS: " + posSamples);
		for(int i = 0; i < pos.size(); i++){
			float f = pos.get(i) / (float)posSamples;
			//System.out.print((i+1) + ":" + pos.get(i) + " ");
			System.out.print((i+1) + ":" + f + " ");
		}
		System.out.println("");
		System.out.println("NEG: " + negSamples);
		for(int i = 0; i < neg.size(); i++){
			float f = neg.get(i) / (float)negSamples;
			//System.out.print((i+1) + ":" + neg.get(i) + " ");
			System.out.print((i+1) + ":" + f + " ");
		}
		System.out.println("");
		System.out.println("DIFF: ");
		
		for(int i = 0; i < neg.size(); i++){
			float f = neg.get(i) / (float)negSamples;
			float g = pos.get(i) / (float)posSamples;
			float diff = 0f;
			if(f <= g){
				diff = g / f;
			} else {
				diff = f / g;
			}

			//System.out.print((i+1) + ":" + neg.get(i) + " ");
			System.out.print((i+1) + ":" + diff + " ");
		}
		
		System.out.println("");
		for(int i = 0; i < neg.size(); i++){
			float f = neg.get(i) / (float)negSamples;
			float g = pos.get(i) / (float)posSamples;
			float diff = 0f;
			if(f <= g){
				diff = g / f;
			} else {
				diff = f / g;
			}
			if(diff > 1.5f){
				System.out.print(i+1 + ", ");
			}

		}
		
	}
	
}
