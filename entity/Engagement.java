package com.nineleaps.authentication.jwt.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonProperty;


@Getter @Setter
@Entity
@Table(name = "Engagement_handling")
public class Engagement {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name="eng_id")
	    private Long id;
	   

	    @ManyToOne(fetch = FetchType.EAGER)
	    @JoinColumn(name = "connection_request_id")
        private ConnectionRequest connectionRequest;
	 
	    @Column(name = "start_time")
	    private LocalDateTime startTime;

	    @Column(name = "duration_hours")
	    private int durationHours; 
	    
	    @OneToOne(mappedBy = "engagement",fetch = FetchType.EAGER)
	    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	    private GoalTracker goalTracker;
	    
	    @OneToMany(mappedBy = "engagement", cascade = CascadeType.ALL,fetch=FetchType.EAGER)
	    private List<EngagementStatus> engagementStatuses = new ArrayList<>();

        public Engagement() {
			
		}
        
     
		public Engagement(Long id, ConnectionRequest connectionRequest, LocalDateTime startTime, int durationHours,
				GoalTracker goalTracker, List<EngagementStatus> engagementStatuses) {
			super();
			this.id = id;
			this.connectionRequest = connectionRequest;
			this.startTime = startTime;
			this.durationHours = durationHours;
			this.goalTracker = goalTracker;
			this.engagementStatuses = engagementStatuses;
		}




		public List<EngagementStatus> getEngagementStatuses() {
			return engagementStatuses;
		}


		public void setEngagementStatuses(List<EngagementStatus> engagementStatuses) {
			this.engagementStatuses = engagementStatuses;
		}


		public ConnectionRequest getConnectionRequest() {
			return connectionRequest;
		}
		public void setConnectionRequest(ConnectionRequest connectionRequest) {
			this.connectionRequest = connectionRequest;
		}
		public LocalDateTime getStartTime() {
			return startTime;
		}
		public void setStartTime(LocalDateTime startTime) {
			this.startTime = startTime;
		}
		public int getDurationHours() {
			return durationHours;
		}
		public void setDurationHours(int durationHours) {
			this.durationHours = durationHours;
		}
		
		
		public GoalTracker getGoalTracker() {
			return goalTracker;
		}
		public void setGoalTracker(GoalTracker goalTracker) {
			this.goalTracker = goalTracker;
		}

		public void setId(Long id) {
			this.id = id;
		}
		public Long getId() {
			return id;
		}	

}
