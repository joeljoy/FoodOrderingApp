package org.upgrad.services;

import org.upgrad.models.Payment;

import java.util.List;

public interface PaymentService {
    Payment getById(Integer paymentId);
    List<Payment> getAll();
}
