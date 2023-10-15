package com.nineleaps.authentication.jwt.dto;

import java.time.LocalDateTime;

import com.nineleaps.authentication.jwt.enums.NoteVisibility;

public class NoteDTO {
	 private Long userId;
	 private Long id;
	    private String title;
	    private String content;
	    private NoteVisibility visibility;
	    private Long engagementId;
	    private LocalDateTime createdTime;
	    private LocalDateTime updatedTime;
	    
	    public NoteDTO() {
		
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


		public Long getId() {
			return id;
		}


		public void setId(Long id) {
			this.id = id;
		}


		public Long getUserId() {
			return userId;
		}

		public void setUserId(Long userId) {
			this.userId = userId;
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

		public Long getEngagementId() {
			return engagementId;
		}

		public void setEngagementId(Long engagementId) {
			this.engagementId = engagementId;
		}


		public NoteDTO(Long userId, Long id, String title, String content, NoteVisibility visibility, Long engagementId,
				LocalDateTime createdTime, LocalDateTime updatedTime) {
			super();
			this.userId = userId;
			this.id = id;
			this.title = title;
			this.content = content;
			this.visibility = visibility;
			this.engagementId = engagementId;
			this.createdTime = createdTime;
			this.updatedTime = updatedTime;
		}

		
	    
}