package com.example.loanapplication.service;

import com.example.loanapplication.data.dtos.requests.*;
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
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Service
@AllArgsConstructor
public class BorrowPadiCustomerService implements CustomerService{
	
	public static final String USER_PROFILE_ERROR_MESSAGE = """
			Seems like you haven't set up your profile
			please set up your profile first""";
	private final CustomerRepo customerRepo;
	private final UserRepository userRepository;
	MailService mailService;
	private UserProfileService userProfileService;
	private LoanApplicationService applicationService;
	
	public RegisterationResponse registerCustomer(RegistrationRequest registrationRequest) throws RegistrationFailedException, FieldCannotBeEmptyException, ObjectDoesNotExistException {
		
		validateCustomerEmailCredentials(registrationRequest);
		Customer customer;
		if (userDoesNotExistByUsernameAndPassword(registrationRequest.getUsername(), registrationRequest.getPassword())){
			try {
				customer = new Customer();
				User mappedUser = Mapper.map(registrationRequest);
				User savedUser = userRepository.save(mappedUser);
				Mapper.map(customer, savedUser);
				Customer savedCustomer = customerRepo.save(customer);
//      		    notifyCustomerThatRegistrationIsSuccessful(registrationRequest);
				log.info("Registration for Customer {} is Successful", savedCustomer);
				return Mapper.map(savedCustomer);
			} catch (Throwable exception) {
				log.info("Exception at service level {}",exception.getMessage());
				throw new FieldCannotBeEmptyException(exception.getMessage()+"\nThe cause is: "+exception.getCause()+" Error: All fields must be filled");
			}
		}
		else throw new RegistrationFailedException("Seems like you already have an account with us");
	}
	
	private boolean userDoesNotExistByUsernameAndPassword(String username, String password) throws ObjectDoesNotExistException {
		Optional<List<User>> foundUser = userRepository.findByUsernameAndPassword(username, password);
		return foundUser.isEmpty() || foundUser.get().isEmpty();
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
	
	@Override
	public LoginResponse login(LoginRequest loginRequest) throws LoginFailedException, ObjectDoesNotExistException {
		boolean userDoesNotExist = userDoesNotExistByUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword()) &&
				                           userDoesNotExistByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword());
		if (userDoesNotExist)
			throw new LoginFailedException("Login Failed:: Seems like you don't have an account with us, please register");
		
