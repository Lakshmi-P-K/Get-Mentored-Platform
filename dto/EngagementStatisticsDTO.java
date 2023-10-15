package com.nineleaps.authentication.jwt.dto;


import lombok.Getter;

import lombok.Setter;

@Getter
@Setter

public class EngagementStatisticsDTO {
    private Long totalEngagements;
    private Long totalHours;
    private Double averageHours;

public EngagementStatisticsDTO() {

}

public Long getTotalEngagements() {
	return totalEngagements;
}

public void setTotalEngagements(Long totalEngagements) {
	this.totalEngagements = totalEngagements;
}

public Long getTotalHours() {
	return totalHours;
}

public void setTotalHours(Long totalHours) {
	this.totalHours = totalHours;
}

public Double getAverageHours() {
	return averageHours;
}

public void setAverageHours(Double averageHours) {
	this.averageHours = averageHours;
}

public EngagementStatisticsDTO(Long totalEngagements, Long totalHours, Double averageHours) {
	super();
	this.totalEngagements = totalEngagements;
	this.totalHours = totalHours;
	this.averageHours = averageHours;
}

}

