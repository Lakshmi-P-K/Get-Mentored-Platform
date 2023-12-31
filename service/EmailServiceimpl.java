package com.nineleaps.authentication.jwt.service;

import java.util.Properties;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Authenticator;
import javax.mail.Message;

import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

@Service
public class EmailServiceimpl {
 
	public boolean sendEmail(String subject, String message , String to) {
		    if (to == null) {
		        // Handle the null value here, such as throwing an exception or returning false
		        return false;
		    }
	    // Variable for gmail
	    String host = "smtp.gmail.com";
	    String from = "hema";
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
	    Session session = Session.getInstance(properties, new Authenticator() {
	        @Override
	        protected PasswordAuthentication getPasswordAuthentication() {
	            return new PasswordAuthentication("hema.b@nineleaps.com", "nnvipaqlddkjoguw");
	        }
	    });
	    session.setDebug(true);
	    // Step 2 : compose the message [text,multi media]
	    MimeMessage m = new MimeMessage(session);
	    try {
	        // from email
	        m.setFrom();
	        // adding recipient to message
	        m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
	        // adding subject to message
	        m.setSubject(subject);
	        // adding text to message
	        m.setText(message);
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

		
	


