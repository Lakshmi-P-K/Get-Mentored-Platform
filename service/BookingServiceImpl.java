package com.nineleaps.authentication.jwt.service;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nineleaps.authentication.jwt.dto.BookingDTO;
import com.nineleaps.authentication.jwt.entity.Booking;
import com.nineleaps.authentication.jwt.entity.Slot;
import com.nineleaps.authentication.jwt.enums.BookingStatus;
import com.nineleaps.authentication.jwt.exception.ResourceNotFoundException;
import com.nineleaps.authentication.jwt.exception.SlotAlreadyExistsException;
import com.nineleaps.authentication.jwt.repository.BookingRepository;
import com.nineleaps.authentication.jwt.repository.EngagementRepository;
import com.nineleaps.authentication.jwt.repository.SlotRepository;
import com.nineleaps.authentication.jwt.repository.UserRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.OptimisticLockException;
@Service
public class BookingServiceImpl implements BookingService {
	private final SlotRepository slotRepository;
    private final BookingRepository bookingRepository;
    private final ModelMapper modelMapper;
    
    @Autowired
    private EmailServiceimpl emailService;
    
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EngagementRepository engagementRepository;
    

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository, SlotRepository slotRepository,ModelMapper modelMapper) {
        this.bookingRepository = bookingRepository;
        this.modelMapper = modelMapper;
        this.slotRepository = slotRepository;
    }

    @Transactional
    public BookingDTO createBooking(BookingDTO bookingDTO) {
        Slot slot = slotRepository.findById(bookingDTO.getSlotId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid slot ID"));

        if (bookingRepository.existsBySlotId(bookingDTO.getSlotId())) {
            throw new SlotAlreadyExistsException("A booking with the same slot already exists.");
        }

        LocalDateTime bookingDateTime = bookingDTO.getBookingDateTime();
        LocalDateTime startDateTime = slot.getStartDateTime();
        LocalDateTime endDateTime = slot.getEndDateTime();
        Duration duration = Duration.between(startDateTime, endDateTime);
        int noOfHours = (int) duration.toHours();

        Booking booking = new Booking();
        booking.setBookingDateTime(bookingDateTime);
        booking.setSlot(slot);

        booking.setCreatedAt(LocalDateTime.now());
        booking.setEngagement(engagementRepository.findById(bookingDTO.getEngagementId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid engagement ID")));
        booking.setMentee(userRepository.findById(bookingDTO.getMenteeId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid mentee ID")));
        booking.setMentor(userRepository.findById(bookingDTO.getMentorId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid mentor ID")));
        booking.setNoOfHours(noOfHours);

       
        booking.getMentee().setUserName(bookingDTO.getMenteeUsername());
        booking.getMentor().setUserName(bookingDTO.getMentorUsername());

        try {
            
            Booking savedBooking = bookingRepository.save(booking);
            slot.setStatus(BookingStatus.BOOKED);
            slotRepository.save(slot);

           
            BookingDTO responseDTO = new BookingDTO();
            responseDTO.setId(savedBooking.getId());
            responseDTO.setBookingDateTime(savedBooking.getBookingDateTime());
	        responseDTO.setSlotId(savedBooking.getSlot().getId());
	        responseDTO.setMenteeId(savedBooking.getMentee().getId());
	        responseDTO.setMenteeUsername(savedBooking.getMentee().getUserName());
	        responseDTO.setMentorId(savedBooking.getMentor().getId());
	        responseDTO.setMentorUsername(savedBooking.getMentor().getUserName());
	        responseDTO.setEngagementId(savedBooking.getEngagement().getId());
	        responseDTO.setNoOfHours(savedBooking.getNoOfHours());
            

            String mentorEmail = savedBooking.getMentor().getUserMail();
            String menteeName = savedBooking.getMentee().getUserName();
            DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
            String startDateTimeFormatted = startDateTime.format(formatter);
            String endDateTimeFormatted = endDateTime.format(formatter);
            String subject = "Booking Notification";
            String content = "Your Mentee " + menteeName + " has booked your slot from " + startDateTimeFormatted + " to " + endDateTimeFormatted + ".";
            emailService.sendEmail(subject, content, mentorEmail);

            return responseDTO;
        } catch (OptimisticLockException ex) {
            // Handle the case where concurrent booking occurred
            throw new RuntimeException("Another user has booked the slot at the same time. Please try again.");
        } catch (Exception ex) {
            // Handle any other exceptions
            throw new RuntimeException("An error occurred while creating the booking.");
        }
    }
    
	    @Override
	    public BookingDTO getBookingById(Long id) throws ResourceNotFoundException {
		        Booking booking = bookingRepository.findById(id)
		                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));
		        return modelMapper.map(booking, BookingDTO.class);
	    }
	    @Override
	    public BookingDTO updateBooking(Long id, BookingDTO bookingDTO) throws ResourceNotFoundException {
		        Booking booking = bookingRepository.findById(id)
		                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));
		        Slot slot = slotRepository.findById(bookingDTO.getSlotId())
		                .orElseThrow(() -> new ResourceNotFoundException("Slot not found with id: " + bookingDTO.getSlotId()));
		        // Check if a booking already exists for the specified slot and booking date/time
		        LocalDateTime bookingDateTime = bookingDTO.getBookingDateTime();
		        Booking existingBooking = bookingRepository.findBySlotAndBookingDateTime(slot, bookingDateTime);
		        booking.setSlot(slot);
		        booking.setBookingDateTime(bookingDTO.getBookingDateTime());
		        booking = bookingRepository.save(booking);
		        return modelMapper.map(booking, BookingDTO.class);
	    }


	    @Transactional
	    public void deleteBooking(Long id) throws ResourceNotFoundException {
	        Booking booking = bookingRepository.findById(id)
	                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));

	        // Send email notification to mentor
	        String mentorEmail = booking.getMentor().getUserMail();
	   
	        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
	        String bookingDateTimeFormatted = booking.getBookingDateTime().format(formatter);
	        String subject = "Booking Cancellation Notification";
	        String content = "Your booking scheduled for " + bookingDateTimeFormatted + " has been cancelled.";
	        emailService.sendEmail(subject, content, mentorEmail);

	        // Send email notification to mentee
	        String menteeEmail = booking.getMentee().getUserMail();
	        subject = "Booking Cancellation Notification";
	        content = "Your booking scheduled for " + bookingDateTimeFormatted + " has been cancelled.";
	        emailService.sendEmail(subject, content, menteeEmail);

	        // Get the associated slot
	        Slot slot = booking.getSlot();

	        // Remove the booking from the slot
	        slot.removeBooking(booking);

	        // Save the slot with the updated bookings list
	        slotRepository.save(slot);

	        // Delete the booking
	        bookingRepository.delete(booking);

	        // Check if there are any other bookings for the slot
	        List<Booking> bookingsForSlot = bookingRepository.findBySlotId(slot.getId());
	        if (bookingsForSlot.isEmpty()) {
	            // If no other bookings, delete the slot from the database
	            slotRepository.delete(slot);
	        }
	    }


	    
	    @Override
		public List<BookingDTO> getAllBookingsByMenteeId(Long menteeId) {
		    List<Booking> bookings = bookingRepository.findAllByMenteeId(menteeId);
		    return bookings.stream()
		            .map(booking -> {
		                BookingDTO bookingDTO = modelMapper.map(booking, BookingDTO.class);
		                // Fetch the username of the mentee
		                String mentorUsername = booking.getMentor().getUserName();
		               // System.out.println(menteeUsername);
		                bookingDTO.setMentorUsername(mentorUsername);
		                return bookingDTO;
		            })
		            .collect(Collectors.toList());
	}
		@Override
		public List<BookingDTO> getAllBookingsByMentorId(Long mentorId) {
		    List<Booking> bookings = bookingRepository.findAllByMentorId(mentorId);
		    return bookings.stream()
		            .map(booking -> {
		                BookingDTO bookingDTO = modelMapper.map(booking, BookingDTO.class);
		                // Fetch the username of the mentee
		                String menteeUsername = booking.getMentee().getUserName();
		               // System.out.println(menteeUsername);
		                bookingDTO.setMenteeUsername(menteeUsername);
		                return bookingDTO;
		            })
		            .collect(Collectors.toList());
	}
		
		
		@Override
	    @Transactional
	    public void deleteMultipleBookingsById(List<Long> bookingIds) {
	        for (Long bookingId : bookingIds) {
	            Booking booking = bookingRepository.findById(bookingId).orElse(null);
	            if (booking != null) {
	                Slot slot = booking.getSlot();
	                if (slot != null) {
	                    sendCancellationEmailToMentor(booking); 
	                    slotRepository.delete(slot); // Delete the associated slot
	                }
	                bookingRepository.delete(booking); // Delete the booking
	            }
	        }
	    }
		
		
		
		
		private void sendCancellationEmailToMentor(Booking booking) {
		    String mentorEmail = booking.getMentor().getUserMail();
		    String menteeName = booking.getMentee().getUserName();
		    DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
		    String startDateTimeFormatted = booking.getSlot().getStartDateTime().format(formatter);
		    String endDateTimeFormatted = booking.getSlot().getEndDateTime().format(formatter);
		    String subject = "Booking Cancellation Notification";
		    String content = "The booking for " + menteeName + " from " + startDateTimeFormatted + " to " + endDateTimeFormatted + " has been canceled.";

		    emailService.sendEmail(subject, content, mentorEmail);
		}


	
	}