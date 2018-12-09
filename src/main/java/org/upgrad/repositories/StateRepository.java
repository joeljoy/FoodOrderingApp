package org.upgrad.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.upgrad.models.Restaurant;
import org.upgrad.models.State;

@Repository
public interface StateRepository extends CrudRepository<State, Integer> {

    //This selects state Name for the state_id.
    @Query(nativeQuery = true,value = "SELECT * FROM STATES WHERE id=?1")
    State isValidState(Integer id);

    // get all states
    @Query(nativeQuery = true,value="select * from states")
    Iterable<State> getAllState();

    //This selects state Name for the state_id.
    @Query(nativeQuery = true,value = "SELECT * FROM STATES WHERE id=?1")
    State getStatebyId(Integer id);

    @Query(nativeQuery = true, value = "SELECT * FROM states WHERE state_id = (?1)")
    State getById(Integer stateId);

}