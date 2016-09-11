package de.unidue.henryvdv.ba.modules;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import de.unidue.henryvdv.ba.param.Parameters;
import de.unidue.henryvdv.ba.type.Anaphora;
import de.unidue.henryvdv.ba.type.GenderFeatures;
import de.unidue.henryvdv.ba.type.PronounFeatures;
import de.unidue.henryvdv.ba.util.FeatureUtils_Gender;

public class FeatureAnnotator_Gender extends JCasAnnotator_ImplBase {
    
	private JCas aJCas;
	private Collection<Anaphora> anaphoras;
	private FeatureUtils_Gender gUtil;
	private Map<String, Integer[]> corpusFrequencies = new HashMap<String, Integer[]>();


	public void initialize(UimaContext context) throws ResourceInitializationException{
		super.initialize(context);
		readCorpus();
	}
	
	private void readCorpus(){
		File folder = new File(Parameters.genderCorpusDirectory);
	    File[] files = folder.listFiles();
	    for(File f : files){
	    	List<String> inputLines;
	    	try {
				inputLines = FileUtils.readLines(f);	
				readCorpusLines(inputLines);
			} catch (IOException e) {
				e.printStackTrace();
			}	    	
	    }
	}
	
	// reads all lines in a document and puts the frequencies in the map
	private void readCorpusLines(List<String> inputLines){
		for(String s : inputLines){
			String word = "";
			String freq = "";
			for(int i = 0; i < s.length(); i++){
				if(s.charAt(i) == '\t'){
					word = s.substring(0, i);
					freq = s.substring(i + 1, s.length());
					break;
				}
			}
			Integer[] frequencies = new Integer[4];
			int startAt = 0;
			int arrayNr = 0;
			freq = freq + " ";
			for(int i = 0; i < freq.length(); i++){
				if(!Character.isDigit(freq.charAt(i))){
					String stringValue = freq.substring(startAt, i);
					frequencies[arrayNr] = Integer.parseInt(stringValue);
					startAt = i + 1;
					arrayNr++;
				}
			}
			corpusFrequencies.put(word, frequencies);
		}
	}
	
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		this.aJCas = aJCas;
		anaphoras = JCasUtil.select(aJCas, Anaphora.class);
		gUtil = new FeatureUtils_Gender(aJCas, corpusFrequencies);
		
		for(Anaphora anaphora : anaphoras){
			GenderFeatures genderFeatures = new GenderFeatures(aJCas);
			anaphora.setGenderFeatures(genderFeatures);
			gUtil.annotateFeatures(anaphora);
		}
	}
}
