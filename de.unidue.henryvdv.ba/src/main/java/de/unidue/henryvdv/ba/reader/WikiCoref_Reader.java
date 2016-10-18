package de.unidue.henryvdv.ba.reader;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.unidue.henryvdv.ba.type.DocumentInfo;
import de.unidue.henryvdv.ba.type.MyCoreferenceChain;
import de.unidue.henryvdv.ba.type.MyCoreferenceLink;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.uima.UimaContext;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.fit.component.JCasCollectionReader_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Progress;
/**
 * Reads the WikiCoref documents with tokens, sentences, and CoreferenceChains
 * @author Henry
 *
 */
@TypeCapability(
        inputs = {},
        outputs = {
                "de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token",
                "de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence",
                "de.unidue.henryvdv.ba.type.MyCoreferenceChain",
                "de.unidue.henryvdv.ba.type.DocumentInfo"})
public class WikiCoref_Reader 
extends JCasCollectionReader_ImplBase{
	
	/**
	 * Input directory
	 */
    public static final String PARAM_INPUT_DIRECTORY = "InputDirectory";
    @ConfigurationParameter(name = PARAM_INPUT_DIRECTORY, mandatory = true)
    private String inputDirectory;
    
    /**
     * Parameter to select all documents (through their indexes) that
     * should be read
     */
    public static final String PARAM_USED_DOCUMENT_NUMBERS = "usedDocuments";
    @ConfigurationParameter(name = PARAM_USED_DOCUMENT_NUMBERS, mandatory = true)
    private Integer[] usedDocuments;
    /**
     * Tags used in class
     */
    private static final String TAG_WORD = "word";
    private static final String TAG_WORDS = "words";
    private static final String TAG_MARKABLES = "markables";
    private static final String TAG_MARKABLE = "markable";
 
    private String documentText;
    private JCas aJCas;
    
    /**
     * Basedata files contain the document text
     */
    private List<File> inputBasedataFiles;
    private int basedataIndex;
    private List<String> basedataDoc;  
    
    /**
     * Markable files contain the sentence and coreference annotations
     */
    private List<File> inputMarkablesFiles;
    private int markablesDataIndex;
    private List<String> sentenceDoc;
    private List<String> corefDoc;

    private int currentLine;


    @Override
    public void initialize(UimaContext context)
        throws ResourceInitializationException
    {
        super.initialize(context);
        
        String[] fileExtensions = {"xml"};
        File inputBasedataDirectory = new File(inputDirectory + "/Basedata");
        inputBasedataFiles = (List<File>) FileUtils.listFiles(inputBasedataDirectory, fileExtensions, false);
        File inputMarkablesDirectory = new File(inputDirectory + "/Markables");
        inputMarkablesFiles = (List<File>) FileUtils.listFiles(inputMarkablesDirectory, fileExtensions, false);
     
        //Since there are two markable files for each document file:
        if(inputBasedataFiles.size()*2 != inputMarkablesFiles.size()){
        	System.out.println("ERROR: Basedata and Markables - sizes are wrong. ( " + inputBasedataFiles.size() + " - " + inputMarkablesFiles.size() + ")" );
        	throw new ResourceInitializationException();
        }
        
        basedataIndex = 0;
        
        while(basedataIndex < inputBasedataFiles.size()){
        	if(ArrayUtils.contains(usedDocuments, basedataIndex)){
            	break;
            }
        	basedataIndex++;
        }
        
        markablesDataIndex = basedataIndex*2;   
    } 

	public boolean hasNext() throws IOException, CollectionException {
		return (basedataIndex < inputBasedataFiles.size());
	}

	public Progress[] getProgress() {
		return null;
	}

	@Override
	public void getNext(JCas jCas) throws IOException, CollectionException {
		documentText = "";
		aJCas = jCas;		
		aJCas.setDocumentLanguage("en");
        try {  
        	
            basedataDoc = FileUtils.readLines(inputBasedataFiles.get(basedataIndex));           
            corefDoc = FileUtils.readLines(inputMarkablesFiles.get(markablesDataIndex));        
            sentenceDoc = FileUtils.readLines(inputMarkablesFiles.get(markablesDataIndex + 1));
            
            
            DocumentInfo docInfo = new DocumentInfo(aJCas);
            String docName = inputBasedataFiles.get(basedataIndex).getName();
            docName = docName.substring(0,docName.length() - 10);
            docInfo.setDocumentName(docName);
            
            docInfo.addToIndexes();
            basedataIndex++;
            
            while(basedataIndex < inputBasedataFiles.size()){
            	if(ArrayUtils.contains(usedDocuments, basedataIndex)){
                	break;
                }
            	basedataIndex++;
            }
            
            markablesDataIndex = basedataIndex*2;  
        }  
        catch (IOException e) {
            throw new IOException(e);
        }
        
        processWords();
        processSentences();
        processCoreferences();

        aJCas.setDocumentText(documentText.trim());
        
	}

	private void processSentences(){
		//starts with 2 to skip DOCTYPE lines
		currentLine = 2;
		int startAt = 0;
		int stopAt = 0;
		while(!sentenceDoc.get(currentLine).contains("</" + TAG_MARKABLES + ">")){
			if(sentenceDoc.get(currentLine).contains("<" + TAG_MARKABLE + " ")){
				String sentence = sentenceDoc.get(currentLine);

				Integer[] w = getWordSpan(sentence);
				int startToken = w[0];
				int stopToken = w[1];
				
				startAt = 0;
				stopAt = 0;
				Collection<Token> tokens = JCasUtil.select(aJCas, Token.class);
				int iToken = 1;
				for(Token t : tokens){
					if(iToken == startToken){
						startAt = t.getBegin();
					}
					if(iToken == stopToken){
						stopAt = t.getEnd();
						break;
					}				
					iToken++;			
				}
				Sentence s = new Sentence(aJCas, startAt, stopAt);
				s.addToIndexes();
			}
			currentLine++;
		}
		Sentence s = new Sentence(aJCas, stopAt + 1, documentText.length() - 1);
		s.addToIndexes();
		
	}
	
	private void processWords(){
		//skip DOCTYPE lines
		currentLine = 2;
		while(!basedataDoc.get(currentLine).contains("</" + TAG_WORDS+ ">")){
			if(basedataDoc.get(currentLine).contains("<" + TAG_WORD + " id=")){
				String word = basedataDoc.get(currentLine);
				word = word.replaceAll("<[^>]+>|\\s+", "");
				documentText += word;
				int tokenStart = documentText.length() - word.length();
				int tokenEnd = documentText.length();				
				Token t = new Token(aJCas, tokenStart, tokenEnd);
				t.addToIndexes();				
				documentText += " ";
			}
			currentLine++;
		}
	}

	private void processCoreferences(){
		HashMap<String, MyCoreferenceChain>  corefChains = new HashMap<String, MyCoreferenceChain>();
		currentLine = 2;
		while(! corefDoc.get(currentLine).contains("</" + TAG_MARKABLES + ">")){
			if( corefDoc.get(currentLine).contains("<" + TAG_MARKABLE + " id=")){
				String curLine =  corefDoc.get(currentLine);
				Integer[] wordSpan = getWordSpan(curLine);
				int startToken = wordSpan[0];
				int stopToken = wordSpan[1];
				String corefClass = getStringAtTag(curLine, "coref_class");
				String corefType = getStringAtTag(curLine, "coreftype");
				String mentionType = getStringAtTag(curLine, "mentiontype");
				
				int startAt = 0;
				int stopAt = 0;
				
				Collection<Token> tokens = JCasUtil.select(aJCas, Token.class);
				int iToken = 1;
				for(Token t : tokens){
					if(iToken == startToken){
						startAt = t.getBegin();
					}
					if(iToken == stopToken){
						stopAt = t.getEnd();
						break;
					}				
					iToken++;			
				}
				
				if(corefChains.get(corefClass) == null){
					MyCoreferenceChain corefChain = new MyCoreferenceChain(aJCas);
					corefChain.setCorefClass(corefClass);
					
					MyCoreferenceLink corefLink = new MyCoreferenceLink(aJCas, startAt, stopAt);
					corefLink.setCorefType(corefType);
					corefLink.setMentionType(mentionType);
					
					corefChain.setFirst(corefLink);
					
					
					corefChains.put(corefClass, corefChain);
				} else {
					MyCoreferenceChain corefChain = corefChains.get(corefClass);
					MyCoreferenceLink corefLink = corefChain.getFirst();
					while(corefLink.getNext() != null){
						corefLink = corefLink.getNext();
					}
					MyCoreferenceLink newCorefLink = new MyCoreferenceLink(aJCas, startAt, stopAt);
					newCorefLink.setMentionType(mentionType);
					newCorefLink.setCorefType(corefType);
					corefLink.setNext(newCorefLink);
				}
				
			}
			currentLine++;
		}		
		for(MyCoreferenceChain c : corefChains.values()){		
			c.addToIndexes();
		}
	}
	
	private String getStringAtTag(String docLine, String typeName){
		String r = "";
		typeName += "=\"";
		
		for(int i = 0; i < docLine.length() - (typeName.length() + 1); i++){
			if(docLine.substring(i,i + typeName.length()).equals(typeName)){
				for(int j = i+13; j < docLine.length(); j++){
					if(docLine.charAt(j) == '"' ){
						 r = docLine.substring(i + typeName.length(),j);						 
						 break;
					}
				}					
			}			
		}
		return r;
	}
	
	
	private Integer[] getWordSpan(String docLine){
		int startToken = 0;
		int stopToken = 0;
		for(int i = 0; i < docLine.length() - 6; i++){
			if(docLine.substring(i,i + 5).equals("word_")){
				for(int j = i+5; j < docLine.length(); j++){
					if(!Character.isDigit(docLine.charAt(j))){
						 if(startToken == 0){
							 startToken = Integer.parseInt(docLine.substring(i+5, j));
							 
						 } else {
							 stopToken = Integer.parseInt(docLine.substring(i+5, j));
						 }							 
						 break;
					}
				}					
			}			
		}
		Integer[] r = {startToken, stopToken};
		return r;
	}
}
