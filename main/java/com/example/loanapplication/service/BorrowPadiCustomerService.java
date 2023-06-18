package com.example.loanapplication.service;

import com.example.loanapplication.data.dtos.requests.LoanApplicationRequest;
import com.example.loanapplication.data.dtos.requests.LoanStatusViewRequest;
import com.example.loanapplication.data.dtos.requests.RegistrationRequest;
import com.example.loanapplication.data.dtos.responses.LoanApplicationResponse;
import com.example.loanapplication.data.dtos.responses.LoanStatusViewResponse;
import com.example.loanapplication.data.dtos.responses.RegisterationResponse;
import com.example.loanapplication.data.dtos.updaterequests.UpdateRequest;
import com.example.loanapplication.data.dtos.updateresponse.UpdateResponse;
import com.example.loanapplication.data.models.Customer;
import com.example.loanapplication.data.repositories.CustomerRepo;
import com.example.loanapplication.data.repositories.UserRepository;
import com.example.loanapplication.exceptions.FieldCannotBeEmptyException;
import com.example.loanapplication.exceptions.LoanApplicationFailedException;
import com.example.loanapplication.exceptions.NoSuchLoanException;
import com.example.loanapplication.exceptions.RegistrationFailedException;
import com.example.loanapplication.utils.Mapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import com.example.loanapplication.data.models.User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class BorrowPadiCustomerService implements CustomerService{
	
	private final CustomerRepo customerRepo;
	private final UserRepository userRepository;
	
	public RegisterationResponse registerCustomer(RegistrationRequest registerationRequest) throws RegistrationFailedException, FieldCannotBeEmptyException {
		try {
			User user = new User();
			ModelMapper modelMapper = new ModelMapper();
			Customer customer = new Customer();
			modelMapper.map(registerationRequest, user);
			User savedUser = userRepository.save(user);
			modelMapper.map(savedUser, customer);
			Customer savedCustomer = customerRepo.save(customer);
			log.info("Registtration for Customer {}", savedCustomer);
			return Mapper.map(savedCustomer);
		}catch (Throwable exception){
			throw new FieldCannotBeEmptyException("Error: All fields must be field");
		}
	}
	public LoanApplicationResponse applyForLoan(LoanApplicationRequest loanApplicationRequest) throws LoanApplicationFailedException{
		return null;
	}
	
	public void agreeToTermsAndConditionForLoanApplication(){
	
	}
	
	public UpdateResponse updateDetails(UpdateRequest updateRequest){
		return null;
	}
	public LoanStatusViewResponse viewLoanStatus(LoanStatusViewRequest loanStatusViewRequest) throws NoSuchLoanException {
		return null;
	}
}
