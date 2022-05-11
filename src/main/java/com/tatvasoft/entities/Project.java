package com.tatvasoft.entities;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "project")
public class Project {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	private String title;
	private String requirements;
	private String status;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name = "USER_PROJECT" , 
				joinColumns = {@JoinColumn(name = "project_id" , referencedColumnName = "id")},
				inverseJoinColumns = {@JoinColumn(name = "user_uid" , referencedColumnName = "uid")})
	private List<User> assignedTo;
	
	@Transient
	private List<String> assignedToUserName;
	
	public List<User> getAssignedTo() {
		return assignedTo;
	}
	public void setAssignedTo(List<User> assignedTo) {
		this.assignedTo = assignedTo;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getRequirements() {
		return requirements;
	}
	public void setRequirements(String requirements) {
		this.requirements = requirements;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Project() {
		super();
	}
	@Override
	public String toString() {
		return "Project [id=" + id + ", title=" + title + ", requirements=" + requirements + ", status=" + status
				+ ", assignedTo=" + assignedTo + "]";
	}
	public List<String> getAssignedToUserName() {
		return assignedToUserName;
	}
	public void setAssignedToUserName(List<String> assignedToUserName) {
		this.assignedToUserName = assignedToUserName;
	}

}
