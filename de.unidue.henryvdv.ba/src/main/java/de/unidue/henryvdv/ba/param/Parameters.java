package de.unidue.henryvdv.ba.param;

import de.unidue.henryvdv.ba.modules.SVMClassifier.ClassificationMode;

public class Parameters {
	
	public final static String[] resolvedPronouns = {"himself","his","he",
			"herself","her","she",
			"itself","its", //"it",
			"themselves","their","they"};

	public final static String[] allPronouns = {
			"i","me","my","mine","myself",
			"you","your","yours","yourself",
			"he","him","his","himself",
			"she","her","hers","herself",
			"it","its","itself",
			"we","us","our","ours","ourselves",
			"they","them","their","theirs","themselves"};
	
	public final static String[] thirdPersonPronouns = {
			"himself","his","he","him",
			"herself","her","she","hers",
			"itself","its","it",
			"themselves","their","they","them","theirs"};
	
	public final static String[] malePronouns = {"himself","his","he","him"};
	public final static String[] femalePronouns = {"herself","her","she","hers"};
	public final static String[] neutralPronouns = {"itself","its","it"};
	public final static String[] pluralPronouns = {"themselves","their","they","them","theirs"};
	
	public final static String[] reflexivePronouns = {"themselves","himself","herself","itself"};
	
	public static final String[] maleDesignators = {"mr","mister","master","lord","mr.","sir"};
	
	public static final String[] femaleDesignators = {"mrs","ms.","ms","mz","mz.","miss","lady","mrs.","madam","dame"};
	
	public static final String genderCorpusDirectory = "src/main/resources/gendercorpus";
		
	public static final boolean removeCoveringNPs = false;
	
	public static final boolean exactBoundMatch = true;
    
	public static final int MAX_SENTENCE_DIST = 1;
    
	public static final float acceptAtThreshold = 0f;
	
	public static final float lowerThresholdFactor = 0.1f;
}
