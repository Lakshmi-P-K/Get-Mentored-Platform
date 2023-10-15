package com.nineleaps.authentication.jwt.service;

import java.util.List;
import com.nineleaps.authentication.jwt.dto.BookingDTO;
import com.nineleaps.authentication.jwt.exception.ResourceNotFoundException;

public interface BookingService {
  

	BookingDTO getBookingById(Long id) throws ResourceNotFoundException;

	BookingDTO updateBooking(Long id, BookingDTO bookingDTO) throws ResourceNotFoundException;

	void deleteBooking(Long id) throws ResourceNotFoundException;
	List<BookingDTO> getAllBookingsByMenteeId(Long menteeId);
    List<BookingDTO> getAllBookingsByMentorId(Long mentorId);

	BookingDTO createBooking(BookingDTO bookingDTO);
	void deleteMultipleBookingsById(List<Long> bookingIds);
	
}
