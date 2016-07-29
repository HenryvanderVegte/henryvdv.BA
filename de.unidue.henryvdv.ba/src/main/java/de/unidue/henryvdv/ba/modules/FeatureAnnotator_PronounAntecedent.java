package de.unidue.henryvdv.ba.modules;

import java.util.Collection;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.unidue.henryvdv.ba.type.Anaphora;
import de.unidue.henryvdv.ba.type.NegativeTrainingInstance;

public class FeatureAnnotator_PronounAntecedent extends JCasAnnotator_ImplBase {
	
	/************************************************
	 *  Annotates the Pronoun-Antecedent-Features   *
	 *  -Same Sentence (bool)
	 *  -In Previous Sentence (bool)
	 *  
	 * 
	 * 
	 ************************************************/
	
	private JCas aJCas;
	private Collection<NegativeTrainingInstance> negInstances;
	private Collection<Anaphora> anaphoras;
	private Collection<Sentence> sentences;
	
	
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		this.aJCas = aJCas;
		anaphoras = JCasUtil.select(aJCas, Anaphora.class);
		negInstances = JCasUtil.select(aJCas, NegativeTrainingInstance.class);
		sentences = JCasUtil.select(aJCas, Sentence.class);
		annotateInSameSentenceFeature();
		annotateInPreviousSentenceFeature();
		annotateInterSentenceDiffFeature();
	}

	public void annotateInSameSentenceFeature(){
		for(Anaphora a : anaphoras){
			boolean value = (getSentenceNr(a.getBegin()) == getSentenceNr(a.getAntecedent().getBegin()));
			a.setP_A_InSameSentence(value);
		}
		for(NegativeTrainingInstance n : negInstances){
			boolean value = (getSentenceNr(n.getBegin()) == getSentenceNr(n.getAnaphora().getBegin()));
			n.setP_A_InSameSentence(value);
		}	
	}
	
	public void annotateInPreviousSentenceFeature(){
		for(Anaphora a : anaphoras){
			boolean value = (getSentenceNr(a.getBegin()) -1 == getSentenceNr(a.getAntecedent().getBegin()));
			a.setP_A_InPreviousSentence(value);
		}
		for(NegativeTrainingInstance n : negInstances){
			boolean value = (getSentenceNr(n.getBegin()) == getSentenceNr(n.getAnaphora().getBegin()) -1 );
			n.setP_A_InPreviousSentence(value);
		}	
	}
	
	public void annotateInterSentenceDiffFeature(){
		for(Anaphora a : anaphoras){
			int anaphoraS = getSentenceNr(a.getBegin());
			int antecedentS = getSentenceNr(a.getAntecedent().getBegin());
			float value = (anaphoraS - antecedentS)/ 50.0f;
			a.setP_A_InterSentenceDiff(value);
		}
		for(NegativeTrainingInstance n : negInstances){
			int anaphoraS = getSentenceNr(n.getAnaphora().getBegin());
			int negS = getSentenceNr(n.getBegin());
			float value = (anaphoraS - negS)/ 50.0f;
			n.setP_A_InterSentenceDiff(value);
		}
	}
	
	private int getSentenceNr(int begin){
		int sentenceNr = 1;
		for(Sentence s : sentences){
			if(s.getEnd() > begin){
				break;
			}
			sentenceNr++;
		}	
		return sentenceNr;
	}
	
	
}
