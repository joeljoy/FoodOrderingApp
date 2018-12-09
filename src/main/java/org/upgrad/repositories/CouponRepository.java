package org.upgrad.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.upgrad.models.Coupon;

import java.util.List;

@Repository
public interface CouponRepository extends CrudRepository<Coupon, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM coupon WHERE UPPER (coupon_name) = UPPER (?1)")
    List<Coupon> findByName(String couponName);
}
