package com.nineleaps.authentication.jwt.service;

import com.nineleaps.authentication.jwt.dto.SmsPojo;
import com.nineleaps.authentication.jwt.dto.StoreOTP;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.text.ParseException;
@Component @Getter @Setter
public class SmsService {


  private String phoneNumber;
//private final String ACCOUNT_SID="****";
//private final String AUTH_TOKEN ="*****";
//private final String FROM_NUMBER="*****";

 private  String ACCOUNT_SID="ACfca5f396dd3f73ca4a12adf4b97c5e39";
 private  String AUTH_TOKEN ="7d0c0716bd0a069dc890745a76c63d5e";
 private  String FROM_NUMBER="+16187063587";

    public void send(SmsPojo sms) throws ParseException{
        Twilio.init(ACCOUNT_SID,AUTH_TOKEN);

        int min = 100000;
        int max = 999999;
        int number  = (int)(Math.random()*(max-min +1)+min);
   
       
        String msg = "Your OTP - "+number+"   please verify this otp   a6mge7C9IMY";
        Message message = Message.creator(new PhoneNumber(sms.getPhoneNumber()),new PhoneNumber(FROM_NUMBER),msg)
                .create();
        StoreOTP.setOtp(number);
        phoneNumber = sms.getPhoneNumber();
        
    }
    public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public void resend(SmsPojo resendsms) throws ParseException{
        Twilio.init(ACCOUNT_SID,AUTH_TOKEN);

        int min = 100000;
        int max = 999999;
        int number  = (int)(Math.random()*(max-min +1)+min);

        String msg = "Your OTP - "+number+"please verify this otp";
        Message newmessage = Message.creator(new PhoneNumber(resendsms.getPhoneNumber()),new PhoneNumber(FROM_NUMBER),msg)
                .create();
        StoreOTP.setOtp(number);
    }
    public void recieve (MultiValueMap<String,String> smscallback){

    }

}
