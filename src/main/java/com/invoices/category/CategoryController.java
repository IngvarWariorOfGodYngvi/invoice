package com.invoices.category;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@CrossOrigin
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @GetMapping("/getAll")
    public List<String> getAllCategories(){
        return categoryService.getAllCategories();
    }
    @PostMapping("/addCategory")
    public ResponseEntity<?> addNewCategory(@RequestParam String categoryName){
        return categoryService.addNewCategory(categoryName);
    }
}
