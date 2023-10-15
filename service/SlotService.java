package com.nineleaps.authentication.jwt.service;

import java.time.LocalDateTime;
import java.util.List;

import com.nineleaps.authentication.jwt.dto.SlotDTO;
import com.nineleaps.authentication.jwt.entity.Slot;
//import com.nineleaps.authentication.jwt.dto.SlotDto;
import com.nineleaps.authentication.jwt.enums.BookingStatus;
import com.nineleaps.authentication.jwt.exception.ConflictException;
import com.nineleaps.authentication.jwt.exception.ResourceNotFoundException;


	public interface SlotService {
	   
	    SlotDTO createSlot(SlotDTO slotDTO) throws ConflictException;
	    List<SlotDTO> getAllSlots();
		SlotDTO getSlotById(Long id) throws ResourceNotFoundException;
		SlotDTO updateSlot(Long id, SlotDTO slotDTO) throws ResourceNotFoundException;
		void deleteSlot(Long id);
		void deleteMultipleSlots(List<Slot> slots);
		public List<Slot> getSlotsByMentorIdAndDate(Long mentorId, LocalDateTime date);
		public void deleteMultipleSlotsById(List<Long> slotId);
		List<SlotDTO> getSlotsByMentorId(Long mentorId);
		void setStatus(Long slotId, BookingStatus bookingStatus) throws ResourceNotFoundException;

	}

    


