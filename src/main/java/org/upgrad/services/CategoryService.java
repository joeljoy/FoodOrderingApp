package org.upgrad.services;


import org.upgrad.models.Category;

import java.util.List;

public interface CategoryService {

    Category getCategory(String categoryName);

    List<Category> getAllCategories();
}
