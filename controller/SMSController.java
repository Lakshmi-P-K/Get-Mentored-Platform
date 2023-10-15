package com.nineleaps.authentication.jwt.controller;

import com.nineleaps.authentication.jwt.service.IUserService;
import com.nineleaps.authentication.jwt.service.SmsService;
import com.nineleaps.authentication.jwt.util.JwtUtils;
import io.swagger.annotations.ApiOperation;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import com.nineleaps.authentication.jwt.dto.StoreOTP;
import com.nineleaps.authentication.jwt.dto.TempOTP;
import com.nineleaps.authentication.jwt.entity.RefreshToken;
import com.nineleaps.authentication.jwt.entity.User;
import com.nineleaps.authentication.jwt.enums.UserRole;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.nineleaps.authentication.jwt.dto.JwtResponse;
import com.nineleaps.authentication.jwt.dto.SmsPojo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nineleaps.authentication.jwt.service.RefreshTokenService;
import java.io.IOException;

@RequestMapping("/api/v1/OTP")
@RestController
public class SMSController {
    @Autowired
     SmsService service;
    @Autowired
	 IUserService userservice;


   @Autowired
   private RefreshTokenService refreshTokenService;

    @Autowired
    private SimpMessagingTemplate webSocket;
    private String number;
    private final String TOPIC_DESTINATION = "/lesson/sms";

   @PostMapping("/send")
   @ApiOperation("Send an OTP for Login using phone Number and OTP")
    public ResponseEntity<String> smsSubmit(@RequestBody SmsPojo sms){
       try{
         
           service.send(sms);
        
       }catch (Exception e){
           return new ResponseEntity<String>("something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
       }
       webSocket.convertAndSend(TOPIC_DESTINATION,getTimeStamp()+":SMS has been sent "+sms.getPhoneNumber());
       return new ResponseEntity<String>("otp sent",HttpStatus.OK);  
   }
    

   @PostMapping("/login/verifyOTP")
   @ApiOperation("Verify the OTP sent to your Mobile Number  for Login using phone Number and OTP")
   public ResponseEntity<?> verifyOTP(@RequestBody TempOTP tempOTP, HttpServletResponse response) throws Exception {
       if (tempOTP.getOtp() == StoreOTP.getOtp()) {
           String phoneNumber = service.getPhoneNumber();          
           // Instantiate the Sms class and set the phoneNumber
           SmsPojo sms = new SmsPojo();
           sms.setPhoneNumber(phoneNumber);           
           User user = userservice.getUserViaPhoneNumber(sms.getPhoneNumber());
           System.out.println(user);
           RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getUserMail());
       		
       				if (user != null) {
			               String email = user.getUserMail();
			              String accessToken= generateToken(email, phoneNumber, response);
			           
			               JwtResponse jwtResponse = new JwtResponse();
			               jwtResponse.setAccessToken(accessToken);
			               jwtResponse.setRefreshToken(refreshToken.getToken());
			               return ResponseEntity.ok(jwtResponse);}

       				else {
       						return ResponseEntity.ok("user not found") ;}}
           
       		else {
       				return ResponseEntity.ok("not a correct otp"); }}


   @PostMapping("/verifyOTP/signUp")
   @ApiOperation("Verify the OTP sent to your Mobile Number  for sign Up using phone Number and OTP")
   
   public String verifyOTPsignup(@RequestBody TempOTP sms,HttpServletResponse response) throws Exception{
	   
       if(sms.getOtp()== StoreOTP.getOtp()) {    	   
    	   return "correct otp";}    	   
       else
    	   return  "not a correct otp";}

   private static final String SECRET_KEY = "HRlELXqpSBssiieeeaqwertyujhgfdszxcASWERFDXCDWqwertyggffAQWSXCFRFFFTYHJkkitrsaafarsrAASSDFQWERQWASDWESDYUNJXCVRTYWSXCDERFVTYHJUK";

   public String generateToken(String email, String phoneNumber, HttpServletResponse response) throws IOException {
	    User userDtls = userservice.getUserByMail(email.trim());
	    Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY.getBytes());
	    Set<UserRole> roles = userDtls.getRoles();

	    // Extract role names as strings
	    List<String> roleNames = new ArrayList<>();
	    for (UserRole role : roles) {
	        roleNames.add(role.toString());
	    }

	    // Convert the List<String> to a comma-separated string
	    String rolesString = String.join(",", roleNames);

	    String accessToken = JWT.create()
	            .withSubject(email)
	            .withClaim("phoneNumber", phoneNumber) // Add phone number claim
	            .withClaim("roles", rolesString)
	            .withClaim("userId", userDtls.getId())
	           // .withExpiresAt(new Date(System.currentTimeMillis() + 2 * 24 * 60 * 60 * 1000))
	            .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 *10))
	            .sign(algorithm);
	    return accessToken;
	}

   private String getTimeStamp(){
       return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
   }
}