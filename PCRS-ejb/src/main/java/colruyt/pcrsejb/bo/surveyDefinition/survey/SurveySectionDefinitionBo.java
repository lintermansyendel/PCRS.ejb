package colruyt.pcrsejb.bo.surveyDefinition.survey;

import java.io.Serializable;
import java.util.List;

import colruyt.pcrsejb.bo.AbstractBo;
import colruyt.pcrsejb.bo.competence.CompetenceImplBo;
import colruyt.pcrsejb.bo.surveyDefinition.strategy.SurveySectionStrategyBo;

public class SurveySectionDefinitionBo extends AbstractBo implements Serializable {
	/* 
	 * PROPERTIES
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Boolean administratorCreated;
	private SurveySectionTitleBo surveySectionTitle;
	private SurveySectionStrategyBo surveySectionStrategy;
	private List<CompetenceImplBo> surveySectionCompetences;
	/*
	 * CONSTRUCTORS
	 */
	public SurveySectionDefinitionBo() {
		super();
	}
	public SurveySectionDefinitionBo(SurveySectionTitleBo surveySectionTitle, SurveySectionStrategyBo surveySectionStrategy, List<CompetenceImplBo> surveySectionCompetences, Boolean administratorCreated) {
		super();
		this.surveySectionTitle = surveySectionTitle;
		this.surveySectionStrategy = surveySectionStrategy;
		this.surveySectionCompetences = surveySectionCompetences;
		this.administratorCreated = administratorCreated;
	}
	public SurveySectionDefinitionBo(Integer id,SurveySectionTitleBo surveySectionTitle, SurveySectionStrategyBo surveySectionStrategy, List<CompetenceImplBo> surveySectionCompetences, Boolean administratorCreated) {
		super();
		this.id = id;
		this.surveySectionTitle = surveySectionTitle;
		this.surveySectionStrategy = surveySectionStrategy;
		this.surveySectionCompetences = surveySectionCompetences;
		this.administratorCreated = administratorCreated;
	}
	/*
	 * GETTERS AND SETTERS
	 */	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public SurveySectionTitleBo getSurveySectionTitle() {
		return surveySectionTitle;
	}
	public void setSurveySectionTitle(SurveySectionTitleBo surveySectionTitle) {
		this.surveySectionTitle = surveySectionTitle;
	}
	public SurveySectionStrategyBo getSurveySectionStrategy() {
		return surveySectionStrategy;
	}
	public void setSurveySectionStrategy(SurveySectionStrategyBo surveySectionStrategy) {
		this.surveySectionStrategy = surveySectionStrategy;
	}
	public List<CompetenceImplBo> getSurveySectionCompetences() {
		return surveySectionCompetences;
	}
	public void setSurveySectionCompetences(List<CompetenceImplBo> surveySectionCompetences) {
		this.surveySectionCompetences = surveySectionCompetences;
	}
	public Boolean getAdministratorCreated() {
		return administratorCreated;
	}
	public void setAdministratorCreated(Boolean administratorCreated) {
		this.administratorCreated = administratorCreated;
	}
}
