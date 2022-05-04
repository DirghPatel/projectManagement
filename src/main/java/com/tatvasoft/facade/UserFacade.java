package com.tatvasoft.facade;

import java.util.List;

import javax.ejb.Stateless;
//import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.faces.bean.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.tatvasoft.entities.User;

@Stateless
public class UserFacade {

	@PersistenceContext(unitName = "usermanager")
	private EntityManager entityManager;

	@Transactional(rollbackOn = Exception.class)
	public String addNewUser(User user) {
		
		User alreadyUser = new User();
		try {
			alreadyUser = entityManager.createQuery("from User u where u.userName = ?1" , User.class)
					.setParameter(1, user.getUserName())
					.getSingleResult();
			return null;
		}
		catch(NoResultException ex) {
			
			alreadyUser = null;
			entityManager.persist(user);
			return "dashboard?faces-redirect=true";
		}
	}
	
//	public List<User> getUser(String userName , String password) {
//		List<User> user = entityManager.createQuery("from User u where u.userName = ?1 and u.password = ?2" , User.class)
//			.setParameter(1, userName)
//			.setParameter(2, password)
//			.getResultList();
//		if(user != null)
//			return user;
//		else
//			return null;
//	}
	
	public User getUser(String userName , String password) {
		return entityManager.createQuery("from User u where u.userName = ?1 and u.password = ?2" , User.class)
			.setParameter(1, userName)
			.setParameter(2, password)
			.getSingleResult();
//		return user;
	}
	
	public User getUserByUserName(String userName) throws Exception {
		try {
			User user = entityManager.createQuery("from User u where u.userName = ?1" , User.class)
					.setParameter(1, userName)
					.getSingleResult();
			return user;
		}
		catch(NoResultException ex) {
			
			return null;
		}
		catch(Exception e) {
			throw new Exception(e);
		}
	}
	
	public List<User> getAllUsernames(){
		List<User> usernames = entityManager.createQuery("from User u" , User.class)
				.getResultList();
		return usernames;
	}
	
	
}

