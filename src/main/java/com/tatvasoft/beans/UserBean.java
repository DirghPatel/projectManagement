package com.tatvasoft.beans;

import java.util.ArrayList;
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
	private List<Project> projects ;
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
		
		allUserObj = userFacade.getAllUsernames();
		
		User u = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loggedInUser");
		if(u != null) {
			if(u.getRole().equals("admin")) {
				projects = projectFacade.getAllProjects();
			}
			else {
				projects = projectFacade.getAllProjects(u.getUserName());
			}
		}
		else {
			projects = new ArrayList<Project>();
		}
		
		user = new User();
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("alreadyUserError");
		
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
	}
	
	
	public String add() {
		
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

//        List<User> users = userFacade.getUser(user.getUserName(), user.getPassword());
//        if(users.size() != 0) {
//        if(!users.isEmpty()) {
//        	FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("loggedInUser", users.get(0));
//        	return "dashboard?faces-redirect=true";
//        }
//        else {
//        	FacesContext context = FacesContext.getCurrentInstance();
//        	FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "failed", "Authentication failed");
//            context.addMessage(null, message);
//            System.out.println("00000000");
//        	return "signin?faces-redirect=true";
//        }
		
		try {
			User u = userFacade.getUser(user.getUserName(), user.getPassword());
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("loggedInUser", u);
        	return "dashboard?faces-redirect=true";
		}
		catch(Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();
        	FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "failed", "Authentication failed");
            context.addMessage(null, message);
            System.out.println(context.getMessageList() + " " + e);
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
	
}

