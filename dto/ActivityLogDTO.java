package com.nineleaps.authentication.jwt.dto;

import java.time.LocalDateTime;

public class ActivityLogDTO {
    private Long id;
    private String activityType;
    private LocalDateTime activityTime;
    private String userName;
    private Long checklistId; 
    private String itemDescription;
    

    public Long getId() {
        return id;
    }

    public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public Long getChecklistId() {
		return checklistId;
	}

	public void setChecklistId(Long checklistId) {
		this.checklistId = checklistId;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
