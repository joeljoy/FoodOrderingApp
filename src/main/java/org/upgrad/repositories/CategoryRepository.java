package org.upgrad.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.upgrad.models.Category;
import org.upgrad.models.Restaurant;

import java.util.List;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Integer> {
    @Query(nativeQuery = true, value = "SELECT * FROM category")
    List<Category> findAll();

    @Query(nativeQuery = true, value = "SELECT * FROM category WHERE UPPER (category_name) LIKE CONCAT ('%',UPPER (?1), '%')")
    Category findByName(String categoryName);
}
