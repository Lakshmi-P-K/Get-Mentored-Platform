package com.nineleaps.authentication.jwt.entity;

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
import javax.persistence.Table;

import com.nineleaps.authentication.jwt.enums.EngStatus;
import lombok.Getter;

import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter

@Entity
@Table(name = "engagement_status")
public class EngagementStatus {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "engagement_status_id")
	    private Long engagementStatusId;
	    @ManyToOne(fetch = FetchType.EAGER)
	    @JoinColumn(name = "engagement_id")
	    private Engagement engagement;
	    @Enumerated(EnumType.STRING)
	    private EngStatus mentorEngStatus;
	    @Enumerated(EnumType.STRING)
	    private EngStatus menteeEngStatus;
		@Column(name = "mentor_status_timestamp")
		private LocalDateTime mentorStatusTimestamp;

		@Column(name = "mentee_status_timestamp")
		private LocalDateTime menteeStatusTimestamp;

		@Column(name="Completed_Eng_Timestamp")
		private LocalDateTime completedEngStatusTimestamp;
public EngagementStatus() {
	
}


	public Long getEngagementStatusId() {
			return engagementStatusId;
		}



		public void setEngagementStatusId(Long engagementStatusId) {
			this.engagementStatusId = engagementStatusId;
		}



		public Engagement getEngagement() {
			return engagement;
		}



		public void setEngagement(Engagement engagement) {
			this.engagement = engagement;
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



		public EngStatus getMentorEngStatus() {
			return mentorEngStatus;
		}



		public EngStatus getMenteeEngStatus() {
			return menteeEngStatus;
		}



	public void setMentorEngStatus(EngStatus mentorEngStatus) {
		if (this.mentorEngStatus != mentorEngStatus) {
			this.mentorEngStatus = mentorEngStatus;
			this.mentorStatusTimestamp = LocalDateTime.now();
				}
	}


	public EngagementStatus(Long engagementStatusId, Engagement engagement, EngStatus mentorEngStatus,
			EngStatus menteeEngStatus, LocalDateTime mentorStatusTimestamp, LocalDateTime menteeStatusTimestamp,
			LocalDateTime completedEngStatusTimestamp) {
		super();
		this.engagementStatusId = engagementStatusId;
		this.engagement = engagement;
		this.mentorEngStatus = mentorEngStatus;
		this.menteeEngStatus = menteeEngStatus;
		this.mentorStatusTimestamp = mentorStatusTimestamp;
		this.menteeStatusTimestamp = menteeStatusTimestamp;
		this.completedEngStatusTimestamp = completedEngStatusTimestamp;
	}



	public void setMenteeEngStatus(EngStatus menteeEngStatus) {
		if (this.menteeEngStatus != menteeEngStatus) {
			this.menteeEngStatus = menteeEngStatus;
			this.menteeStatusTimestamp = LocalDateTime.now();

		}
	}
	}
	










