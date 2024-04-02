package com.example.onlinepharmacy.services.concrets;

import com.example.onlinepharmacy.dtos.request.PharmacyRequest;
import com.example.onlinepharmacy.exceptions.NotFoundException;
import com.example.onlinepharmacy.models.Address;
import com.example.onlinepharmacy.dtos.request.ContactInformation;
import com.example.onlinepharmacy.models.Pharmacy;
import com.example.onlinepharmacy.models.User;
import com.example.onlinepharmacy.repositories.AddressRepository;
import com.example.onlinepharmacy.repositories.PharmacyRepository;
import com.example.onlinepharmacy.repositories.UserRepository;
import com.example.onlinepharmacy.services.abstracts.PharmacyService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PharmacyServiceImpl implements PharmacyService {
    private final PharmacyRepository pharmacyRepository;
    private final AddressRepository addressRepository;
    private final ModelMapper mapper;

    private final UserRepository userRepository;

    @Override
    public Pharmacy createPharmacy(PharmacyRequest request, Principal principal) {
        Address address = mapper.map(request.getAddress(), Address.class);
        addressRepository.save(address);

        Pharmacy pharmacy = mapper.map(request, Pharmacy.class);
        User user = userRepository.findByUsername(principal.getName()).orElseThrow(()
                -> new NotFoundException("User not found with " + principal.getName() + " username"));
        pharmacy.setOwner(user);
        pharmacy.setRegistrationDate(LocalDate.now());
        pharmacy.setAddress(address);
        return pharmacyRepository.save(pharmacy);
    }

    @Override
    public Pharmacy updatePharmacy(Long pharmacyId, PharmacyRequest request) {

        Address address = mapper.map(request.getAddress(), Address.class);



        Pharmacy pharmacy = pharmacyRepository.findById(pharmacyId)
                .orElseThrow(() -> new NotFoundException("This pharmacy does not exist"));
        Optional.ofNullable(address)
                .map(addressRepository::save)
                .ifPresent(pharmacy::setAddress);
        Optional.ofNullable(request.getContactInformation())
                .ifPresent(pharmacy::setContactInformation);

        Optional.ofNullable(request.getName())
                .ifPresent(pharmacy::setName);

        Optional.ofNullable(request.getOpeningHours())
                .ifPresent(pharmacy::setOpeningHours);

        Optional.ofNullable(request.getDescription())
                .ifPresent(pharmacy::setDescription);
        return pharmacyRepository.save(pharmacy);
    }

    @Override
    public void deletePharmacy(Long pharmacyId) {
        Pharmacy pharmacy = findById(pharmacyId);
        pharmacyRepository.delete(pharmacy);
    }

    @Override
    public List<Pharmacy> getAllPharmacy() {
        return pharmacyRepository.findAll();
    }

    @Override
    public List<Pharmacy> searchPharmacy(String keyword) {
        return pharmacyRepository.findBySearchQuery(keyword);
    }

    @Override
    public Pharmacy findById(Long id) {
        return pharmacyRepository.findById(id)
                .orElseThrow(()->new NotFoundException("This pharmacy does not exist"));
    }

    @Override
    public Pharmacy findByUsername(String username) {
        return pharmacyRepository.findByOwnerUsername(username)
                .orElseThrow(()->
                        new NotFoundException("Pharmacy not found with owner username " + username));
    }

    @Override
    public Pharmacy updatePharmacyStatus(Long id) {
        Pharmacy pharmacy = findById(id);
        pharmacy.setOpen(!pharmacy.isOpen());
        return pharmacyRepository.save(pharmacy);
    }
}
