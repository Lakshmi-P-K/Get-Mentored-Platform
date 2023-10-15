package com.nineleaps.authentication.jwt.dto;
import com.nineleaps.authentication.jwt.entity.ConnectionRequest;
import com.nineleaps.authentication.jwt.enums.ConnectionRequestStatus;
import java.time.LocalDateTime;
import java.util.List;
public class ConnectionRequestDto {
    private Long id;
    private Long menteeId;
    private Long mentorId;
    private String message;
    private ConnectionRequestStatus status;
    private LocalDateTime requestTime;
    private LocalDateTime acceptanceTime;
    private LocalDateTime rejectionTime;
    private List<Long> recommendedMentors;
    
    public List<Long> getRecommendedMentors() {
        return recommendedMentors;
    }
    public void setRecommendedMentors(List<Long> recommendedMentors) {
        this.recommendedMentors = recommendedMentors;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getMenteeId() {
        return menteeId;
    }
    public void setMenteeId(Long menteeId) {
        this.menteeId = menteeId;
    }
    public Long getMentorId() {
        return mentorId;
    }
    public void setMentorId(Long mentorId) {
        this.mentorId = mentorId;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public ConnectionRequestStatus getStatus() {
        return status;
    }
    public void setStatus(ConnectionRequestStatus status) {
        this.status = status;
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
    public ConnectionRequestDto() {
    }
    public ConnectionRequestDto(ConnectionRequest connectionRequest) {
        this.id = connectionRequest.getId();
        this.menteeId = connectionRequest.getMentee().getId();
        this.mentorId = connectionRequest.getMentor().getId();
        this.message = connectionRequest.getMessage();
        this.status = connectionRequest.getStatus();
        this.requestTime = connectionRequest.getRequestTime();
        this.acceptanceTime = connectionRequest.getAcceptanceTime();
        this.rejectionTime = connectionRequest.getRejectionTime();
    }
    public static ConnectionRequestDto fromConnectionRequest(ConnectionRequest connectionRequest) {
        return new ConnectionRequestDto(connectionRequest);
    }
}
