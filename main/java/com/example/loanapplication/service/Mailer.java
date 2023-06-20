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
				\t\t\t\t<html lang="en">
				\t\t\t\t\t<head>
				\t\t\t\t        <meta charset="UTF-8">
				\t\t\t\t        <style>
				\t\t\t\t            .Registration_Div{
				\t\t\t\t                align-content: space-evenly;
				\t\t\t\t                justify-content: center;
				\t\t\t\t                align-items: center;
				\t\t\t\t                display: grid;
				\t\t\t\t            }
				\t\t\t\t            button{
				\t\t\t\t                border-style: solid;
				\t\t\t\t                background-color: rgba(230, 24, 86, 1);
				\t\t\t\t                height: 30px;
				\t\t\t\t                width: 120px;
				\t\t\t\t                font-size: 15px;
				\t\t\t\t                color: white;
				\t\t\t\t                border-radius: 20px;
				\t\t\t\t            }
				\t\t\t\t            button:hover{
				\t\t\t\t\t            transform: scale(0.2);
				\t\t\t\t\t            background-image: linear-gradient(180deg, #1E3448 99.99%, rgba(30, 52, 72, 0) 100%);
				\t\t\t\t\t            color: white;
				\t\t\t\t            }
				\t\t\t\t            h1{
				\t\t\t\t                font-size: 35px;
				\t\t\t\t            }
				\t\t\t\t            .Registration_Div p{
				\t\t\t\t                font-size: 20px;
				\t\t\t\t            }
				\t\t\t\t            .thanks_div{
				\t\t\t\t                color: blue;
				\t\t\t\t                font-size: 20px;
				\t\t\t\t            }
				\t\t\t\t        </style>
				\t\t\t\t        <title>Registration Updates</title>
				\t\t\t\t    </head>
				\t\t\t\t    <body>
				\t\t\t\t        <div class='Registration_Div'>
				\t\t\t\t            <h1>Registration Successful</h1>
				\t\t\t\t            <p>Hi, if you receive this email then you have access to your account,<br>we just want to be sure
				\t\t\t\t                you have access to your email account,<br>please kindly click the button below to verify your account</p>
				\t\t\t\t            <div>
				\t\t\t\t                <button>Verify Account</button>
				\t\t\t\t            </div>
				\t\t\t\t            <div>
				\t\t\t\t                <p>
				\t\t\t\t                    Contact us at <span><a>+2347036174617</a></span> for further support
				\t\t\t\t                </p>
				\t\t\t\t            </div>
				\t\t\t\t            <div class='thanks_div'>
				\t\t\t\t                <p>Thanks for your cooperation</p>
				\t\t\t\t            </div>
				\t\t\t\t        </div>
				\t\t\t\t\t</body>
				\t\t\t\t</html>""";
	}
}
