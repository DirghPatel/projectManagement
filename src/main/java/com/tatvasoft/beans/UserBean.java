package com.tatvasoft.beans;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.sql.DataSource;

import org.primefaces.model.DualListModel;

import com.tatvasoft.entities.Project;
import com.tatvasoft.entities.User;
import com.tatvasoft.entities.UserProject;
import com.tatvasoft.entities.UserProjectId;
import com.tatvasoft.facade.ProjectFacade;
import com.tatvasoft.facade.UserFacade;

@ManagedBean
@RequestScoped
public class UserBean {
	
	@Inject
	private UserFacade userFacade;
	
	@Inject 
	private ProjectFacade projectFacade;

	private User user;
	private List<String> allUsers = new ArrayList<String>();
	private List<Project> projects = new ArrayList<Project>() ;
	private List<User> allUserObj = new ArrayList<User>();
	private DualListModel<User> dualListUser = new DualListModel<User>();

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<String> getAllUsers() {
    	return allUsers;
    }
    public void setAllUsers(List<String> allUsers) {
    	this.allUsers = allUsers;
    }
	public List<Project> getProjects() {
		return projects;
	}
	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}
	public UserBean() {
	}
	
	@PostConstruct
	public void init() {
		userFacade.getAllUsernames().forEach(u -> {
			allUsers.add(u.getUserName());
		});
		
		user = new User();
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("alreadyUserError");
		
		
		allUserObj = userFacade.getAllUsernames();
		
		User u = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loggedInUser");
		if(u != null) {
			if(u.getRole().equals("admin")) {
				projects = projectFacade.getAllProjects();
			}
			else {
				List<UserProject> up = projectFacade.getAllProjectsId(u.getUserId());
				for(UserProject i: up) {
					projects.add(projectFacade.getProjectById(i.getId().getProject().getId()));
				}
			}
		}
		else {
			projects = new ArrayList<Project>();
		}
		
		
		for(Project p: projects) {
			List<String> ls = new ArrayList<String>();
			for(User user: p.getAssignedTo()) {
				ls.add(user.getUserName());
			}
			p.setAssignedToUserName(ls);
		}
		
		List<User> userSource = userFacade.getAllUsernames();
		List<User> userTarget = new ArrayList<User>();
		setDualListUser(new DualListModel<User>(userSource , userTarget));
		
		System.out.println(projects);
		System.out.println("-------------------------");
		
	}
	
	
	public String add() {
		
		user.setPassword(encryptPassword(user.getPassword()));
		String val = userFacade.addNewUser(user);
		
		if(val == null) {
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("alreadyUserError", "Username already exist.");
			return "addnewuser?faces-redirect=true";
		}
		else {
			return val;
		}
    }

	public String validate() {
		try {
			User u = userFacade.getUser(user.getUserName(), encryptPassword(user.getPassword()));
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("loggedInUser", u);
        	return "dashboard?faces-redirect=true";
		}
		catch(Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();
        	FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "failed", "Authentication failed");
            context.addMessage("message", message);
            return "signin?faces-redirect=true";
		}
		
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
        return "signin?faces-redirect=true";
    }
	public List<User> getAllUserObj() {
		return allUserObj;
	}
	public void setAllUserObj(List<User> allUserObj) {
		this.allUserObj = allUserObj;
	}
	public DualListModel<User> getDualListUser() {
		return dualListUser;
	}
	public void setDualListUser(DualListModel<User> dualListUser) {
		this.dualListUser = dualListUser;
	}
	
	public String encryptPassword(String password) {
		String BasicBase64fromat = Base64.getEncoder().encodeToString(password.getBytes());
		return BasicBase64fromat;
	}
	
}

