package com.example.loanapplication.service;

import com.example.loanapplication.data.dtos.requests.AddressRequest;
import com.example.loanapplication.data.dtos.responses.AddressResponse;
import com.example.loanapplication.data.dtos.updaterequests.AddressUpdateRequest;
import com.example.loanapplication.data.models.Address;
import com.example.loanapplication.data.repositories.AddressRepo;
import com.example.loanapplication.exceptions.FieldCannotBeEmptyException;
import com.example.loanapplication.exceptions.ObjectDoesNotExistException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
 @Slf4j
@Service
public class BorrowPadiAddressService implements AddressService{
	
	@Autowired
	AddressRepo addressRepo;
	
	@Override
	public AddressResponse saveAddress(AddressRequest addressRequest) throws FieldCannotBeEmptyException{
		try{
			Address address = new Address();
			ModelMapper modelMapper = new ModelMapper();
			modelMapper.map(addressRequest, address);
			Address savedAddress = addressRepo.save(address);
			AddressResponse addressResponse = new AddressResponse();
			modelMapper.map(savedAddress, addressResponse);
			return addressResponse;
		}catch (Throwable exception){
			throw new FieldCannotBeEmptyException("Address Fields Cannot be empty");
		}
	}
	@Override
	public Optional<AddressResponse> findAddressById(String addressId) throws ObjectDoesNotExistException{
		ModelMapper modelMapper = new ModelMapper();
		AddressResponse addressResponse = new AddressResponse() ;
		Optional<Address> foundAddress = addressRepo.findById(addressId);
		if (foundAddress.isPresent()) {
			modelMapper.map(foundAddress.get(), addressResponse);
			return Optional.of(addressResponse);
		}
		throw new ObjectDoesNotExistException("Address not found");
	}
	
	@Override
	public Optional<List<AddressResponse>> findAllAddressByPostCode(String postCode) {
		ModelMapper modelMapper = new ModelMapper();
		AddressResponse addressResponse = new AddressResponse() ;
		Optional<List<Address>> foundAddress = addressRepo.findAllByPostCode(postCode);
		Optional<List<AddressResponse>> responses = Optional.of(new ArrayList<>());
		foundAddress.ifPresent(doThis -> {
			for (int i = 0; i < foundAddress.get().size(); i++) {
				modelMapper.map(foundAddress.get().get(i), addressResponse);
				responses.get().add(addressResponse);
			}
		});
		
		return responses;
	}
	
	@Override
	public Optional<List<AddressResponse>> findAllAddressByCityAndState(String city, String state) {
		return Optional.empty();
	}
	
	@Override
	public Optional<List<AddressResponse>> findAllAddressByStreetName(String streetName) {
		return Optional.empty();
	}
	
	@Override
	public void deleteAddressByAddressId(String addressId) {        
	
	}
	
	@Override
	public AddressResponse updateAddress(AddressUpdateRequest addressUpdateRequest) {
		return null;
	}
	
	@Override
	public void deleteAll() {
		addressRepo.deleteAll();
	}
	
	@Override
	public boolean existById(String addressId) {
		return addressRepo.existsById(addressId);
	}
}
