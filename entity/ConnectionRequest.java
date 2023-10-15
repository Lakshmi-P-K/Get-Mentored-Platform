package com.nineleaps.authentication.jwt.entity;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.List;

import com.nineleaps.authentication.jwt.enums.ConnectionRequestStatus;

@Entity
public class ConnectionRequest {
		 @Id
		 @GeneratedValue(strategy = GenerationType.IDENTITY)
		 @Column(name = "connections_id")
		 private Long id;
		 @ManyToOne(fetch = FetchType.EAGER)
		 @JoinColumn(name = "mentee_id", referencedColumnName = "id")
		 private User mentee;
		 @ManyToOne(fetch = FetchType.EAGER)
		 @JoinColumn(name = "mentor_id", referencedColumnName = "id")
		 private User mentor;
		 private String message;
		 @Enumerated(EnumType.STRING)
		 private ConnectionRequestStatus status;
		 @Column(name = "request_time")
		 private LocalDateTime requestTime;
		 @Column(name = "acceptance_time")
		 private LocalDateTime acceptanceTime;
		 @Column(name = "rejection_time")
		 private LocalDateTime rejectionTime;
		  @ElementCollection
		 @CollectionTable(name = "recommended_mentors",
		 joinColumns = @JoinColumn(name = "connection_request_id"))
		 @Column(name = "mentor_id")
		 private List<Long> recommendedMentors;
		 public Long getId() {
		 return id;
		 }
		 public void setId(Long id) {
		 this.id = id;
		 }
		 public User getMentor() {
		 return mentor;
		 }
		 public void setMentor(User mentor) {
		 this.mentor = mentor;
		 }
		 public User getMentee() {
		 return mentee;
		 }
		 public void setMentee(User mentee) {
		 this.mentee = mentee;
		 }
		 public ConnectionRequestStatus getStatus() {
		 return status;
		 }
		 public void setStatus(ConnectionRequestStatus status) {
		 this.status = status;
		 }
		 public String getMessage() {
		 return message;
		 }
		 public void setMessage(String message) {
		 this.message = message;
		 }
		 public LocalDateTime getRequestTime() {
		 return requestTime;
		 }
		 public void setRequestTime(LocalDateTime requestTime) {
		 this.requestTime = requestTime;
		 }
		 public LocalDateTime getAcceptanceTime() {
		 return acceptanceTime;
		 }
		 public void setAcceptanceTime(LocalDateTime acceptanceTime) {
		 this.acceptanceTime = acceptanceTime;
		 }
		 public LocalDateTime getRejectionTime() {
		 return rejectionTime;
		 }
		 public void setRejectionTime(LocalDateTime rejectionTime) {
		 this.rejectionTime = rejectionTime;
		 }
		 public List<Long> getRecommendedMentors() {
		 return recommendedMentors;
		 }
		 public void setRecommendedMentors(List<Long> recommendedMentors) {
		 this.recommendedMentors = recommendedMentors;
		 }
		 	public ConnectionRequest(User mentor, User mentee, ConnectionRequestStatus status, String message) {
		 this.mentor = mentor;
		 this.mentee = mentee;
		 this.status = status;
		 this.message = message;
		 this.requestTime = LocalDateTime.now();
		 }
		 public ConnectionRequest() {
		 }
		}
