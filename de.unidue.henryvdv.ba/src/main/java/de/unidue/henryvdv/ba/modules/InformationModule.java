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
import de.tudarmstadt.ukp.dkpro.core.api.ner.type.NamedEntity;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Lemma;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.PennTree;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.constituent.Constituent;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.constituent.NP;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.dependency.Dependency;
import de.unidue.henryvdv.ba.param.Parameters;
import de.unidue.henryvdv.ba.type.Anaphora;
import de.unidue.henryvdv.ba.type.DocumentInfo;
import de.unidue.henryvdv.ba.type.MyCoreferenceChain;
import de.unidue.henryvdv.ba.type.MyCoreferenceLink;
import de.unidue.henryvdv.ba.util.AnnotationUtils;
import edu.stanford.nlp.trees.Tree;
/**
 * A helpful module to detect errors or to have a look on the data.
 * @author Henry
 *
 */
public class InformationModule 
	extends JCasAnnotator_ImplBase
{

	private JCas aJCas;
	private FrequencyDistribution<Integer> sentenceDistanceFD;
	private FrequencyDistribution<Integer> antecedentTokenSizeFD;
	private FrequencyDistribution<String> pronounFD;
	private Collection<Anaphora> anaphoras;
	private Collection<Sentence> sentences;
	private Collection<Token> tokens;
	private Collection<Lemma> lemmata;
	private Collection<NamedEntity> namedEntities;
	private Collection<Constituent> constituents;
	private Collection<Dependency> dependencies;
	int i = 0;
	
	public void initialize(UimaContext context) throws ResourceInitializationException{
		super.initialize(context);
		sentenceDistanceFD = new FrequencyDistribution<Integer>();
		antecedentTokenSizeFD = new FrequencyDistribution<Integer>();
		pronounFD = new FrequencyDistribution<String>();
	}
	
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		this.aJCas = aJCas;
		

		/*
		for(Token t : tokens){
			if(Arrays.asList(Parameters.resolvedPronouns).contains(t.getCoveredText().toLowerCase())){
				pronounFD.addSample(t.getCoveredText().toLowerCase(), 1);
			}
		}

		Collection<PennTree> pTree = JCasUtil.select(aJCas, PennTree.class); 
		
		for(PennTree p : pTree){
			System.out.println(p.getPennTree());
		}
				*/
		anaphoras = JCasUtil.select(aJCas, Anaphora.class);
		sentences = JCasUtil.select(aJCas, Sentence.class);
		tokens = JCasUtil.select(aJCas, Token.class);
		lemmata = JCasUtil.select(aJCas, Lemma.class);
		namedEntities = JCasUtil.select(aJCas, NamedEntity.class);
		constituents = JCasUtil.select(aJCas, Constituent.class);
		dependencies = JCasUtil.select(aJCas, Dependency.class);
		
		
		Float[] posVal = new Float[]{0f,0f,0f,0f};
		Integer[] posTotal = new Integer[]{0,0,0,0};
		Float[] negVal = new Float[]{0f,0f,0f,0f};
		Integer[] negTotal = new Integer[]{0,0,0,0};
		for(Anaphora a : anaphoras){
			if(a.getHasCorrectAntecedent()){
				if(a.getPronounFeatures().getP_Masculine()){
					System.out.println("Ante: " + a.getAntecedent().getCoveredText());
					System.out.println(a.getGenderFeatures().getG_Masculine_Mean());
					System.out.println(a.getGenderFeatures().getG_Feminine_Mean());
					System.out.println(a.getGenderFeatures().getG_Neutral_Mean());
					System.out.println(a.getGenderFeatures().getG_Plural_Mean());
					posVal[0] += a.getGenderFeatures().getG_Masculine_Mean();
					posTotal[0] += 1;
				}
				if(a.getPronounFeatures().getP_Feminine()){

					posVal[1] += a.getGenderFeatures().getG_Feminine_Mean();
					posTotal[1] += 1;
				}
				if(a.getPronounFeatures().getP_Neutral()){

					posVal[2] += a.getGenderFeatures().getG_Neutral_Mean();
					posTotal[2] += 1;
				}
				if(a.getPronounFeatures().getP_Plural()){

					posVal[3] += a.getGenderFeatures().getG_Plural_Mean();
					posTotal[3] += 1;
				}
			}
			if(!a.getHasCorrectAntecedent()){
				if(a.getPronounFeatures().getP_Masculine()){
					negVal[0] += a.getGenderFeatures().getG_Masculine_Mean();
					negTotal[0] += 1;
				}
				if(a.getPronounFeatures().getP_Feminine()){

					negVal[1] += a.getGenderFeatures().getG_Feminine_Mean();
					negTotal[1] += 1;
				}
				if(a.getPronounFeatures().getP_Neutral()){

					negVal[2] += a.getGenderFeatures().getG_Neutral_Mean();
					negTotal[2] += 1;
				}
				if(a.getPronounFeatures().getP_Plural()){
					negVal[3] += a.getGenderFeatures().getG_Plural_Mean();
					negTotal[3] += 1;
				}
			}

		}
		System.out.println("********************************");
		System.out.println("POS");
		System.out.println(posVal[0] + " " +  posTotal[0]);
		System.out.println(posVal[1] + " " +  posTotal[1]);
		System.out.println(posVal[2] + " " +  posTotal[2]);
		System.out.println(posVal[3] + " " +  posTotal[3]);
		System.out.println("");
		System.out.println(posVal[0] / (float) posTotal[0]);
		System.out.println(posVal[1] / (float) posTotal[1]);
		System.out.println(posVal[2] / (float) posTotal[2]);
		System.out.println(posVal[3] / (float) posTotal[3]);
		System.out.println("********************************");
		System.out.println("NEG");
		System.out.println(negVal[0] + " " + negTotal[0]);
		System.out.println(negVal[1] + " " + negTotal[1]);
		System.out.println(negVal[2] + " " + negTotal[2]);
		System.out.println(negVal[3] + " " + negTotal[3]);
		System.out.println("");
		System.out.println(negVal[0] / (float) negTotal[0]);
		System.out.println(negVal[1] / (float) negTotal[1]);
		System.out.println(negVal[2] / (float) negTotal[2]);
		System.out.println(negVal[3] / (float) negTotal[3]);
		System.out.println("********************************");
		System.out.println("********************************");

	//	collectAntecedentTokenSize();
		collectSentenceDistanceInfo();
	//	printyMyCorefChains();
	//	printInfos();
	//	printNounPhrases();
	//	printDependencies();
	//	printCorefChains();
	//	printDocText();
	//	printTokens();
	//	printSentences();
		
		//explorePOS(10);
		
	}
	
	public void printAllInfos(){
		System.out.println("Sentences: ");
		for(Sentence s : sentences){
			System.out.println(s.getCoveredText());
		}
		System.out.println(" ");
		System.out.println("Tokens: ");
		for(Token t : tokens){
			System.out.println(t.getCoveredText() + " -- " + t.getPos().getPosValue());
		}
		System.out.println(" ");
		System.out.println("Lemmata: ");
		for(Lemma l : lemmata){
			System.out.println(l.getCoveredText() + " -- " + l.getValue());
		}
		System.out.println(" ");
		System.out.println("Named Entities: ");
		for(NamedEntity n : namedEntities){
			System.out.println(n.getCoveredText() + " -- " + n.getValue());
		}
		System.out.println(" ");
		System.out.println("Constituents: ");
		for(Constituent c : constituents){
			System.out.println(c.getCoveredText() + "  " + c.getConstituentType());
		}
		System.out.println(" ");
		System.out.println("Dependencies: ");
		for(Dependency d : dependencies){
			System.out.println(d.getCoveredText() + " " + d.getDependencyType() + " " + d.getGovernor().getCoveredText());
		}
		
	}
	
	
	@Override
	public void collectionProcessComplete(){
		//System.out.println(i);
		
		for(Integer s : sentenceDistanceFD.getKeys()){
			System.out.println("Sentence-distance: " + s);
			System.out.println("Count: " + ((float)sentenceDistanceFD.getCount(s)/(float)sentenceDistanceFD.getN()*100f) + " %");
		}
		/*
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
		*/
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
