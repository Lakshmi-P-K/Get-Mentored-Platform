package com.nineleaps.authentication.jwt.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nineleaps.authentication.jwt.entity.Slot;


	@Repository
	public interface SlotRepository extends JpaRepository<Slot, Long> {
	 
		boolean existsByStartDateTimeAndEndDateTimeAndMentorId(LocalDateTime startDateTime, LocalDateTime endDateTime,
				Long mentorId);
		List<Slot> findByMentorId(Long mentorId);
		boolean existsById(Long slotId);
		
		//Queryfor counting the slots
		
		@Query("SELECT COUNT(s) FROM Slot s WHERE s.mentorId = :mentorId")
	       int getTotalSlotsByMentorId(Long mentorId);
	       @Query("SELECT COUNT(s) FROM Slot s WHERE s.mentorId = :mentorId AND s.status = 'BOOKED'")
	       int getBookedSlotsByMentorId(Long mentorId);
	       @Query("SELECT COUNT(s) FROM Slot s WHERE s.mentorId = :mentorId AND s.status = 'PENDING'")
	       int getPendingSlotsByMentorId(Long mentorId);

		//		___________________________________________________-
//		Queries for reporting
		long count();
		@Query("SELECT s.status, COUNT(s) FROM Slot s GROUP BY s.status")
		List<Object[]> countByStatus();
		@Query("SELECT s.mentorId, COUNT(s) FROM Slot s GROUP BY s.mentorId")
		List<Object[]> countByMentor();
		@Query(value = "SELECT TIMESTAMPDIFF(HOUR, s.start_date_time, s.end_date_time) FROM Slot s", nativeQuery = true)
		List<Long> getDurations();
		@Query("SELECT DATE(s.createdAt), COUNT(s) FROM Slot s GROUP BY DATE(s.createdAt)")
		List<Object[]> getFrequency();
//		__________________________________________________________
		List<Slot> findByMentorIdAndStartDateTimeAfter(Long mentorId, LocalDateTime atStartOfDay);
	
		@Lock(LockModeType.PESSIMISTIC_WRITE)
	    Optional<Slot> findById(Long id);
		
		List<Slot> findByMentorIdAndStartDateTimeBetween(Long mentorId, LocalDateTime startDate, LocalDateTime endDate);

		List<Slot> findByMentorIdAndStartDateTime(Long mentorId, LocalDateTime date);

		List<Slot> findByMentorIdAndStartDateTimeGreaterThanEqualAndStartDateTimeLessThanEqual(
		       Long mentorId,
		       LocalDateTime startDate,
		       LocalDateTime endDate
		);
		List<Slot> findByMentorIdAndStartDateTimeLessThanEqualAndEndDateTimeGreaterThanEqual(Long mentorId,
				LocalDateTime overlappingSlotEnd, LocalDateTime overlappingSlotStart);
	
	
	
	
		 @Query("SELECT COUNT(s) FROM Slot s " +
	             "WHERE s.mentorId = :mentorId " +
	             "AND s.createdAt BETWEEN :startDate AND :endDate")
	       long getTotalSlotsByMentorAndDateRange(@Param("mentorId") Long mentorId,
	                                     @Param("startDate") LocalDateTime startDate,
	                                     @Param("endDate") LocalDateTime endDate);

	       @Query("SELECT COUNT(s) FROM Slot s " +
	             "WHERE s.mentorId = :mentorId " +
	             "AND s.createdAt BETWEEN :startDate AND :endDate " +
	             "AND s.status = 'PENDING'")
	       long getPendingSlotsByMentorAndDateRange(@Param("mentorId") Long mentorId,
	                                      @Param("startDate") LocalDateTime startDate,
	                                      @Param("endDate") LocalDateTime endDate);

	       @Query("SELECT COUNT(s) FROM Slot s " +
	             "WHERE s.mentorId = :mentorId " +
	             "AND s.createdAt BETWEEN :startDate AND :endDate " +
	             "AND s.status = 'BOOKED'")
	       long getBookedSlotsByMentorAndDateRange(@Param("mentorId") Long mentorId,
	                                     @Param("startDate") LocalDateTime startDate,
	                                     @Param("endDate") LocalDateTime endDate);
	}
	






	
	
	
	
	
	


