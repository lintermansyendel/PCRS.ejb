package colruyt.pcrsejb.util.exceptions.validation.surveyDefinition.strategy;

import colruyt.pcrsejb.util.exceptions.validations.ValidationException;

public class SurveySectionStrategyCantBeDeletedException extends ValidationException {

	private static final long serialVersionUID = 1L;
	
	public SurveySectionStrategyCantBeDeletedException() {
		super("Section strategy can't be deleted.");
	}
}
