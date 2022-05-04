package com.tatvasoft.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;

@Embeddable
public class UserProjectId implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name="project_id")
	private int Id;
	
	@Column(name="user_userId")
	private int userID;
	
	
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		this.Id = id;
	}
	public UserProjectId() {
		super();
	}
	
	
	
}
