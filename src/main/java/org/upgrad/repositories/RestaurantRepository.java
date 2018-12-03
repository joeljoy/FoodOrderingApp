package org.upgrad.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.upgrad.models.Restaurant;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface RestaurantRepository extends CrudRepository<Restaurant, Integer>{

    @Query(nativeQuery = true, value = "SELECT  * FROM restaurant WHERE id = ?1")
    Restaurant find(Integer id);

    @Query(nativeQuery = true, value = "SELECT * FROM restaurant")
    List<Restaurant> findAll();

    @Query(nativeQuery = true, value = "SELECT * FROM restaurant WHERE UPPER (restaurant_name) LIKE CONCAT ('%',UPPER (?1), '%')")
    List<Restaurant> findByName(String restaurantName);

    @Query(nativeQuery = true, value = "SELECT * FROM restaurant INNER JOIN restaurant_category ON restaurant.id = restaurant_category.restaurant_id INNER JOIN category ON restaurant_category.category_id = category.id WHERE UPPER (category_name) LIKE UPPER (?1)")
    List<Restaurant> findByCategory(String categoryName);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE restaurant SET user_rating = ?1, number_of_users_rated = number_of_users_rated + 1 WHERE id = ?2")
    void updateRestaurantById(Integer restaurantRating, Integer restaurantId);
}
