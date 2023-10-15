package com.nineleaps.authentication.jwt.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "activity_logs")
public class ActivityLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "activity_type")
    private String activityType;

    @Column(name = "activity_time")
    private LocalDateTime activityTime;

    @Column(name="user_name")
    private String userName; 

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "checklist_item_id")
    private ChecklistItem checklistItem;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public LocalDateTime getActivityTime() {
		return activityTime;
	}

	public void setActivityTime(LocalDateTime activityTime) {
		this.activityTime = activityTime;
	}

	public ChecklistItem getChecklistItem() {
		return checklistItem;
	}

	public void setChecklistItem(ChecklistItem checklistItem) {
		this.checklistItem = checklistItem;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

 
}
