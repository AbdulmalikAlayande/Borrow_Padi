package com.example.loanapplication.service;

import com.example.loanapplication.data.dtos.requests.EmailRequest;
import com.example.loanapplication.data.dtos.requests.LoanApplicationRequest;
import com.example.loanapplication.data.dtos.requests.LoanStatusViewRequest;
import com.example.loanapplication.data.dtos.requests.RegistrationRequest;
import com.example.loanapplication.data.dtos.responses.LoanApplicationResponse;
import com.example.loanapplication.data.dtos.responses.LoanStatusViewResponse;
import com.example.loanapplication.data.dtos.responses.RegisterationResponse;
import com.example.loanapplication.data.dtos.updaterequests.UpdateRequest;
import com.example.loanapplication.data.dtos.updateresponse.UpdateResponse;
import com.example.loanapplication.data.models.Customer;
import com.example.loanapplication.data.models.User;
import com.example.loanapplication.data.repositories.CustomerRepo;
import com.example.loanapplication.data.repositories.UserRepository;
import com.example.loanapplication.exceptions.*;
import com.example.loanapplication.utils.Mapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class BorrowPadiCustomerService implements CustomerService{
	
	private final CustomerRepo customerRepo;
	private final UserRepository userRepository;
	MailService mailService;
	private UserProfileService userProfileService;
	
	public RegisterationResponse registerCustomer(RegistrationRequest registrationRequest) throws RegistrationFailedException, FieldCannotBeEmptyException, MessageFailedException {
		validateCustomerEmailCredentials(registrationRequest);
		Customer customer;
		try {
			User user = new User();
			ModelMapper modelMapper = new ModelMapper();
			customer = new Customer();
			modelMapper.map(registrationRequest, user);
			User savedUser = userRepository.save(user);
			modelMapper.map(savedUser, customer);
		} catch (Throwable exception) {
			throw new FieldCannotBeEmptyException(exception.getMessage() + " Error: All fields must be filled");
		}
		Customer savedCustomer = customerRepo.save(customer);
		log.info("Registration for Customer {} is Successful", savedCustomer);
//		notifyCustomerThatRegistrationIsSuccessful(registrationRequest);
		return Mapper.map(savedCustomer);
	}
	
	public void notifyCustomerThatRegistrationIsSuccessful(RegistrationRequest registrationRequest) throws MessageFailedException {
		String username = registrationRequest.getFirstName()+" "+registrationRequest.getLastName();
		EmailRequest emailRequest = buildEmailRequest(registrationRequest, username);
		mailService.emailCustomerToVerifyTheirCustomer(emailRequest);
	}
	
	public void validateCustomerEmailCredentials(RegistrationRequest registrationRequest) throws RegistrationFailedException {
		try{
			mailService.isValidEmail(registrationRequest.getEmail());
			mailService.isValidPassword(registrationRequest.getPassword());
		}catch (IllegalArgumentException exception){
			throw new RegistrationFailedException("Registration Failed::\nCaused by"+exception.getMessage());
		}
	}
	
	private static EmailRequest buildEmailRequest(RegistrationRequest registrationRequest, String username) {
		return EmailRequest.builder()
				       .userEmailAddress(registrationRequest.getEmail())
				       .userName(username)
				       .userPassword(registrationRequest.getPassword())
				       .build();
	}
	
	public LoanApplicationResponse applyForLoan(LoanApplicationRequest loanApplicationRequest) throws LoanApplicationFailedException, ObjectDoesNotExistException{
		checkIfUserExists(loanApplicationRequest);
		checkIfUserProfileIsSetUp(loanApplicationRequest);
		return null;
	}
	
	private void checkIfUserProfileIsSetUp(LoanApplicationRequest loanApplicationRequest) throws ObjectDoesNotExistException{
	
	}
	
	private void checkIfUserExists(LoanApplicationRequest loanApplicationRequest) throws ObjectDoesNotExistException{
		String username = loanApplicationRequest.getUserName();
		String userPassword = loanApplicationRequest.getPassword();
		Optional<List<User>> foundUser = userRepository.findByUsernameAndPassword(username, userPassword);
		if (foundUser.isEmpty()){
			log.info("User does not exist");
			throw new ObjectDoesNotExistException("""
					Seems Like you don't have an account with us,
					Please click the register button
					to create an account with us""");
		}
	}
	
	public void agreeToTermsAndConditionForLoanApplication(){
	
	}
	
	public UpdateResponse updateDetails(UpdateRequest updateRequest){
		return null;
	}
	
	public LoanStatusViewResponse viewLoanStatus(LoanStatusViewRequest loanStatusViewRequest) throws NoSuchLoanException {
		
		return null;
	}
	
	@Override
	public void deleteAll() {
		customerRepo.deleteAll();
	}
}
