package org.archisoft.test;

import java.util.Date;


public class TaskBean {
	
	private String taskId;
	private String taskNumber;
	private String title;
	private String priority;
	private String state;
	private Date updatedDate;
	private String updatedBy;
	private String creator;
	private String outcome;
	private Date createdDate;
	private String assigneeUsers;
	private String assigneeGroups;
	
	
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getTaskNumber() {
		return taskNumber;
	}
	public void setTaskNumber(String taskNumber) {
		this.taskNumber = taskNumber;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getOutcome() {
		return outcome;
	}
	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}

	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getAssigneeUsers() {
		return assigneeUsers;
	}
	public void setAssigneeUsers(String assigneeUsers) {
		this.assigneeUsers = assigneeUsers;
	}
	public String getAssigneeGroups() {
		return assigneeGroups;
	}
	public void setAssigneeGroups(String assigneeGroups) {
		this.assigneeGroups = assigneeGroups;
	}
	
}
