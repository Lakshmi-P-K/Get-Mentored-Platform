package com.nineleaps.authentication.jwt.controller;
import com.nineleaps.authentication.jwt.dto.GoalTrackerDTO;
import com.nineleaps.authentication.jwt.entity.GoalTracker;
import com.nineleaps.authentication.jwt.exception.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nineleaps.authentication.jwt.service.GoalTrackerService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v1/goalTrackers")
public class GoalTrackerController {

    @Autowired
    private GoalTrackerService goalTrackerService;



    @PostMapping("/createGoal")
    @ApiOperation("Create a Goal Tracker for youe Engagement")
    
    public ResponseEntity<GoalTrackerDTO> createGoalTracker(@RequestBody GoalTrackerDTO goalTrackerDTO) {
        GoalTrackerDTO createdGoalTracker = goalTrackerService.createGoalTracker(goalTrackerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGoalTracker);
    }
    

    

    @GetMapping("/getAllGoalTrackersByEngagementId")
    @ApiOperation("Get All the Goal Tracker Details By Engagement Id")
    public ResponseEntity<List<GoalTrackerDTO>> getAllGoalTrackersByEngagementId(@RequestParam Long engagementId) {
        List<GoalTracker> goalTrackers = goalTrackerService.getAllGoalTrackersByEngagementId(engagementId);
        List<GoalTrackerDTO> goalTrackerDTOs = new ArrayList<>();
        for (GoalTracker goalTracker : goalTrackers) {
            GoalTrackerDTO goalTrackerDTO = convertToGoalTrackerDTO(goalTracker);
            goalTrackerDTOs.add(goalTrackerDTO);
        }
        return ResponseEntity.ok(goalTrackerDTOs);
    }

    private GoalTrackerDTO convertToGoalTrackerDTO(GoalTracker goalTracker) {
        GoalTrackerDTO goalTrackerDTO = new GoalTrackerDTO();
        BeanUtils.copyProperties(goalTracker, goalTrackerDTO);
        goalTrackerDTO.setEngagementId(goalTracker.getEngagement().getId());
        goalTrackerDTO.setUserId(goalTracker.getUser().getId()); // Set the userId
        return goalTrackerDTO;
    }

    
    @PutMapping("/updateById")
    @ApiOperation("Update Goal Tracker Deatils By Goal tracker Id")
    
    public ResponseEntity<GoalTrackerDTO> updateGoalTracker(@RequestParam Long id, @RequestBody GoalTrackerDTO goalTrackerDTO) throws ResourceNotFoundException {
        GoalTrackerDTO updatedGoalTracker = goalTrackerService.updateGoalTracker(id, goalTrackerDTO);
        return ResponseEntity.ok(updatedGoalTracker);
    }
    
    @DeleteMapping("/deleteById")
    @ApiOperation("Delete a Goal Tracker By Goal tracker Id")
    
    public ResponseEntity<Void> deleteGoalTracker(@RequestParam Long id) throws ResourceNotFoundException {
        goalTrackerService.deleteGoalTracker(id);
        return ResponseEntity.noContent().build();
    }
}
