package com.nineleaps.authentication.jwt.controller;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.nineleaps.authentication.jwt.dto.ConnectionRequestDto;
import com.nineleaps.authentication.jwt.dto.ConnectionRequestStatisticsReportDTO;
import com.nineleaps.authentication.jwt.dto.UserDTO;
import com.nineleaps.authentication.jwt.entity.User;
import com.nineleaps.authentication.jwt.enums.ConnectionRequestStatus;
import com.nineleaps.authentication.jwt.enums.UserRole;
import com.nineleaps.authentication.jwt.repository.ConnectionRequestRepo;
import com.nineleaps.authentication.jwt.repository.FeedbackRepository;
import com.nineleaps.authentication.jwt.repository.UserRepository;
import com.nineleaps.authentication.jwt.service.ConnectionRequestService;
import com.nineleaps.authentication.jwt.service.UserService;
import io.swagger.annotations.ApiOperation;
@RestController
@RequestMapping("/api/v1/connectionRequests")
public class ConnectionRequestController {
	
	@Autowired
    private ConnectionRequestService connectionRequestService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private FeedbackRepository feedbackRepository;
	
	@Autowired
	private ConnectionRequestRepo connectionRequestrepo;
	@PostMapping("/sendConnection")
	 @ApiOperation("Send a Connection Request to your chosen mentor")
	public ResponseEntity<ConnectionRequestDto> sendConnectionRequest(@RequestParam Long mentorId, @RequestParam Long menteeId, @RequestParam String message) {
	    // Retrieve the mentor and mentee from the database
	    User mentor = userRepository.findById(mentorId).orElseThrow();
	    User mentee = userRepository.findById(menteeId).orElseThrow();
	   
	    // Send the connection request
	    ConnectionRequestDto connectionRequest = connectionRequestService.sendConnectionRequest(mentor, mentee, message);
	   
	    //Set the connection request fields and status
	    connectionRequest.setId(connectionRequest.getId());
	    connectionRequest.setMenteeId(mentee.getId());
	    connectionRequest.setMentorId(mentor.getId());
	    connectionRequest.setMessage(connectionRequest.getMessage());
	    connectionRequest.setStatus(ConnectionRequestStatus.PENDING);
	  
	    connectionRequest.setRequestTime(LocalDateTime.now());
	   
	    // Return the connection request with status as a response
	   
	    return ResponseEntity.ok(connectionRequest);
	}	
	 @GetMapping("/withoutConnectedMentors")
	 @ApiOperation("Get users without accepted connection")
	    public List<User> findUsersWithoutAcceptedConnections(@RequestParam Long menteeId) {
	        return userService.findUsersWithoutAcceptedConnections(menteeId);
	    }
    @PutMapping("/accept")
    @ApiOperation("Accept a connection Request sent by Mentee")
    public ResponseEntity<ConnectionRequestDto> acceptConnectionRequest(@RequestParam("id") Long connectionRequestId,
            @RequestParam("mentorId") Long mentorId,
            @RequestParam("menteeId") Long menteeId) {
    	ConnectionRequestDto connectionRequestDto = connectionRequestService.acceptConnectionRequest(connectionRequestId, mentorId, menteeId);
    	if (connectionRequestDto == null) {
    		// Handle the case where the connection request could not be accepted
    		return ResponseEntity.badRequest().build();
    	}
    	return ResponseEntity.ok(connectionRequestDto);
    }
    @PutMapping("/reject")
    @ApiOperation("Reject a connection Request sent by Mentee")
    public ResponseEntity<ConnectionRequestDto> rejectConnectionRequest(
            @RequestParam Long connectionRequestId,
            @RequestParam Long mentorId,
            @RequestParam Long menteeId) {
        ConnectionRequestDto connectionRequestDto = connectionRequestService.rejectConnectionRequest(connectionRequestId, mentorId,menteeId);
        if (connectionRequestDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(connectionRequestDto);
    }
    
    @GetMapping("/acceptedConnectionsByUserId")
    @ApiOperation("Get Users with Accepted Connection By User Id")
    public ResponseEntity<List<UserDTO>> getUsersWithAcceptedConnection(@RequestParam Long userId) {
    User user = userService.getUserById(userId);
    if (user == null) {
    return ResponseEntity.notFound().build();
    }
    List<UserDTO> usersWithAcceptedConnection = new ArrayList<>();
    List<User> connectedUsers = connectionRequestService.getUsersWithAcceptedConnection(userId);
    for (User connectedUser : connectedUsers) {
    UserDTO userDTO = populateUserDTOWithAverageRating(connectedUser);
    usersWithAcceptedConnection.add(userDTO);
    }
    return ResponseEntity.ok(usersWithAcceptedConnection);
    }
    public UserDTO populateUserDTOWithAverageRating(User user) {
    	 UserDTO userDTO = new UserDTO();
    	 // Set other user details
    	 userDTO.setId(user.getId());
    	 userDTO.setUserName(user.getUserName());
    	 userDTO.setUserMail(user.getUserMail());
    	 userDTO.setBio(user.getBio());
    	 userDTO.setProfileImage(user.getProfileImage());
    	 userDTO.setPhoneNumber(user.getPhoneNumber());
    	 userDTO.setExpertise(user.getExpertise());
    	 userDTO.setIndustry(user.getIndustry());
    	 userDTO.setLocation(user.getLocation());
    	 userDTO.setMentoringRequiredFor(user.getMentoringRequiredFor());
    	 Double averageRating = null;
 	    
     
     	    
     	    if (user.getRoles().contains(UserRole.MENTOR)) {
     	        averageRating = feedbackRepository.calculateAverageRatingForMentorId(user.getId());
     	    } else if (user.getRoles().contains(UserRole.MENTEE)) {
     	        averageRating = feedbackRepository.calculateAverageRatingForMenteeId(user.getId());
     	    }
     	    
     	   
    	 userDTO.setAverageRating(averageRating);
    	 return userDTO;
    	 }
    @GetMapping("/getAllConnectionRequests")
    @ApiOperation("Get all connection Requests")
    public ResponseEntity<List<ConnectionRequestDto>> getAllConnectionRequestsByUserId(@RequestParam Long userId) {
        List<ConnectionRequestDto> connectionRequests = connectionRequestService.getAllConnectionRequestsByUserId(userId);
        return new ResponseEntity<>(connectionRequests, HttpStatus.OK);
    }
	@GetMapping("/searchTheConnectionByMentorId")
	  @ApiOperation("Search for a connection Request by MenteeId")
	public List<Object[]> searchByConnectionId(@RequestParam("mentorId") Long menteeId) {
		return connectionRequestService.findConnectionsByMenteeId(menteeId);
	}
	
	
	@GetMapping("/status")
	public ResponseEntity<String> getConnectionStatus(@RequestParam Long menteeId, @RequestParam Long mentorId) {
	    ConnectionRequestStatus connectionStatus = connectionRequestService.getConnectionStatus(menteeId, mentorId);
	    if (connectionStatus == null) {
	        String message = "The connection between the mentee and mentor does not exist.";
	        return ResponseEntity.ok(message);
	    }
	    return ResponseEntity.ok(connectionStatus.toString());
	}
	
	@PutMapping("/recommendMentors")
	@ApiOperation("Recommend mentors for a connection request")
	public ResponseEntity<ConnectionRequestDto> recommendMentors(
	        @RequestParam Long connectionRequestId,
	        @RequestParam Long mentorId,
	        @RequestParam Long menteeId,
	        @RequestParam List<Long> recommendedMentorIds) {
	    ConnectionRequestDto connectionRequestDto = connectionRequestService.recommendMentors(connectionRequestId, mentorId, menteeId, recommendedMentorIds);
	    if (connectionRequestDto == null) {
	        return ResponseEntity.notFound().build();
	    }
	   
	    return ResponseEntity.ok(connectionRequestDto);
	}
	@GetMapping("/mentee/recommendedMentors")
	@ApiOperation("Get all recommended mentors for a mentee")
	public ResponseEntity<List<User>> getRecommendedMentors(@RequestParam Long menteeId) {
 List<User> recommendedMentors = connectionRequestService.getRecommendedMentorsByMenteeId(menteeId);
 return ResponseEntity.ok(recommendedMentors);
	}
	 
	 @GetMapping("/pendingConnectionByUserId")
	 @ApiOperation("giving only the pending connections")
		public List<Map<String, Object>> getConnectionDetailsByUserId(@RequestParam("userId") Long userId) {
		    List<Object[]> results = connectionRequestrepo.findConnectionDetailsByUserId(userId);
		    List<Map<String, Object>> connectionDetails = new ArrayList<>();

		    for (Object[] row : results) {
		        Map<String, Object> connection = new HashMap<>();
		        connection.put("menteeName", row[0]);
		        connection.put("mentorName", row[1]);
		        connection.put("menteeId", row[2]);
		        connection.put("mentorId", row[3]);
		        connection.put("message", row[4]);
		        connectionDetails.add(connection);
		    }

		    return connectionDetails;
		}
	 
	 
	 @GetMapping("/countsOfConnectionRequest")
	 public ResponseEntity<ConnectionRequestStatisticsReportDTO> getSlotCountsByMentorAndDateRange(
	        @RequestParam("mentorId") Long mentorId,
	        @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
	        @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate
	 ) {
	     LocalDateTime start = startDate.atStartOfDay();
	     LocalDateTime end = endDate.atStartOfDay().plusDays(1L);
	     Long recievedConnections=connectionRequestrepo.getConnectionsRecievedByMentorAndDateRange(mentorId, start, end);
	     Long acceptedConnections=connectionRequestrepo.getConnectionsAcceptedByMentorAndDateRange(mentorId,start,end);
	     Long pendingConnections=connectionRequestrepo.getConnectionsPendingByMentorAndDateRange(mentorId,start,end);
	     Long rejectedConnections=connectionRequestrepo.getConnectionsRejectedByMentorAndDateRange(mentorId,start,end);

	     return new ResponseEntity<>(new ConnectionRequestStatisticsReportDTO(recievedConnections, acceptedConnections, pendingConnections,rejectedConnections), HttpStatus.OK);
	 }

		
}




