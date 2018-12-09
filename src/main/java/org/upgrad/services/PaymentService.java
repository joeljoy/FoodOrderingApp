package org.upgrad.services;

import org.upgrad.models.Payment;

public interface PaymentService {
    Payment getById(Integer paymentId);
}
