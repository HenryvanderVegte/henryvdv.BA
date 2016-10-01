package de.unidue.henryvdv.ba.param;

public class Parameters {

	public final static String[] allPronouns = {"himself","his","he",
												"herself","her","she",
												"itself","its", //"it",
												"themselves","their","they"};
	
	public final static String[] malePronouns = {"himself","his","he"};
	public final static String[] femalePronouns = {"herself","her","she"};
	public final static String[] neutralPronouns = {"itself","its","it"};
	public final static String[] pluralPronouns = {"themselves","their","they"};
	
	
	public static final String[] maleDesignators = {"mr","mister","master","lord","mr.","sir"};
	
	public static final String[] femaleDesignators = {"mrs","ms.","ms","mz","mz.","miss","lady","mrs.","madam","dame"};
	
	public static final String genderCorpusDirectory = "src/main/resources/gendercorpus";
	
	public static final float acceptAtThreshold = 1.0f;
}
