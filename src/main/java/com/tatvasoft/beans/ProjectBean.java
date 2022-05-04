package com.tatvasoft.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.primefaces.model.DualListModel;

import com.tatvasoft.entities.Project;
import com.tatvasoft.entities.User;
import com.tatvasoft.facade.ProjectFacade;
import com.tatvasoft.facade.UserFacade;

@ManagedBean
@SessionScoped
public class ProjectBean {

	private Project project;
	
	private Map<String,Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
	private DualListModel<User> dualListUser = new DualListModel<User>();
	private List<String> assignedToUserNames;
	private DualListModel<String> allAssignedTo = new DualListModel<String>();
	
	@Inject
	private ProjectFacade projectFacade;
	
	@Inject
	private UserFacade userFacade;
	
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	
	public DualListModel<User> getDualListUser() {
		return dualListUser;
	}
	public void setDualListUser(DualListModel<User> dualListUser) {
		this.dualListUser = dualListUser;
	}
	public List<String> getAssignedToUserNames() {
		return assignedToUserNames;
	}
	public void setAssignedToUserNames(List<String> assignedToUserNames) {
		this.assignedToUserNames = assignedToUserNames;
	}
	public DualListModel<String> getAllAssignedTo() {
		return allAssignedTo;
	}
	public void setAllAssignedTo(DualListModel<String> allAssignedTo) {
		this.allAssignedTo = allAssignedTo;
	}
	
	public ProjectBean() {
	}
	
	
	@PostConstruct
	public void init() {
		project = new Project();
		List<User> userSource = userFacade.getAllUsernames();
		List<User> userTarget = new ArrayList<User>();
		setDualListUser(new DualListModel<User>(userSource , userTarget));
	}
	
	public String add() throws Exception{
		project.setAssignedTo(dualListUser.getTarget());
		projectFacade.addNewProject(project);
		clear();
        return "dashboard?faces-redirect=true";
    }
	
	public void clear() {
		project.setRequirements("");
		project.setStatus("");
		project.setTitle("");
	}
	
	public String editProject(int id) {
		Project p =projectFacade.getProjectById(id);
		
//		DualListModel<User> dualListU = new DualListModel<User>();
//		List<User> assignedUserSource = userFacade.getAllUsernames();
//		List<User> users = userFacade.getAllUsernames();
		List<User> assignedUserSource = new ArrayList<User>();
		List<User> assignedUserTarget = p.getAssignedTo();

		for(User i: userFacade.getAllUsernames()) {
			System.out.println(assignedUserTarget.contains(i));
			if(!assignedUserTarget.contains(i)) {
				assignedUserSource.add(i);
			}
		}
		dualListUser = new DualListModel<User>(assignedUserSource , assignedUserTarget);
//		sessionMap.put("assignedUserPickList", dualListUser);
		sessionMap.put("editProject", p);
        return "updateform?faces-redirect=true";
	}
	public void allAssignedToUsers(int id) {
		Project p =projectFacade.getProjectById(id);
//		List<User> users = userFacade.getAllUsernames();
//		List<User> assignedUserAll = p.getAssignedTo();
		List<String> assignedUserSource = new ArrayList<String>();
		List<String> assignedUserTarget = new ArrayList<String>();
		for(User i: p.getAssignedTo()) {
			assignedUserTarget.add(i.getUserName());
		}
		for(User i: userFacade.getAllUsernames()) {
			if(!assignedUserTarget.contains(i.getUserName())) {
				assignedUserSource.add(i.getUserName());
			}
		}
		allAssignedTo = new DualListModel<String>(assignedUserSource , assignedUserTarget);
	}
	
	public String updateProject(Project p) {
		System.out.println(dualListUser.getTarget());
		p.setAssignedTo(dualListUser.getTarget());
		projectFacade.updateProject(p);
		return "dashboard?faces-redirect=true";
    }
	
	
}

