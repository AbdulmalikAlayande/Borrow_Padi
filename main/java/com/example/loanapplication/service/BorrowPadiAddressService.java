package com.example.loanapplication.service;

import com.example.loanapplication.data.dtos.requests.AddressRequest;
import com.example.loanapplication.data.dtos.responses.AddressResponse;
import com.example.loanapplication.data.dtos.updaterequests.AddressUpdateRequest;

import java.util.List;
import java.util.Optional;

public class BorrowPadiAddressService implements AddressService{
	
	@Override
	public AddressResponse saveAddress(AddressRequest addressRequest) {
		return null;
	}
	
	@Override
	public Optional<AddressResponse> findAddressByUserId(String userId) {
		return Optional.empty();
	}
	
	@Override
	public Optional<AddressResponse> findAddressById(String addressId) {
		return Optional.empty();
	}
	
	@Override
	public Optional<List<AddressResponse>> findAllAddressByPostCode(String postCode) {
		return Optional.empty();
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
}
