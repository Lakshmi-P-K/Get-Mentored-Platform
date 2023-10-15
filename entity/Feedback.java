package com.nineleaps.authentication.jwt.entity;


import java.time.LocalDateTime;

import javax.persistence.*;

@Entity
@Table(name = "feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mentee_id")
    private User mentee;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mentor_id")
    private User mentor;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "engagement_id")
    private Engagement engagement;
    private Double mentorRating;
    private Double menteeRating;
    @Column(length = 500)
    private String mentorFeedback;
    private String menteeFeedback;
    private Double engagementRating;
    @Column(length = 500)
    private String engagementFeedback;
    private String feedbackFromUserName;
    private LocalDateTime createdTime;
    public Feedback() {
		
	}
    
    public LocalDateTime getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(LocalDateTime createdTime) {
		this.createdTime = createdTime;
	}

	public String getFeedbackFromUserName() {
		return feedbackFromUserName;
	}
    public void setFeedbackFromUserName(String feedbackFromUserName) {
		this.feedbackFromUserName = feedbackFromUserName;
	}
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public User getMentee() {
		return mentee;
	}


	public void setMentee(User mentee) {
		this.mentee = mentee;
	}


	public User getMentor() {
		return mentor;
	}


	public void setMentor(User mentor) {
		this.mentor = mentor;
	}


	public Engagement getEngagement() {
		return engagement;
	}


	public void setEngagement(Engagement engagement) {
		this.engagement = engagement;
	}


	public Double getMentorRating() {
		return mentorRating;
	}


	public void setMentorRating(Double mentorRating) {
		this.mentorRating = mentorRating;
	}


	public Double getMenteeRating() {
		return menteeRating;
	}


	public void setMenteeRating(Double menteeRating) {
		this.menteeRating = menteeRating;
	}


	public String getMentorFeedback() {
		return mentorFeedback;
	}


	public void setMentorFeedback(String mentorFeedback) {
		this.mentorFeedback = mentorFeedback;
	}


	public String getMenteeFeedback() {
		return menteeFeedback;
	}


	public void setMenteeFeedback(String menteeFeedback) {
		this.menteeFeedback = menteeFeedback;
	}


	public Double getEngagementRating() {
		return engagementRating;
	}


	public void setEngagementRating(Double engagementRating) {
		this.engagementRating = engagementRating;
	}


	public String getEngagementFeedback() {
		return engagementFeedback;
	}


	public void setEngagementFeedback(String engagementFeedback) {
		this.engagementFeedback = engagementFeedback;
	}

	public Feedback(Long id, User mentee, User mentor, Engagement engagement, Double mentorRating, Double menteeRating,
			String mentorFeedback, String menteeFeedback, Double engagementRating, String engagementFeedback,
			String feedbackFromUserName, LocalDateTime createdTime) {
		super();
		this.id = id;
		this.mentee = mentee;
		this.mentor = mentor;
		this.engagement = engagement;
		this.mentorRating = mentorRating;
		this.menteeRating = menteeRating;
		this.mentorFeedback = mentorFeedback;
		this.menteeFeedback = menteeFeedback;
		this.engagementRating = engagementRating;
		this.engagementFeedback = engagementFeedback;
		this.feedbackFromUserName = feedbackFromUserName;
		this.createdTime = createdTime;
	}


	
    
}
