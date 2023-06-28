package com.example.loanapplication.service;

import com.example.loanapplication.data.dtos.requests.EmailRequest;
import com.example.loanapplication.data.dtos.requests.LoanApplicationRequest;
import com.example.loanapplication.data.dtos.requests.LoanStatusViewRequest;
import com.example.loanapplication.data.dtos.requests.RegistrationRequest;
import com.example.loanapplication.data.dtos.responses.*;
import com.example.loanapplication.data.dtos.updaterequests.UpdateRequest;
import com.example.loanapplication.data.dtos.updateresponse.UpdateResponse;
import com.example.loanapplication.data.models.Customer;
import com.example.loanapplication.data.models.LoanPaymentRecord;
import com.example.loanapplication.data.models.User;
import com.example.loanapplication.data.repositories.CustomerRepo;
import com.example.loanapplication.data.repositories.UserRepository;
import com.example.loanapplication.exceptions.*;
import com.example.loanapplication.utils.Mapper;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
		if (userDoesNotExist(registrationRequest)){
			try {
				User user = new User();
				ModelMapper modelMapper = new ModelMapper();
				customer = new Customer();
				User mappedUser = Mapper.map(registrationRequest);
				User savedUser = userRepository.save(mappedUser);
				Mapper.map(customer, savedUser);
				System.out.println("The user is"+customer.getUser());
				Customer savedCustomer = customerRepo.save(customer);
//      		    notifyCustomerThatRegistrationIsSuccessful(registrationRequest);
				log.info("Registration for Customer {} is Successful", savedCustomer);
				return Mapper.map(savedCustomer);
			} catch (Throwable exception) {
				throw new FieldCannotBeEmptyException(exception.getMessage()+"\nThe cause is: "+exception.getCause()+" Error: All fields must be filled");
			}
		}
		throw new RegistrationFailedException("Seems like you already have an account with us");
	}
	
	private boolean userDoesNotExist(RegistrationRequest registrationRequest){
		userRepository.findByUsernameAndPassword(registrationRequest.getUsername(), registrationRequest.getPassword());
		return true;
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
			throw new RegistrationFailedException("Registration Failed::\nCaused by: "+exception.getCause()+"\n"+exception.getMessage());
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
		return new LoanApplicationResponse();
	}
	
	private void checkIfUserProfileIsSetUp(@NonNull LoanApplicationRequest loanApplicationRequest) throws ObjectDoesNotExistException{
		Optional<UserProfileResponse> userFoundByUsername = userProfileService.findUserProfileByUsername(loanApplicationRequest.getUserName());
		if(userFoundByUsername.isEmpty()){
			log.info("User profile does not exist");
			throw new ObjectDoesNotExistException("""
					Seems like you haven't set up your profile
					please set up your profile first""");
		}
		checkUserLoanEligibility(userFoundByUsername, loanApplicationRequest);
	}
	
	private void checkUserLoanEligibility(@NonNull Optional<UserProfileResponse> userFoundByUsername, LoanApplicationRequest loanApplicationRequest) {
		int loanLevel = 0;
		BigDecimal loanLimit = null;
		LoanPaymentRecord record = null;
		boolean haspendingLoan = false;
		if (userFoundByUsername.isPresent()) {
			loanLevel = userFoundByUsername.get().getLoanLevel();
			loanLimit = userFoundByUsername.get().getLoanLimit();
			record = userFoundByUsername.get().getRecord();
			haspendingLoan = userFoundByUsername.get().isHasPendingLoan();
		}
		boolean isInvalidLoanLimit = loanApplicationRequest.getLoanAmount().compareTo(loanLimit) > 0;
		boolean isBadRecord = record == LoanPaymentRecord.BAD;
		if (isInvalidLoanLimit || isBadRecord || haspendingLoan) throw new LoanApplicationFailedException("Loan Application Request Failed::");
	}
	
	private void checkIfUserExists(@NonNull LoanApplicationRequest loanApplicationRequest) throws LoanApplicationFailedException{
		String username = loanApplicationRequest.getUserName();
		String userPassword = loanApplicationRequest.getPassword();
		Optional<List<User>> foundUser = userRepository.findByUsernameAndPassword(username, userPassword);
		if (foundUser.isEmpty()){
			log.info("User does not exist");
			throw new LoanApplicationFailedException("""
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
	public Optional<FoundUserResponse> findCustomerById(String customerId) {
		Optional<Customer> foundCustomer = customerRepo.findById(customerId);
		FoundUserResponse response;
		if (foundCustomer.isPresent()){
			response = FoundUserResponse.builder()
					           .userid(foundCustomer.get().getCustomerId())
					           .message("User found")
					           .build();
			return Optional.of(response);
		}
		throw new ObjectDoesNotExistException("Object does not exist:: probably caused by incorrect id");
	}
	
	@Override
	public Optional<FoundUserResponse> findCustomerByUsername(String username) {
		
		return Optional.empty();
	}
	
	@Override
	public Optional<FoundUserResponse> findCustomerByUsernameAndPassword(String username, String password) {
		return Optional.empty();
	}
	
	@Override
	public void deleteAll() {
		userRepository.deleteAll();
		customerRepo.deleteAll();
	}
}
