package com.nineleaps.authentication.jwt.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nineleaps.authentication.jwt.dto.SlotDTO;
import com.nineleaps.authentication.jwt.entity.Booking;
import com.nineleaps.authentication.jwt.entity.Slot;
import com.nineleaps.authentication.jwt.enums.BookingStatus;
import com.nineleaps.authentication.jwt.exception.ConflictException;
import com.nineleaps.authentication.jwt.exception.ResourceNotFoundException;
import com.nineleaps.authentication.jwt.repository.BookingRepository;
import com.nineleaps.authentication.jwt.repository.SlotRepository;

@Service
public class SlotServiceImpl implements SlotService {

    private final SlotRepository slotRepository;
    private final ModelMapper modelMapper;
    private final BookingRepository bookingRepository;
    
    @Autowired
    private EmailServiceimpl emailService;
    

    @Autowired
    public SlotServiceImpl(SlotRepository slotRepository, ModelMapper modelMapper,BookingRepository bookingRepository) {
        this.slotRepository = slotRepository;
        this.modelMapper = modelMapper;
        this.bookingRepository=bookingRepository;
    }

    @Override
    public SlotDTO createSlot(SlotDTO slotDTO) throws ConflictException {
        Slot slot = modelMapper.map(slotDTO, Slot.class);
        slot.setCreatedAt(LocalDateTime.now());
        slot.setStatus(BookingStatus.PENDING); // Set the initial status as PENDING

        // Check for existing slot with the same start and end date times and mentor ID
        LocalDateTime newSlotStart = slot.getStartDateTime();
        LocalDateTime newSlotEnd = slot.getEndDateTime();
        
        
        if (slotRepository.existsByStartDateTimeAndEndDateTimeAndMentorId(
                newSlotStart, newSlotEnd, slot.getMentorId())) {
            throw new ConflictException("Slot already exists with the same start and end date times and mentor ID");
        }

        // Check for overlapping slots within a one-hour window
        LocalDateTime overlappingSlotStart = newSlotStart.minusHours(0);
        LocalDateTime overlappingSlotEnd = newSlotEnd.plusHours(1);

        List<Slot> overlappingSlots = slotRepository.findByMentorIdAndStartDateTimeLessThanEqualAndEndDateTimeGreaterThanEqual(
                slot.getMentorId(), overlappingSlotEnd, overlappingSlotStart
        );

        if (!overlappingSlots.isEmpty()) {
            throw new ConflictException("Overlapping slot found within a one-hour window");
        }

        Slot createdSlot = slotRepository.save(slot);
        return modelMapper.map(createdSlot, SlotDTO.class);
    }

    
    
   
    
    
   
	
	    @Override
	    public List<SlotDTO> getAllSlots() {
	        List<Slot> slots = slotRepository.findAll();
	        return slots.stream()
	                .map(slot -> modelMapper.map(slot, SlotDTO.class))
	                .collect(Collectors.toList());
	    }
	
	    @Override
	    public SlotDTO getSlotById(Long id) throws ResourceNotFoundException {
	        Slot slot = slotRepository.findById(id)
	                .orElseThrow(() -> new ResourceNotFoundException("Slot not found with id " + id));
	        return modelMapper.map(slot, SlotDTO.class);
	    }
	
	    @Override
	    public SlotDTO updateSlot(Long id, SlotDTO slotDTO) throws ResourceNotFoundException {
	        Slot slot = slotRepository.findById(id)
	                .orElseThrow(() -> new ResourceNotFoundException("Slot not found with id " + id));
	
	        // Update the slot object with the data from the DTO
	        slot.setStartDateTime(slotDTO.getStartDateTime());
	        slot.setEndDateTime(slotDTO.getEndDateTime());
	        slot.setMentorId(slotDTO.getMentorId());
	        slot.setCreatedAt(LocalDateTime.now());
	
	        // Save the updated slot object
	        Slot updatedSlot = slotRepository.save(slot);
	
	        // Map the updated slot to a DTO and return it
	        SlotDTO updatedSlotDTO = modelMapper.map(updatedSlot, SlotDTO.class);
	        return updatedSlotDTO;
	    }
	
	
	    
	    

