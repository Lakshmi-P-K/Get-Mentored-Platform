package com.nineleaps.authentication.jwt.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.nineleaps.authentication.jwt.enums.ChecklistitemStatus;

@Entity
@Table(name = "checklist_items")
public class ChecklistItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "goal_tracker_id")
    private GoalTracker goalTracker;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ChecklistitemStatus status;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "item_description")
    private String itemDescription;
    @OneToMany(mappedBy = "checklistItem", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<ActivityLog> activityLogs;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public GoalTracker getGoalTracker() {
		return goalTracker;
	}
	public void setGoalTracker(GoalTracker goalTracker) {
		this.goalTracker = goalTracker;
	}
	public String getItemDescription() {
		return itemDescription;
	}
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}
	

	public List<ActivityLog> getActivityLogs() {
		return activityLogs;
	}
	public void setActivityLogs(List<ActivityLog> activityLogs) {
		this.activityLogs = activityLogs;
	}

	public ChecklistItem() {
		
	}
	public ChecklistitemStatus getStatus() {
		return status;
	}
	public void setStatus(ChecklistitemStatus status) {
		this.status = status;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	
	
	 public ChecklistItem(Long id, GoalTracker goalTracker, ChecklistitemStatus status, LocalDateTime createdAt,
			LocalDateTime updatedAt, String itemDescription, List<ActivityLog> activityLogs) {
		super();
		this.id = id;
		this.goalTracker = goalTracker;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.itemDescription = itemDescription;
		this.activityLogs = activityLogs;
	}
	public void addActivityLog(ActivityLog activityLog) {
	        if (activityLogs == null) {
	            activityLogs = new ArrayList<>();
	        }
	        activityLogs.add(activityLog);
	        activityLog.setChecklistItem(this);
	    }
    
}
