package de.unidue.henryvdv.ba.param;

/**
 * All static parameters and used values can be adjusted here
 * @author Henry
 *
 */
public class Parameters {
	
	/**
	 * All Pronouns that should be resolved
	 */
	public final static String[] resolvedPronouns = {"himself","his","he",
			"herself","her","she",
			"itself","its", //"it",
			"themselves","their","they"};

	/**
	 * All Pronouns 
	 */
	public final static String[] allPronouns = {
			"i","me","my","mine","myself",
			"you","your","yours","yourself",
			"he","him","his","himself",
			"she","her","hers","herself",
			"it","its","itself",
			"we","us","our","ours","ourselves",
			"they","them","their","theirs","themselves"};
	
	/**
	 * All Third-Person Pronouns
	 */
	public final static String[] thirdPersonPronouns = {
			"himself","his","he","him",
			"herself","her","she","hers",
			"itself","its","it",
			"themselves","their","they","them","theirs"};
	
	/**
	 * All gender indicating pronouns:
	 */
	public final static String[] malePronouns = {"himself","his","he","him"};
	public final static String[] femalePronouns = {"herself","her","she","hers"};
	public final static String[] neutralPronouns = {"itself","its","it"};
	public final static String[] pluralPronouns = {"themselves","their","they","them","theirs"};
	
	/**
	 * All reflexive pronouns 
	 */
	public final static String[] reflexivePronouns = {"themselves","himself","herself","itself"};
	
	/**
	 * All gender indicating designators (honorifics)
	 */
	public static final String[] maleDesignators = {"mr","mister","master","lord","sir"};	
	public static final String[] femaleDesignators = {"mrs","ms.","ms","mz","miss","lady","madam","dame"};
	
	/**
	 * Gender corpus files path
	 */
	public static final String genderCorpusDirectory = "src/main/resources/gendercorpus";
	
	/**
	 * Whether noun phrases covering other noun phrases should be removed
	 * All evaluations were done with this value being false
	 */
	public static final boolean removeCoveringNPs = false;
	
	/**
	 * Whether the detected antecedent must exactly match the gold antecedent
	 * All evaluations were done with this value being true
	 */
	public static final boolean exactBoundMatch = true;
    
	/**
	 * Max. Sentence distance to look for an antecedent candidate
	 * All evaluations were done with this value being 1
	 */
	public static final int MAX_SENTENCE_DIST = 1;
    
	/**
	 * Value to accept a detected antecedent (a value of 1f will probably increase the precision but lower recall extremely)
	 * All evaluations were done with this value being 0f
	 */
	public static final float acceptAtThreshold = 0f;
	
	/**
	 * Factor for lowering the threshold in bergsma's approach
	 * All evaluations were done with this value being 0.1f (since bergsma didn't specified any value)
	 */
	public static final float lowerThresholdFactor = 0.1f;
}
