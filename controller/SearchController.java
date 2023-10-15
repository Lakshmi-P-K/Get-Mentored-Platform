package com.nineleaps.authentication.jwt.controller;

import com.nineleaps.authentication.jwt.dto.MentorDTO;
import com.nineleaps.authentication.jwt.entity.User;
import com.nineleaps.authentication.jwt.enums.ConnectionRequestStatus;
import com.nineleaps.authentication.jwt.enums.UserRole;
import com.nineleaps.authentication.jwt.repository.ConnectionRequestRepo;
import com.nineleaps.authentication.jwt.repository.FeedbackRepository;
import com.nineleaps.authentication.jwt.service.ISearchForMentor;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/search")
public class SearchController {
    @Autowired
    private ISearchForMentor iSearchForMentor;
    @Autowired
    private ConnectionRequestRepo connectionRequestRepository;
    @Autowired
    private FeedbackRepository feedbackRepository;

    //Controller for searching users using their role
    @GetMapping("/getByRole")
    @ApiOperation("searching users based on their Role")
    public List<MentorDTO> getUsersByRole(@RequestParam UserRole role) {
        List<MentorDTO> mentorDTOList = iSearchForMentor.getUsersByRole(role);
        return mentorDTOList;
    }

    //Controller for searching users by using three keywords
    @GetMapping("/searchByNameOrExpertiseOrIndustry")
    @ApiOperation("searching users using their Name or Expertise or Industry")
    public List<User> getBynameorexpertiseorindustry(@RequestParam(value="name", required=false) String name,
                                                     @RequestParam(value="expertise", required=false) String expertise,
                                                     @RequestParam(value="industry", required=false) String industry) {
        return iSearchForMentor.getUserByNameOrExpertiseOrIndustry(name, expertise, industry);
    }


    //Controller for getting user by expsertise or industry
    @GetMapping("/searchByKeyword")
    @ApiOperation("Search users by expertise or industry")
    public List<MentorDTO> searchByKeyword(@RequestParam("keyword") String keyword) {
        List<MentorDTO> mentorDTOs = iSearchForMentor.getByMentorDto(keyword);

        return mentorDTOs;
    }


   
    
    @GetMapping("/getSortedMentors")
    @ApiOperation("Sorting mentors by expertise, industry, and location")
    public List<MentorDTO> getSortedMentors(
    @RequestParam(required = false) String expertise,
    @RequestParam(required = false) String industry,
    @RequestParam(required = false) String location,
    @RequestParam Long menteeId
    ) {
    List<MentorDTO> mentorDTOList = iSearchForMentor.getUsersByRole(UserRole.MENTOR);
    mentorDTOList = mentorDTOList.stream()
    .filter(mentor -> mentorIsNotConnected(mentor, menteeId))
    .sorted((mentor1, mentor2) -> {
    if (expertise != null) {
    int expertiseComparison = compareExpertise(mentor1.getExpertise(), mentor2.getExpertise(), expertise);
    if (expertiseComparison != 0) {
    return expertiseComparison;
    }
    }
    if (industry != null) {
    int industryComparison = compareValues(mentor1.getIndustry(), mentor2.getIndustry());
    if (industryComparison != 0) {
    return industryComparison;
    }
    }
    if (location != null) {
    int locationComparison = compareValues(mentor1.getLocation(), mentor2.getLocation());
    if (locationComparison != 0) {
    return locationComparison;
    }
    }
    return mentor1.getUserName().compareToIgnoreCase(mentor2.getUserName());
    })
    .collect(Collectors.toList());
    
    for (MentorDTO mentorDTO : mentorDTOList) {
        Double averageMentorRating = feedbackRepository.calculateAverageRatingForMentorId(mentorDTO.getId());
        mentorDTO.setAverageMentorRating(averageMentorRating);
    }
   
    return mentorDTOList;
    }
    private int compareExpertise(String expertise1, String expertise2, String targetExpertise) {
    List<String> skills1 = Arrays.asList(expertise1.split(","));
    List<String> skills2 = Arrays.asList(expertise2.split(","));
    // Check if any of the skills match the target expertise
    boolean hasTargetSkill1 = skills1.stream().map(String::trim).anyMatch(skill -> skill.equalsIgnoreCase(targetExpertise));
    boolean hasTargetSkill2 = skills2.stream().map(String::trim).anyMatch(skill -> skill.equalsIgnoreCase(targetExpertise));
    if (hasTargetSkill1 && !hasTargetSkill2) {
    return -1; // mentor1 has the target expertise but mentor2 doesn't, so mentor1 comes before mentor2
    } else if (!hasTargetSkill1 && hasTargetSkill2) {
    return 1; // mentor1 doesn't have the target expertise but mentor2 does, so mentor1 comes after mentor2
    } else {
    return 0; // Both mentors either have the target expertise or don't have it, so the order remains unchanged
    }
    }
    private int compareValues(String value1, String value2) {
    if (value1.equalsIgnoreCase(value2)) {
    return 0;
    } else {
    return value1.compareToIgnoreCase(value2);
    }
    }
    private boolean mentorIsNotConnected(MentorDTO mentor, Long menteeId) {
    	 ConnectionRequestStatus[] connectedStatuses = {ConnectionRequestStatus.PENDING, ConnectionRequestStatus.ACCEPTED};
    	 User mentorUser = new User();
    	 mentorUser.setId(mentor.getId());
    	 mentorUser.setRoles(mentor.getRoles());
    	 mentorUser.setUserName(mentor.getUserName());
    	 mentorUser.setUserMail(mentor.getUserMail());
    	 mentorUser.setPhoneNumber(mentor.getPhoneNumber());
    	 mentorUser.setExpertise(mentor.getExpertise());
    	 mentorUser.setLocation(mentor.getLocation());
    	 mentorUser.setBio(mentor.getBio());
    	 mentorUser.setProfileImage(mentor.getProfileImage());
    	 mentorUser.setIndustry(mentor.getIndustry());
    	 mentorUser.setChargePerHour(mentor.getChargePerHour());
    	 // Set other properties of the mentorUser as needed
    	 return !connectionRequestRepository.existsByMentorAndMenteeIdAndStatusIn(mentorUser, menteeId, connectedStatuses);
    	 }
}


















