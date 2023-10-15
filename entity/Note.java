package com.nineleaps.authentication.jwt.entity;


import java.time.LocalDateTime;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nineleaps.authentication.jwt.enums.NoteVisibility;

@Entity
@Table(name = "notes")
public class Note {
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    private String title;
	    @Column(length = 5000)
	    private String content;
	   
	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "user_id")
	    private User user;

	
	    @Enumerated(EnumType.STRING)
	    @Column(nullable = false)
	    private NoteVisibility visibility;
	    @JsonIgnoreProperties("notes")
	    
	    @ManyToOne(fetch = FetchType.EAGER)
	    @JoinColumn(name = "engagement_id")
	    private Engagement engagement;

	    private LocalDateTime createdTime;
	    private LocalDateTime updatedTime;
		public Note() {
			
		}
		
		
	    
		public LocalDateTime getCreatedTime() {
			return createdTime;
		}



		public void setCreatedTime(LocalDateTime createdTime) {
			this.createdTime = createdTime;
		}



		public LocalDateTime getUpdatedTime() {
			return updatedTime;
		}



		public void setUpdatedTime(LocalDateTime updatedTime) {
			this.updatedTime = updatedTime;
		}



		public User getUser() {
			return user;
		}
	
		public void setUser(User user) {
			this.user = user;
		}
	
		public Long getId() {
			return id;
		}
	
		public void setId(Long id) {
			this.id = id;
		}
	
		public String getTitle() {
			return title;
		}
	
		public void setTitle(String title) {
			this.title = title;
		}
	
		public String getContent() {
			return content;
		}
	
		public void setContent(String content) {
			this.content = content;
		}
	
		
	
		public NoteVisibility getVisibility() {
			return visibility;
		}
	
		public void setVisibility(NoteVisibility visibility) {
			this.visibility = visibility;
		}
	
		public Engagement getEngagement() {
			return engagement;
		}
	
		public void setEngagement(Engagement engagement) {
			this.engagement = engagement;
		}
	
		public Note(Long id, String title, String content, User mentee, User mentor, NoteVisibility visibility,
				Engagement engagement) {
			super();
			this.id = id;
			this.title = title;
			this.content = content;
		
			this.visibility = visibility;
			this.engagement = engagement;
		}
	
		
	
		public void setEngagementId(Long engagementId) {
			
		}
	
		public Long setUserId(Long userId) {
			
			return userId;
			
		}
		   
	}
		   
		   
	

