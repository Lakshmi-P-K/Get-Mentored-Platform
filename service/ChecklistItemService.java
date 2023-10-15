package com.nineleaps.authentication.jwt.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nineleaps.authentication.jwt.dto.ActivityLogDTO;
import com.nineleaps.authentication.jwt.dto.ChecklistItemDTO;
import com.nineleaps.authentication.jwt.entity.ActivityLog;
import com.nineleaps.authentication.jwt.entity.ChecklistItem;
import com.nineleaps.authentication.jwt.entity.GoalTracker;
import com.nineleaps.authentication.jwt.entity.User;
import com.nineleaps.authentication.jwt.enums.ChecklistitemStatus;
import com.nineleaps.authentication.jwt.exception.ResourceNotFoundException;
import com.nineleaps.authentication.jwt.repository.ActivityLogRepository;
import com.nineleaps.authentication.jwt.repository.ChecklistItemRepository;
import com.nineleaps.authentication.jwt.repository.GoalTrackerRepository;
import com.nineleaps.authentication.jwt.repository.UserRepository;

@Service
public class ChecklistItemService implements IChecklistItemService {
    
	
	@Autowired
	private UserRepository userRepository;

    @Autowired
    private ChecklistItemRepository checklistItemRepository;
    @Autowired
    private GoalTrackerRepository goalTrackerRepository;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private ActivityLogRepository activityLogRepository;

    public List<ChecklistItemDTO> getAllChecklistItemsByGoalTrackerId(Long goalTrackerId) throws ResourceNotFoundException {
        List<ChecklistItem> checklistItems = checklistItemRepository.findAllByGoalTrackerId(goalTrackerId);
        if (checklistItems.isEmpty()) {
            throw new ResourceNotFoundException("No checklist items found for the provided goal tracker ID.");
        }

        List<ChecklistItemDTO> checklistItemDTOs = new ArrayList<>();
        for (ChecklistItem checklistItem : checklistItems) {
            ChecklistItemDTO checklistItemDTO = new ChecklistItemDTO();
            checklistItemDTO.setId(checklistItem.getId());
            checklistItemDTO.setGoalTrackerId(checklistItem.getGoalTracker().getId());
            checklistItemDTO.setItemDescription(checklistItem.getItemDescription());
            checklistItemDTO.setStatus(checklistItem.getStatus());
           checklistItemDTO.setCreatedAt(checklistItem.getCreatedAt());
           checklistItemDTO.setUpdatedAt(checklistItem.getUpdatedAt());
          // checklistItemDTO.setDeletedAt(checklistItem.getDeletedAt());// Skip setting createdAt and updatedAt fields
            checklistItemDTOs.add(checklistItemDTO);
        }

        return checklistItemDTOs;
    }


	    @Override
	    public ChecklistItemDTO getChecklistItemById(Long id) throws ResourceNotFoundException {
	        ChecklistItem checklistItem = checklistItemRepository.findById(id)
	                .orElseThrow(() -> new ResourceNotFoundException("Checklist item not found for this id :: " + id));
	        return mapToDTO(checklistItem);
	    }

	    public ChecklistItemDTO mapToDTO(ChecklistItem checklistItem) {
	        ChecklistItemDTO dto = new ChecklistItemDTO();
	        dto.setId(checklistItem.getId());
	        dto.setItemDescription(checklistItem.getItemDescription());
	        dto.setGoalTrackerId(checklistItem.getGoalTracker().getId());
	      // Add createdAt timestamp
	      
	        return dto;
	    }


