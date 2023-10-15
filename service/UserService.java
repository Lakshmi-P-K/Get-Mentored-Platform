package com.nineleaps.authentication.jwt.service;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.nineleaps.authentication.jwt.dto.SSOUserDto;
import com.nineleaps.authentication.jwt.entity.User;
import com.nineleaps.authentication.jwt.repository.UserRepository;

@Transactional
@Service

public class UserService implements IUserService{

	
	@Autowired
	private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmailServiceimpl emailService;

		public UserService(UserRepository userRepository) {
			this.userRepository = userRepository;
		}
		
		public List<User> findUsersWithoutAcceptedConnections(Long menteeId) {
		    return userRepository.findUsersWithoutAcceptedConnections(menteeId);
		} 
			
		@Override
		public User getUserViaPhoneNumber(String phoneNumber) {
			return userRepository.findByPhoneNumber(phoneNumber);
		}
		 
		 public void deleteUserById(Long userId) {
		        userRepository.deleteById(userId);
		 }
		 public String getEncodedPassword(String password){
			 return passwordEncoder.encode(password);
	    }
		 
		@Override
		public User updateUser(User user) {
		
			return userRepository.save(user);
		}

		@Override
		public User getUserByMail(String userMai) {
		
			return userRepository.findByUserMail(userMai);
		}
		
		public User getUserById1(Long Id) {
			return userRepository.findById(Id).get();
		}
	
		public User getUserById(Long Id) {
			return userRepository.findById(Id).get();
		}
	
		
		public User registerNewUser(User user) {
		    user.setUserPassword(getEncodedPassword(user.getUserPassword()));
		    User savedUser = userRepository.save(user);
		    
		    String subject = "REGISTRATION PROCESS";
		    String message = "You are Successfully Registered and Welcome to the Learn Buddy Platform";
		    String to = savedUser.getUserMail(); // Assuming 'getEmail()' returns the user's email address
		    
		    emailService.sendEmail(subject, message, to);
		    
		    return savedUser;
	}

		public boolean isEmailExists(String userMail) {
			  User existingUser = userRepository.findByUserMail(userMail);
		        return existingUser != null;// TODO Auto-generated method stub
	}

		@Override
		public User insertuser(SSOUserDto newSsoUser) {
			User user = new User();
		    user.setUserName(newSsoUser.getUserName());
		    user.setUserMail(newSsoUser.getUserMail());
		return userRepository.save(user);
	}
		@Override
		public User findUserByEmail(String userMail) {
			// TODO Auto-generated method stub
			return userRepository.findByUserMail(userMail);
	}
		@Override
		public User insertUser(SSOUserDto newSsoUser) {
			// TODO Auto-generated method stub
			User user = new User();
		    user.setUserName(newSsoUser.getUserName());
		    user.setUserMail(newSsoUser.getUserMail());
		    return userRepository.save(user);
	}
		
		public String getMenteeEmailById(Long menteeId) {
		    User mentee = userRepository.findById(menteeId)
		            .orElseThrow();
		    return mentee.getUserMail();
	}

		
 
}
	


	