        @Transactional
	    @Override
	    public void deleteSlot(Long id) {
	        try {
	            Slot slot = slotRepository.findById(id)
	                    .orElseThrow(() -> new ResourceNotFoundException("Slot not found with id " + id));

	            // Get the associated bookings for the slot
	            List<Booking> bookingsForSlot = bookingRepository.findBySlotId(slot.getId());

	            // Send email notifications to the mentees about the slot cancellation
	            for (Booking booking : bookingsForSlot) {
	                String menteeEmail = booking.getMentee().getUserMail();
	                DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
	                String bookingDateTimeFormatted = booking.getBookingDateTime().format(formatter);
	                String subject = "Slot Cancellation Notification";
	                String content = "The slot scheduled for " + bookingDateTimeFormatted + " has been cancelled by the mentor.";
	                emailService.sendEmail(subject, content, menteeEmail);
	            }

	            // Delete the associated bookings from the database
	            bookingRepository.deleteAll(bookingsForSlot);

	            // Delete the slot from the database
	            slotRepository.delete(slot);
	        } catch (ResourceNotFoundException e) {
	            // Handle the exception here, such as logging or returning an error response
	            // For example, you can log the error:
	            e.printStackTrace();
	            // Or you can throw a custom exception:
	            throw new RuntimeException("Slot not found with id " + id);
	        }
	    }
	    
	    @Override
		public List<SlotDTO> getSlotsByMentorId(Long mentorId) {
			LocalDate currentDate = LocalDate.now();
			List<Slot> slots = slotRepository.findByMentorIdAndStartDateTimeAfter(mentorId, currentDate.atStartOfDay());
			return slots.stream()
					.map(slot -> modelMapper.map(slot, SlotDTO.class))
					.collect(Collectors.toList());
		}
		@Override
		public void setStatus(Long slotId, BookingStatus bookingStatus) throws ResourceNotFoundException {
		     Slot slot = slotRepository.findById(slotId)
		                .orElseThrow(() -> new ResourceNotFoundException("Slot not found with id " + slotId));
		        slot.setStatus(bookingStatus);
		        slotRepository.save(slot);
		    }
		@Transactional
		@Override
//		public void deleteMultipleSlotsById(List<Long> slotIds) {
//		    for (Long slotId : slotIds) {
//		        // Get the slots before deletion for later use in the email
//		        Optional<Slot> optionalSlot = slotRepository.findById(slotId);
//		        if (optionalSlot.isPresent()) {
//		            Slot slot = optionalSlot.get();
//		            // Fetch the associated bookings to get mentee's information
//		            List<Booking> bookings = slot.getBookings();
//		            for (Booking booking : bookings) {
//		                sendCancellationEmailToMentee(booking);
//		            }
//		            slotRepository.deleteById(slotId);
//		        }
//		    }
//		}
		public void deleteMultipleSlotsById(List<Long> slotIds) {
		    // Fetch all slots and their associated bookings in a single query
		    List<Slot> slots = slotRepository.findAllById(slotIds);
		    for (Slot slot : slots) {
		        List<Booking> bookings = slot.getBookings();
		        for (Booking booking : bookings) {
		            sendCancellationEmailToMentee(booking);
		        }
		    }
		    // Perform a batch delete for all slots
		    slotRepository.deleteAll(slots);
		}

		private void sendCancellationEmailToMentee(Booking booking) {
		    String menteeEmail = booking.getMentee().getUserMail();
		    String mentorName = booking.getMentor().getUserName();
		    DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
		    String startDateTimeFormatted = booking.getSlot().getStartDateTime().format(formatter);
		    String endDateTimeFormatted = booking.getSlot().getEndDateTime().format(formatter);
		    String subject = "Booking Cancellation Notification";
		    String content = "Your booking with Mentor " + mentorName + " from " + startDateTimeFormatted + " to " + endDateTimeFormatted + " has been canceled.";

		    // Assuming you have an EmailService to send the email
		    emailService.sendEmail(subject, content, menteeEmail);
		}
		@Override
		public List<Slot> getSlotsByMentorIdAndDate(Long mentorId, LocalDateTime startDate) {
		       // Get the start and end date for the given date (considering the whole day)
		//  LocalDateTime startDate = LocalDate.of(date).atStartOfDay();
		    LocalDateTime endDate = startDate.plusDays(1).minusSeconds(1);

		       // Call the repository method to fetch slots for the given mentorId and date
		       return slotRepository.findByMentorIdAndStartDateTimeGreaterThanEqualAndStartDateTimeLessThanEqual(
		             mentorId, startDate, endDate
		       );
		    }
		@Override
	    public void deleteMultipleSlots(List<Slot> slots) {
	       for(var slot : slots) {
	          slotRepository.deleteById(slot.getId());
	       }
	    }
			
	
}


    



