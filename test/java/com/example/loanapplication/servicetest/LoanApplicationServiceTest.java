package com.example.loanapplication.servicetest;

import com.example.loanapplication.data.dtos.requests.LoanApplicationRequest;
import com.example.loanapplication.data.dtos.requests.RegistrationRequest;
import com.example.loanapplication.data.dtos.requests.UserProfileRequest;
import com.example.loanapplication.data.dtos.responses.LoanApplicationResponse;
import com.example.loanapplication.data.models.*;
import com.example.loanapplication.data.repositories.*;
import com.example.loanapplication.exceptions.LoanApplicationFailedException;
import com.example.loanapplication.exceptions.ObjectDoesNotExistException;
import com.example.loanapplication.service.CustomerService;
import com.example.loanapplication.service.LoanApplicationService;
import com.example.loanapplication.service.UserProfileService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
@SpringBootTest
class LoanApplicationServiceTest {
	
	@Autowired
	LoanApplicationService loanApplicationService;
	@Autowired
	UserProfileService profileService;
	@Autowired
	CustomerService customerService;
	@Autowired BankInfoRepo bankInfoRepo;
	@Autowired AddressRepo addressRepo;
	@Autowired UserRepository userRepository;
	@Autowired CustomerRepo customerRepo;
	@Autowired UserProfileRepo profileRepo;
	
	@Test void userHasToHaveAGoodOrNeutralLoanRecordOrBeANewUserBeforeTheyCanApplyForLoan() throws ObjectDoesNotExistException {
		customerService.deleteByUsername("MichealKing005");
		BankInfo bankInfo = BankInfo.builder().name("Micheal Kingston").accountNumber("3470921903-4-4").bankName("Spider").bvn("2343583738").build();
		User user = User.builder().username("MichealKing005").phoneNumber("+1 234 667 350").firstName("Micheal").lastName("Kingston")
				            .email("mkton@gmail.com").password("mk005#$king").expiredLoginSession(false).build();
		Address address = Address.builder().streetName("Mcklee St").city("Ohio").postCode("54321").houseNumber("34J").state("Okhlahoma").build();
		UserProfile userProfile = UserProfile.builder()
				                          .user(user).address(address).userPin("21324").hasPendingLoan(false).loanLevel(1).loanLimit(BigDecimal.valueOf(4000))
				                          .loanRepaymentRecord(LoanPaymentRecord.BAD).username("MichealKing005").info(bankInfo).build();
		Customer customer = Customer.builder().lastTimeLoggedIn(LocalDateTime.now()).user(user).isLoggedIn(true).build();
		bankInfoRepo.save(bankInfo);
		userRepository.save(user);
		addressRepo.save(address);
		profileRepo.save(userProfile);
		customerRepo.save(customer);
		LoanApplicationRequest applicationRequest = LoanApplicationRequest.builder().password(user.getPassword()).loanTenure(30).loanAmount(4000)
				               .loanPurpose("for feeding").userPin(userProfile.getUserPin()).userName(user.getUsername()).repaymentPreference("card").build();
		assertThatThrownBy(()-> customerService.applyForLoan(applicationRequest)).isInstanceOf(LoanApplicationFailedException.class)
				.hasMessageContaining("You do not have a good loan record");
	}
	
	@SneakyThrows
	@Test void testThatAWarningIsIssuedIfCustomersLoanApplicationRecordIsNeutral(){
		customerService.deleteByUsername("Omoyale");
		RegistrationRequest customer = RegistrationRequest.builder().lastName("Omolanke").firstName("Omiyale").email("ObolankeOmiyale@gmail.com")
				                       .password("Obol#yale").username("Omoyale").phoneNumber("09156724351").build();
		customerService.registerCustomer(customer);
		
		UserProfileRequest profileRequest = UserProfileRequest.builder().userPin("1798").username("Omoyale").houseNumber("56K").city("Abule-egba")
				                       .bvn("3456987521").postCode("09876").streetName("Alagemo Onire Street").password("Obol#yale")
				                       .accountNumber("7897542315").accountName("Obolanke Omiyale").bankName("Palmpay").state("Ogun").build();
		profileService.saveUserProfile(profileRequest);
		LoanApplicationRequest request = LoanApplicationRequest.builder().loanAmount(5000).loanPurpose("for feeding").loanTenure(30).repaymentPreference("Cash")
				                       .password("Obol#yale").userPin("1798").userName("Omoyale").build();
		LoanApplicationResponse applicationResponse = customerService.applyForLoan(request);
		assertThat(applicationResponse.getWarning()).isNotNull();
		assertThat(applicationResponse.getWarning())
				.isEqualTo("Maintain A good record and pay the loan on time or else you will be black listed");
	}
	
