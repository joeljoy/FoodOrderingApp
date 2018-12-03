package org.upgrad.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.upgrad.models.Category;
import org.upgrad.services.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping()
    public ResponseEntity<?> getAllCategories(){
        List<Category> categoryList = categoryService.getAllCategories();
        if (categoryList == null || categoryList.isEmpty()) {
            return ResponseEntity.ok("There are no categories!");
        } else {
            return ResponseEntity.ok(categoryList);
        }
    }

    @GetMapping("{categoryName}")
    public ResponseEntity<?> getRestaurantByName(@PathVariable("categoryName") String restaurantName) {
        Category category = categoryService.getCategory(restaurantName);
        if (category == null) {
            return new ResponseEntity<>("No Category by this name!", HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(category);
        }
    }
}
