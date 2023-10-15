package com.nineleaps.authentication.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.nineleaps.authentication.jwt.dto.SlotDTO;
import com.nineleaps.authentication.jwt.dto.SlotStatisticsDTO;
import com.nineleaps.authentication.jwt.entity.Slot;
import com.nineleaps.authentication.jwt.enums.BookingStatus;
import com.nineleaps.authentication.jwt.exception.ConflictException;
import com.nineleaps.authentication.jwt.exception.ResourceNotFoundException;
import com.nineleaps.authentication.jwt.exception.SlotAlreadyExistsException;
import com.nineleaps.authentication.jwt.repository.SlotRepository;
import com.nineleaps.authentication.jwt.service.SlotService;
import io.swagger.annotations.ApiOperation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/slots")
public class SlotController {

    @Autowired
    private SlotService slotService;
@Autowired

private SlotRepository slotRepository;
   
    @GetMapping("/getSlotsByMentorId")
    @ApiOperation("get All the Uploaded slots of a Mentor by Mentor id ")
    public ResponseEntity<List<SlotDTO>> getSlotsByMentorId(@RequestParam Long mentorId) {
        List<SlotDTO> slots = slotService.getSlotsByMentorId(mentorId);
        return ResponseEntity.ok(slots);
    }



        @PostMapping("/createTheSlots")
        @ApiOperation("Upload the slots for your Mentee to Book  ")
        public SlotDTO createSlot(@RequestBody SlotDTO slotDTO) throws ConflictException {
            slotDTO.setStatus(BookingStatus.PENDING); // Set the initial status as PENDING
            return slotService.createSlot(slotDTO);
        }

  
    @PutMapping("/updateSlotById")
    @ApiOperation("Update the slot details by Slot id  ")
    public SlotDTO updateSlot(@RequestParam Long id, @RequestBody SlotDTO slotDTO) throws ResourceNotFoundException {
        return slotService.updateSlot(id, slotDTO);
    }

    @DeleteMapping("/deleteById")
    @ApiOperation("delete the slot by Slot id ")
    public void deleteSlot(@RequestParam Long id) {
        slotService.deleteSlot(id);
    }
    
    @DeleteMapping("/deleteMultipleSlots")
    public ResponseEntity<String> deleteBySlotId(@RequestParam List<Long> slotId){
        slotService.deleteMultipleSlotsById(slotId);
        return ResponseEntity.ok("Slots Deleted");
    }
    
    @DeleteMapping ("/deleteSlotsBasedOnDate/mentorId")
    public ResponseEntity<List<Slot>> getSlotsByMentorIdAndDate(
            @RequestParam("mentorId")Long mentorId,
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date
    ) {
        LocalDateTime localDateTime = date.atStartOfDay();
        List<Slot> slots = slotService.getSlotsByMentorIdAndDate(mentorId, localDateTime);
        if(!slots.isEmpty()) {
            slotService.deleteMultipleSlots(slots);
        }
        else {
            throw new SlotAlreadyExistsException("There is not slot for that particular mentor on the specified date");
        }
        return ResponseEntity.ok(slots);
    }
    
    @GetMapping("/counts")
    public ResponseEntity<SlotStatisticsDTO> getSlotCountsByMentorAndDateRange(
            @RequestParam("mentorId") Long mentorId,
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate
    ) {
        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.atStartOfDay().plusDays(1L);
        long totalSlots = slotRepository.getTotalSlotsByMentorAndDateRange(mentorId, start, end);
        long pendingSlots = slotRepository.getPendingSlotsByMentorAndDateRange(mentorId, start, end);
        long bookedSlots = slotRepository.getBookedSlotsByMentorAndDateRange(mentorId, start, end);

        return new ResponseEntity<>(new SlotStatisticsDTO(totalSlots, pendingSlots, bookedSlots), HttpStatus.OK);
    }
    
    
    
}


