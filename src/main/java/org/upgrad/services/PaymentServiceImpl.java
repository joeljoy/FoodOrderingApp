package org.upgrad.services;

import org.springframework.stereotype.Service;
import org.upgrad.models.Payment;
import org.upgrad.repositories.PaymentRepository;

import javax.transaction.Transactional;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Payment getById(Integer paymentId) {
        return paymentRepository.getById(paymentId);
    }
}
