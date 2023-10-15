package com.nineleaps.authentication.jwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nineleaps.authentication.jwt.dto.GoalTrackerDTO;

import com.nineleaps.authentication.jwt.entity.GoalTracker;

import com.nineleaps.authentication.jwt.exception.DuplicateResourceException;
import com.nineleaps.authentication.jwt.exception.MappingException;
import com.nineleaps.authentication.jwt.exception.ResourceNotFoundException;
import com.nineleaps.authentication.jwt.repository.GoalTrackerRepository;


import java.time.LocalDateTime;
import java.util.List;


import org.modelmapper.ModelMapper;



@Service
public class GoalTrackerServiceImpl implements GoalTrackerService {

    @Autowired
    private GoalTrackerRepository goalTrackerRepository;
    

    @Autowired
    private ModelMapper modelMapper;

	    private GoalTracker mapToEntity(GoalTrackerDTO goalTrackerDto) {
	        try {
	            return modelMapper.map(goalTrackerDto, GoalTracker.class);
	        } catch (Exception e) {
	            throw new MappingException("Error mapping GoalTrackerDTO to entity: " + e.getMessage());
	        }
	    }
	
	    @Override
	    public GoalTrackerDTO getGoalTrackerById(Long id) throws ResourceNotFoundException {
	        GoalTracker goalTracker = goalTrackerRepository.findById(id)
	                .orElseThrow(() -> new ResourceNotFoundException("GoalTracker", "id", id));
	        return mapToDTO(goalTracker);
	    }
	
	

	    @Override
	    public GoalTrackerDTO createGoalTracker(GoalTrackerDTO goalTrackerDto) throws DuplicateResourceException {
	        String description = goalTrackerDto.getDescription();
	        Long engagementId = goalTrackerDto.getEngagementId();

	        // Check if a goal tracker with the same description and engagement ID already exists
	        boolean exists = goalTrackerRepository.existsByDescriptionAndEngagement_Id(description, engagementId);
	        if (exists) {
	            throw new DuplicateResourceException("A goal tracker with the same description and engagement ID already exists.");
	        }

	        GoalTracker goalTracker = modelMapper.map(goalTrackerDto, GoalTracker.class);
	        goalTracker.setGoalTrackerStartTime(LocalDateTime.now()); // Set the current timestamp

	      
	       

	        GoalTracker createdGoalTracker = goalTrackerRepository.save(goalTracker);
	        return mapToDTO(createdGoalTracker);
	    }

	
	    @Override
	    public GoalTrackerDTO updateGoalTracker(Long id, GoalTrackerDTO goalTrackerDto) throws ResourceNotFoundException {
	        GoalTracker goalTracker = goalTrackerRepository.findById(id)
	                .orElseThrow(() -> new ResourceNotFoundException("GoalTracker", "id", id));
	        goalTracker = mapToEntity(goalTrackerDto);
	        goalTracker.setId(id);
	        GoalTracker updatedGoalTracker = goalTrackerRepository.save(goalTracker);
	        return mapToDTO(updatedGoalTracker);
	    }
	    
	    public GoalTracker getGoalTrackerByEngagementId(Long engagementId) {
	        return goalTrackerRepository.findByEngagementId(engagementId);
	    }
	    
	    public List<GoalTracker> getAllGoalTrackersByEngagementId(Long engagementId) {
	        return goalTrackerRepository.findAllByEngagementId(engagementId);
	    }
	
	    @Override
	    public void deleteGoalTracker(Long id) throws ResourceNotFoundException {
	        GoalTracker goalTracker = goalTrackerRepository.findById(id)
	                .orElseThrow(() -> new ResourceNotFoundException("GoalTracker", "id", id));
	        goalTrackerRepository.delete(goalTracker);
	    }
	 

	    
	    public GoalTrackerDTO mapToDTO(GoalTracker goalTracker) {
	        GoalTrackerDTO dto = new GoalTrackerDTO();
	        dto.setId(goalTracker.getId());
	        dto.setEngagementId(goalTracker.getEngagement().getId());
	        dto.setDescription(goalTracker.getDescription());
	        dto.setUserId(goalTracker.getUser().getId());
	        dto.setGoalTrackerStartTime(goalTracker.getGoalTrackerStartTime());
	     
	        return dto;
	    }
}

