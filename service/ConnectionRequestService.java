package com.nineleaps.authentication.jwt.service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nineleaps.authentication.jwt.dto.ConnectionRequestDto;
import com.nineleaps.authentication.jwt.entity.ConnectionRequest;
import com.nineleaps.authentication.jwt.entity.User;
import com.nineleaps.authentication.jwt.enums.ConnectionRequestStatus;
import com.nineleaps.authentication.jwt.exception.DuplicateConnectionRequestException;
import com.nineleaps.authentication.jwt.repository.ConnectionRequestRepo;
import com.nineleaps.authentication.jwt.repository.UserRepository;
@Service
public class ConnectionRequestService implements IConnectionRequestService {
     @Autowired
     private UserRepository userRepository;
     @Autowired
     private ConnectionRequestRepo connectionRequestRepo;
     @Autowired
     private UserService userService;
     @Autowired
     private EmailServiceimpl emailService;
     @Override
     public ConnectionRequestDto acceptConnectionRequest(Long connectionRequestId, Long mentorId, Long menteeId) {
         Optional<ConnectionRequest> connectionRequestOpt = connectionRequestRepo.findById(connectionRequestId);
         if (!connectionRequestOpt.isPresent()) {
             // Handle the case where the connection request does not exist
             return null;
         }
         ConnectionRequest connectionRequest = connectionRequestOpt.get();
         if (connectionRequest.getMentee().getId() != menteeId) {
             // Handle the case where the mentee ID does not match
             return null; 
         }
         connectionRequest.setStatus(ConnectionRequestStatus.ACCEPTED);
         connectionRequest.setAcceptanceTime(LocalDateTime.now()); // Set the acceptance time to the current time
         ConnectionRequest savedConnectionRequest = connectionRequestRepo.save(connectionRequest);
         ConnectionRequestDto connectionRequestDto = new ConnectionRequestDto(savedConnectionRequest);
         String menteeEmail = userService.getMenteeEmailById(menteeId);
         // Compose email subject and content
         String subject = "Acceptance Notification";
         String mentorName = savedConnectionRequest.getMentor().getUserName();
         String content = "Your request has been accepted by: " + mentorName;
         // Send the email
         emailService.sendEmail(subject, content, menteeEmail);
         return connectionRequestDto;
     }
 	
	 	public List<User> findUsersWithoutAcceptedConnections(Long menteeId) {
	 	    return userRepository.findUsersWithoutAcceptedConnections(menteeId);
	 	}
	 	@Override
	 	public ConnectionRequestDto rejectConnectionRequest(Long connectionRequestId, Long mentorId, Long menteeId) {
	 	    Optional<ConnectionRequest> connectionRequestOpt = connectionRequestRepo.findById(connectionRequestId);
	 	    if (!connectionRequestOpt.isPresent()) {
	 	        return null; // or throw appropriate exception
	 	    }
	 	   
	 	    ConnectionRequest connectionRequest = connectionRequestOpt.get();
	 	    if (!connectionRequest.getMentor().getId().equals(mentorId)) {
	 	        return null; // or throw appropriate exception
	 	    }
	 	   
	 	    connectionRequest.setStatus(ConnectionRequestStatus.REJECTED);
	 	    connectionRequest.setRejectionTime(LocalDateTime.now()); // Set the rejection time to the current time
	 	    ConnectionRequest savedConnectionRequest = connectionRequestRepo.save(connectionRequest);
	 	    ConnectionRequestDto connectionRequestDto = new ConnectionRequestDto(savedConnectionRequest);
	 	   
	 	    return connectionRequestDto;
	 	}
 	
 	
	 	
	 	 public ConnectionRequestStatus getConnectionStatus(Long menteeId, Long mentorId) {
	         ConnectionRequest connectionRequest = connectionRequestRepo.findByMenteeIdAndMentorId(menteeId, mentorId);
	         if (connectionRequest == null) {
	             return null;
	         }
	         return connectionRequest.getStatus();
	     }
	
	 	
	 	
