package colruyt.pcrsejb.service.dl.user;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import colruyt.pcrsejb.entity.user.User;

@Stateless
public class DbUserServiceDl implements Serializable, IUserServiceDl {
	@PersistenceContext(unitName = "PCRSEJB")
	private EntityManager em;

	private static final long serialVersionUID = 1L;

	@Override
	public User save(User element) {
		try {
			em.persist(element);
		} catch (EntityExistsException eee) {
			em.find(User.class, element.getId());
			element = em.merge(element);
		}
		return element;
	}

	@Override
	public User get(User element) {
		User user = em.find(User.class, element);
		if (user == null) {
			throw new EntityNotFoundException();
		}
		return user;
	}

	@Override
	public List<User> getAll() {
		TypedQuery<User> q = em.createNamedQuery("USER.GETALL", User.class);
		List<User> userList = q.getResultList();
		return userList;
	}

	@Override
	public void delete(User element) {
		User user = em.find(User.class, element);
		if (user != null) {
			em.remove(element);
		}
	}

	@Override
	public User getElementByEmail(String email) {
		TypedQuery<User> q = em.createNamedQuery("USER.GETBYEMAIL", User.class);
		q.setParameter("email", email);
		return (User) q.getSingleResult();
	}

	@Override
	public List<User> getUsersByShortName(String shortName) {
		TypedQuery<User> q = em.createNamedQuery("USER.GETBYSHORTNAME", User.class);
		q.setParameter("shortname", shortName);
		List<User> resultList = q.getResultList();
		return resultList;
	}
}
