package com.tatvasoft.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Embeddable
public class UserProjectId implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManyToOne
    @MapsId("uid")
    private User user;
 
    @ManyToOne
    @MapsId("id") 
    private Project project;
	
	public UserProjectId() {
		super();
	}
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
	
	
}

//@Column(name="project_id")
//private int projectId;
//	
//@Column(name="user_uid")
//private int userId;
//public int getUserId() {
//return userId;
//}
//
//
//public void setUserId(int userId) {
//this.userId = userId;
//}