	@SneakyThrows
	@Test void testThatLoanApplicationFailsIfLoanLimitIsExceeded(){
		assertThrowsExactly(LoanApplicationFailedException.class, ()-> customerService.applyForLoan(LoanApplicationRequest.builder()
				       .loanAmount(10000).loanPurpose("for feeding").loanTenure(30).repaymentPreference("card")
				       .password("mk005#$king").userPin("21324").userName("MichealKing005").build()), "You can't borrow more than your loan limit");
		
		assertThatThrownBy(()->customerService.applyForLoan(LoanApplicationRequest.builder()
				          .loanAmount(10000).loanPurpose("for feeding").loanTenure(30).repaymentPreference("card")
				          .password("mk005#$king").userPin("21324").userName("MichealKing005").build()))
				.isInstanceOf(LoanApplicationFailedException.class)
				.hasMessageContaining("You can't borrow more than your loan limit");
	}
	
	@Test void userHasToFillAllNecessaryInformationBeforeTheyAreEligibleToGetLoanAndIfNotLoanApplicationFailedExceptionIsThrown(){
		assertThrowsExactly(LoanApplicationFailedException.class, ()-> customerService.applyForLoan(LoanApplicationRequest.builder()
				  .password("ayanniyi@20").userName("blaqmhee").loanTenure(35).userPin("2663").build()), "Loan Application Failed");
	}
	
	@Test void testThatLoanApplicationFailsIfPinIsIncorrect(){
		assertThrowsExactly(LoanApplicationFailedException.class, ()-> customerService.applyForLoan(LoanApplicationRequest.builder()
				  .password("ayanniyi@20").loanTenure(30).loanAmount(5000).loanPurpose("for feeding")
				  .userPin("3456").userName("blaqmhee").repaymentPreference("CARD").build()), "Incorrect pin");
	}
	
	@SneakyThrows
	@Test void testThatLoanApplicationIsSuccessfulIfAllCheckArePassed(){
		LoanApplicationResponse loanResponse = customerService.applyForLoan(buildLoanApplicationRequest());
		assertEquals("Loan Application Successful", loanResponse.getMessage());
	}
	
	private LoanApplicationRequest buildLoanApplicationRequest() {
		return LoanApplicationRequest.builder().password("ayanniyi@20").loanTenure(30).loanAmount(5000)
				       .loanPurpose("for feeding").userPin("1967").userName("blaqmhee").repaymentPreference("CARD").build();
	}
	
	@SneakyThrows
	@Test void saveLoanApplicationForm_FindSavedLoanApplicationFormByIdTest(){
		customerService.deleteByUsername("Ghaniy009");
		RegistrationRequest customer = RegistrationRequest.builder().lastName("AbdulGhaniy").firstName("Yusuf").email("AbdulGhaniyYusuf@gmail.com")
				                               .password("Yus#luv").username("Ghaniy009").phoneNumber("09045678923").build();
		customerService.registerCustomer(customer);
		
		UserProfileRequest profileRequest = UserProfileRequest.builder().userPin("1999").username("Ghaniy009").houseNumber("32B").city("Ogba")
				                                    .bvn("3456987521").postCode("09876").streetName("Cole Street").password("Yus#luv")
				                                    .accountNumber("4532190858").accountName("AbdulGhaniy Yusuf").bankName("Kuda").state("Lagos").build();
		profileService.saveUserProfile(profileRequest);
		LoanApplicationRequest request = LoanApplicationRequest.builder().loanAmount(5000).loanPurpose("for education").loanTenure(30).repaymentPreference("card")
				                                 .password("Yus#luv").userPin("1999").userName("Ghaniy009").build();
		LoanApplicationResponse applicationResponse = customerService.applyForLoan(request);
		Optional<LoanApplicationResponse> foundLoan = loanApplicationService.findLoanById(applicationResponse.getApplicationFormId());
		assertThat(foundLoan).isPresent();
		foundLoan.ifPresent(loanApplicationResponse -> {
			assertThat(loanApplicationResponse.getApplicationFormId()).isNotNull();
			assertThat(loanApplicationResponse.getApplicationFormId()).isNotNegative();
			assertThat(loanApplicationResponse.getApplicationFormId()).isNotZero();
		});
	}
	
	@Test void findAllLoanApplicationsWithTheSameLoanStatusTest(){
	
	}
	
	@Test void findLoanApplicationsGreaterThanAParticularAmountTest(){
	
	}
	@Test void findLoanApplicationsLesserThanAParticularAmountTest(){
	
	}
	
	
}
