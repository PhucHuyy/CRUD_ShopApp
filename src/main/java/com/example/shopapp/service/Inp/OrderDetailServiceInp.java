package com.example.shopapp.service.Inp;


import com.example.shopapp.dtos.OrderDetailDTO;
import com.example.shopapp.exceptions.DataNotFoundException;
import com.example.shopapp.models.OrderDetail;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderDetailServiceInp {
    OrderDetail createOrderDetail(OrderDetailDTO orderDetailDTO) throws DataNotFoundException;
    OrderDetail getOrderDetail(int id);
    OrderDetail updateOrderDetail(int id, OrderDetailDTO orderDetailDTO);
    void deleteOrderDetail(int id);
    List<OrderDetail> getOrderDetails(int orderId);
}
