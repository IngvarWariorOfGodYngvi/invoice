package com.invoices.adapters;

import com.invoices.category.Category;
import com.invoices.category.CategoryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

interface CategorySQLRepository extends CategoryRepository, JpaRepository<Category, String> {
}
