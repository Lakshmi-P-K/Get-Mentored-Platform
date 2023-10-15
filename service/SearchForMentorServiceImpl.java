package com.nineleaps.authentication.jwt.service;

import com.nineleaps.authentication.jwt.dto.MentorDTO;
import com.nineleaps.authentication.jwt.entity.User;
import com.nineleaps.authentication.jwt.enums.UserRole;
import com.nineleaps.authentication.jwt.repository.FeedbackRepository;
import com.nineleaps.authentication.jwt.repository.SearchRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchForMentorServiceImpl implements ISearchForMentor{

    @Autowired
    private SearchRepository searchRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private FeedbackRepository feedbackRepository;

    @Override
    public List<User> getUserByName(String name) {
        return searchRepository.findByuserNameContainingIgnoreCase(name);
    }



    @Override
    public List<User> getUserByExpertise(String expertise) {
        return searchRepository.findByExpertiseContaining(expertise);
    }



    @Override
    public List<User> getUserByIndustry(String industry) {
        return searchRepository.findByIndustryContainingIgnoreCase(industry);
    }
    
    //getting users by role(mentor,mentee)
    @Override
    public List<MentorDTO> getUsersByRole(UserRole role) {
        List<User> users = searchRepository.findByRoles(role);
        List<MentorDTO> mentorDTOList=new ArrayList<>();
        for(User user:users){
            System.out.println(user.getId());
            mentorDTOList.add(modelMapper.map(user,MentorDTO.class));
        }
        return mentorDTOList;
    }



    @Override
    public List<User> getUserByNameOrExpertiseOrIndustry(String name,String expertise,String industry) {
        return searchRepository.findByuserNameOrExpertiseOrIndustry(name,expertise,industry);
    }

    //
    @Override
    public List<User> getUsersByKeyword(String keyword) {

        return searchRepository.findByKeyword(keyword);
    }
    //Implementation for Getting user by keyword(Expertise or industry)

    //Method used for searching using model mapper with MENTORDTO class
    @Override
    public List<MentorDTO> getByMentorDto(String keyword) {
        List<User> users = searchRepository.findByMentorDto(keyword);
        List<MentorDTO> mentorDTOs = new ArrayList<>();

        for (User user : users) {
            MentorDTO mentorDTO = modelMapper.map(user, MentorDTO.class);

            // Calculate average mentor rating for the current user
            Long mentorId = mentorDTO.getId();
            Double averageMentorRating = feedbackRepository.calculateAverageRatingForMentorId(mentorId);
            mentorDTO.setAverageMentorRating(averageMentorRating);

            // Calculate average mentee rating for the current user
            Long menteeId = mentorDTO.getId(); // Assuming you have a menteeId property in MentorDTO
            Double averageMenteeRating = feedbackRepository.calculateAverageRatingForMenteeId(menteeId);
            mentorDTO.setAverageMenteeRating(averageMenteeRating);

            mentorDTOs.add(mentorDTO);
        }

        return mentorDTOs;
    }

    


}