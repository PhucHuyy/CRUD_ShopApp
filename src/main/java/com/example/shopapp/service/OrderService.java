package com.example.shopapp.service;

import com.example.shopapp.dtos.OrderDTO;
import com.example.shopapp.exceptions.DataNotFoundException;
import com.example.shopapp.models.Order;
import com.example.shopapp.models.User;
import com.example.shopapp.repository.OrderRepository;
import com.example.shopapp.repository.UserRepository;
import com.example.shopapp.service.Inp.OrderServiceInp;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class OrderService implements OrderServiceInp {
    @Autowired
    UserRepository userRepository;

    @Autowired
    OrderRepository orderRepository;

    @Override
    public Order createOrder(OrderDTO orderDTO) throws Exception {
        User existingUser = userRepository.findById(orderDTO.getUserID())
                .orElseThrow(() -> new RuntimeException("Cannot find user id with id: "+ orderDTO.getUserID()));

        Order order = Order.builder()
                .fullName(orderDTO.getFullName())
                .user(existingUser)
                .email(orderDTO.getEmail())
                .phoneNumber(orderDTO.getPhoneNumber())
                .address(orderDTO.getAddress())
                .note(orderDTO.getNote())
                .totalMoney(orderDTO.getTotalMoney())
                .shippingMethod(orderDTO.getShippingMethod())
                .shippingAddress(orderDTO.getShippingAddress())
                .paymentMethod(orderDTO.getPaymentMethod())
                .build();


        LocalDate shippingDate = orderDTO.getShippingDate();
        if(shippingDate == null || shippingDate.isBefore(LocalDate.now())){
            throw new RuntimeException("Delivery date must be after the order date");
        }

        order.setShippingDate(shippingDate);

        orderRepository.save(order);

        return order;
    }

    @Override
    public Order getOrder(int orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Cannot find order with id = "+ orderId));

        return order;
    }

    @Override
    public Order updateOrder(int orderId, OrderDTO orderDTO) throws DataNotFoundException {
        User existringUser = userRepository.findById(orderDTO.getUserID())
                .orElseThrow(() -> new DataNotFoundException ("Cannot find user with id = "+ orderDTO.getUserID()));

        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new DataNotFoundException("Cannot find order with id = "+ orderId));
        existingOrder.setFullName(orderDTO.getFullName());
        existingOrder.setAddress(orderDTO.getAddress());
        existingOrder.setEmail(orderDTO.getEmail());
        existingOrder.setPhoneNumber(orderDTO.getPhoneNumber());
        existingOrder.setNote(orderDTO.getNote());
        existingOrder.setTotalMoney(orderDTO.getTotalMoney());
        existingOrder.setShippingMethod(orderDTO.getShippingMethod());
        existingOrder.setShippingDate(orderDTO.getShippingDate());
        existingOrder.setShippingDate(orderDTO.getShippingDate());
        existingOrder.setPaymentMethod(orderDTO.getPaymentMethod());

        orderRepository.save(existingOrder);

        return existingOrder;
    }

    @Override
    public void deleteOrder(int orderId) {
        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Cannot find order with id = "+ orderId));

        existingOrder.setActive(false);
        orderRepository.save(existingOrder);
    }

    @Override
    public List<Order> findByUserId(int userId) {

        List<Order> orderList = orderRepository.findByUserId(userId);
        return orderList;
    }
}