	    @Override
	    public ChecklistItemDTO updateChecklistItem(Long id, ChecklistItemDTO checklistItemDto, Long userId) throws ResourceNotFoundException {
	        ChecklistItem existingChecklistItem = checklistItemRepository.findById(id)
	                .orElseThrow(() -> new ResourceNotFoundException("Checklist item not found with id " + id));

	        LocalDateTime createdAt = existingChecklistItem.getCreatedAt(); // Get the existing createdAt timestamp

	        modelMapper.map(checklistItemDto, existingChecklistItem);

	        existingChecklistItem.setCreatedAt(createdAt); // Set the original createdAt timestamp
	        existingChecklistItem.setUpdatedAt(LocalDateTime.now()); // Set the updatedAt timestamp

	        ChecklistItem updatedChecklistItem = checklistItemRepository.save(existingChecklistItem);

	        // Fetch the user and set it in the activity log
	        Optional<User> optionalUser = userRepository.findById(userId);
	        User user = optionalUser.orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

	        // Create an activity log for the update
	        ActivityLog activityLog = new ActivityLog();
	        activityLog.setActivityType("Updated Task");
	        activityLog.setActivityTime(LocalDateTime.now());
	        activityLog.setUser(user);
	        activityLog.setChecklistItem(updatedChecklistItem);

	        // Set the username in the activity log
	        String username = user.getUserName();
	        activityLog.setUserName(username);

	        updatedChecklistItem.addActivityLog(activityLog);

	        // Save the updated checklist item with the activity log
	        updatedChecklistItem = checklistItemRepository.save(updatedChecklistItem);

	        return modelMapper.map(updatedChecklistItem, ChecklistItemDTO.class);
	    }


	    @Override
	    public void deleteChecklistItemById(Long id) throws ResourceNotFoundException {
	        ChecklistItem checklistItem = checklistItemRepository.findById(id)
	                .orElseThrow(() -> new ResourceNotFoundException("Checklist item not found with id " + id));
	      //  checklistItem.setDeletedAt(LocalDateTime.now()); // Set the deletion timestamp
	        checklistItemRepository.save(checklistItem);
	    }
	    
	    
	    public void deleteChecklistItem(Long id) {
	        ChecklistItem checklistItem = checklistItemRepository.findById(id)
	                .orElseThrow();

	        // Create a new activity log entry
	        ActivityLog activityLog = new ActivityLog();
	        activityLog.setActivityTime(LocalDateTime.now());
	        activityLog.setActivityType("Checklist Item Deleted");
	        activityLog.setChecklistItem(checklistItem); // Associate the checklist item with the activity log

	        checklistItem.addActivityLog(activityLog); // Add the activity log to the checklist item

	        activityLogRepository.save(activityLog); // Save the activity log

	        checklistItemRepository.delete(checklistItem); // Delete the checklist item
	    }
   
  
	    public ChecklistItemDTO createChecklistItem(Long userId, ChecklistItemDTO checklistItemDTO) {
	        try {
	            checklistItemDTO.setStatus(ChecklistitemStatus.PENDING); // Set the initial status as "Pending"
	            ChecklistItem checklistItem = convertToEntity(checklistItemDTO);
	            checklistItem.setCreatedAt(LocalDateTime.now()); // Set the createdAt timestamp
	            ChecklistItem savedChecklistItem = checklistItemRepository.save(checklistItem);

	            // Create an activity log for the creation
	            ActivityLog activityLog = new ActivityLog();
	            activityLog.setActivityType("Created Task");
	            activityLog.setActivityTime(LocalDateTime.now());

	            // Fetch the user and set it in the activity log
	            User user = userRepository.findById(userId)
	                    .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
	            activityLog.setUser(user);

	            // Set the username in the activity log
	            String username = user.getUserName();
	            activityLog.setUserName(username);

	            activityLog.setChecklistItem(savedChecklistItem);
	            savedChecklistItem.addActivityLog(activityLog);

	            // Save the activity log
	            activityLogRepository.save(activityLog);

	            // Convert the saved checklist item to DTO
	            ChecklistItemDTO savedChecklistItemDTO = convertToDTO(savedChecklistItem);
	            savedChecklistItemDTO.setCreatedAt(savedChecklistItem.getCreatedAt()); // Set the createdAt timestamp

	            return savedChecklistItemDTO;
	        } catch (ResourceNotFoundException e) {
	            return null; // Handle the exception here (e.g., log an error message, return an appropriate response)
	        }
	    }


    
	    
