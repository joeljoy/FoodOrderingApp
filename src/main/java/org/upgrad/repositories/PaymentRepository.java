package org.upgrad.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.upgrad.models.Payment;

@Repository
public interface PaymentRepository extends CrudRepository<Payment, Integer> {

    @Query(nativeQuery = true, value = "SELECT * from payment WHERE id = (?1)")
    Payment getById(Integer paymentId);
}
