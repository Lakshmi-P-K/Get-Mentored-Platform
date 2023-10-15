package com.nineleaps.authentication.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.nineleaps.authentication.jwt.dto.BookingDTO;
import com.nineleaps.authentication.jwt.dto.SlotDTO;
import com.nineleaps.authentication.jwt.entity.Slot;
import com.nineleaps.authentication.jwt.enums.BookingStatus;
import com.nineleaps.authentication.jwt.exception.ResourceNotFoundException;
import com.nineleaps.authentication.jwt.repository.BookingRepository;
import com.nineleaps.authentication.jwt.repository.SlotRepository;
import com.nineleaps.authentication.jwt.service.BookingService;
import com.nineleaps.authentication.jwt.service.SlotService;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;
    @Autowired
    private SlotService slotService;
    @Autowired
    private SlotRepository slotRepository;
    @Autowired
    private BookingRepository bookingRepository;
    

   
    
    @PostMapping("/createBookings")
    @ApiOperation("Book the Slots of your Mentor")
    public ResponseEntity<BookingDTO> createBooking(@RequestBody BookingDTO bookingDTO) {
        // Validate the request body if needed

        BookingDTO responseDTO = bookingService.createBooking(bookingDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }
   
    private void updateSlotStatus(Long slotId, BookingStatus bookingStatus) throws ResourceNotFoundException {
        SlotDTO slotDTO = slotService.getSlotById(slotId);
        Slot slot = slotRepository.findById(slotId)
                .orElseThrow(() -> new ResourceNotFoundException("Slot not found with id " + slotId));

        // Update the slot entity with the values from the DTO
        slot.setStartDateTime(slotDTO.getStartDateTime());
        slot.setEndDateTime(slotDTO.getEndDateTime());
        slot.setMentorId(slotDTO.getMentorId());
        slot.setStatus(bookingStatus);
        slotRepository.save(slot);
    }



    @PutMapping("/updateById")
    @ApiOperation("Update your Booking Details ")
    public BookingDTO updateBooking(@RequestParam Long id, @RequestBody BookingDTO bookingDTO) throws ResourceNotFoundException {
        return bookingService.updateBooking(id, bookingDTO);
    }

    @DeleteMapping("/deleteById")
    @ApiOperation("Delete your Booking ")
    public void deleteBooking(@RequestParam Long id) throws ResourceNotFoundException {
        bookingService.deleteBooking(id);
    }
    
    
  

    @GetMapping("/detailsByUserId")
    @ApiOperation(" Get Booking Details By user Id ")
    public List<Map<String, Object>> getBookingDetailsByUserId(@RequestParam("userId") Long userId) {
        return bookingRepository.findBookingDetailsByUserId(userId);
    }
    
    @DeleteMapping("/deleteMultipleBookings")
    public ResponseEntity<String> deleteMultipleBookings(@RequestParam List<Long> bookingIds) {
        bookingService.deleteMultipleBookingsById(bookingIds);
        return ResponseEntity.ok("Bookings Deleted");
    }

}