	 	@Override
	 	public ConnectionRequestDto sendConnectionRequest(User mentor, User mentee, String message) {
	 	    ConnectionRequest connectionRequest = connectionRequestRepo.findByMentorAndMentee(mentor, mentee);
	 	   
	 	    if (connectionRequest != null && connectionRequest.getStatus() == ConnectionRequestStatus.REJECTED) {
	 	        // Connection request previously rejected, update the status to "Pending"
	 	        connectionRequest.setStatus(ConnectionRequestStatus.PENDING);
	 	        connectionRequest.setMessage(message);
	 	        connectionRequest.setRequestTime(LocalDateTime.now()); // Set the request time
	 	        connectionRequestRepo.save(connectionRequest);
	 	        return convertToDTO(connectionRequest);
	 	    }
	 	   
	 	    if (connectionRequestRepo.existsByMentorAndMentee(mentor, mentee)) {
	 	        throw new DuplicateConnectionRequestException("A connection request already exists.");
	 	    }
	 	    connectionRequest = new ConnectionRequest();
	 	    connectionRequest.setMentor(mentor);
	 	    connectionRequest.setMentee(mentee);
	 	    connectionRequest.setMessage(message);
	 	    connectionRequest.setStatus(ConnectionRequestStatus.PENDING);
	 	    connectionRequest.setRequestTime(LocalDateTime.now()); // Set the request time
	 	    connectionRequestRepo.save(connectionRequest);
	 	    return convertToDTO(connectionRequest);
	 	}
 	
	 	@Override
	 	public List<ConnectionRequestDto> getAllConnectionRequestsByUserId(Long userId) {
	 		
	 		 List<ConnectionRequest> connectionRequests = connectionRequestRepo.findAllByMenteeIdOrMentorId(userId, userId);
	 	        List<ConnectionRequestDto> connectionRequestDtos = new ArrayList<>();
		 	        for (ConnectionRequest connectionRequest : connectionRequests) {
		 	            connectionRequestDtos.add(mapToConnectionRequestDto(connectionRequest));
		 	        }
	 	        return connectionRequestDtos;
	 	    }
	
		 private ConnectionRequestDto mapToConnectionRequestDto(ConnectionRequest connectionRequest) {
			
			 	ConnectionRequestDto connectionRequestDto = new ConnectionRequestDto();
				  connectionRequestDto.setId(connectionRequest.getId());
				  connectionRequestDto.setMentorId(connectionRequest.getMentor().getId());
				  connectionRequestDto.setMenteeId(connectionRequest.getMentee().getId());
				  connectionRequestDto.setMessage(connectionRequest.getMessage());
				  connectionRequestDto.setStatus(connectionRequest.getStatus());
				  connectionRequestDto.setRequestTime(connectionRequest.getRequestTime());
				  connectionRequestDto.setAcceptanceTime(connectionRequest.getAcceptanceTime());
				  connectionRequestDto.setRejectionTime(connectionRequest.getRejectionTime());
				  return connectionRequestDto;
		 }
	     public List<Object[]> findConnectionsByMenteeId(Long menteeId) {
	         return connectionRequestRepo.findConnectionsByMenteeId(menteeId);
	     }
	
	
	 	private ConnectionRequestDto convertToDTO(ConnectionRequest connectionRequest) {
	 	    ConnectionRequestDto connectionRequestDTO = new ConnectionRequestDto();
	 	    connectionRequestDTO.setId(connectionRequest.getId());
	 	    connectionRequestDTO.setMenteeId(connectionRequest.getMentee().getId());
	 	    connectionRequestDTO.setMentorId(connectionRequest.getMentor().getId());
	 	    connectionRequestDTO.setMessage(connectionRequest.getMessage());
	 	    connectionRequestDTO.setStatus(connectionRequest.getStatus());
	 	    return connectionRequestDTO;
	 	}

	 	
	 	public ConnectionRequestDto recommendMentors(Long connectionRequestId, Long mentorId, Long menteeId, List<Long> recommendedMentorIds) {
	 	    Optional<ConnectionRequest> optionalConnectionRequest = connectionRequestRepo.findById(connectionRequestId);
	 	    if (optionalConnectionRequest.isPresent()) {
	 	        ConnectionRequest connectionRequest = optionalConnectionRequest.get();
	 	        
	 	        // Set the recommended mentors
	 	        connectionRequest.setRecommendedMentors(recommendedMentorIds);
	 	        
	 	        // Save the updated connection request
	 	        connectionRequestRepo.save(connectionRequest);
	 	        
	 	        // Create and return the ConnectionRequestDto
	 	        ConnectionRequestDto connectionRequestDto = new ConnectionRequestDto(connectionRequest);
	 	        connectionRequestDto.setRecommendedMentors(recommendedMentorIds);
	 	        
	 	        return connectionRequestDto;
	 	    } else {
	 	        return null;
	 	    }
	 	}
	 	
