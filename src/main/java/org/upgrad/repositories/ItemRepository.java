package org.upgrad.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.upgrad.models.Item;

@Repository
public interface ItemRepository extends CrudRepository<Item, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM item WHERE id = (?1)")
    Item getById(Integer itemId);
}
