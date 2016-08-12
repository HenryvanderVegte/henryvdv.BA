package de.unidue.henryvdv.ba.modules;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import org.dkpro.tc.core.Constants;


public class SVMClassify implements Constants {

	private static final String BINARIES_BASE_LOCATION = "src/main/resources/svm/bin";

	// where the trained model is stored
	private static final String MODEL_NAME = "svm_light.model";

	private static final String MODEL_DIRECTORY = "src/main/resources/svm/dat";
	
	private static final String TEST_NAME = "test.dat";

	private static final String TEST_DIRECTORY = "src/main/resources/svm/dat";
	
	private static final String PREDICTION_NAME = "output.dat";

	private static final String PREDICTION_DIRECTORY = "src/main/resources/svm/dat";
	
	public void classify() throws IOException {
		
		String modelFilePath = MODEL_DIRECTORY + "/" + MODEL_NAME;
		File modelFile = new File(modelFilePath);
		
		String testFilePath = TEST_DIRECTORY + "/" + TEST_NAME;
		File testFile = new File(testFilePath);
	
		
		if(!modelFile.isFile() || !testFile.isFile()){
			System.out.println("Cant read file.");
			throw new IOException();
		}		
		
		String outputFilePath = PREDICTION_DIRECTORY + "/" + PREDICTION_NAME;
		File outputFile = new File(outputFilePath);
		
		if(outputFile.isFile()){
			outputFile.delete();
		}			
		try {
			outputFile.getParentFile().mkdirs();
			outputFile.createNewFile();
		} catch (IOException e) {
			System.out.println("Error while creating the file.");
			e.printStackTrace();
		}
		
		List<String> command = buildTrainCommand(testFile, modelFile, outputFile);
		System.out.println(command);
		
		try {
			runCommand(command);
		} catch (Exception e) {
			System.out.println("Error occured while creating the modelfile.");
			e.printStackTrace();
		}
	}
	
	private List<String> buildTrainCommand(File testFile, File modelFile, File outputFile) {
		List<String> result = new ArrayList<String>();
		result.add(BINARIES_BASE_LOCATION + "/svm_classify.exe");

		
		// test file
		result.add(testFile.getAbsolutePath());

		// model file
		result.add(modelFile.getAbsolutePath());
		
		// output file
		result.add(outputFile.getAbsolutePath());

		return result;
	}

	
	private static void runCommand(List<String> command) throws Exception {

		Process process = new ProcessBuilder().inheritIO().command(command).start();
		process.waitFor();
	}


}
