package com.travelgo_payment_service.dto;

//package com.travelgo.payment.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.travelgo_payment_service.entity.PaymentStatus;

//mport com.travelgo.payment.entity.PaymentStatus;

public class PaymentResponse {

    private Long id;
    private Long bookingId;
    private Long userId;
    private BigDecimal amount;
    private PaymentStatus paymentStatus;
    private LocalDateTime createdAt;

    public PaymentResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
