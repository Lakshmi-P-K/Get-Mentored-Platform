package com.nineleaps.authentication.jwt.dto;

import java.time.LocalDateTime;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;


import com.nineleaps.authentication.jwt.enums.BookingStatus;


    public class SlotDTO {

        private Long id;
        private LocalDateTime startDateTime;
        private LocalDateTime endDateTime;
        private Long mentorId;
        @Enumerated(EnumType.STRING)
        private BookingStatus status;
	    public BookingStatus getStatus() {
				return status;
		}
	
	    public void setStatus(BookingStatus status) {
				this.status = status;
		}
	
		public SlotDTO() {
		
		}
		
		public Long getId() {
			return id;
		}
		
		public void setId(Long id) {
			this.id = id;
		}
		
		public LocalDateTime getStartDateTime() {
			return startDateTime;
		}
		
		public void setStartDateTime(LocalDateTime startDateTime) {
			this.startDateTime = startDateTime;
		}
		
		public LocalDateTime getEndDateTime() {
			return endDateTime;
		}
		
		public void setEndDateTime(LocalDateTime endDateTime) {
			this.endDateTime = endDateTime;
		}
		
		public Long getMentorId() {
			return mentorId;
		}
		
		public void setMentorId(Long mentorId) {
			this.mentorId = mentorId;
		}
		
		public SlotDTO(Long id, LocalDateTime startDateTime, LocalDateTime endDateTime, Long mentorId) {
			super();
			this.id = id;
			this.startDateTime = startDateTime;
			this.endDateTime = endDateTime;
			this.mentorId = mentorId;
		
		}
    }
		
		
		  
		  
				
				
			
		
