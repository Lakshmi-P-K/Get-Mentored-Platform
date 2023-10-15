package com.nineleaps.authentication.jwt.controller;

import java.util.List;
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
import com.nineleaps.authentication.jwt.dto.ActivityLogDTO;
import com.nineleaps.authentication.jwt.dto.ChecklistItemDTO;
import com.nineleaps.authentication.jwt.enums.ChecklistitemStatus;
import com.nineleaps.authentication.jwt.exception.ResourceNotFoundException;
import com.nineleaps.authentication.jwt.service.ChecklistItemService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v1/checklistitems")
public class ChecklistItemController {
    
    @Autowired
    private ChecklistItemService checklistItemService;
	

	    @GetMapping("/getAllChecklistItemByGoalTrackerId/")
	    @ApiOperation("Get All Checklist Items by Goal Tracker Id")
	    public List<ChecklistItemDTO> getAllChecklistItemsByGoalTrackerId(@RequestParam("goalTrackerId") Long goalTrackerId)
	            throws ResourceNotFoundException {
	        return checklistItemService.getAllChecklistItemsByGoalTrackerId(goalTrackerId);
	    }
	    
	    @PostMapping("/createChecklist")
	    @ApiOperation("Create checklists for your Goal")
	    public ResponseEntity<ChecklistItemDTO> createChecklistItem(@RequestParam Long userId, @RequestBody ChecklistItemDTO checklistItemDTO){
	        ChecklistItemDTO createdChecklistItem = checklistItemService.createChecklistItem(userId, checklistItemDTO);
	        return ResponseEntity.status(HttpStatus.CREATED).body(createdChecklistItem);
	    }
	
	    @PutMapping("/updateStatus")
	    @ApiOperation("Update Status of checklists for your Goal")
	    public ResponseEntity<ChecklistItemDTO> updateChecklistItemStatus(@RequestParam("userId")Long userId,
	            @RequestParam("id") Long checklistItemId,
	            @RequestParam("status") ChecklistitemStatus newStatus) throws ResourceNotFoundException {
	        ChecklistItemDTO updatedChecklistItem = checklistItemService.updateChecklistItemStatus(userId,checklistItemId, newStatus);
	        return ResponseEntity.ok(updatedChecklistItem);
	    }
	    @PutMapping("/updateById")
	    @ApiOperation("Update checklist items by checklist Id")
	    public ChecklistItemDTO updateChecklistItem(@RequestParam("id") Long id, @RequestBody ChecklistItemDTO checklistItemDto,
	            @RequestParam("userId") Long userId) throws ResourceNotFoundException {
	        return checklistItemService.updateChecklistItem(id, checklistItemDto, userId);
	    }

	    @GetMapping("/getActivityLogByUserIdAndGoalId")
	    @ApiOperation("Get Activity history for the Goal")
	    public ResponseEntity<List<ActivityLogDTO>> getActivityDetailsByGoal(
	            @RequestParam("user_id") Long userId,
	            @RequestParam("goal_id") Long goalId
	    ) {
	        try {
	            List<ActivityLogDTO> activityLogs = checklistItemService.getActivityDetailsByGoal(userId, goalId);
	            return ResponseEntity.ok(activityLogs);
	        } catch (ResourceNotFoundException e) {
	            return ResponseEntity.notFound().build();
	        }
	    }
	    
	  
	    @DeleteMapping("/deleteById")
	    public ResponseEntity<String> deleteChecklistItem(@RequestParam("id") Long id) {
	        checklistItemService.deleteChecklistItem(id);
	        return ResponseEntity.ok("Checklist item deleted successfully");
	    }
}
