package com.nineleaps.authentication.jwt.dto;

import java.time.LocalDateTime;


public class BookingDTO {
    private Long id;
    private Long slotId;
    private Long MenteeId;
    private Long MentorId;
    private LocalDateTime bookingDateTime;
    private Long engagementId;
    private Integer noOfHours;
    private String menteeUsername;
	private String mentorUsername;

    public BookingDTO() {
    	}

	public String getMentorUsername() {
		return mentorUsername;
	}
	

	public Long getEngagementId() {
		return engagementId;
	}

	public void setEngagementId(Long engagementId) {
		this.engagementId = engagementId;
	}

	public Integer getNoOfHours() {
		return noOfHours;
	}

	public void setNoOfHours(Integer noOfHours) {
		this.noOfHours = noOfHours;
	}

	public Long getId() {
		return id;
	}

	public String getMenteeUsername() {
		return menteeUsername;
	}


	public void setId(Long id) {
		this.id = id;
	}

	public Long getSlotId() {
		return slotId;
	}

	public void setSlotId(Long slotId) {
		this.slotId = slotId;
	}

	public Long getMenteeId() {
		return MenteeId;
	}

	public void setMenteeId(Long menteeId) {
		MenteeId = menteeId;
	}

	public Long getMentorId() {
		return MentorId;
	}

	public void setMentorId(Long mentorId) {
		MentorId = mentorId;
	}

	public LocalDateTime getBookingDateTime() {
		return bookingDateTime;
	}

	public void setBookingDateTime(LocalDateTime bookingDateTime) {
		this.bookingDateTime = bookingDateTime;
	}
    public void setMenteeUsername(String menteeUsername) {
        this.menteeUsername = menteeUsername;
    }

	public void setMentorUsername(String mentorUsername) {
		this.mentorUsername = mentorUsername;// TODO Auto-generated method stub
		
	}

	public BookingDTO(Long id, Long slotId, Long menteeId, Long mentorId, LocalDateTime bookingDateTime,
			Long engagementId, Integer noOfHours, String menteeUsername, String mentorUsername) {
		super();
		this.id = id;
		this.slotId = slotId;
		MenteeId = menteeId;
		MentorId = mentorId;
		this.bookingDateTime = bookingDateTime;
		this.engagementId = engagementId;
		this.noOfHours = noOfHours;
		this.menteeUsername = menteeUsername;
		this.mentorUsername = mentorUsername;
	}

	


	
    
    }

    