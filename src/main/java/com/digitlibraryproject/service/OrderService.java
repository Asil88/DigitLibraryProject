package com.digitlibraryproject.service;

import com.digitlibraryproject.domain.Order;
import com.digitlibraryproject.domain.request.OrderRequest;
import com.digitlibraryproject.repository.OrderRepository;
import com.digitlibraryproject.util.PaymentMethodEnum;
import com.digitlibraryproject.util.StatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Optional<List<Order>> findAllOrders() {
        return Optional.of((ArrayList<Order>) orderRepository.findAll());
    }

    public Optional<Order> getOrderById(int id) {
        return orderRepository.findById(id);
    }


    public void createOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setStatus(orderRequest.getStatus());
        order.setPaymentMethod(orderRequest.getPaymentMethod());
        order.setDateCreated(Timestamp.valueOf(LocalDateTime.now()));
        order.setUserId(orderRequest.getUserId());
        order.setBookId(orderRequest.getBookId());
        orderRepository.save(order);
    }

    public void updateOrderById(Order order) {
        orderRepository.saveAndFlush(order);
    }

    public boolean updateOrderStatus(int id, StatusEnum status) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setStatus(status);
            orderRepository.saveAndFlush(order);
            return true;
        } else {
            return false;
        }
    }

    public boolean updatePaymentMethod(int id, PaymentMethodEnum paymentMethod) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setPaymentMethod(paymentMethod);
            orderRepository.saveAndFlush(order);
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteOrderById(Integer id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        try {
            if (optionalOrder.isPresent()) {
                orderRepository.deleteById(id);
                return true;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }
}

