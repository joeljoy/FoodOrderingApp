package org.upgrad.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.upgrad.models.State;

@Repository
public interface StateRepository extends CrudRepository<State, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM states WHERE state_id = (?1)")
    State getById(Integer stateId);
}
