package com.invoices.category;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private final Logger log = LogManager.getLogger(getClass());

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<String> getAllCategories() {
        List<String> list = new ArrayList<>();
        categoryRepository.findAll().forEach(e->list.add(e.getName()));
        return list;
    }

    public ResponseEntity<?> addNewCategory(String categoryName) {
        categoryName = categoryName.trim();

        if (categoryRepository.existsByName(categoryName)) {
            return ResponseEntity.badRequest().body("\"Taka kategoria już istnieje\nSpróbuj nadać inną nazwę\"");
        } else {
            categoryRepository.save(Category.builder().name(categoryName).build());
            log.info("Wprowadzono nową kategorię: " + categoryName);
            return ResponseEntity.ok("\"Wprowadzono kategorię: " + categoryName + "\"");
        }
    }
}