		else {
			if (loginRequest.getEmail() == null)
				checkForIncorrectPasswordOrUsername(loginRequest.getPassword(), loginRequest.getUsername());
			else if (loginRequest.getUsername() == null) {
				checkForIncorrectPasswordOrEmail(loginRequest.getPassword(), loginRequest.getEmail());
			}
		}
		return LoginResponse.builder()
				       .message("Login Successful")
				       .build();
	}
	
	private boolean userDoesNotExistByEmailAndPassword(String email, String password) {
		return !(userRepository.existsByEmailAndPassword(email, password));
	}
	
	private void checkForIncorrectPasswordOrEmail(String password, String email) throws LoginFailedException {
		AtomicBoolean isValidUser = new AtomicBoolean();
		isValidUser.set(false);
		Optional<List<User>> foundUser = userRepository.findAllByEmail(email);
		foundUser.ifPresent(x-> foundUser.get().forEach(y->{
			if (y.getPassword().equals(password))
				isValidUser.set(true);
		}));
		if (!isValidUser.get())
			throw new LoginFailedException("Invalid password");
	}
	
	private void checkForIncorrectPasswordOrUsername(String password, String username) throws LoginFailedException, ObjectDoesNotExistException {
		Optional<List<User>> foundUser = userRepository.findByUsername(username);
		if (foundUser.isPresent() && !foundUser.get().isEmpty())
			if (!foundUser.get().get(0).getPassword().equals(password)) throw new LoginFailedException("Incorrect Password");
	}
	
	public LoanApplicationResponse applyForLoan(LoanApplicationRequest loanApplicationRequest) throws LoanApplicationFailedException, ObjectDoesNotExistException {
		checkIfUserExists(loanApplicationRequest);
		checkIfUserProfileIsSetUp(loanApplicationRequest);
		checkIfUserIsLoggedIn(loanApplicationRequest.getUserName());
		validateUserPin(loanApplicationRequest);
		return applicationService.applyForLoan(loanApplicationRequest);
	}
	
	private void validateUserPin(LoanApplicationRequest loanApplicationRequest) throws ObjectDoesNotExistException {
		Optional<UserProfileResponse> foundProfile = userProfileService.findUserProfileByUsername(loanApplicationRequest.getUserName());
		foundProfile.ifPresent((x)->{
			if (!Objects.equals(x.getUserPin(), loanApplicationRequest.getUserPin())) {
				LoanApplicationFailedException failedException = new LoanApplicationFailedException("Loan Application Failed");
				failedException.setCause("Incorrect Pin");
				Throwable throwable = new Throwable(failedException.getCause());
				failedException.setCause(throwable.getCause());
				StackTraceElement[] stackTraceElements = new StackTraceElement[]{
						new StackTraceElement("BorrowPadiCustomerService", "applyForLoan", "BorrowPadiCustomerService.java", 135),
						new StackTraceElement("BorrowPadiCustomerService", "validateUserPin", "BorrowPadiCustomerService.java", 140),
				};
				failedException.setStackTrace(stackTraceElements);
				throw failedException;
		}
		});
	}
	
	private void checkIfUserIsLoggedIn(String userName) throws ObjectDoesNotExistException {
		Optional<FoundUserResponse> foundCustomer = findCustomerByUsername(userName);
		foundCustomer.ifPresent(x->{
			if (!x.isLoggedIn()) {
				LoanApplicationFailedException exception = new LoanApplicationFailedException("Loan Application Failed Exception");
				exception.setCause("You are not logged in");
				throw exception;
			}
		});
	}
	
	private void checkIfUserProfileIsSetUp(@NonNull LoanApplicationRequest loanApplicationRequest) throws ObjectDoesNotExistException{
		try{
			Optional<UserProfileResponse> userFoundByUsername = userProfileService.findUserProfileByUsername(loanApplicationRequest.getUserName());
			if (userFoundByUsername.isPresent()) {
				checkUserLoanEligibility(userFoundByUsername, loanApplicationRequest);
			}
		}catch(ObjectDoesNotExistException e){
			ObjectDoesNotExistException exception = new ObjectDoesNotExistException(USER_PROFILE_ERROR_MESSAGE);
			exception.setStackTrace(e.getStackTrace());
			exception.setExceptionCause("You don't have an existing profile");
			exception.setCause(e.getCause());
			throw exception;
		}
	}
	
	private void checkUserLoanEligibility(@NonNull Optional<UserProfileResponse> userFoundByUsername, LoanApplicationRequest loanApplicationRequest) {
		try{
			BigDecimal loanLimit = null;
			LoanPaymentRecord record = null;
			boolean hasPendingLoan = false;
			if (userFoundByUsername.isPresent()) {
				loanLimit = userFoundByUsername.get().getLoanLimit();
				record = userFoundByUsername.get().getRecord();
				hasPendingLoan = userFoundByUsername.get().isHasPendingLoan();
			}
			BigDecimal loanAmount = BigDecimal.valueOf(loanApplicationRequest.getLoanAmount());
			boolean isInvalidLoanLimit = loanAmount.compareTo(loanLimit) > 0;
			boolean isBadRecord = record == LoanPaymentRecord.BAD;
			if (isInvalidLoanLimit || isBadRecord || hasPendingLoan)
				throw new LoanApplicationFailedException("Loan Application Request Failed::");
		}
		catch(Throwable exception){
			LoanApplicationFailedException failedException = new LoanApplicationFailedException(exception.getMessage());
			failedException.setCause(exception.getCause());
			failedException.setStackTrace(exception.getStackTrace());
			throw failedException;
		}
	}
	
	private void checkIfUserExists(@NonNull LoanApplicationRequest loanApplicationRequest) throws LoanApplicationFailedException, ObjectDoesNotExistException {
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
		
		return applicationService.viewLoanStatus(loanStatusViewRequest);
	}
	
	@Override
	public Optional<List<LoanStatusViewResponse>> viewLoanHistory(String username, String password) {
		return applicationService.getAllLoans(username, password);
	}
	
	@Override
	public Optional<LoanStatusViewResponse> viewLoanLimit(String username, String password) {
		return Optional.empty();
	}
	
	@Override
	public Optional<FoundUserResponse> findCustomerById(String customerId) throws ObjectDoesNotExistException {
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
	public Optional<FoundUserResponse> findCustomerByUsername(String username) throws ObjectDoesNotExistException {
		Optional<List<User>> foundUsers = userRepository.findByUsername(username);
		User user;
		AtomicBoolean isLoggedIn = new AtomicBoolean();
		if (foundUsers.isPresent() && !foundUsers.get().isEmpty()) {
			user = foundUsers.get().get(0);
			Optional<Customer> foundCustomer = customerRepo.findByUser(user);
			foundCustomer.ifPresent(x-> isLoggedIn.set(x.isLoggedIn()));
			return Optional.of(FoundUserResponse.builder()
					                   .message("User Found")
					                   .isLoggedIn(isLoggedIn.get())
					                   .userid(user.getUserId())
					                   .build());
		}
		throw new ObjectDoesNotExistException("Customer not found: Probably incorrect username");
	}
	
	@Override
	public Optional<FoundUserResponse> findCustomerByUsernameAndPassword(String username, String password) throws ObjectDoesNotExistException {
		Optional<List<User>> foundUsers = userRepository.findByUsernameAndPassword(username, password);
		User user;
		if (foundUsers.isPresent() && !foundUsers.get().isEmpty()) {
			user = foundUsers.get().get(0);
			return Optional.of(FoundUserResponse.builder()
					                   .message("User Found")
					                   .userid(user.getUserId())
					                   .build());
		}
		throw new ObjectDoesNotExistException("Customer not found: Probably incorrect username or password");
	}
	
	@Override
	public void deleteAll() {
		customerRepo.deleteAll();
		userRepository.deleteAll();
	}
	
	@Override
	public void deleteById(String id) {
		customerRepo.deleteById(id);
		userRepository.deleteById(id);
	}
	
	@Override
	@Transactional public void deleteByUsername(String username) {
		userRepository.deleteByUsername(username);
	}
}
