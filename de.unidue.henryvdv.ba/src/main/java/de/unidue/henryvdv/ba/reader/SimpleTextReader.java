package de.unidue.henryvdv.ba.reader;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.uima.UimaContext;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.fit.component.JCasCollectionReader_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Progress;

import de.unidue.henryvdv.ba.type.DocumentInfo;

public class SimpleTextReader
	extends JCasCollectionReader_ImplBase{
	
    public static final String PARAM_INPUT_DIRECTORY= "InputDirectory";
    @ConfigurationParameter(name = PARAM_INPUT_DIRECTORY, mandatory = true)
    private String inputDirectory;
   
    
    private int docIndex;
    private List<File> inputFiles;
    private String documentText;
    
    @Override
    public void initialize(UimaContext context)
        throws ResourceInitializationException
    {
        super.initialize(context);
        String[] ext = {"txt"};
        File inputDir = new File(inputDirectory);
        docIndex = 0;
        inputFiles = (List<File>) FileUtils.listFiles(inputDir, ext, false);
    }
    

	public boolean hasNext() throws IOException, CollectionException {
		return docIndex < inputFiles.size();
	}

	public Progress[] getProgress() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void getNext(JCas jCas) throws IOException, CollectionException {
		jCas.setDocumentLanguage("en");
		DocumentInfo docInfo = new DocumentInfo(jCas);
        String docName =  inputFiles.get(docIndex).getName();
        docName = docName.substring(0,docName.length() - 4);
        docInfo.setDocumentName(docName);       
        docInfo.addToIndexes();
		
		documentText = FileUtils.readFileToString(inputFiles.get(docIndex));
		docIndex++;
		jCas.setDocumentText(documentText.trim());
	}

}
