package com.digitlibraryproject.domain;

import com.digitlibraryproject.util.PaymentMethodEnum;
import com.digitlibraryproject.util.StatusEnum;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_seq")
    @SequenceGenerator(name = "order_seq", sequenceName = "orders_id_seq", allocationSize = 1)
    private int id;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @Column(name = "date_created")
    private Timestamp dateCreated;

    @Column(name = "payment_method",nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentMethodEnum paymentMethod;

    @NotNull(message = "User ID cannot be null")
    @Column(name = "user_id")
    private int userId;

    @NotNull(message = "Book ID cannot be null")
    @Column(name = "book_id")
    private int bookId;

}
