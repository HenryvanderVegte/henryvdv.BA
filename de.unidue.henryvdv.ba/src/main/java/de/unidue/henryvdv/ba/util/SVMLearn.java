package de.unidue.henryvdv.ba.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.dkpro.lab.engine.TaskContext;
import org.dkpro.lab.task.Discriminator;
import org.dkpro.lab.task.impl.ExecutableTaskBase;
import org.dkpro.tc.core.Constants;

import de.tudarmstadt.ukp.dkpro.core.api.resources.RuntimeProvider;

/** 
 * 	Use this class in order to execute the SVM-learn-process
 *  The required information will be derived from the train.dat file
 *  (containing all positive and negative feature vectors).
 *   The modelfile is the output
 *   
 * @author Henry
 */
public class SVMLearn implements Constants {

	/**
	 * The direction of all svm data
	 */
	private static final String BINARIES_BASE_LOCATION = "src/main/resources/svm/bin";

	/**
	 * Learning mode discriminators; Only Constants.LM_SINGLE_LABEL is allowed
	 */
	@Discriminator
	private String learningMode = LM_SINGLE_LABEL;

	/**
	 *  Parameter:
	 *  -z {c,r,p}  - select between classification (c), regression (r), and 
     *                 preference ranking (p) (see [Joachims, 2002c])
     *                 (default classification) 
	 */
	public static final String PARAM_Z = "paramZ";
	@Discriminator
	private char paramZ = 'c';

	/**
	 * Parameter 
	 *  -j float    - Cost: cost-factor, by which training errors on
     *                positive examples outweight errors on negative
     *                examples (default 1) 
	 */
	public static final String PARAM_J = "paramJ";
	@Discriminator
	private double paramJ = 1.0;
	
	
	/**
	 * Parameter 
	 *   -b [0,1]    - use biased hyperplane (i.e. x*w+b0) instead
     *                 of unbiased hyperplane (i.e. x*w0) (default 1)              
	 */
	public static final String PARAM_B = "paramB";
	@Discriminator
	private int paramB = 1;
	
	
	/**
	 * Parameter 
	 *  -i [0,1]    - remove inconsistent training examples
     *                and retrain (default 0))                              
	 */
	public static final String PARAM_I = "paramI";
	@Discriminator
	private int paramI = 0;
	
	
	/**
	 * Parameter 
	 *   -x [0,1]    - compute leave-one-out estimates (default 0)                          
	 */
	public static final String PARAM_X = "paramX";
	@Discriminator
	private int paramX = 0;
	
	/**
	 * Parameter 
     *    -o [0..2]  - value of rho for XiAlpha-estimator and for pruning
     *                 leave-one-out computation (default 1.0)                        
	 */
	public static final String PARAM_O = "paramO";
	@Discriminator
	private double paramO = 1.0;
	
	
	/**
	 * Parameter 
	 *    -k [0..100] - search depth for extended XiAlpha-estimator
     *                  (default 0)                      
	 */
	public static final String PARAM_K = "paramK";
	@Discriminator
	private double paramK = 0.0;
	
	
	/**
	 * Parameter 
	 *   -m [5..]    - size of cache for kernel evaluations in MB (default 40)
     *                  The larger the faster                
	 */
	public static final String PARAM_M = "paramM";
	@Discriminator
	private double paramM = 40.0;

	/**
	 * The name of the modelfile
	 */
	private static final String MODEL_NAME = "svm_light.model";
	/**
	 * The direction of the modelfile
	 */
	private static final String MODEL_DIRECTORY = "src/main/resources/svm/dat";
	
	/**
	 * The name of the trainfile
	 */
	private static final String TRAIN_NAME = "train.dat";
	/**
	 * The direction of the trainfile
	 */
	private static final String TRAIN_DIRECTORY = "src/main/resources/svm/dat";
	
	/**
	 * Runs the svm_learn.exe - 
	 * The input is the train-file (train.dat)
	 * The output is the modelfile (svm_light.model)
	 */
	public void learn(){	
		String modelFilePath = MODEL_DIRECTORY + "/" + MODEL_NAME;
		File modelFile = new File(modelFilePath);	
		if(modelFile.isFile()){
			modelFile.delete();
		}			
		try {
			modelFile.getParentFile().mkdirs();
			modelFile.createNewFile();
		} catch (IOException e) {
			System.out.println("Error while creating the file.");
			e.printStackTrace();
		}
		
		File trainFile = new File(TRAIN_DIRECTORY + "/" + TRAIN_NAME);
		
		List<String> command = buildTrainCommand(trainFile, modelFile.getAbsolutePath());
		System.out.println(command);
		
		try {
			runCommand(command);
		} catch (Exception e) {
			System.out.println("Error occured while creating the modelfile.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns the command with all parameters that svmlight requires
	 */
	private List<String> buildTrainCommand(File trainingFile, String targetModelLocation) {
		List<String> result = new ArrayList<String>();
		result.add(BINARIES_BASE_LOCATION + "/svm_learn.exe");

		// svm light struct params
		result.add("-z");
		result.add(paramZ + "");
		result.add("-j");
		result.add(String.format(Locale.ENGLISH, "%f", this.paramJ));
		result.add("-b");
		result.add(Integer.toString(paramB));
		result.add("-i");
		result.add(Integer.toString(paramI));
		result.add("-x");
		result.add(Integer.toString(paramX));
		result.add("-o");
		result.add(String.format(Locale.ENGLISH, "%f", this.paramO));
		result.add("-k");
		result.add(String.format(Locale.ENGLISH, "%f", this.paramK));
		result.add("-m");
		result.add(String.format(Locale.ENGLISH, "%f", this.paramM));
		
		// training file
		result.add(trainingFile.getAbsolutePath());

		// output model
		result.add(targetModelLocation);

		return result;
	}

	/**
	 * Executes the learning process
	 * @param command the whole command containing directions and parameters
	 * @throws Exception
	 */
	private static void runCommand(List<String> command) throws Exception {	
		Process process = new ProcessBuilder().inheritIO().command(command).start();
		process.waitFor();
	}


}
