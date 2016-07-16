package de.unidue.henryvdv.ba.reader;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.unidue.henryvdv.ba.type.DocumentInfo;
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

public class WikiCoref_Reader 
extends JCasCollectionReader_ImplBase{
	
    public static final String PARAM_INPUT_DIRECTORY = "InputDirectory";
    @ConfigurationParameter(name = PARAM_INPUT_DIRECTORY, mandatory = true)
    private String inputDirectory;
	
    private static final String TAG_WORD = "word";
    private static final String TAG_WORDS = "words";
    private static final String TAG_MARKABLES = "markables";
    private static final String TAG_MARKABLE = "markable";
 
    private String documentText;
    private JCas aJCas;
    
    
    private List<File> inputBasedataFiles;
    private int basedataIndex;
    private List<String> basedataDoc;
    
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
        
        if(inputBasedataFiles.size()*2 != inputMarkablesFiles.size()){
        	System.out.println("ERROR: Basedata and Markables - sizes are wrong. ( " + inputBasedataFiles.size() + " - " + inputMarkablesFiles.size() + ")" );
        	throw new ResourceInitializationException();
        }
        
        basedataIndex = 0;
        markablesDataIndex = 0;   
    } 

	public boolean hasNext() throws IOException, CollectionException {
		return basedataIndex < inputBasedataFiles.size();
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
            System.out.println("Coref Filename:" + inputMarkablesFiles.get(markablesDataIndex).getName() );
            
            sentenceDoc = FileUtils.readLines(inputMarkablesFiles.get(markablesDataIndex + 1));
            System.out.println("Coref Filename:" + inputMarkablesFiles.get(markablesDataIndex + 1).getName() );
            
            
            DocumentInfo docInfo = new DocumentInfo(aJCas);
            String docName = inputBasedataFiles.get(basedataIndex).getName();
            docName = docName.substring(docName.length() - 10);
            docInfo.setDocumentName(docName);
            
            docInfo.addToIndexes();
            basedataIndex++;
            markablesDataIndex += 2;
        }  
        catch (IOException e) {
            throw new IOException(e);
        }
        
        processWords();
        processSentences();
        
        System.out.println(documentText.length());
        aJCas.setDocumentText(documentText.trim());
        
	}

	private void processSentences(){
		//skip DOCTYPE lines
		currentLine = 2;
		int startAt = 0;
		int stopAt = 0;
		while(!sentenceDoc.get(currentLine).contains("</" + TAG_MARKABLES + ">")){
			if(sentenceDoc.get(currentLine).contains("<" + TAG_MARKABLE + " ")){
				String sentence = sentenceDoc.get(currentLine);
				int startToken = 0;
				int stopToken = 0;
				
				for(int i = 0; i < sentence.length() - 6; i++){
					if(sentence.substring(i,i + 5).equals("word_")){
						for(int j = i+5; j < sentence.length(); j++){
							if(!Character.isDigit(sentence.charAt(j))){
								 if(startToken == 0){
									 startToken = Integer.parseInt(sentence.substring(i+5, j));
									 
								 } else {
									 stopToken = Integer.parseInt(sentence.substring(i+5, j));
								 }							 
								 break;
							}
						}					
					}			
				}			
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

}
