package com.example.onlinepharmacy.services.concrets;

import com.example.onlinepharmacy.dtos.response.PaymentResponse;
import com.example.onlinepharmacy.models.Order;
import com.example.onlinepharmacy.services.abstracts.PaymentService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
   @Value("${stripe.api.key}")
    private String stripeSecretKey;
    @Override
    public PaymentResponse createPaymentLink(Order order) throws StripeException {
        Stripe.apiKey=stripeSecretKey;
        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams
                        .PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:3000/payment/success/" + order.getId())
                .setCancelUrl("http://localhost:3000/payment/fail")
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setQuantity(1L).setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("usd")
                                .setUnitAmount((order.getTotalPrice().longValue())*100)
                                .setProductData(SessionCreateParams
                                        .LineItem
                                        .PriceData
                                        .ProductData.builder().setName("online-pharmacy").build())
                                .build()
                        )
                        .build()
                )

                .build();
        Session session = Session.create(params);

        PaymentResponse response = new PaymentResponse();
        response.setPaymentUrl(session.getUrl());
        return response;
    }
}
