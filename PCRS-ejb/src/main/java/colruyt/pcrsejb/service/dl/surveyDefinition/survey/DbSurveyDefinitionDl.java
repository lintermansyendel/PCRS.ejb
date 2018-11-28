
package colruyt.pcrsejb.service.dl.surveyDefinition.survey;

import java.io.Serializable;
import java.util.EmptyStackException;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import colruyt.pcrsejb.entity.surveyDefinition.survey.SurveyDefinition;
import colruyt.pcrsejb.entity.user.User;

@Stateless
public class DbSurveyDefinitionDl implements Serializable, ISurveyDefinitionDl {

	
	private static final long serialVersionUID = 1L;
	
	
	@PersistenceContext(unitName = "PCRSEJB")
	private EntityManager em;
	
	
	@Override
	public SurveyDefinition save(SurveyDefinition element) {
		SurveyDefinition surveyDefinition = em.merge(element);
		if (surveyDefinition == null) {
			throw new EmptyStackException();
		}
		return surveyDefinition;
	}

	@Override
	public SurveyDefinition get(SurveyDefinition element) {
		SurveyDefinition surveyDefinition = em.find(SurveyDefinition.class, element.getId());
		if (surveyDefinition == null) {
			throw new EmptyStackException();
		}
		return surveyDefinition;
	}

	@Override
	public List<SurveyDefinition> getAll() {
		TypedQuery<SurveyDefinition> query = em.createNamedQuery("SURVEYDEFINITION.GETALL", SurveyDefinition.class);
		return query.getResultList();
	}

	@Override
	public void delete(SurveyDefinition element) {
		SurveyDefinition surveyDefinition = em.find(SurveyDefinition.class, element);
		if (surveyDefinition == null) {
			throw new EmptyStackException();
		}
		em.remove(element);
	}

	@Override
	public List<SurveyDefinition> getSurveyDefinitionsOfUser(User user) {
		TypedQuery<SurveyDefinition> q = em.createNamedQuery("SURVEYDEFINITION.GETBYRESPONSIBLE", SurveyDefinition.class);
		q.setParameter("responsibleUser", user);
		List<SurveyDefinition> listOfSurveyDefinitions = q.getResultList();
		return listOfSurveyDefinitions;

	}

	@Override
	public User getResponsible(SurveyDefinition surveyDefinition) {
		Query q = em.createNativeQuery("SELECT * FROM Users u INNER JOIN userprivileges up ON u.ID = up.USER_ID WHERE PRIVILEGETYPE = 'SURVEYDEFINITIONRESPONSIBLE' AND SURVEYDEFINITION_ID = :surveyDefinitionID", User.class);
		q.setParameter("surveyDefinitionID", surveyDefinition.getId());
		User u = (User) q.getSingleResult();
		return u;
	}


}


