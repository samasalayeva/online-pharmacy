package com.example.onlinepharmacy.services.abstracts;

import com.example.onlinepharmacy.dtos.response.PaymentResponse;
import com.example.onlinepharmacy.models.Order;
import com.stripe.exception.StripeException;

public interface PaymentService {
    PaymentResponse createPaymentLink(Order order) throws StripeException;
}
