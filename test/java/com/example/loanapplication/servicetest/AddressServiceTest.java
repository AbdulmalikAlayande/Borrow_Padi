package com.example.loanapplication.servicetest;

import com.example.loanapplication.data.dtos.requests.AddressRequest;
import com.example.loanapplication.data.dtos.responses.AddressResponse;
import com.example.loanapplication.exceptions.FieldCannotBeEmptyException;
import com.example.loanapplication.service.AddressService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AddressServiceTest {
	
	@Autowired
	AddressService addressService;
	AddressResponse addressResponse;
	AddressRequest addressRequest;
	
	@BeforeEach void startAllTestWith() {
		addressService.deleteAll();
		addressRequest = AddressRequest.builder()
				                 .postCode("12345")
				                 .city("Sabo")
				                 .houseNumber("322")
				                 .state("Lagos")
				                 .streetName("Herbert Macaulay Way")
				                 .build();
		addressResponse = addressService.saveAddress(addressRequest);
	}
	
	@AfterEach void endAllTestWith() {
		addressService.deleteAll();
	}
	
	@Test void saveAddressToDatabaseSavedAddressExistsInDataBase(){
		assertNotNull(addressResponse);
		assertThat(addressResponse.getAddressId()).isNotEmpty();
		assertTrue(addressService.existById(addressResponse.getAddressId()));
	}
	
	@Test void saveAddress_WithEmptyFields_AddressExceptionIsThrown(){
		assertThatThrownBy(()->{
			addressService.saveAddress(new AddressRequest());
			}, "Address Fields Cannot be empty")
				.isInstanceOf(FieldCannotBeEmptyException.class)
				.hasMessageContaining("Fields Cannot be empty");
	}
	
	@Test void saveAddress_FindSavedAddressByIdTest(){
		Optional<AddressResponse> response = addressService.findAddressById(addressResponse.getAddressId());
		assertTrue(response.isPresent());
		System.out.println(response.get());
		assertNotNull(response.get().getAddressId());
	}
	
	@Test void saveAddress_FindSavedAddressByUserIdTest(){
		Optional<AddressResponse> response = addressService.findAddressByUserId(addressResponse.getAddressId());
		
	}
	
	@Test void saveAddress_FindSavedAddressByPostCode(){
		Optional<List<AddressResponse>> response = addressService.findAllAddressByPostCode(addressRequest.getPostCode());
		response.ifPresent(AddressResponse->{
			assertNotNull(response.get());
			assertNotNull(response.get());
		});
	}
}