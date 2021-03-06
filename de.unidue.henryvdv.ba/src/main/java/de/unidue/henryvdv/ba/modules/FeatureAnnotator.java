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
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import de.unidue.henryvdv.ba.param.Parameters;
import de.unidue.henryvdv.ba.type.Anaphora;
import de.unidue.henryvdv.ba.type.AntecedentFeatures;
import de.unidue.henryvdv.ba.type.GenderFeatures;
import de.unidue.henryvdv.ba.type.PronounAntecedentFeatures;
import de.unidue.henryvdv.ba.type.PronounFeatures;
import de.unidue.henryvdv.ba.type.Quotation;
import de.unidue.henryvdv.ba.util.FeatureUtils_Antecedent;
import de.unidue.henryvdv.ba.util.FeatureUtils_Gender;
import de.unidue.henryvdv.ba.util.FeatureUtils_Pronoun;
import de.unidue.henryvdv.ba.util.FeatureUtils_PronounAntecedent;

/**
 * Annotates for all anaphoras all features
 * @author Henry
 *
 */
@TypeCapability(
        inputs = {
                "de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token",
                "de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence",
                "de.tudarmstadt.ukp.dkpro.core.api.syntax.type.dependency.Dependency",
                "de.tudarmstadt.ukp.dkpro.core.api.ner.type.NamedEntity",
                "de.tudarmstadt.ukp.dkpro.core.api.syntax.type.constituent.Constituent",
                "de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.POS",
                "de.unidue.henryvdv.ba.type.Anaphora"},
        outputs = {
                "de.unidue.henryvdv.ba.util.FeatureUtils_Antecedent",
                "de.unidue.henryvdv.ba.util.FeatureUtils_Gender",
                "de.unidue.henryvdv.ba.util.FeatureUtils_Pronoun",
                "de.unidue.henryvdv.ba.util.FeatureUtils_PronounAntecedent"})
public class FeatureAnnotator  extends JCasAnnotator_ImplBase {
	
	/**
	 * All anaphoras
	 */
	private Collection<Anaphora> anaphoras;
	
	/**
	 * All utils to annotate the features
	 */
	private FeatureUtils_Antecedent antecedentUtil;
	private FeatureUtils_Pronoun pronounUtil;
	private FeatureUtils_PronounAntecedent pronounAntecedentUtil;
	private FeatureUtils_Gender genderUtil;
	/**
	 * A map with the gender frequencies. The String represents the noun (e.g. "John").
	 * The Integer array represents its values
	 * [0] = masc. frequency, [1] = fem. frequency, [2] = neutral frequency, [3] plural frequency
	 */
	private Map<String, Integer[]> corpusFrequencies = new HashMap<String, Integer[]>();
	
	/**
	 * Reads the corpus first
	 */
	public void initialize(UimaContext context) throws ResourceInitializationException{
		super.initialize(context);
		readCorpus();
	}
	
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		anaphoras = JCasUtil.select(aJCas, Anaphora.class);
		
		//All utilities that are needed to annotate the features
		antecedentUtil = new FeatureUtils_Antecedent(aJCas);
		pronounUtil = new FeatureUtils_Pronoun();
		pronounAntecedentUtil = new FeatureUtils_PronounAntecedent(aJCas);
		genderUtil = new FeatureUtils_Gender(aJCas, corpusFrequencies);
		
		for(Anaphora anaphora : anaphoras){
			//Annotate Antecedent Features:
			AntecedentFeatures antecedenFeatures = new AntecedentFeatures(aJCas);
			anaphora.setAntecedentFeatures(antecedenFeatures);		
			antecedentUtil.annotateFeatures(anaphora);
			//Annotate Pronoun Features:
			PronounFeatures pronounFeatures = new PronounFeatures(aJCas);
			anaphora.setPronounFeatures(pronounFeatures);
			pronounUtil.annotateFeatures(anaphora);
			//Annotate Gender Features:
			GenderFeatures genderFeatures = new GenderFeatures(aJCas);
			anaphora.setGenderFeatures(genderFeatures);
			genderUtil.annotateFeatures(anaphora);;
			//Annotate Pronoun-Antecedent Features:
			PronounAntecedentFeatures a = new PronounAntecedentFeatures(aJCas);
			anaphora.setPronounAntecedentFeatures(a);
			pronounAntecedentUtil.annotateFeatures(anaphora);
		}
	}
	
	/**
	 * Reads the corpus mined gender frequencies
	 */
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
	
	/**
	 * Reads all lines in a document and puts the frequencies in the map
	 * @param inputLines All corpus lines
	 */
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
	
	
}
