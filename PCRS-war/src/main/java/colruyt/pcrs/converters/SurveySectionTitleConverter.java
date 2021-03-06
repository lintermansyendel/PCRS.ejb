package colruyt.pcrs.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import colruyt.pcrsejb.bo.surveyDefinition.survey.SurveySectionTitleBo;

/**
 * SURVEY SECTION TITLE CONVERTER
 * @author jda1mbw
 */
@FacesConverter("surveySectionTitleConverter")
public class SurveySectionTitleConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		SurveySectionTitleBo sst = new SurveySectionTitleBo();
		String[] parts = value.split(";");
		sst.setId(Integer.valueOf(parts[0]));
		sst.setTitle(parts[1]);
		return sst;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		SurveySectionTitleBo sst = (SurveySectionTitleBo) value;
		String s = sst.getId() + ";" + sst.getTitle() + ";";
		return s;
	}
}
