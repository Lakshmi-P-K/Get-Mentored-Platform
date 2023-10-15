package com.nineleaps.authentication.jwt.service;

import java.util.List;

//
import com.nineleaps.authentication.jwt.dto.ConnectionRequestDto;
import com.nineleaps.authentication.jwt.entity.User;

public interface IConnectionRequestService {


	ConnectionRequestDto sendConnectionRequest(User mentor, User mentee, String message);
	ConnectionRequestDto acceptConnectionRequest(Long connectionRequestId, Long mentorId, Long menteeId);
	ConnectionRequestDto rejectConnectionRequest(Long connectionRequestId, Long mentorId, Long menteeId);
	List<ConnectionRequestDto> getAllConnectionRequestsByUserId(Long userId);
	public List<Object[]> findConnectionsByMenteeId(Long menteeId);

}






