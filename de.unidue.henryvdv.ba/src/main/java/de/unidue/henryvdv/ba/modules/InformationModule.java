package de.unidue.henryvdv.ba.modules;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import de.tudarmstadt.ukp.dkpro.core.api.coref.type.CoreferenceChain;
import de.tudarmstadt.ukp.dkpro.core.api.coref.type.CoreferenceLink;
import de.tudarmstadt.ukp.dkpro.core.api.frequency.util.FrequencyDistribution;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.constituent.Constituent;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.constituent.NP;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.dependency.Dependency;
import de.unidue.henryvdv.ba.type.Anaphora;
import de.unidue.henryvdv.ba.type.DocumentInfo;
import de.unidue.henryvdv.ba.type.MyCoreferenceChain;
import de.unidue.henryvdv.ba.type.MyCoreferenceLink;
import de.unidue.henryvdv.ba.util.AnnotationUtils;
import edu.stanford.nlp.trees.Tree;

public class InformationModule 
	extends JCasAnnotator_ImplBase
{

	private JCas aJCas;
	private FrequencyDistribution<Integer> sentenceDistanceFD;
	private FrequencyDistribution<Integer> antecedentTokenSizeFD;
	private Collection<Anaphora> anaphoras;
	private Collection<Sentence> sentences;
	private Collection<Token> tokens;
	
	public void initialize(UimaContext context) throws ResourceInitializationException{
		super.initialize(context);
		sentenceDistanceFD = new FrequencyDistribution<Integer>();
		antecedentTokenSizeFD = new FrequencyDistribution<Integer>();
	}
	
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		this.aJCas = aJCas;
		anaphoras = JCasUtil.select(aJCas, Anaphora.class);
		sentences = JCasUtil.select(aJCas, Sentence.class);
		tokens = JCasUtil.select(aJCas, Token.class);
		collectAntecedentTokenSize();
	//	collectSentenceDistanceInfo();
	//	printyMyCorefChains();
		printInfos();
	//	printNounPhrases();
	//	printDependencies();
	//	printCorefChains();
	//	printDocText();
	//	printTokens();
	//	printSentences();
		
		//explorePOS(10);
		
	}
	
	@Override
	public void collectionProcessComplete(){
		/*
		for(Integer s : sentenceDistanceFD.getKeys()){
			System.out.println("Sentence-distance: " + s);
			System.out.println("Count: " + ((float)sentenceDistanceFD.getCount(s)/(float)sentenceDistanceFD.getN()*100f) + " %");
		}*/
		
		int[] sortedList = new int[antecedentTokenSizeFD.getKeys().size()];
		int j = 0;
		for(Integer s : antecedentTokenSizeFD.getKeys()){
			sortedList[j] = s;
			j++;
		}
		
		Arrays.sort(sortedList);
		
		float totalCount = 0.0f;
		for(int i = 0; i < sortedList.length; i++){
			System.out.println("Token Distance: " + sortedList[i]);
			float currentCount = ((float)antecedentTokenSizeFD.getCount(sortedList[i])/(float)antecedentTokenSizeFD.getN()*100f);
			//System.out.println("Count: " + ((float)antecedentTokenSizeFD.getCount(sortedList[i])/(float)antecedentTokenSizeFD.getN()*100f) + " %");
			totalCount += currentCount;
			System.out.println("Total: " + totalCount + " %");
		}
		
	}
	
	private void collectAntecedentTokenSize(){
		for(Anaphora anaphora : anaphoras){
			if(anaphora.getHasCorrectAntecedent()){
				List<Token> anteTokens = AnnotationUtils.getCoveredTokens(anaphora.getAntecedent(), tokens);
				antecedentTokenSizeFD.inc(anteTokens.size());
			}
		}
	}
	
	private void collectSentenceDistanceInfo(){
		for(Anaphora anaphora : anaphoras){
			int nr1 = getSentenceNr(anaphora.getBegin());
			int nr2 = getSentenceNr(anaphora.getAntecedent().getBegin());
			int dist = nr1 - nr2;

			if(dist >= 0){
				sentenceDistanceFD.inc((Integer)dist);
			} else {
				System.out.println("Something wrong here: " + dist);
			}

		}
	}
	
	private void printyMyCorefChains(){
		Collection<MyCoreferenceChain> corefChains = JCasUtil.select(aJCas, MyCoreferenceChain.class);
		for(MyCoreferenceChain c : corefChains){
			System.out.println("* * * * * * * * * * * * * * * * *");
			System.out.println("Coreference Chain: " + c.getCorefClass());
			MyCoreferenceLink corefLink = c.getFirst();
			while(corefLink.getNext() != null){
				System.out.println("Text: " + corefLink.getCoveredText());
				System.out.println("At Position: " + corefLink.getBegin());
				System.out.println("Coref Type: " + corefLink.getCorefType());
				System.out.println("Mention Type: " + corefLink.getMentionType());				
				System.out.println("---------");	
				corefLink = corefLink.getNext();
			}
			
			
		}
	}
	
	private void printNounPhrases(){
		Collection<Constituent> constituents = JCasUtil.select(aJCas, Constituent.class);
		if(constituents == null){
			System.out.println("No Constituents");
			return;
		}
		for(Constituent c : constituents){
			if(c instanceof NP){
				System.out.println("Noun Phrase: " +  c.getCoveredText());
			}
		}
	}
	
	private void printInfos(){
		DocumentInfo docInfo = JCasUtil.selectSingle(aJCas, DocumentInfo.class);
		System.out.println("Document name: " + docInfo.getDocumentName());
		
		/*
		System.out.println("Text length: " + aJCas.getDocumentText().length()  + " chars");
		
		Collection<Sentence> sentences = JCasUtil.select(aJCas, Sentence.class);
		System.out.println("Sentences  : " + sentences.size());
		
		Collection<Token> tokens = JCasUtil.select(aJCas, Token.class);
		System.out.println("Tokens     : " + tokens.size());
		*/
		/*
		FrequencyDistribution<String> fd = new FrequencyDistribution<String>();
		for(Token t : tokens){
			fd.inc(t.getPos().getPosValue());
		}
		
		System.out.println("PRP : " + fd.getCount("PRP"));
		System.out.println("PRP$ : " + fd.getCount("PRP$"));
		*/
	}
	
	private void printDependencies(){
		Collection<Dependency> dependencies = JCasUtil.select(aJCas, Dependency.class);
		System.out.println("----------------DEPENDENCIES-------------");
		for(Dependency d : dependencies){
			System.out.println("-----------------------------");
			System.out.println("Dependency Type: " + d.getDependencyType());
	     	System.out.println("Dependency Gov: " + d.getGovernor().getCoveredText());
			System.out.println("Dependency Dep: " + d.getDependent().getCoveredText());			
		}
		System.out.println("----------------DEPENDENCIES-------------");
	}
	
	private void printCorefChains(){
		Collection<CoreferenceChain> corefChains = JCasUtil.select(aJCas, CoreferenceChain.class);
		for(CoreferenceChain cChain : corefChains){
			System.out.println("-----------------------------");
			CoreferenceLink cLink = cChain.getFirst();
			while(cLink.getNext() != null){
				System.out.println("Text: " + cLink.getCoveredText());
				cLink = cLink.getNext();
			}
			System.out.println("Text: " + cLink.getCoveredText());
		}
	}
	
	private void printDocText(){
		System.out.println("DocText:" + aJCas.getDocumentText());
	}	
	
	private void explorePOS(int n){
		Collection<Token> tokens = JCasUtil.select(aJCas, Token.class);
		FrequencyDistribution<String> fd = new FrequencyDistribution<String>();
		for(Token t : tokens){
			fd.inc(t.getPos().getPosValue());
		}
		for(String s : fd.getMostFrequentSamples(n)){
			System.out.println("POS : " + s + " --- " + "Frequency: " + fd.getCount(s));
		}
	}
	
	private void printTokens(){
		Collection<Token> tokens = JCasUtil.select(aJCas, Token.class);
		for(Token t : tokens){
			System.out.println("++++++++++++++++++++++++++");	
			System.out.println("Token : " +  t.getCoveredText());
			System.out.println("Lemma : " +  t.getLemma().getValue());			
			System.out.println("POS : " +  t.getPos().getPosValue());			
		}
	}
	
	private void printSentences(){
		Collection<Sentence> sentences = JCasUtil.select(aJCas, Sentence.class);
		int i = 1;
		for(Sentence s : sentences){
			System.out.println(s.getCoveredText());
		}
	}
	
	private int getSentenceNr(int begin){
		int i = 1;
		for(Sentence s : sentences){
			if(s.getEnd() > begin){
				break;
			}
			i++;
		}		
		return i;
	}

}
