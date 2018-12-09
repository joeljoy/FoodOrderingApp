package org.upgrad.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.upgrad.models.Payment;

import java.util.List;

@Repository
public interface PaymentRepository extends CrudRepository<Payment, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM payment WHERE id = (?1)")
    Payment getById(Integer paymentId);

    @Query(nativeQuery = true, value = "SELECT * FROM payment")
    List<Payment> getAll();
}
