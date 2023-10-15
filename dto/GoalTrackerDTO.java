package com.nineleaps.authentication.jwt.dto;

import java.time.LocalDateTime;



public class GoalTrackerDTO {
    private Long id;
    private Long engagementId;
    private String description;
 
    private Long userId;
    private LocalDateTime goalTrackerStartTime;
  

    public GoalTrackerDTO() {
    }

    public GoalTrackerDTO(Long id, Long engagementId, String description,
            Long userId, LocalDateTime goalTrackerStartTime) {
        this.id = id;
        this.engagementId = engagementId;
        this.description = description;
   
        this.userId = userId;
        this.goalTrackerStartTime = goalTrackerStartTime;
     
    }

    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEngagementId() {
        return engagementId;
    }

    public void setEngagementId(Long engagementId) {
        this.engagementId = engagementId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getGoalTrackerStartTime() {
        return goalTrackerStartTime;
    }

    public void setGoalTrackerStartTime(LocalDateTime goalTrackerStartTime) {
        this.goalTrackerStartTime = goalTrackerStartTime;
    }


}
