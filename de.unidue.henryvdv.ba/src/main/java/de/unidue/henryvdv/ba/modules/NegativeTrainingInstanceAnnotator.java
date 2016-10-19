package de.unidue.henryvdv.ba.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.constituent.NP;
import de.unidue.henryvdv.ba.param.Parameters;
import de.unidue.henryvdv.ba.type.Anaphora;
import de.unidue.henryvdv.ba.type.Antecedent;
import de.unidue.henryvdv.ba.util.AnnotationUtils;
/**
 * Annotates the negative training instances e.g. all intermediate noun phrases 
 * with the getHasCorrectAntecedent-value = false
 * @author Henry
 *
 */
@TypeCapability(
        inputs = {
                "de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token",
                "de.tudarmstadt.ukp.dkpro.core.api.syntax.type.constituent.NP",
                "de.unidue.henryvdv.ba.type.Anaphora"},
        outputs = {
                "de.unidue.henryvdv.ba.type.Anaphora" })
public class NegativeTrainingInstanceAnnotator extends JCasAnnotator_ImplBase{

	private JCas aJCas;
	/**
	 * Required collections:
	 */
	Collection<Anaphora> anaphoras;
	Collection<NP> nps;
	Collection<Token> tokens;
	
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		this.aJCas = aJCas;
		anaphoras = JCasUtil.select(aJCas, Anaphora.class);
		nps = JCasUtil.select(aJCas, NP.class);
		tokens = JCasUtil.select(aJCas, Token.class);
		generateNegativeTrainingInstances();
	}

	/**
	 * Adds all intermediate noun phrases with its anaphora to the JCas
	 */
	public void generateNegativeTrainingInstances(){
		List<Anaphora> negAnaphoras = new ArrayList<Anaphora>();
		for(Anaphora an : anaphoras){
			for(NP np : getNPSBetween(an, an.getAntecedent())){			
				
				Anaphora negInst = new Anaphora(aJCas, an.getBegin(), an.getEnd());	
				Antecedent antecedent = new Antecedent(aJCas, np.getBegin(), np.getEnd());			
				
				List<Token> covTokens = AnnotationUtils.getCoveredTokens(np, tokens);
				
				for(Token t : covTokens){
					if(Arrays.asList(Parameters.allPronouns).contains(t.getCoveredText().toLowerCase())){
						antecedent = new Antecedent(aJCas, t.getBegin(),t.getEnd());
						break;
					}
				}
								
				negInst.setAntecedent(antecedent);
				negInst.setHasCorrectAntecedent(false);
				negAnaphoras.add(negInst);
							
			}
		}
		for(Anaphora a : negAnaphoras){
			a.addToIndexes();
		}
		
	}
	
	/**
	 * Creates a list of all intermediate noun phrases
	 * @param anaphora Current Anaphora
	 * @param antecedent Current Antecedent
	 * @return A List with all intermediate NPs
	 */
	private List<NP> getNPSBetween(Anaphora anaphora, Antecedent antecedent){
		List<NP> npsBetween = new ArrayList<NP>();
		
		for(NP np : nps){
			if(np.getBegin() > antecedent.getEnd() && np.getEnd() > antecedent.getEnd() &&
				np.getBegin() < anaphora.getBegin() && np.getEnd() < anaphora.getBegin()){
					
					npsBetween.add(np);
				
				}
		}
		
		//If parameter is enabled, all nps covering other nps will be removed
		if(Parameters.removeCoveringNPs){
			List<NP> fixedNpsBetween = new ArrayList<NP>();
			for(NP np1 : npsBetween){
				boolean addIt = true;
				for(NP np2 : npsBetween){
					if(np1 != np2){
						if(np1.getBegin() <=  np2.getBegin() && np1.getEnd() >= np2.getEnd()){
							addIt = false;
						}
					}
				}
				if(addIt){
					fixedNpsBetween.add(np1);
				}
			}
			return fixedNpsBetween;
		} 
		return npsBetween;
	}
	
}
