package com.nineleaps.authentication.jwt.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.nineleaps.authentication.jwt.entity.Engagement;

public interface IEngagementService {
    Engagement createEngagement(Long connectionRequestId, LocalDateTime startTime, int durationHours);
    List<Engagement> getAllEngagements();
	Optional<Engagement> getEngagementByConnectionId(Long connectionId);
	List<Engagement> getAllEngagementsByUserId(Long userId);
}
