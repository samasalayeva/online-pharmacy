package com.example.onlinepharmacy.controller;

import com.example.onlinepharmacy.dtos.request.AddressRequest;
import com.example.onlinepharmacy.services.abstracts.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/addresses")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;

    @GetMapping
    ResponseEntity<?> getByUser(Principal principal){
        return ResponseEntity.ok(addressService.getByUser(principal));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody AddressRequest request, Principal principal){
        addressService.create(request,principal);
        return ResponseEntity.ok().body(request);
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<?> delete(@PathVariable Long id){
        addressService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping ("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,@RequestBody AddressRequest request){
        addressService.update(id,request);
        return ResponseEntity.ok().body(request);
    }
}
