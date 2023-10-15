package com.nineleaps.authentication.jwt.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;


@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime bookingDateTime;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "slot_id", nullable = false)
    private Slot slot;
	private LocalDateTime createdAt;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "mentee_id", referencedColumnName = "id")
    private User mentee;
    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "mentor_id", referencedColumnName = "id")
	private User mentor;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "engagement_id")
	private Engagement engagement;
	@Column(name = "no_of_hours")
	private Integer noOfHours;
	@Version
    private Long version;

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Booking() {
		
	}

	public Engagement getEngagement() {
		return engagement;
	}

	public void setEngagement(Engagement engagement) {
		this.engagement = engagement;
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

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getBookingDateTime() {
		return bookingDateTime;
	}

	public void setBookingDateTime(LocalDateTime bookingDateTime) {
		this.bookingDateTime = bookingDateTime;
	}

	public Slot getSlot() {
		return slot;
	}

	public void setSlot(Slot slot) {
		this.slot = slot;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
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

	public Booking(Long id, LocalDateTime bookingDateTime, Slot slot, LocalDateTime createdAt, User mentee, User mentor,
			Engagement engagement, Integer noOfHours, Long version) {
		super();
		this.id = id;
		this.bookingDateTime = bookingDateTime;
		this.slot = slot;
		this.createdAt = createdAt;
		this.mentee = mentee;
		this.mentor = mentor;
		this.engagement = engagement;
		this.noOfHours = noOfHours;
		this.version = version;
	}

   
	
	
}
