package com.tatvasoft.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.tatvasoft.entities.Project;
import com.tatvasoft.entities.User;
import com.tatvasoft.facade.ProjectFacade;

@ManagedBean
@SessionScoped
public class ProjectListBean {

	@Inject
	private ProjectFacade projectFacade;
	
	private List<Project> projects;

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}
	
	public ProjectListBean() {
		
	}
	
	@PostConstruct
	public void allProjects() {
		User u = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loggedInUser");
		
		if(u.getRole().equals("admin")) {
			projects = projectFacade.getAllProjects();
		}
		else {
			projects = projectFacade.getAllProjects(u.getUserId());
		}
	}	
}

