package de.unidue.henryvdv.ba.gender.reader;

import java.io.File;
import java.io.IOException;
import java.text.Normalizer;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.uima.UimaContext;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.fit.component.JCasCollectionReader_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Progress;

import de.unidue.henryvdv.ba.gender.param.GenderParam;
import de.unidue.henryvdv.ba.gender.util.GenderUtil;
import de.unidue.henryvdv.ba.type.DocumentInfo;

public class CorpusTextReader
	extends JCasCollectionReader_ImplBase{
	
    public static final String PARAM_INPUT_DIRECTORY= "InputDirectory";
    @ConfigurationParameter(name = PARAM_INPUT_DIRECTORY, mandatory = true)
    private String inputDirectory;
    
    /**
     * The maximum of sentences allowed per document.
     * If there are more sentences in one document, the document will be split.
     */
    public static final String PARAM_MAX_SENTENCES = "MaxSentences";
    @ConfigurationParameter(name = PARAM_MAX_SENTENCES, mandatory = true)
    private int maxSentences;
   
    
    private int docIndex;
    private List<File> inputFiles;
    private String documentText;
    
    private int currentLine;
    
    private String[] identifiers;

    @Override
    public void initialize(UimaContext context)
        throws ResourceInitializationException
    {
        super.initialize(context);
        String[] ext = {"txt"};
        File inputDir = new File(inputDirectory);
        docIndex = 5;
        inputFiles = (List<File>) FileUtils.listFiles(inputDir, ext, false);
        currentLine = 100000;
        identifiers = GenderUtil.concatAll(GenderParam.reflexive,
        									GenderParam.nominative,
        									GenderParam.possessive,
        									GenderParam.designators);
    }
    

	public boolean hasNext() throws IOException, CollectionException {
		return docIndex < inputFiles.size();
	}

	public Progress[] getProgress() {
		return null;
	}

	public void getNext(JCas jCas) throws IOException, CollectionException {
		System.out.println("Read Document Nr: " + docIndex);
		System.out.println("From line " + currentLine + " to (at most) line " + (currentLine + maxSentences));
		jCas.setDocumentLanguage("en");
		
		DocumentInfo docInfo = new DocumentInfo(jCas);
        String docName =  inputFiles.get(docIndex).getName();
        docName = docName.substring(0,docName.length() - 4);
        docInfo.setDocumentName(docName);       
        docInfo.addToIndexes();
        
        
		documentText = "";
        
        List<String> docText = FileUtils.readLines(inputFiles.get(docIndex));
               
        boolean readFromFile = true;
        
        int filteredLines = 0;
        
        while(readFromFile){
        	String s = docText.get(currentLine);
        	for(int j = 0; j < s.length(); j++){
        		if(Character.isAlphabetic(s.charAt(j))){
        			s = s.substring(j);
        			break;
        		}
        	}
        	
        	s = Normalizer
        	        .normalize(s, Normalizer.Form.NFD)
        	        .replaceAll("[^\\p{ASCII}]", "");
        	
        	
        	for(int i = 0; i < identifiers.length; i++){        		
        		if(s.contains(" " + identifiers[i] + " ")){
        			filteredLines++;
        			documentText += s + " ";
        			break;
        		}
        	}
        	
     
        	currentLine++;
        	
        	if(currentLine == docText.size()){
        		readFromFile = false;
        		docIndex++;
        		currentLine = 0;
        	} else if((currentLine % maxSentences) == 0){
        		readFromFile = false;
        	}
        }
        System.out.println("Lines with Identifier: " + filteredLines);
		jCas.setDocumentText(documentText.trim());     
	}

}
