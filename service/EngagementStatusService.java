package com.nineleaps.authentication.jwt.service;

import java.time.LocalDateTime;
import java.util.Optional;

import com.nineleaps.authentication.jwt.enums.EngStatus;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nineleaps.authentication.jwt.dto.EngagementStatusDTO;
import com.nineleaps.authentication.jwt.entity.Engagement;
import com.nineleaps.authentication.jwt.entity.EngagementStatus;
import com.nineleaps.authentication.jwt.repository.EngagementStatusRepository;

@Service
public class EngagementStatusService {
	@Autowired
    private EngagementStatusRepository engagementStatusRepository;
   @Autowired
    private ModelMapper modelMapper;
    public EngagementStatusService(EngagementStatusRepository engagementStatusRepository) {
        this.engagementStatusRepository = engagementStatusRepository;
    }



    
    public EngagementStatusDTO createOrUpdateEngagementStatus(EngagementStatusDTO engagementStatusDTO) {
        Long engagementId = engagementStatusDTO.getEngagementId();
        Optional<EngagementStatus> engagementStatusOptional = engagementStatusRepository.findByEngagementId(engagementId);

        if (engagementStatusOptional.isPresent()) {
            EngagementStatus existingEngagementStatus = engagementStatusOptional.get();
            // Update the existing engagement status
            existingEngagementStatus.setMentorEngStatus(engagementStatusDTO.getMentorEngStatus());
            existingEngagementStatus.setMenteeEngStatus(engagementStatusDTO.getMenteeEngStatus());

            LocalDateTime mentorStatusTimestamp = existingEngagementStatus.getMentorStatusTimestamp();
            LocalDateTime menteeStatusTimestamp = existingEngagementStatus.getMenteeStatusTimestamp();
            LocalDateTime latestStatusTimestamp = mentorStatusTimestamp.isAfter(menteeStatusTimestamp)
                    ? mentorStatusTimestamp
                    : menteeStatusTimestamp;

            if (existingEngagementStatus.getMentorEngStatus() == EngStatus.DONE &&
                    existingEngagementStatus.getMenteeEngStatus() == EngStatus.DONE) {
                existingEngagementStatus.setCompletedEngStatusTimestamp(latestStatusTimestamp); // Set the completion timestamp
            } else {
                existingEngagementStatus.setCompletedEngStatusTimestamp(null); // Set default value if condition not satisfied
            }

            EngagementStatus updatedEngagementStatus = engagementStatusRepository.save(existingEngagementStatus);
            return modelMapper.map(updatedEngagementStatus, EngagementStatusDTO.class);
        } else {
            // Create a new engagement status
            Engagement engagement = new Engagement();
            engagement.setId(engagementStatusDTO.getEngagementId());

            EngagementStatus engagementStatus = modelMapper.map(engagementStatusDTO, EngagementStatus.class);
            engagementStatus.setEngagement(engagement);

            LocalDateTime currentTimestamp = LocalDateTime.now();
            engagementStatus.setMentorStatusTimestamp(currentTimestamp);
            engagementStatus.setMenteeStatusTimestamp(currentTimestamp);

            // Set initial status to "Pending"
            engagementStatus.setMentorEngStatus(EngStatus.PENDING);
            engagementStatus.setMenteeEngStatus(EngStatus.PENDING);
            
            // Set completedEngStatusTimestamp to null when engagement is in "Pending" status
            if (engagementStatusDTO.getMentorEngStatus() == EngStatus.PENDING &&
                    engagementStatusDTO.getMenteeEngStatus() == EngStatus.PENDING) {
                engagementStatus.setCompletedEngStatusTimestamp(null); // Set default value
            } else {
                engagementStatus.setCompletedEngStatusTimestamp(null); // Set to null for "Pending" status
            }

            EngagementStatus savedEngagementStatus = engagementStatusRepository.save(engagementStatus);
            return modelMapper.map(savedEngagementStatus, EngagementStatusDTO.class);
        }
    }









    public EngagementStatusDTO getEngagementStatusById(Long id) {
	        Optional<EngagementStatus> engagementStatusOptional = engagementStatusRepository.findById(id);
		        if (engagementStatusOptional.isPresent()) {
		            EngagementStatus engagementStatus = engagementStatusOptional.get();
		            return modelMapper.map(engagementStatus, EngagementStatusDTO.class);
		        }
	        return null;
	    }
	    public EngagementStatusDTO getEngagementStatusByEngagementId(Long engagementId) {
	        Optional<EngagementStatus> engagementStatusOptional = engagementStatusRepository.findByEngagementId(engagementId);
		        if (engagementStatusOptional.isPresent()) {
		            EngagementStatus engagementStatus = engagementStatusOptional.get();
		            return modelMapper.map(engagementStatus, EngagementStatusDTO.class);
		        }
	        return null;
	    }


}

