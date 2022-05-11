package com.tatvasoft.entities;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "USER_PROJECT")
public class UserProject {
	
	@EmbeddedId
	private UserProjectId id = new UserProjectId();

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


//@ManyToOne
//@MapsId("userId")
//private User user;
//
//@ManyToOne
//@MapsId("id")
//private Project project;

