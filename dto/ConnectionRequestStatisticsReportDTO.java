package com.nineleaps.authentication.jwt.dto;



import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ConnectionRequestStatisticsReportDTO {

    private Long totalRequests;
    private Long acceptedRequests;
    private Long rejectedRequests;
    private Long pendingRequests;

	public ConnectionRequestStatisticsReportDTO() {
		
	}
	
	public Long getTotalRequests() {
		return totalRequests;
	}
	
	public void setTotalRequests(Long totalRequests) {
		this.totalRequests = totalRequests;
	}
	
	public Long getAcceptedRequests() {
		return acceptedRequests;
	}
	
	public void setAcceptedRequests(Long acceptedRequests) {
		this.acceptedRequests = acceptedRequests;
	}
	
	public Long getRejectedRequests() {
		return rejectedRequests;
	}
	
	public void setRejectedRequests(Long rejectedRequests) {
		this.rejectedRequests = rejectedRequests;
	}
	
	public Long getPendingRequests() {
		return pendingRequests;
	}
	
	public void setPendingRequests(Long pendingRequests) {
		this.pendingRequests = pendingRequests;
	}
	
	public ConnectionRequestStatisticsReportDTO(Long totalRequests, Long acceptedRequests, Long rejectedRequests,
			Long pendingRequests) {
		super();
		this.totalRequests = totalRequests;
		this.acceptedRequests = acceptedRequests;
		this.rejectedRequests = rejectedRequests;
		this.pendingRequests = pendingRequests;
	}
	
	}
	
