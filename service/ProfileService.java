package com.nineleaps.authentication.jwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.nineleaps.authentication.jwt.entity.User;
import com.nineleaps.authentication.jwt.repository.UserRepository;

@Service
public class ProfileService {
    
        private final UserRepository userRepository;

        @Autowired
        public ProfileService(UserRepository userRepository) {
            this.userRepository = userRepository;
        }


        public void uploadImage(String userMail, byte[] imageBytes) throws NotFoundException {
            User user = userRepository.findByuserMail(userMail)
                    .orElseThrow(() -> new NotFoundException());
            user.setProfileImage(imageBytes);
            userRepository.save(user);
        }
    

}
