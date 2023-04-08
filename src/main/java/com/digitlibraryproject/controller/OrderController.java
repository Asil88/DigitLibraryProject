package com.digitlibraryproject.controller;

import com.digitlibraryproject.domain.Order;
import com.digitlibraryproject.domain.request.OrderRequest;
import com.digitlibraryproject.exception.ResourceNotFoundException;
import com.digitlibraryproject.service.OrderService;
import com.digitlibraryproject.util.PaymentMethodEnum;
import com.digitlibraryproject.util.StatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(value = "/order", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Order>> findAllOrders() {
        List<Order> allOrders = orderService.findAllOrders().orElseThrow(() -> new ResourceNotFoundException("ORDERS_NOT_FOUND"));
        return new ResponseEntity<>(allOrders, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable int id) {
        Order oderById = orderService.getOrderById(id).orElseThrow(() -> new ResourceNotFoundException("ORDER_NOT_FOUND"));
        return new ResponseEntity<>(oderById, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> createOrder(@RequestBody @Valid OrderRequest orderRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (ObjectError o : bindingResult.getAllErrors()) {
                log.warn(o.getDefaultMessage());
            }
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        orderService.createOrder(orderRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update")
    public ResponseEntity<HttpStatus> updateOrderById(@RequestBody @Valid Order order, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (ObjectError o : bindingResult.getAllErrors()) {
                log.warn(o.getDefaultMessage());
            }
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        orderService.updateOrderById(order);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/status/{id}/{status}")
    public ResponseEntity<HttpStatus> updateOrderStatus(@PathVariable int id,@PathVariable StatusEnum status) {
        boolean updateOrderStatus = orderService.updateOrderStatus(id, status);
        if (updateOrderStatus) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/paymentMethod/{id}/{paymentMethod}")
    public ResponseEntity<HttpStatus> updateOrderPaymentMethod(@PathVariable int id,@PathVariable PaymentMethodEnum paymentMethod) {
        boolean updateOrderPaymentMethod = orderService.updatePaymentMethod(id, paymentMethod);
        if (updateOrderPaymentMethod) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteOrderById(@PathVariable Integer id) {
        boolean isDeleted = orderService.deleteOrderById(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
