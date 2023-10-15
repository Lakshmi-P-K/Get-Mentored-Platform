package com.nineleaps.authentication.jwt.service;

import com.nineleaps.authentication.jwt.dto.MenteeFeedbackDTO;
import com.nineleaps.authentication.jwt.dto.MentorFeedbackDTO;
import com.nineleaps.authentication.jwt.entity.Engagement;
import com.nineleaps.authentication.jwt.entity.Feedback;
import com.nineleaps.authentication.jwt.entity.User;
import com.nineleaps.authentication.jwt.exception.RedundantFeedbackException;
import com.nineleaps.authentication.jwt.repository.EngagementRepository;
import com.nineleaps.authentication.jwt.repository.FeedbackRepository;
import com.nineleaps.authentication.jwt.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class FeedbackService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private final FeedbackRepository feedbackRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final EngagementRepository engagementRepository;
    public FeedbackService(FeedbackRepository feedbackRepository, UserRepository userRepository,
                           EngagementRepository engagementRepository) {
        this.feedbackRepository = feedbackRepository;
        this.userRepository = userRepository;
        this.engagementRepository = engagementRepository;
    }
    //creating feedback for mentor by mentee
	    public MenteeFeedbackDTO createFeedbackByMentee(Long menteeId, Long mentorId, Long engagementId, MenteeFeedbackDTO feedbackDTO) {
	        User mentee = userRepository.findById(menteeId)
	                .orElseThrow();
	        User mentor = userRepository.findById(mentorId)
	                .orElseThrow();
	        Engagement engagement = engagementRepository.findById(engagementId)
	                .orElseThrow();
	
		       if (feedbackRepository.existsByMenteeIdAndMentorIdAndEngagementId(menteeId, mentorId, engagementId)) {
		
		
		
		           throw new RedundantFeedbackException("Feedback already submitted for the mentor and engagement");
		        }
	        Feedback feedback = new Feedback();
	        feedback.setMentee(mentee);
	        feedback.setMentor(mentor);
	        feedback.setEngagement(engagement);
	        feedback.setCreatedTime(LocalDateTime.now());
	
		        if (feedbackDTO.getMentorRating() != null && feedbackDTO.getMentorRating() >= 0 && feedbackDTO.getMentorRating() <= 5) {
		            feedback.setMentorRating(feedbackDTO.getMentorRating());
		        }
		        else {
		            throw new IllegalArgumentException("Invalid mentor rating. Rating must be between 0 and 5.");
		        }
	        feedback.setMentorFeedback(feedbackDTO.getMentorFeedback());
	
		        if (feedbackDTO.getEngagementRating() != null && feedbackDTO.getEngagementRating() >= 0 && feedbackDTO.getEngagementRating() <= 5) {
		            feedback.setEngagementRating(feedbackDTO.getEngagementRating());
		        }
		        else {
		            throw new IllegalArgumentException("Invalid engagement rating. Rating must be between 0 and 5.");
		        }
	        feedback.setEngagementFeedback(feedbackDTO.getEngagementFeedback());
	        feedback.setFeedbackFromUserName(feedbackDTO.getFeedbackFromUserName());
	        
	        
	        Feedback savedFeedback = feedbackRepository.save(feedback);
	        return modelMapper.map(savedFeedback, feedbackDTO.getClass());
	    }
    //creating feedback for mentee by mentor
	    public MentorFeedbackDTO createFeedbackByMentor(Long mentorId, Long menteeId, Long engagementId, MentorFeedbackDTO mentorFeedbackDTO) {
	        User mentee = userRepository.findById(menteeId)
	                .orElseThrow();
	        User mentor = userRepository.findById(mentorId)
	                .orElseThrow();
	        Engagement engagement = engagementRepository.findById(engagementId)
	                .orElseThrow();
		        if (feedbackRepository.existsByMenteeIdAndMentorIdAndEngagementIdAndMentorRating(menteeId, mentorId, engagementId,new Feedback().getMentorRating())) {
		            throw new RedundantFeedbackException("Feedback already submitted for the mentor and engagement");
		        }
	        Feedback feedback = new Feedback();
	        feedback.setMentee(mentee);
	        feedback.setMentor(mentor);
	        feedback.setEngagement(engagement);
	        feedback.setCreatedTime(LocalDateTime.now());
	//        feedback.setMentorRating(feedbackDTO.getMentorRating());
		        if ( mentorFeedbackDTO.getMenteeRating()!= null && mentorFeedbackDTO.getMenteeRating() >= 0 && mentorFeedbackDTO.getMenteeRating() <= 5) {
		            feedback.setMenteeRating(mentorFeedbackDTO.getMenteeRating());
		        }
		        else {
		            throw new IllegalArgumentException("Invalid mentor rating. Rating must be between 0 and 5.");
		        }
	        feedback.setMenteeFeedback(mentorFeedbackDTO.getMenteeFeedback());
	//        feedback.setEngagementRating(feedbackDTO.getEngagementRating());
		        if (mentorFeedbackDTO.getEngagementRating() != null && mentorFeedbackDTO.getEngagementRating() >= 0 && mentorFeedbackDTO.getEngagementRating() <= 5) {
		            feedback.setEngagementRating(mentorFeedbackDTO.getEngagementRating());
		        }
		        else {
		            throw new IllegalArgumentException("Invalid engagement rating. Rating must be between 0 and 5.");
		        }
	        feedback.setEngagementFeedback(mentorFeedbackDTO.getEngagementFeedback());
	        feedback.setFeedbackFromUserName(mentorFeedbackDTO.getFeedbackFromUserName());
	        Feedback savedFeedback = feedbackRepository.save(feedback);
	        return modelMapper.map(savedFeedback, mentorFeedbackDTO.getClass());
	    	}
	    public List<MenteeFeedbackDTO> getAllFeedbackByMentee(Long menteeId) {
	        List<Feedback> feedbacks = feedbackRepository.findByMentorId(menteeId);
	        return feedbacks.stream().map(this::convertToDTO).collect(Collectors.toList());
	    }
	    public List<MentorFeedbackDTO> getAllFeedbackByMentor(Long mentorId) {
	        List<Feedback> feedbackss = feedbackRepository.findByMenteeId(mentorId);
	        return feedbackss.stream().map(this::convertToDTOO).collect(Collectors.toList());
	    }
	    private MentorFeedbackDTO convertToDTOO(Feedback feedback) {
	        return modelMapper.map(feedback, MentorFeedbackDTO.class);
	    }
	    private MenteeFeedbackDTO convertToDTO(Feedback feedback) {
	        return modelMapper.map(feedback, MenteeFeedbackDTO.class);
	    }
}