package de.unidue.henryvdv.ba.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;

import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.POS;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.constituent.NP;
import de.unidue.henryvdv.ba.type.Anaphora;
import de.unidue.henryvdv.ba.type.DetectedNP;
import de.unidue.henryvdv.ba.type.DocumentInfo;
import de.unidue.henryvdv.ba.type.GoldNP;
import de.unidue.henryvdv.ba.type.MyCoreferenceChain;
import de.unidue.henryvdv.ba.type.MyCoreferenceLink;

public class Baseline_Evaluator
extends JCasAnnotator_ImplBase{

	private JCas aJCas;
	
	private int anaphorsInDoc;
	private int correctAnaphorsInDoc;
	
	private int anaphorsTotal;
	private int correctAnaphorsTotal;
	
	private List<NP> nps;
	private List<GoldNP> goldAntecedent;
	private List<DetectedNP> detectedAntecedent;
	private Collection<MyCoreferenceChain> corefChains;
	private Collection<Anaphora> anaphoras;
	
	public void initialize(UimaContext context) throws ResourceInitializationException{
		super.initialize(context);
		anaphorsTotal = 0;
		correctAnaphorsTotal = 0;
	}
	
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		this.aJCas = aJCas;
		nps = new ArrayList<NP>();
		goldAntecedent = new ArrayList<GoldNP>();
		detectedAntecedent = new ArrayList<DetectedNP>();
		correctAnaphorsInDoc = 0;
		anaphorsInDoc = 0;
		
		corefChains = JCasUtil.select(aJCas, MyCoreferenceChain.class);		
		anaphoras = JCasUtil.select(aJCas, Anaphora.class);		
		Collection<NP> nounphrases = JCasUtil.select(aJCas, NP.class);
		
		for(NP n : nounphrases){	
			nps.add(n);			
		}
		
		SetGoldNPs();
		SetDetectedNPs();
		
		
		if(detectedAntecedent.size() != goldAntecedent.size()){
			System.out.println("Something wrong here");
		} else {
			evaluate();
			//printResults();
		}
	}
	
	@Override
	public void collectionProcessComplete(){
		float rel = 0f;	
		if(anaphorsTotal != 0){
			rel = ((float)correctAnaphorsTotal / (float)anaphorsTotal) * 100f;
		} 
		System.out.println("Baseline Accuracy: " + rel + " % ");
	}
	
	private void printResults(){
		float rel = 0f;	
		if(anaphorsInDoc != 0){
			rel = ((float)correctAnaphorsInDoc / (float)anaphorsInDoc) * 100f;
		} 
		DocumentInfo docInfo = JCasUtil.selectSingle(aJCas, DocumentInfo.class);
		System.out.println("Document: " + docInfo.getDocumentName());
		System.out.println("Correct: " + rel + " %");
	}
	
	private void evaluate(){

		
		for(int i = 0; i < goldAntecedent.size(); i++){
			if(goldAntecedent.get(i) == null && detectedAntecedent.get(i) == null){
				/*
				System.out.println("--Wrong--");
				System.out.println("One is zero");
				*/
				correctAnaphorsInDoc++;	
				anaphorsInDoc++;
				continue;
			}
			if(goldAntecedent.get(i) == null || detectedAntecedent.get(i) == null){
				/*
				System.out.println("--Correct--");
				System.out.println("Both are zero");
				*/
				anaphorsInDoc++;
				continue;
			}
			
			if(goldAntecedent.get(i).getBegin() >= detectedAntecedent.get(i).getBegin() && 
				goldAntecedent.get(i).getEnd() <= detectedAntecedent.get(i).getEnd()){
				
				/*
				System.out.println("--Correct--");
				System.out.println("Anaphora: " + npsWithAnaphora.get(i).getCoveredText() + "(Sentence: " + getSentenceNr(npsWithAnaphora.get(i).getBegin()) + ")" );
				System.out.println("Gold : " + goldAntecedent.get(i).getCoveredText() + "(Sentence: " + getSentenceNr(goldAntecedent.get(i).getBegin()) + ")");
				System.out.println("Detected : " + detectedAntecedent.get(i).getCoveredText() + "(Sentence: " + getSentenceNr(detectedAntecedent.get(i).getBegin()) + ")");
				*/
				
				correctAnaphorsInDoc++;	
				anaphorsInDoc++;
				continue;
			}
			
			if(goldAntecedent.get(i).getBegin() <= detectedAntecedent.get(i).getBegin() && 
				goldAntecedent.get(i).getEnd() >= detectedAntecedent.get(i).getEnd()){
				/*
				System.out.println("--Correct--");
				System.out.println("Anaphora: " + npsWithAnaphora.get(i).getCoveredText() + "(Sentence: " + getSentenceNr(npsWithAnaphora.get(i).getBegin()) + ")" );
				System.out.println("Gold : " + goldAntecedent.get(i).getCoveredText() + "(Sentence: " + getSentenceNr(goldAntecedent.get(i).getBegin()) + ")");
				System.out.println("Detected : " + detectedAntecedent.get(i).getCoveredText() + "(Sentence: " + getSentenceNr(detectedAntecedent.get(i).getBegin()) + ")");
				*/
				
				
				correctAnaphorsInDoc++;		
				anaphorsInDoc++;
				continue;
			}
			/*
			System.out.println("--Wrong--");
			System.out.println("Anaphora: " + npsWithAnaphora.get(i).getCoveredText() + "(Sentence: " + getSentenceNr(npsWithAnaphora.get(i).getBegin()) + ")" );
			System.out.println("Gold : " + goldAntecedent.get(i).getCoveredText() + "(Sentence: " + getSentenceNr(goldAntecedent.get(i).getBegin()) + ")");
			System.out.println("Detected : " + detectedAntecedent.get(i).getCoveredText() + "(Sentence: " + getSentenceNr(detectedAntecedent.get(i).getBegin()) + ")");
			*/
			anaphorsInDoc++;
			
		}
		anaphorsTotal += anaphorsInDoc;
		correctAnaphorsTotal += correctAnaphorsInDoc;
		
	}

	
	private void SetDetectedNPs(){
		for(Anaphora anaphora : anaphoras){
			if(!anaphora.getHasCorrectAntecedent())
				continue;
			for(int i = 1; i < nps.size(); i++){
				if(nps.get(i).getBegin() <= anaphora.getBegin() && nps.get(i).getEnd() >= anaphora.getEnd()){
					DetectedNP np = new DetectedNP(aJCas, nps.get(i-1).getBegin(), nps.get(i -1).getEnd());
					detectedAntecedent.add(np);
					break;
				}
			}	
		}
	}
	
	private void SetGoldNPs(){
		for(Anaphora anaphora : anaphoras){
			if(!anaphora.getHasCorrectAntecedent())
				continue;
			GoldNP np = new GoldNP(aJCas, anaphora.getAntecedent().getBegin(), anaphora.getAntecedent().getEnd());
			goldAntecedent.add(np);
		}
	}
	

}
