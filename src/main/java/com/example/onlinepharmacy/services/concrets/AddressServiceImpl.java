package com.example.onlinepharmacy.services.concrets;

import com.example.onlinepharmacy.dtos.request.AddressRequest;
import com.example.onlinepharmacy.exceptions.NotFoundException;
import com.example.onlinepharmacy.models.Address;
import com.example.onlinepharmacy.models.Pharmacy;
import com.example.onlinepharmacy.models.User;
import com.example.onlinepharmacy.repositories.AddressRepository;
import com.example.onlinepharmacy.repositories.PharmacyRepository;
import com.example.onlinepharmacy.repositories.UserRepository;
import com.example.onlinepharmacy.services.abstracts.AddressService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;



    @Override
    public List<AddressRequest> getByUser(Principal principal) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new NotFoundException("User not found"));
        List<Address> byUserId = addressRepository.findByUserId(user.getId());
        return byUserId.stream().map(a->mapper.map(a,AddressRequest.class)).toList();
    }

    public void create(AddressRequest request, Principal principal) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new NotFoundException("User not found"));
        Address address = mapper.map(request, Address.class);
        address.setUser(user);
        addressRepository.save(address);
    }

    public void update(Long id, AddressRequest request){
        Address address = addressRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("This address does not exist"));
        Optional.ofNullable(request.getCity()).ifPresent(address::setCity);
        Optional.ofNullable(request.getPhone()).ifPresent(address::setPhone);
        Optional.ofNullable(request.getStreet()).ifPresent(address::setStreet);
        Optional.ofNullable(request.getCountry()).ifPresent(address::setCountry);
        Optional.ofNullable(request.getPostalCode()).ifPresent(address::setPostalCode);


        addressRepository.save(address);
    }

    public void delete(Long id){
        Address address = addressRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("This address does not exist"));
        addressRepository.delete(address);
    }
}
