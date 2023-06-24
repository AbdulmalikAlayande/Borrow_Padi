package com.example.loanapplication.service;

import com.example.loanapplication.data.dtos.requests.AddressRequest;
import com.example.loanapplication.data.dtos.responses.AddressResponse;
import com.example.loanapplication.data.dtos.updaterequests.AddressUpdateRequest;
import com.example.loanapplication.exceptions.FieldCannotBeEmptyException;
import com.example.loanapplication.exceptions.ObjectDoesNotExistException;

import java.util.List;
import java.util.Optional;

public interface AddressService{
	
	AddressResponse saveAddress(AddressRequest addressRequest) throws FieldCannotBeEmptyException;
	
	Optional<AddressResponse> findAddressById(String addressId) throws ObjectDoesNotExistException;
	Optional<List<AddressResponse>> findAllAddressByPostCode(String postCode);
	Optional<List<AddressResponse>> findAllAddressByCityAndState(String city, String state);
	Optional<List<AddressResponse>> findAllAddressByStreetName(String streetName);
	void deleteAddressByAddressId(String addressId);
	AddressResponse updateAddress(AddressUpdateRequest addressUpdateRequest);
	
	void deleteAll();
	
	boolean existById(String addressId);
}
