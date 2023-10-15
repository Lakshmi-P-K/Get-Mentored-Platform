package com.nineleaps.authentication.jwt.service;

import org.springframework.stereotype.Service;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


@Service
public class EmailServiceImplementation implements IEmailServices {


    private static final int OTP_LENGTH = 6;
    private static final int TIMEOUT = 5 * 60 * 1000; // 5 minutes in milliseconds
    private static String userEmail;
    private final Map<String, Long> otpMap = new HashMap<>();
    private final Map<String, String> otpMapForOtp = new HashMap<>();
    private final Random random = new Random();

    public String generateOTP(String userEmail) {
        String otp = generateRandomOTP();
        this.userEmail = userEmail;
        storeOTP(userEmail, otp);
        scheduleTimeout(userEmail);
        return otp;
    }

    private String generateRandomOTP() {
        StringBuilder otpBuilder = new StringBuilder();
        for (int i = 0; i < OTP_LENGTH; i++) {
            otpBuilder.append(random.nextInt(10));
        }
        return otpBuilder.toString();
    }

    private void storeOTP(String userEmail, String otp) {
        otpMap.put(userEmail, System.currentTimeMillis());
        otpMapForOtp.put(userEmail, otp);
        // store OTP in database or cache
    }

    private void scheduleTimeout(String userEmail) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Invalidate the OTP here
                otpMap.remove(userEmail);
                // update OTP status in database or cache
            }
        }, TIMEOUT);
    }

    public boolean verifyOTP(String otp, String enteredOTP) {
        Long creationTime = otpMap.get(userEmail);
        if (creationTime == null) {
            System.out.println("otp session expired");
            return false; // OTP not found or has expired
        }
        if (System.currentTimeMillis() - creationTime > TIMEOUT) {
            otpMap.remove(userEmail);
            otpMapForOtp.remove(userEmail);
            return false; // OTP has expired
        }
        String storedOTP = getStoredOTP(userEmail); // get stored OTP from database or cache
        System.out.println(storedOTP);
        return enteredOTP.equals(storedOTP);
    }

    private String getStoredOTP(String userEmail) {
        // get stored OTP from database or cache
        return otpMapForOtp.get(userEmail);
    }

    // this is responsible to send email..
    public boolean sendEmail(String otp, String to) {

        // Variable for gmail
        String host = "smtp.gmail.com";
        String from = "learnbuddy";
        // String from = "irfan.hussain@nineleaps.com";
        String subject = "Email Verification Through OTP";
        String message = "Otp for your verification is : ";
        boolean f = false;
        // get the system properties
        Properties properties = System.getProperties();
        System.out.println("PROPERTIES " + properties);

        // setting important information to properties object

        // host set
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Step 1: to get the session object..
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("elanifu@gmail.com", "anyuvqzjhlwavwrd");
            }

        });

        session.setDebug(true);

        // Step 2 : compose the message [text,multi media]
        MimeMessage m = new MimeMessage(session);

        try {

            // from email
            m.setFrom(from);

            // adding recipient to message
            m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // adding subject to message
            m.setSubject(subject);

            // adding text to message
            m.setText(message + otp);

            // send

            // Step 3 : send the message using Transport class
            Transport.send(m);

            System.out.println("Sent success...................");

            f = true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return f;
    }
}