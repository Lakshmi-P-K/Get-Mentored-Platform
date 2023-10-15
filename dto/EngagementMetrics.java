package com.nineleaps.authentication.jwt.dto;

public class EngagementMetrics {
	
  private Long pendingEngagements;

  private Long doneEngagements;
  public EngagementMetrics() {
	
}
public EngagementMetrics(Long pendingEngagements, Long doneEngagements) {
	super();
	this.pendingEngagements = pendingEngagements;
	this.doneEngagements = doneEngagements;
}
public Long getPendingEngagements() {
	return pendingEngagements;
}
public void setPendingEngagements(Long pendingEngagements) {
	this.pendingEngagements = pendingEngagements;
}
public Long getDoneEngagements() {
	return doneEngagements;
}
public void setDoneEngagements(Long doneEngagements) {
	this.doneEngagements = doneEngagements;
}
  
  
  
}
