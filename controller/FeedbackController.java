package com.nineleaps.authentication.jwt.controller;

import com.nineleaps.authentication.jwt.dto.MenteeFeedbackDTO;
import com.nineleaps.authentication.jwt.dto.MentorFeedbackDTO;
import com.nineleaps.authentication.jwt.repository.FeedbackRepository;

import com.nineleaps.authentication.jwt.service.FeedbackService;

import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/v1/feedBacks")
public class FeedbackController {
    private final FeedbackService feedbackService;
    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }
    @Autowired
    private FeedbackRepository feedbackRepository;
    
    @PostMapping("/menteeFeedbackToMentor")
    @ApiOperation("Mentee gives feedback to his Mentor")
    public ResponseEntity<MenteeFeedbackDTO> createFeedbackByMentee(
            @RequestParam Long menteeId,
            @RequestParam Long mentorId,
            @RequestParam Long engagementId,
            @RequestBody MenteeFeedbackDTO feedbackDTO) {
        MenteeFeedbackDTO createdFeedback = feedbackService.createFeedbackByMentee(menteeId, mentorId, engagementId, feedbackDTO);
        return ResponseEntity.ok(createdFeedback);
    }
//feedback by mentor to mentee
    @PostMapping("/mentorFeedaBackToMentee")
    @ApiOperation("Mentor gives feedback to his Mentee")
    public ResponseEntity<MentorFeedbackDTO> createFeedbackByMentor(
            @RequestParam Long mentorId,
            @RequestParam Long menteeId,
            @RequestParam Long engagementId,
            @RequestBody MentorFeedbackDTO feedbackDTO) {
        MentorFeedbackDTO createdFeedbackMentor = feedbackService.createFeedbackByMentor(mentorId, menteeId, engagementId, feedbackDTO);
        return ResponseEntity.ok(createdFeedbackMentor);
    }

    //Getting feedbacks given by mentee to mentor
    @GetMapping("/getAllFeedbackByMenteeToMentor")
    @ApiOperation("get All feedbacks by Mentee to Mentor")
    public ResponseEntity<List<MenteeFeedbackDTO>> getAllFeedbackByMentee(@RequestParam Long mentorId) {
        List<MenteeFeedbackDTO> feedbackDTOs = feedbackService.getAllFeedbackByMentee(mentorId);
        return ResponseEntity.ok(feedbackDTOs);
    }
    //getting feedbacks given by mentor to mentee
    @GetMapping("getAllFeedbackByMentorToMentee")
    @ApiOperation("get All feedbacks by Mentor to Mentee")
    public ResponseEntity<List<MentorFeedbackDTO>> getAllFeedbackByMentor(@RequestParam Long menteeId) {
        List<MentorFeedbackDTO> feedbackDTOs = feedbackService.getAllFeedbackByMentor(menteeId);
        return ResponseEntity.ok(feedbackDTOs);
    }


    //Getting averagre rating for mentor
    @GetMapping("/getAvgMentorRatingByMentorId")
    @ApiOperation("get Average Feedback Rating for a Mentor By his userId")
    public Double getAvgMentorRating(@RequestParam Long mentorId){
        return feedbackRepository.calculateAverageRatingForMentorId(mentorId);
    }

    

    //Getting averagre rating for mentee
    @GetMapping("/getAvgMenteeRatingByMenteeId")
    @ApiOperation("Get Average Feedback Rating for a Mentee By his userId")
    
    public Double getAvgMenteeRating(@RequestParam Long menteeId){
        return feedbackRepository.calculateAverageRatingForMenteeId(menteeId);

    }
    //Getting averagre rating for engagement
    @GetMapping("/getAvgEngagementRatingByMentorId")
    @ApiOperation("Get Average Feedback Rating for Engagement By his userId")
    
    public Double getAvgEngagementRating(@RequestParam Long mentorId){
        return feedbackRepository.calculateAverageRatingForEngagement(mentorId);
    }   
    
}