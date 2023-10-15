package com.nineleaps.authentication.jwt.service;

import com.nineleaps.authentication.jwt.dto.ChecklistItemDTO;
import com.nineleaps.authentication.jwt.exception.ResourceNotFoundException;

public interface IChecklistItemService {
    ChecklistItemDTO createChecklistItem(Long userId,ChecklistItemDTO checklistItemDto);
    ChecklistItemDTO getChecklistItemById(Long id) throws ResourceNotFoundException;
    void deleteChecklistItemById(Long id) throws ResourceNotFoundException;
	ChecklistItemDTO updateChecklistItem(Long id, ChecklistItemDTO checklistItemDto, Long userId)
			throws ResourceNotFoundException;

}