	 	public List<User> getRecommendedMentorsByMenteeId(Long menteeId) {
	 		 List<ConnectionRequest> connectionRequests = connectionRequestRepo.findByMenteeId(menteeId);
	 		 for (ConnectionRequest connectionRequest : connectionRequests) {
	 		 connectionRequest.getRecommendedMentors().size(); // Initialize the collection
	 		 }
	 		 List<Long> mentorIds = connectionRequests.stream()
	 		 .filter(request -> {
	 		 ConnectionRequestStatus status = getConnectionStatus(menteeId, request.getMentor().getId());
	 		 boolean filterResult = status == null || (status != ConnectionRequestStatus.PENDING && status != ConnectionRequestStatus.ACCEPTED);
	 		 System.out.println("Mentor ID: " + request.getMentor().getId() + ", Status: " + status + ", Filtered: " + filterResult);
	 		 return filterResult;
	 		 })
	 		 .flatMap(request -> request.getRecommendedMentors().stream())
	 		 .distinct()
	 		 .filter(mentorId -> {
	 		 ConnectionRequestStatus status = getConnectionStatus(menteeId, mentorId);
	 		 return status != ConnectionRequestStatus.PENDING && status != ConnectionRequestStatus.ACCEPTED;
	 		 })
	 		 .collect(Collectors.toList());
	 		 List<User> recommendedMentors = mentorIds.stream()
	 		 .map(mentorId -> userRepository.findById(mentorId).orElse(null))
	 		 .filter(Objects::nonNull)
	 		 .collect(Collectors.toList());
	 		 System.out.println("Recommended Mentors: " + recommendedMentors);
	 		 return recommendedMentors;
	 		 }

	 	
	 	
	 	public List<User> getUsersWithAcceptedConnection(Long userId) {
	 		 List<User> usersWithAcceptedConnection = new ArrayList<>();
	 		 // Retrieve all the connections for the user
	 		 List<ConnectionRequest> connections = connectionRequestRepo.findByMenteeIdOrMentorId(userId, userId);
	 		 for (ConnectionRequest connection : connections) {
	 		 if (connection.getStatus() == ConnectionRequestStatus.ACCEPTED) {
	 		 User connectedUser = null;
	 		 // Determine if the given user is the mentee or mentor in the connection
	 		 if (connection.getMentee().getId().equals(userId)) {
	 		 connectedUser = connection.getMentor();
	 		 } else if (connection.getMentor().getId().equals(userId)) {
	 		 connectedUser = connection.getMentee();
	 		 }
	 		 if (connectedUser != null) {
	 		 usersWithAcceptedConnection.add(connectedUser);
	 		 }
	 		 }
	 		 }
	 		 return usersWithAcceptedConnection;
	 		 }
	
	 	
	
 }