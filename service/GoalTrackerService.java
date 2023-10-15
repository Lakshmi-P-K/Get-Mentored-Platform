package com.nineleaps.authentication.jwt.service;

import java.util.List;

import com.nineleaps.authentication.jwt.dto.GoalTrackerDTO;
import com.nineleaps.authentication.jwt.entity.GoalTracker;
import com.nineleaps.authentication.jwt.exception.DuplicateResourceException;
import com.nineleaps.authentication.jwt.exception.ResourceNotFoundException;

public interface GoalTrackerService {

    GoalTrackerDTO getGoalTrackerById(Long id) throws ResourceNotFoundException;
    GoalTrackerDTO createGoalTracker(GoalTrackerDTO goalTrackerDto) throws DuplicateResourceException;
    List<GoalTracker> getAllGoalTrackersByEngagementId(Long engagementId);
    GoalTrackerDTO updateGoalTracker(Long id, GoalTrackerDTO goalTrackerDto) throws ResourceNotFoundException;
    GoalTracker getGoalTrackerByEngagementId(Long engagementId);
    void deleteGoalTracker(Long id) throws ResourceNotFoundException;
}
