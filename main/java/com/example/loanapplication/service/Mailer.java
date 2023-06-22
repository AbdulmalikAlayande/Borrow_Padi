package com.example.loanapplication.service;

import com.example.loanapplication.data.dtos.requests.EmailRequest;
import lombok.extern.slf4j.Slf4j;

import javax.mail.*;
import javax.mail.internet.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.Properties;

@Slf4j
public class Mailer {
	
	public Properties emailProperties(){
		Properties properties = new Properties();
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		return properties;
	}
	public Session hostSession(){
		return Session.getInstance(emailProperties(), new PasswordAuthenticator());
	}
	private Message message(EmailRequest request) throws MessagingException {
		Message message = new MimeMessage(hostSession());
		String username = request.getUserName();
		message.setContent(multipartOfMessage(username));
		message.setFrom(address("alaabdulmalik03@gmail.com"));
		message.setRecipient(Message.RecipientType.TO, address(request.getUserEmailAddress()));
		message.setSentDate(new Date(LocalDate.now().getYear()));
		message.setSubject("Registration Updates");
		return message;
	}
	private Multipart multipartOfMessage(String username) throws MessagingException{
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(bodyPartOfMessage(username));
		return multipart;
	}
	
	private BodyPart bodyPartOfMessage(String username) throws MessagingException{
		BodyPart bodyPart = new MimeBodyPart();
		System.out.println("Hello there::");
		bodyPart.setContent(htmlContent(username), "text/html");
		return bodyPart;
	}
	
	private Address address(String userEmailAddress) throws AddressException {
		return new InternetAddress(userEmailAddress);
	}
	
	public void send(EmailRequest request) throws MessagingException{
		Transport.send(message(request));
		log.info("Message Sent {}", "Successfully");
		System.out.println(log);
	}
	private String htmlContent(String username){
		return """
				<!Doctype html>
				<html lang="en">
				<head>
					<meta charset="UTF-8">
				    <style>
				        .Registration_Div{
					        align-content: space-evenly;
					        justify-content: center;
					        align-items: center;
					        display: grid;
				        }
						button{
				            border-style: solid;
				            background-color: rgba(230, 24, 86, 1);
				            height: 30px;
				            width: 120px;
				            font-size: 15px;
				            color: white;
				            border-radius: 20px;
				        }
				        button:hover{
				            transform: scale(0.2);
				            background-image: linear-gradient(180deg, #1E3448 99.99%, rgba(30, 52, 72, 0) 100%);
				            color: white;
				        }
				        h1{
				            font-size: 35px;
				        }
				        .Registration_Div p{
				            font-size: 20px;
				        }
				        .thanks_div{
				            color: blue;
				            font-size: 20px;
				        }
				    </style>
				    <title>Registration Updates</title>
				</head>
				<body>
					<div class='Registration_Div'>
					    <h1>Registration Successful</h1>
						<p>Hi, if you receive this email then you have access to your account,<br>we just want to be sure
						you have access to your email account,<br>please kindly click the button below to verify your account</p>
					<div>
					<button>Verify Account</button>
				    </div>
					<div>
				         <p>
				            Contact us at <span><a>+2347036174617</a></span> for further support
				         </p>
				         </div>
				         <div class='thanks_div'>
				         <p>Thanks for your cooperation</p>
				    </div>
				    </div>
				</body>
				</html>
				""";
	}
}
