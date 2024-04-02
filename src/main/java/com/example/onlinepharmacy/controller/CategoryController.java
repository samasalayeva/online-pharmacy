package com.example.onlinepharmacy.controller;

import com.example.onlinepharmacy.dtos.response.CategoryResponse;
import com.example.onlinepharmacy.services.abstracts.CategoryService;
import com.example.onlinepharmacy.services.abstracts.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAll() {
        return ResponseEntity.ok(categoryService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.get(id));
    }
    @GetMapping("/pharmacy")
    public ResponseEntity<?> getCategoryByPharmacy(Long pharmacyId){
        return ResponseEntity.ok(categoryService.getCategoryByPharmacy(pharmacyId));
    }
    @PreAuthorize("hasRole('admin_client_role')")
    @PostMapping("/create")
    public ResponseEntity<CategoryResponse> save(@RequestParam String categoryName, Principal principal) {
        return ResponseEntity.ok(categoryService.save(categoryName, principal.getName()));
    }
    @PreAuthorize("hasRole('admin_client_role')")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> update(@PathVariable Long id, @RequestParam String categoryName) {
        return ResponseEntity.ok(categoryService.update(id, categoryName));
    }

    @PreAuthorize("hasRole('admin_client_role')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.ok("Category successfully removed");
    }
}
