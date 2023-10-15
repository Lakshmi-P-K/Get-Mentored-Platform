package com.nineleaps.authentication.jwt.dto;

import com.nineleaps.authentication.jwt.enums.EngStatus;

import lombok.Getter;

import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter



public class EngagementStatusDTO {
    private Long engagementStatusId;
    private Long engagementId;
    private EngStatus mentorEngStatus;
    private EngStatus menteeEngStatus;
    private LocalDateTime mentorStatusTimestamp;
    private LocalDateTime menteeStatusTimestamp;
    private LocalDateTime completedEngStatusTimestamp;
    public EngagementStatusDTO() {
        this.mentorEngStatus = EngStatus.PENDING;
        this.menteeEngStatus = EngStatus.PENDING;
    }
    
	public Long getEngagementStatusId() {
		return engagementStatusId;
	}
	public void setEngagementStatusId(Long engagementStatusId) {
		this.engagementStatusId = engagementStatusId;
	}
	public Long getEngagementId() {
		return engagementId;
	}
	public void setEngagementId(Long engagementId) {
		this.engagementId = engagementId;
	}
	public EngStatus getMentorEngStatus() {
		return mentorEngStatus;
	}
	public void setMentorEngStatus(EngStatus mentorEngStatus) {
		this.mentorEngStatus = mentorEngStatus;
	}
	public EngStatus getMenteeEngStatus() {
		return menteeEngStatus;
	}
	public void setMenteeEngStatus(EngStatus menteeEngStatus) {
		this.menteeEngStatus = menteeEngStatus;
	}
	public LocalDateTime getMentorStatusTimestamp() {
		return mentorStatusTimestamp;
	}
	public void setMentorStatusTimestamp(LocalDateTime mentorStatusTimestamp) {
		this.mentorStatusTimestamp = mentorStatusTimestamp;
	}
	public LocalDateTime getMenteeStatusTimestamp() {
		return menteeStatusTimestamp;
	}
	public void setMenteeStatusTimestamp(LocalDateTime menteeStatusTimestamp) {
		this.menteeStatusTimestamp = menteeStatusTimestamp;
	}
	public LocalDateTime getCompletedEngStatusTimestamp() {
		return completedEngStatusTimestamp;
	}
	public void setCompletedEngStatusTimestamp(LocalDateTime completedEngStatusTimestamp) {
		this.completedEngStatusTimestamp = completedEngStatusTimestamp;
	}
	public EngagementStatusDTO(Long engagementStatusId, Long engagementId, EngStatus mentorEngStatus,
			EngStatus menteeEngStatus, LocalDateTime mentorStatusTimestamp, LocalDateTime menteeStatusTimestamp,
			LocalDateTime completedEngStatusTimestamp) {
		super();
		this.engagementStatusId = engagementStatusId;
		this.engagementId = engagementId;
		this.mentorEngStatus = mentorEngStatus;
		this.menteeEngStatus = menteeEngStatus;
		this.mentorStatusTimestamp = mentorStatusTimestamp;
		this.menteeStatusTimestamp = menteeStatusTimestamp;
		this.completedEngStatusTimestamp = completedEngStatusTimestamp;
	}
    

}