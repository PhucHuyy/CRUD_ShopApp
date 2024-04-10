package com.example.shopapp.service.Inp;

import com.example.shopapp.dtos.OrderDTO;
import com.example.shopapp.exceptions.DataNotFoundException;
import com.example.shopapp.models.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderServiceInp {
    Order createOrder(OrderDTO orderDTO) throws Exception;
    Order getOrder(int orderId);
    Order updateOrder(int orderId, OrderDTO orderDTO) throws DataNotFoundException;
    void deleteOrder(int orderId);
    List<Order> findByUserId(int userId);
}
