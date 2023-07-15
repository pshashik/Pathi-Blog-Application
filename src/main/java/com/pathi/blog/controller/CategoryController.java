package com.pathi.blog.controller;

import com.pathi.blog.payload.CategoryDto;
import com.pathi.blog.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@Tag(name = "CRUD REST APIs for Category Resource")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @SecurityRequirement(
            name = "Authentication"
    )
    @Operation(
            summary = "Create a Category using REST API",
            description = "Create a Category using REST API is used to save Category to the database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED"
    )
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
        return new ResponseEntity<>(categoryService.createCategory(categoryDto), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get all Category using REST API",
            description = "Get all Category using REST API is used to get all Category from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    @GetMapping
   // @PreAuthorize("hasAnyRole('ADMIN','USER')")
        public List<CategoryDto> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @Operation(
            summary = "Get Category By Id using REST API",
            description = "Get Category By Id using REST API is used to get single Category from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable(value = "id") long id){
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @SecurityRequirement(
            name = "Authentication"
    )
    @Operation(
            summary = "Update a Category using REST API",
            description = "Update a Category using REST API is used to update single Category from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable(value = "id") long id,
                                                     @Valid @RequestBody CategoryDto categoryDto){
        return ResponseEntity.ok(categoryService.updateCategory(id,categoryDto));
    }

    @Operation(
            summary = "Delete a Category using REST API",
            description = "Delete a Category using REST API is used to delete single Category from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    @SecurityRequirement(
            name="Authentication"
    )
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteCategory(@PathVariable(value = "id") long id){
        categoryService.deleteCategory(id);
        return "Category successfully deleted!";
    }
}
