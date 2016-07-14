package de.unidue.henryvdv.ba.reader;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.uima.UimaContext;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.fit.component.JCasCollectionReader_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Progress;

import de.tudarmstadt.ukp.dkpro.core.api.coref.type.CoreferenceChain;
import de.tudarmstadt.ukp.dkpro.core.api.coref.type.CoreferenceLink;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.dependency.Dependency;
import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.POS;
import de.tudarmstadt.ukp.dkpro.core.api.ner.type.NamedEntity;
import de.tudarmstadt.ukp.dkpro.core.api.parameter.ComponentParameters;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Lemma;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;

public class WikiCorefReader 
	extends JCasCollectionReader_ImplBase
{
    public static final String PARAM_INPUT_FILE = "InputFile";
    @ConfigurationParameter(name = PARAM_INPUT_FILE, mandatory = true)
    private File inputFile;
    
    /**
     * Write token annotations to the CAS.
     */
	public static final String PARAM_READ_TOKEN = ComponentParameters.PARAM_READ_TOKEN;
	@ConfigurationParameter(name = PARAM_READ_TOKEN, mandatory = true, defaultValue = "true")
	private boolean readToken;

    /**
     * Write part-of-speech annotations to the CAS.
     */
	public static final String PARAM_READ_POS = ComponentParameters.PARAM_READ_POS;
	@ConfigurationParameter(name = PARAM_READ_POS, mandatory = true, defaultValue = "true")
	private boolean readPOS;

    /**
     * Write lemma annotations to the CAS.
     */
	public static final String PARAM_READ_LEMMA = ComponentParameters.PARAM_READ_LEMMA;
	@ConfigurationParameter(name = PARAM_READ_LEMMA, mandatory = true, defaultValue = "true")
	private boolean readLemma;

	/**
	 * Write sentence annotations to the CAS.
	 */
	public static final String PARAM_READ_SENTENCE = ComponentParameters.PARAM_READ_SENTENCE;
	@ConfigurationParameter(name = PARAM_READ_SENTENCE, mandatory = true, defaultValue = "true")
	private boolean readSentence;
	
	/**
	 * Write NER annotations to the CAS.
	 */
	public static final String PARAM_READ_NER = ComponentParameters.PARAM_READ_NAMED_ENTITY;
	@ConfigurationParameter(name = PARAM_READ_SENTENCE, mandatory = true, defaultValue = "true")
	private boolean readNER;	
	
	/**
	 * Write Speaker annotations to the CAS.
	 */
	public static final String PARAM_READ_SPEAKER = "readSpeaker";
	@ConfigurationParameter(name = PARAM_READ_SPEAKER, mandatory = false, defaultValue = "false")
	private boolean readSpeaker;	
	 	
    /**
     * Use this part-of-speech tag set to use to resolve the tag set mapping instead of using the
     * tag set defined as part of the model meta data. This can be useful if a custom model is
     * specified which does not have such meta data, or it can be used in readers.
     */
	public static final String PARAM_POS_TAG_SET = ComponentParameters.PARAM_POS_TAG_SET;
	@ConfigurationParameter(name = PARAM_POS_TAG_SET, mandatory = false)
	protected String posTagset;
    
	
	private static final String TAG_DOCUMENT = "document";
	private static final String TAG_SENTENCES = "sentences";
	private static final String TAG_SENTENCE = "sentence";
	private static final String TAG_TOKENS = "tokens";
	private static final String TAG_TOKEN = "token";
	private static final String TAG_WORD = "word";
	private static final String TAG_LEMMA = "lemma";
	private static final String TAG_POS = "POS";
	private static final String TAG_NER = "NER";
	private static final String TAG_SPEAKER = "Speaker";
	private static final String TAG_COREFERENCE = "coreference";
	private static final String TAG_MENTION = "mention";
	private static final String TAG_ROOT = "root";
	
	private static final String TAG_COREF_START = "start";
	private static final String TAG_COREF_END = "end";
	
	private static final String TAG_DEPENDENCIES = "dependencies";
	private static final String TAG_DEPENDENCY = "dep";
	private static final String TAG_GOVERNOR = "governor";
	private static final String TAG_DEPENDENT = "dependent";
	
    private List<String> document;
    private String documentText;
    
    private int currentLine;
    private int currentSentence;
    private int tokenStart;
    private int tokenEnd;
    private CoreferenceChain currentCoreferenceChain;
    private CoreferenceLink currentCoreferenceLink;
    private Dependency currentDependency;
    
      
    private boolean test = true;
    
    private JCas aJCas;

    @Override
    public void initialize(UimaContext context)
        throws ResourceInitializationException
    {
        super.initialize(context);
        try {
            document = FileUtils.readLines(inputFile);
            document.remove(0);
            document.remove(0);          
            currentLine = 0;
        }
        catch (IOException e) {
            throw new ResourceInitializationException(e);
        }
    } 
    
	public boolean hasNext() throws IOException, CollectionException {		
		return test;
	}

	public Progress[] getProgress() {
		return null;
	}

	public void getNext(JCas jCas) throws IOException, CollectionException {
		documentText = "";
		aJCas = jCas;
		currentSentence = 1;
		
		while(!document.get(currentLine).contains("</" + TAG_ROOT + ">")){
			
			if(document.get(currentLine).contains("<" + TAG_ROOT + ">") ||
				document.get(currentLine).contains("<" + TAG_DOCUMENT + ">")){
				currentLine++;
				continue;
			}
			
			if(document.get(currentLine).contains("<" + TAG_SENTENCES + ">")){
				currentLine++;
				processSentences();
			}
			if(document.get(currentLine).contains("<" + TAG_COREFERENCE + ">")){
				currentLine++;
				processCoreferences();
			}
			currentLine++;
		}
						
		
		aJCas.setDocumentText(documentText.trim());
		test = false;
		
	}
	
	private void processSentences(){
		while(!document.get(currentLine).contains("</" + TAG_SENTENCES + ">")){
			if(document.get(currentLine).contains("<" + TAG_SENTENCE + " id=")){
				currentLine++;
				processSentence();
				currentSentence++;
			}
			currentLine++;
		}
	}
	
	private void processSentence(){
		
		while(!document.get(currentLine).contains("</" + TAG_SENTENCE + ">")){
			if(document.get(currentLine).contains("<" + TAG_TOKENS + ">")){
				currentLine++;
				processTokens();
			}
			//--parse line here;
			if(document.get(currentLine).contains("<" + TAG_DEPENDENCIES + " type=\"basic-dependencies\">")){
				currentLine++;
				processDependencies();
			}
			
			currentLine++;
		}

	}
	
	private void processTokens(){
		int sentenceStart = documentText.length();
		while(!document.get(currentLine).contains("</" + TAG_TOKENS + ">")){
			if(document.get(currentLine).contains("<" + TAG_TOKEN + " id=")){
				currentLine++;
				processToken();
			}
			currentLine++;
		}
		//when all tokens of a sentence are processed, we can annotate the sentence
		int sentenceStop = documentText.length() - 1;		
		if(sentenceStart == sentenceStop){
			System.out.println("ERROR");
		} else {
			Sentence s = new Sentence(aJCas, sentenceStart, sentenceStop);
			s.addToIndexes();
		}
	}
	
	private void processToken(){
		Token t = null;
		while(!document.get(currentLine).contains("</" + TAG_TOKEN + ">")){
			if(document.get(currentLine).contains("<" + TAG_WORD + ">")){
				String word = document.get(currentLine);
				word = word.replaceAll("<[^>]+>|\\s+", "");
				documentText += word;
				
				tokenStart = documentText.length() - word.length();
				tokenEnd = documentText.length();
				
				t = new Token(aJCas, tokenStart, tokenEnd);
				t.addToIndexes();
				
				documentText += " ";
			}
			if(document.get(currentLine).contains("<" + TAG_LEMMA + ">") && t != null){
				String lemma = document.get(currentLine);
				lemma = lemma.replaceAll("<[^>]+>|\\s+", "");
				Lemma l = new Lemma(aJCas, tokenStart, tokenEnd);
				l.setValue(lemma);	
				t.setLemma(l);
			}
			if(document.get(currentLine).contains("<" + TAG_POS+ ">") && t != null){
				String pos = document.get(currentLine);
				pos = pos.replaceAll("<[^>]+>|\\s+", "");
				POS p = new POS(aJCas, tokenStart, tokenEnd);
				p.setPosValue(pos);
				t.setPos(p);
			}
			if(document.get(currentLine).contains("<" + TAG_NER + ">") && t != null){
				String ner = document.get(currentLine);
				ner = ner.replaceAll("<[^>]+>|\\s+", "");
				NamedEntity n = new NamedEntity(aJCas, tokenStart, tokenEnd);
				n.setValue(ner);
				n.addToIndexes();
			}
			if(document.get(currentLine).contains("<" + TAG_SPEAKER + ">") && t != null){
				//if Speaker should be read
			}		
			currentLine++;
		}
	}
		
	private void processCoreferences(){
		while(!document.get(currentLine).contains("</" + TAG_COREFERENCE + ">")){
			if(document.get(currentLine).contains("<" + TAG_COREFERENCE + ">")){
				currentLine++;
				processCoreference();
			}
			currentLine++;
		}
	}
	
	private void processCoreference(){
		while(!document.get(currentLine).contains("</" + TAG_COREFERENCE + ">")){
			if(document.get(currentLine).contains("<" + TAG_MENTION  + " representative=")){
				currentLine++;
				currentCoreferenceChain = new CoreferenceChain(aJCas);			
				processMention();
				
			}
			if(document.get(currentLine).contains("<" + TAG_MENTION  + ">")){
				currentLine++;
				processMention();
			}
			
			currentLine++;
		}
		currentCoreferenceChain.addToIndexes();
	}
		
	private void processMention(){
		int sentence = 0, start = 0, end = 0;		
		while(!document.get(currentLine).contains("</" + TAG_MENTION + ">")){
			if(document.get(currentLine).contains("<" + TAG_SENTENCE + ">")){				
				String sentenceS = document.get(currentLine);
				sentenceS = sentenceS.replaceAll("<[^>]+>|\\s+", "");
				sentence = Integer.parseInt(sentenceS);		
			}
			if(document.get(currentLine).contains("<" + TAG_COREF_START + ">")){				
				String startS = document.get(currentLine);
				startS = startS.replaceAll("<[^>]+>|\\s+", "");
				start = Integer.parseInt(startS);		
			}
			if(document.get(currentLine).contains("<" + TAG_COREF_END + ">")){				
				String endS = document.get(currentLine);
				endS = endS.replaceAll("<[^>]+>|\\s+", "");
				end = Integer.parseInt(endS);		
			}
			currentLine++;			
		}
		
		Collection<Sentence> sentences = JCasUtil.select(aJCas, Sentence.class);
		Collection<Token> tokens = JCasUtil.select(aJCas, Token.class);
		//counter for sentences
		int iSentence = 1;
		int sentenceStart = 0;
		//counter for word in sentence
		int iToken = 1;
		
		for(Sentence s : sentences){		
			if(sentence == iSentence){
				sentenceStart = s.getBegin();				
				break;
			}						
			iSentence++;
		}
		
		for(Token t : tokens){
			if(t.getBegin() >= sentenceStart){
				if(iToken == start){
					start = t.getBegin();
				}
				if(iToken == end - 1){
					end = t.getEnd();
					break;
				}				
				iToken++;
			}
		}
		
		CoreferenceLink corefLink = new CoreferenceLink(aJCas, start, end);
		if(currentCoreferenceChain.getFirst() == null){
			currentCoreferenceChain.setFirst(corefLink);
		}else {
			currentCoreferenceLink.setNext(corefLink);
		}
		currentCoreferenceLink = corefLink;
		
	}
	
	private void processDependencies(){
		while(!document.get(currentLine).contains("</" + TAG_DEPENDENCIES + ">")){
			if(document.get(currentLine).contains("<" + TAG_DEPENDENCY + " type=")){
				String depType = document.get(currentLine);
				depType = depType.replaceAll("<dep type=\"|\\s+|\">", "");
				currentDependency = new Dependency(aJCas);
				currentDependency.setDependencyType(depType);
				currentLine++;			
				processDependency();
			}		
			currentLine++;
		}
	}
	
	private void processDependency(){
		String govText = "", depText = "", govIDText, depIDText;
		int govID = 0, depID = 0;
		
		while(!document.get(currentLine).contains("</" + TAG_DEPENDENCY + ">")){
			if(document.get(currentLine).contains("<" + TAG_GOVERNOR )){
				govText = document.get(currentLine);
				govText = govText.replaceAll("<[^>]+>|\\s+", "");
				govIDText = document.get(currentLine);
				govIDText = govIDText.replaceAll("<governor idx=\"|\\s+|\">(.*)","");
				govID = Integer.parseInt(govIDText);		
			}
			if(document.get(currentLine).contains("<" + TAG_DEPENDENT )){
				depText = document.get(currentLine);
				depText = depText.replaceAll("<[^>]+>|\\s+", "");
				depIDText = document.get(currentLine);
				depIDText = depIDText.replaceAll("<dependent idx=\"|\\s+|\">(.*)","");
				depID = Integer.parseInt(depIDText);
			}
		
			currentLine++;
		}
				
		Collection<Sentence> sentences = JCasUtil.select(aJCas, Sentence.class);
		Collection<Token> tokens = JCasUtil.select(aJCas, Token.class);
		int iSentence = 1;
		int sentenceStart = 0;
		int iToken = 1;
		
		for(Sentence s : sentences){		
			if(currentSentence == iSentence){
				sentenceStart = s.getBegin();					
				break;
			}						
			iSentence++;
		}	
		
		for(Token t : tokens){
			if(t.getBegin() >= sentenceStart){
				if(iToken == govID){
					currentDependency.setGovernor(t);
					break;
				}			
				iToken++;
			}
		}
		iToken = 1;
		for(Token t : tokens){
			if(t.getBegin() >= sentenceStart){
				if(iToken == depID){
					currentDependency.setDependent(t);
					break;
				}			
				iToken++;
			}
		}	
		
		if(currentDependency.getGovernor() != null && currentDependency.getDependent() != null){
			currentDependency.addToIndexes();
		}
		
	}
}
