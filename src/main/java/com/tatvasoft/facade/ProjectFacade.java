package com.tatvasoft.facade;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.tatvasoft.entities.Project;
import com.tatvasoft.entities.UserProject;
import com.tatvasoft.entities.UserProjectId;

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
	
	public List<Project> getAllProjects(int id){
		List<Project> projects = entityManager.createQuery("from Project p where p.assignedTo = ?1" , Project.class)
				.setParameter(1, id)
				.getResultList();
		
		return projects;
	}
	
	public List<UserProject> getAllProjectsId(int userId){
		
		return entityManager.createQuery("from UserProject where user_uid = ?1" , UserProject.class)
				.setParameter(1, userId)
				.getResultList();
	}
	
	public Project getProjectById(int id) {
		List<Project> project = entityManager.createQuery("from Project p where p.id = ?1" , Project.class)
				.setParameter(1, id)
				.getResultList();
		
		return project.get(0);
	}
	
	@Transactional(rollbackOn = Exception.class)
	public void deleteUserProject(int projectId) {
		try {
			entityManager.createQuery("delete UserProject where project_id = ?1")
				.setParameter(1, projectId)
				.executeUpdate();
		}
		catch(Exception e) {
			throw e;
		}
	}
	
	@Transactional(rollbackOn = Exception.class)
	public void hardDeleteProject(int projectId) {
		try {
			entityManager.createQuery("delete Project where id = ?1")
				.setParameter(1, projectId)
				.executeUpdate();
		}
		catch(Exception e) {
			throw e;
		}
	}
	
}
