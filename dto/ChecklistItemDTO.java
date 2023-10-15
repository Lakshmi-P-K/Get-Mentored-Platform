package com.nineleaps.authentication.jwt.dto;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.nineleaps.authentication.jwt.enums.ChecklistitemStatus;

public class ChecklistItemDTO {
    private Long id;
    private Long goalTrackerId;
    private String itemDescription;
    
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    @Enumerated(EnumType.STRING)
    private ChecklistitemStatus status;
    
    
	public ChecklistitemStatus getStatus() {
		return status;
	}
	public void setStatus(ChecklistitemStatus status) {
		this.status = status;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getGoalTrackerId() {
		return goalTrackerId;
	}
	public void setGoalTrackerId(Long goalTrackerId) {
		this.goalTrackerId = goalTrackerId;
	}
	public String getItemDescription() {
		return itemDescription;
	}
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
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

    
}
