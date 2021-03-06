package colruyt.pcrsejb.facade.surveyDefinition.survey;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import colruyt.pcrsejb.bo.surveyDefinition.survey.SurveySectionDefinitionImplBo;
import colruyt.pcrsejb.converter.surveyDefinition.survey.SurveySectionDefinitionImplConverter;
import colruyt.pcrsejb.service.bl.surveyDefinition.survey.ISurveySectionDefinitionImplServiceBl;
import colruyt.pcrsejb.util.exceptions.validations.ValidationException;

@Stateless
public class SurveySectionDefinitionImplFacade implements ISurveySectionDefinitionImplFacade {

	@EJB
	private ISurveySectionDefinitionImplServiceBl surveySectionDefinitionImplBl;
	
	private SurveySectionDefinitionImplConverter surveySectionDefinitionImplConverter = new SurveySectionDefinitionImplConverter();
	
	@Override
	public SurveySectionDefinitionImplBo save(SurveySectionDefinitionImplBo entityBo) throws ValidationException {
		return surveySectionDefinitionImplConverter.convertToBo(
				surveySectionDefinitionImplBl.save(surveySectionDefinitionImplConverter.convertToEntity(entityBo)));
	}

	@Override
	public SurveySectionDefinitionImplBo get(SurveySectionDefinitionImplBo entityBo) throws ValidationException {
		return surveySectionDefinitionImplConverter.convertToBo(surveySectionDefinitionImplBl.get(
				surveySectionDefinitionImplConverter.convertToEntity(entityBo)));  
	}

	@Override
	public List<SurveySectionDefinitionImplBo> getAll() {
		return surveySectionDefinitionImplConverter.convertToBos(surveySectionDefinitionImplBl.getAll()); 
	}

	@Override
	public void delete(SurveySectionDefinitionImplBo entityBo) throws ValidationException {
		surveySectionDefinitionImplBl.delete(surveySectionDefinitionImplConverter.convertToEntity(entityBo));
	}

	

}
