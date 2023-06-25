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
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Service
@Slf4j
@AllArgsConstructor
public class BorrowPadiUserProfileService implements UserProfileService{
	
	BankInfoRepo bankInfoRepo;
	UserProfileRepo userProfileRepo;
	UserRepository userRepository;
	AddressService addressService;
	
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
		ModelMapper modelMapper = new ModelMapper();
		Address address = getAddress(userProfileRequest);
		AddressRequest addressRequest = new AddressRequest();
		try {
			modelMapper.map(address, addressRequest);
			System.out.println("hey youngin");
			Address savedAddress = addressService.saveAddress(addressRequest);
			System.out.println("plead for death");
			Optional<List<User>> listOfFoundUsers = userRepository.findByUsernameAndPassword(userProfileRequest.getUsername(), userProfileRequest.getPassword());
			listOfFoundUsers.ifPresent(doThis -> listOfFoundUsers.get().forEach(x -> userProfile.setUser(listOfFoundUsers.get().get(0))));
			System.out.println("You may not find death");
			userProfile.setAddress(savedAddress);
			modelMapper.addConverter(new NullValueChecker<>());
			System.out.println("plead for your life");
			modelMapper.map(userProfileRequest, userProfile);
			System.out.println("hello boy boi");
			UserProfile saveProfile = userProfileRepo.save(userProfile);
			System.out.println("you are still not gonna live to see the next moon");
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
	public Optional<UserProfileResponse> findUserProfileByUsernameAndPassword(String username, String password){
		
		return Optional.empty();
	}
	
	@Override
	public Optional<UserProfileResponse> findUserById(String userId) {
		return Optional.empty();
	}
	
	@Override
	public Optional<UserProfileResponse> findUserProfileByUsernameAndPin(String username, String pin) {
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
