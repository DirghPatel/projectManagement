package com.tatvasoft.entities;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "USER_PROJECT")
public class UserProject {
	
	@EmbeddedId
	private UserProjectId id;
	
	@ManyToOne
    @MapsId("userId")
    private User user;
 
    @ManyToOne
    @MapsId("id")
    private Project project;
    
    
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public UserProjectId getId() {
		return id;
	}

	public void setId(UserProjectId id) {
		this.id = id;
	}	

	public UserProject() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
