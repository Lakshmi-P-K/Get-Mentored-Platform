package com.nineleaps.authentication.jwt.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.nineleaps.authentication.jwt.entity.ConnectionRequest;
import com.nineleaps.authentication.jwt.entity.Engagement;
import com.nineleaps.authentication.jwt.exception.DuplicateEngagementException;
import com.nineleaps.authentication.jwt.repository.ConnectionRequestRepo;
import com.nineleaps.authentication.jwt.repository.EngagementRepository;


@Service
@Transactional
public class EngagementService implements IEngagementService {
	    private  EngagementRepository engagementRepository;
	    
	    private  ConnectionRequestRepo connectionRequestRepository;
	    @Autowired
	    private EmailServiceimpl emailService;
	
	    public EngagementService(EngagementRepository engagementRepository, ConnectionRequestRepo connectionRequestRepository) {
	        this.engagementRepository = engagementRepository;
	        this.connectionRequestRepository = connectionRequestRepository;
	    }
	    
	    @Override
	    public Engagement createEngagement(Long connectionRequestId, LocalDateTime startTime, int durationHours) throws DuplicateEngagementException {
	        ConnectionRequest connectionRequest = connectionRequestRepository.findById(connectionRequestId)
	                .orElseThrow(() -> new IllegalArgumentException("Connection request not found"));
	        
	        if (engagementRepository.existsByConnectionRequestId(connectionRequestId)) {
	            throw new DuplicateEngagementException("Engagement already exists with the same start time, duration, and connection request");
	        }

	        Engagement engagement = new Engagement();
	        engagement.setConnectionRequest(connectionRequest);
	        engagement.setStartTime(startTime);
	        engagement.setDurationHours(durationHours);

	        Engagement savedEngagement = engagementRepository.save(engagement);

	        // Retrieve the mentee's email
	        String menteeEmail = savedEngagement.getConnectionRequest().getMentee().getUserMail();
	        String mentorName = savedEngagement.getConnectionRequest().getMentor().getUserName();
	        
	        // Compose email subject and content
	        String subject = "Engagement Confirmation";
	        String content = "Your Mentor  "+ mentorName +" has carefully evaluated the "
	        		+ "goals and objectives, and the time required to cover the relevant topics. The estimated duration of "+ durationHours + " hours will allow for comprehensive guidance and "
	        		+ "support so that you can make the most of this mentorship opportunity. \n Happy Learning!!!! ";

	        
	        emailService.sendEmail(subject, content, menteeEmail);

	        return savedEngagement;
	    }

	
	
	    
	    public List<Engagement> getAllEngagementsByUserId(Long userId) {
	        return engagementRepository.findAllByConnectionRequest_Mentee_IdOrConnectionRequest_Mentor_Id(userId, userId);
	    }
	    
	    public List<Engagement> getAllEngagements() {
	        return engagementRepository.findAll();
	    }
	
	    public Optional<Engagement> getEngagementByConnectionId(Long connectionId) {
	        return engagementRepository.findByConnectionRequest_Id(connectionId);
	    }
	
}
