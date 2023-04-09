package com.digitlibraryproject.service;


import com.digitlibraryproject.domain.Order;
import com.digitlibraryproject.domain.request.OrderRequest;
import com.digitlibraryproject.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.digitlibraryproject.util.PaymentMethodEnum.VISA;
import static com.digitlibraryproject.util.StatusEnum.FINISHED;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;

    private OrderService orderService;

    private Order order;

    private List<Order> orderList;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        orderService = new OrderService(orderRepository);
        order = new Order(1, FINISHED, Timestamp.valueOf(LocalDateTime.now()), VISA, 1, 1);
        orderList = new ArrayList<>();
        orderList.add(order);
    }

    @Test
    public void testFindAllOrders() {
        when(orderRepository.findAll()).thenReturn(orderList);
        Optional<List<Order>> optionalOrders = orderService.findAllOrders();

        assertTrue(optionalOrders.isPresent());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    public void testGetOrderById() {
        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
        Optional<Order> optionalOrder = orderService.getOrderById(order.getId());

        assertTrue(optionalOrder.isPresent());
        verify(orderRepository, times(1)).findById(order.getId());
    }

    @Test
    public void testCreateOrder() {
        OrderRequest newOrder = new OrderRequest(FINISHED, VISA, 1, 1);
        orderService.createOrder(newOrder);
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    public void testUpdateOrderById() {
        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
        order.setUserId(3);
        orderService.updateOrderById(order);

        verify(orderRepository, times(1)).saveAndFlush(order);
    }

    @Test
    public void testDeleteOrderById() {
        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
        assertTrue(orderService.deleteOrderById(order.getId()));
        verify(orderRepository, times(1)).deleteById(order.getId());
    }
}
