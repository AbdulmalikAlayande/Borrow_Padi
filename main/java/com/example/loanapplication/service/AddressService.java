package com.example.loanapplication.service;

import com.example.loanapplication.data.dtos.requests.AddressRequest;
import com.example.loanapplication.data.dtos.responses.AddressResponse;
import com.example.loanapplication.data.dtos.updaterequests.AddressUpdateRequest;

import java.util.List;
import java.util.Optional;

public interface AddressService{
	
	AddressResponse saveAddress(AddressRequest addressRequest);
	Optional<AddressResponse> findAddressByUserId(String userId);
	Optional<AddressResponse> findAddressById(String addressId);
	Optional<List<AddressResponse>> findAllAddressByPostCode(String postCode);
	Optional<List<AddressResponse>> findAllAddressByCityAndState(String city, String state);
	Optional<List<AddressResponse>> findAllAddressByStreetName(String streetName);
	void deleteAddressByAddressId(String addressId);
	AddressResponse updateAddress(AddressUpdateRequest addressUpdateRequest);
}
