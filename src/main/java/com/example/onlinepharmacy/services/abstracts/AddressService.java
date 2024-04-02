package com.example.onlinepharmacy.services.abstracts;

import com.example.onlinepharmacy.dtos.request.AddressRequest;

import java.security.Principal;
import java.util.List;

public interface AddressService {
    List<AddressRequest> getByUser(Principal principal);
    void create(AddressRequest request,Principal principal);
    void update(Long id, AddressRequest request);
    void delete(Long id);
}
