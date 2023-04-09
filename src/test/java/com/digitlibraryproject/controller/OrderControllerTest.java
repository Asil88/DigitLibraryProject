package com.digitlibraryproject.controller;


import com.digitlibraryproject.domain.Order;
import com.digitlibraryproject.domain.request.OrderRequest;
import com.digitlibraryproject.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static com.digitlibraryproject.util.PaymentMethodEnum.VISA;
import static com.digitlibraryproject.util.StatusEnum.FINISHED;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    private Order order;

    private List<Order> orderList;

    @BeforeEach
    void setUp() {
        order = new Order(1, FINISHED, Timestamp.valueOf(LocalDateTime.now()), VISA, 1, 1);
        orderList = new ArrayList<>();
        orderList.add(order);
    }

    @Test
    @WithMockUser(username = "login", password = "password")
    void testFindAllOrders() throws Exception {
        when(orderService.findAllOrders()).thenReturn(Optional.of(orderList));
        mockMvc.perform(get("/order/findAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(order.getId())))
                .andExpect(jsonPath("$[0].status", equalTo(order.getStatus().toString())))
                .andExpect(jsonPath("$[0].paymentMethod", equalTo(order.getPaymentMethod().toString())))
                .andExpect(jsonPath("$[0].userId", is(order.getUserId())))
                .andExpect(jsonPath("$[0].bookId", is(order.getBookId())));
    }

    @Test
    @WithMockUser(username = "login", password = "password")
    void testGetOrderById() throws Exception {
        when(orderService.getOrderById(order.getId())).thenReturn(Optional.of(order));
        mockMvc.perform(get("/order/{id}", order.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(order.getId())))
                .andExpect(jsonPath("$.status", equalTo(order.getStatus().toString())))
                .andExpect(jsonPath("$.paymentMethod", equalTo(order.getPaymentMethod().toString())))
                .andExpect(jsonPath("$.userId", is(order.getUserId())))
                .andExpect(jsonPath("$.bookId", is(order.getBookId())));
    }

    @Test
    @WithMockUser(username = "login", password = "password")
    void testCreateOrder() throws Exception {
        OrderRequest newOrder = new OrderRequest(FINISHED, VISA, 1, 1);
        doNothing().when(orderService).createOrder(newOrder);

        mockMvc.perform(post("/order/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newOrder)))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "login", password = "password")
    void testUpdateOrderById() throws Exception {
        doNothing().when(orderService).updateOrderById(order);
        mockMvc.perform(put("/order/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(order)))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "login", password = "password")
    void testDeleteOrderById() throws Exception {
        int id = 1;
        when(orderService.deleteOrderById(id)).thenReturn(true);

        mockMvc.perform(delete("/order/{id}", id))
                .andExpect(status().isNoContent());
    }
}
