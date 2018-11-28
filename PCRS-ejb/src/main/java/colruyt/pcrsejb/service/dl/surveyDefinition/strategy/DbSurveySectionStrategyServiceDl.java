package colruyt.pcrsejb.service.dl.surveyDefinition.strategy;

import java.io.Serializable;
import java.util.EmptyStackException;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import colruyt.pcrsejb.entity.surveyDefinition.strategy.SurveySectionStrategy;
import colruyt.pcrsejb.entity.user.User;

@Stateless
public class DbSurveySectionStrategyServiceDl implements Serializable, ISurveySectionStrategyServiceDL{
	@PersistenceContext(unitName = "PCRSEJB")
	private EntityManager em;
	
	private static final long serialVersionUID = 1L;

	@Override
	public SurveySectionStrategy save(SurveySectionStrategy element) {
		try {
			em.persist(element);
		} catch (EntityExistsException eee) {
			em.find(SurveySectionStrategy.class, element.getId());
			element = em.merge(element);
		}
		return element;
	}

	@Override
	public SurveySectionStrategy get(SurveySectionStrategy element) {
		SurveySectionStrategy surveySectionStrategy = em.find(SurveySectionStrategy.class, element.getId());
		if (surveySectionStrategy == null) {
			throw new EntityNotFoundException();
		}
		return surveySectionStrategy;
	}

	@Override
	public List<SurveySectionStrategy> getAll() {
		TypedQuery<SurveySectionStrategy> q = em.createNamedQuery("SURVEYSECTIONSTRATEGY.GETALL", SurveySectionStrategy.class);
		List<SurveySectionStrategy> surveySectionStrategyList = q.getResultList();
		return surveySectionStrategyList;
	}

	@Override
	public void delete(SurveySectionStrategy element) {
		SurveySectionStrategy surveySectionStrategy = em.find(SurveySectionStrategy.class, element);
		if (surveySectionStrategy != null) {
			em.remove(element);
		}
	}

	
	
}
