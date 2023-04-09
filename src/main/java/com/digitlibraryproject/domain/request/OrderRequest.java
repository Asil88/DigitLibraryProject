package com.digitlibraryproject.domain.request;

import com.digitlibraryproject.util.PaymentMethodEnum;
import com.digitlibraryproject.util.StatusEnum;
import lombok.Data;


@Data
public class OrderRequest {
    private StatusEnum status;
    private PaymentMethodEnum paymentMethod;
    private int userId;
    private int bookId;

    public OrderRequest() {
    }

    public OrderRequest(StatusEnum status, PaymentMethodEnum paymentMethod, int userId, int bookId) {
        this.status = status;
        this.paymentMethod = paymentMethod;
        this.userId = userId;
        this.bookId = bookId;
    }
}