	    public ChecklistItemDTO updateChecklistItemStatus(Long userId, Long checklistItemId, ChecklistitemStatus newStatus) throws ResourceNotFoundException {
	        User user = userRepository.findById(userId)
	                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

	        ChecklistItem checklistItem = checklistItemRepository.findById(checklistItemId)
	                .orElseThrow(() -> new ResourceNotFoundException("Checklist item not found with ID: " + checklistItemId));

	        ActivityLog activityLog = new ActivityLog();
	        activityLog.setActivityTime(LocalDateTime.now());
	        activityLog.setUser(user);
	        activityLog.setChecklistItem(checklistItem);

	        String username = user.getUserName();
	        activityLog.setUserName(username);

	        if (newStatus == ChecklistitemStatus.DONE) {
	            activityLog.setActivityType("Completed");
	        } else if (newStatus == ChecklistitemStatus.PENDING) {
	            activityLog.setActivityType("Marked as incomplete");
	        } else {
	            // Handle other status cases if needed
	            activityLog.setActivityType("Status updated");
	        }

	        activityLogRepository.save(activityLog);

	        checklistItem.setStatus(newStatus);
	        checklistItem.setUpdatedAt(LocalDateTime.now()); // Update the updatedAt timestamp to current time
	        ChecklistItem updatedChecklistItem = checklistItemRepository.save(checklistItem);

	        ChecklistItemDTO updatedChecklistItemDTO = convertToDTO(updatedChecklistItem);
	        updatedChecklistItemDTO.setCreatedAt(checklistItem.getCreatedAt()); // Set the created timestamp
	        updatedChecklistItemDTO.setUpdatedAt(checklistItem.getUpdatedAt()); // Set the updated timestamp

	        return updatedChecklistItemDTO;
	    }



        // Other methods and utility functions

        private ChecklistItem convertToEntity(ChecklistItemDTO checklistItemDTO) {
            ChecklistItem checklistItem = new ChecklistItem();
            checklistItem.setId(checklistItemDTO.getId());
            checklistItem.setGoalTracker(goalTrackerRepository.findById(checklistItemDTO.getGoalTrackerId())
                    .orElseThrow());
            checklistItem.setItemDescription(checklistItemDTO.getItemDescription());
            checklistItem.setStatus(checklistItemDTO.getStatus());
            return checklistItem;
        }

        private ChecklistItemDTO convertToDTO(ChecklistItem checklistItem) {
            ChecklistItemDTO checklistItemDTO = new ChecklistItemDTO();
            checklistItemDTO.setId(checklistItem.getId());
            checklistItemDTO.setGoalTrackerId(checklistItem.getGoalTracker().getId());
            checklistItemDTO.setItemDescription(checklistItem.getItemDescription());
            checklistItemDTO.setStatus(checklistItem.getStatus());
            return checklistItemDTO;
        }


        public List<ActivityLogDTO> getActivityDetailsByGoal(Long userId, Long goalId) throws ResourceNotFoundException {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

            GoalTracker goal = goalTrackerRepository.findById(goalId)
                    .orElseThrow(() -> new ResourceNotFoundException("Goal not found with ID: " + goalId));

            List<ChecklistItem> checklists = checklistItemRepository.findAllByGoalTrackerId(goalId);

            List<ActivityLogDTO> activityLogs = new ArrayList<>();

            for (ChecklistItem checklist : checklists) {
                List<ActivityLog> checklistActivityLogs = activityLogRepository.findByChecklistItem(checklist);

                for (ActivityLog activityLog : checklistActivityLogs) {
                    ActivityLogDTO activityLogDTO = modelMapper.map(activityLog, ActivityLogDTO.class);
                    activityLogDTO.setChecklistId(checklist.getId());
                    activityLogDTO.setItemDescription(checklist.getItemDescription());
                    activityLogs.add(activityLogDTO);
                }
            }

            if (activityLogs.isEmpty()) {
                // Return an empty list with 200 response
                return Collections.emptyList();
            }
            activityLogs.sort(Comparator.comparing(ActivityLogDTO::getActivityTime).reversed());

            return activityLogs;
        }




    }




