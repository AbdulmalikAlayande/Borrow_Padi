package com.example.loanapplication.service;

import com.example.loanapplication.data.dtos.requests.AddressRequest;
import com.example.loanapplication.data.dtos.requests.UserProfileRequest;
import com.example.loanapplication.data.dtos.responses.UserProfileResponse;
import com.example.loanapplication.data.models.Address;
import com.example.loanapplication.data.models.User;
import com.example.loanapplication.data.models.UserProfile;
import com.example.loanapplication.data.repositories.BankInfoRepo;
import com.example.loanapplication.data.repositories.UserProfileRepo;
import com.example.loanapplication.data.repositories.UserRepository;
import com.example.loanapplication.exceptions.FieldCannotBeEmptyException;
import com.example.loanapplication.exceptions.ObjectDoesNotExistException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class BorrowPadiUserProfileService implements UserProfileService{
	
	BankInfoRepo bankInfoRepo;
	UserProfileRepo userProfileRepo;
	UserRepository userRepository;
	AddressService addressService;
	ModelMapper modelMapper;
	
	private static class NullValueChecker<DTO, Model> implements Converter<DTO, Model> {
		@Override
		public Model convert(MappingContext<DTO, Model> context) {
			if (context.getSource() == null)
				throw new FieldCannotBeEmptyException("Please enter a valid object");
			return context.getDestination();
		}
	}
	
	@Override
	public UserProfileResponse saveUserProfile(UserProfileRequest userProfileRequest) {
		UserProfile userProfile = new UserProfile();
		Address address = getAddress(userProfileRequest);
		modelMapper = new ModelMapper();
		AddressRequest addressRequest = new AddressRequest();
		try {
			modelMapper.map(address, addressRequest);
			Address savedAddress = addressService.saveAddress(addressRequest);
			Optional<List<User>> listOfFoundUsers = userRepository.findByUsernameAndPassword(userProfileRequest.getUsername(), userProfileRequest.getPassword());
			listOfFoundUsers.ifPresent(doThis -> listOfFoundUsers.get().forEach(x -> userProfile.setUser(listOfFoundUsers.get().get(0))));
			userProfile.setAddress(savedAddress);
			modelMapper.addConverter(new NullValueChecker<>());
			modelMapper.map(userProfileRequest, userProfile);
			UserProfile saveProfile = userProfileRepo.save(userProfile);
			UserProfileResponse userProfileResponse = new UserProfileResponse();
			userProfileResponse.setProfileSetUpState(true);
			userProfileResponse.setMessage("Profile Set Successfully");
			modelMapper.map(saveProfile, userProfileResponse);
			return userProfileResponse;
		}catch (Throwable exception){
			throw new FieldCannotBeEmptyException(exception.getMessage());
		}
	}
	
	private static Address getAddress(UserProfileRequest userProfileRequest) {
		return Address.builder()
				       .houseNumber(userProfileRequest.getHouseNumber())
				       .state(userProfileRequest.getState())
				       .postCode(userProfileRequest.getPostCode())
				       .city(userProfileRequest.getCity())
				       .streetName(userProfileRequest.getStreetName())
				       .build();
	}
	
	
	@Override
	public Optional<UserProfileResponse> findProfileByUser(User user) {
		return Optional.empty();
	}
	
	@Override
	public Optional<UserProfileResponse> findProfileById(String userId) throws ObjectDoesNotExistException {
		Optional<UserProfile> foundUser = userProfileRepo.findById(userId);
		Optional<UserProfileResponse> response1 = buildProfileResponse(foundUser);
		if (response1.isPresent()) return response1;
		throw new ObjectDoesNotExistException("Object does not exist\nCaused by incorrect profile id");
	}
	
	@Override
	public Optional<UserProfileResponse> findUserProfileByUsername(String username) throws ObjectDoesNotExistException {
		Optional<UserProfile> foundUser = userProfileRepo.findByUsername(username);
		Optional<UserProfileResponse> response = buildProfileResponse(foundUser);
		if (response.isPresent()) return response;
		throw new ObjectDoesNotExistException("Object does not exist\nCaused by incorrect username or unexisting profile, Please Set up your profile");
	}
	
	private Optional<UserProfileResponse> buildProfileResponse(Optional<UserProfile> foundUser) {
		UserProfileResponse response = new UserProfileResponse();
		if (foundUser.isPresent()) {
			response.setRecord(foundUser.get().getRecord());
			response.setUsername(foundUser.get().getUsername());
			response.setLoanLevel(foundUser.get().getLoanLevel());
			response.setLoanLimit(foundUser.get().getLoanLimit());
			response.setMessage("Profile found");
			response.setProfileSetUpState(true);
			response.setProfileId(foundUser.get().getProfileId());
			response.setHasPendingLoan(foundUser.get().isHasPendingLoan());
			return Optional.of(response);
		}
		return Optional.empty();
	}
	
	@Override
	public void deleteUserProfileByUserId(String userId) {
	
	}
	
	@Override
	public long count() {
		return userProfileRepo.count();
	}
	
	@Override
	public void deleteAll() {
		userProfileRepo.deleteAll();
	}
	
	@Override
	public void deleteUserByUsernameAndPin(String username, String pin) {
	
	}
}
