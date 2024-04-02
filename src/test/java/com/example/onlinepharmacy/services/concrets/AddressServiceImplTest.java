package com.example.onlinepharmacy.services.concrets;

import com.example.onlinepharmacy.dtos.request.AddressRequest;
import com.example.onlinepharmacy.exceptions.NotFoundException;
import com.example.onlinepharmacy.models.Address;
import com.example.onlinepharmacy.models.User;
import com.example.onlinepharmacy.repositories.AddressRepository;
import com.example.onlinepharmacy.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AddressServiceImplTest {
    @Mock
    private AddressRepository addressRepository;

    @Mock
    private UserRepository userRepository;
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private AddressServiceImpl addressService;



    @Test
    public void create_AddressRequestAndPrincipal_AddressShouldBeCreated(){

        // Mock user
        User user = new User();
        user.setId(1L);
        user.setUsername("Ayla");

        // Mock principal
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("Ayla");

        // Mock request
        AddressRequest request = new AddressRequest();
        request.setCity("Baki");
        request.setStreet("Hesen Eliyev");
        request.setCountry("Azerbaycan");
        request.setPostalCode("1700");

        // Mock mapping
        Address address = new Address();
        when(modelMapper.map(request, Address.class)).thenReturn(address);

        // Mock repository methods
        when(userRepository.findByUsername("Ayla")).thenReturn(Optional.of(user));

        // Call the service method
        addressService.create(request, principal);

        // Verify interactions
        verify(userRepository, times(1)).findByUsername("Ayla");
        verify(addressRepository, times(1)).save(address);
    }

    @Test
    public void update_AddressIdAndRequest_AddressShouldBeUpdated(){
        // Mock request
        AddressRequest request = new AddressRequest();
        request.setCity("New City");
        request.setStreet("New Street");
        request.setCountry("New Country");
        request.setPostalCode("54321");

        // Mock address
        Address address = new Address();
        address.setId(1L);
        address.setCity("Old City");
        address.setStreet("Old Street");
        address.setCountry("Old Country");
        address.setPostalCode("12345");

        // Mock repository methods
        when(addressRepository.findById(1L)).thenReturn(Optional.of(address));

        // Call the service method
        addressService.update(1L, request);

        // Verify interactions
        verify(addressRepository, times(1)).findById(1L);
        verify(addressRepository, times(1)).save(address);
        assertEquals("New City", address.getCity());
        assertEquals("New Street", address.getStreet());
        assertEquals("New Country", address.getCountry());
        assertEquals("54321", address.getPostalCode());
    }

    @Test
    public void delete_AddressId_AddressShouldBeDeleted(){
        // Mock address
        Address address = new Address();
        address.setId(1L);
        address.setCity("City");
        address.setStreet("Street");
        address.setCountry("Country");
        address.setPostalCode("12345");

        // Mock repository methods
        when(addressRepository.findById(1L)).thenReturn(Optional.of(address));

        // Call the service method
        addressService.delete(1L);

        // Verify interactions
        verify(addressRepository, times(1)).findById(1L);
        verify(addressRepository, times(1)).delete(address);
    }

    @Test
    public void getByUser_ValidPrincipal_ReturnAddressRequestsByUser(){


        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");

        Address address1 = new Address();
        address1.setId(1L);
        address1.setUser(user);
        address1.setCity("City1");
        address1.setStreet("Street1");

        Address address2 = new Address();
        address2.setId(2L);
        address2.setUser(user);
        address2.setCity("City2");
        address2.setStreet("Street2");

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(addressRepository.findByUserId(user.getId())).thenReturn(Arrays.asList(address1, address2));

        AddressRequest addressRequest1 = new AddressRequest();
        addressRequest1.setId(1L);
        addressRequest1.setCity("City1");
        addressRequest1.setStreet("Street1");

        AddressRequest addressRequest2 = new AddressRequest();
        addressRequest2.setId(2L);
        addressRequest2.setCity("City2");
        addressRequest2.setStreet("Street2");

        when(modelMapper.map(address1, AddressRequest.class)).thenReturn(addressRequest1);
        when(modelMapper.map(address2, AddressRequest.class)).thenReturn(addressRequest2);

        Principal principal = user::getUsername;
        List<AddressRequest> result = addressService.getByUser(principal);

        assertEquals(2, result.size());
        assertEquals("City1", result.get(0).getCity());
        assertEquals("Street1", result.get(0).getStreet());
        assertEquals("City2", result.get(1).getCity());
        assertEquals("Street2", result.get(1).getStreet());

        verify(userRepository, times(1)).findByUsername(user.getUsername());
        verify(addressRepository, times(1)).findByUserId(user.getId());
        verify(modelMapper, times(1)).map(address1, AddressRequest.class);
        verify(modelMapper, times(1)).map(address2, AddressRequest.class);
    }

    @Test
    public void getByUser_WhenUserNotExists_ThrowNotFoundException() {

        User user = new User();
        user.setUsername("testUser");

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());

        Principal principal = user::getUsername;
        try {
            addressService.getByUser(principal);
        } catch (NotFoundException e) {
            assertEquals("User not found", e.getMessage());
        }

        verify(userRepository, times(1)).findByUsername(user.getUsername());
        verifyNoMoreInteractions(userRepository);
        verifyNoInteractions(addressRepository);
        verifyNoInteractions(modelMapper);
    }

}