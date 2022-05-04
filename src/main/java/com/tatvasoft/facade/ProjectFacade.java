package com.tatvasoft.facade;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.tatvasoft.entities.Project;

@Stateless
public class ProjectFacade {

	@PersistenceContext(unitName = "usermanager")
	private EntityManager entityManager;
	
	@Transactional(rollbackOn = Exception.class)
	public void addNewProject(Project project) {
		entityManager.persist(project);
	}
	
	@Transactional(rollbackOn = Exception.class)
	public void updateProject(Project project) {
		entityManager.merge(project);
	}
	
	public List<Project> getAllProjects(){
		List<Project> projects = entityManager.createQuery("from Project p" , Project.class)
				.getResultList();
		
		return projects;
	}
	
	public List<Project> getAllProjects(String userName){
		List<Project> projects = entityManager.createQuery("from Project p where p.assignedTo = ?1" , Project.class)
				.setParameter(1, userName)
				.getResultList();
		
		return projects;
	}
	
	public Project getProjectById(int id) {
		List<Project> project = entityManager.createQuery("from Project p where p.id = ?1" , Project.class)
				.setParameter(1, id)
				.getResultList();
		
		return project.get(0);
	}
	
}
