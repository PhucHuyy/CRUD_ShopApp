package com.example.shopapp.service;

import com.example.shopapp.dtos.OrderDetailDTO;
import com.example.shopapp.exceptions.DataNotFoundException;
import com.example.shopapp.models.Order;
import com.example.shopapp.models.OrderDetail;
import com.example.shopapp.models.Product;
import com.example.shopapp.repository.OrderDetailRepository;
import com.example.shopapp.repository.OrderRepository;
import com.example.shopapp.repository.ProductRepository;
import com.example.shopapp.service.Inp.OrderDetailServiceInp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailService implements OrderDetailServiceInp {
    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderRepository orderRepository;
    @Override
    public OrderDetail createOrderDetail(OrderDetailDTO orderDetailDTO) throws DataNotFoundException {
        Order existingOrder = orderRepository.findById(orderDetailDTO.getOrderID())
                .orElseThrow(() -> new DataNotFoundException("Cannot find order with id = "+ orderDetailDTO.getOrderID()));

        Product existingProduct = productRepository.findById(orderDetailDTO.getProductID())
                .orElseThrow(() -> new DataNotFoundException("Cannot find product with id = "+ orderDetailDTO.getProductID()));

        OrderDetail newOrderDetail = OrderDetail
                .builder()
                .order(existingOrder)
                .product(existingProduct)
                .price(orderDetailDTO.getPrice())
                .numberOfProduct(orderDetailDTO.getNumberOfProduct())
                .totalMoney(orderDetailDTO.getTotalMoney())
                .color(orderDetailDTO.getColor())
                .build();

        orderDetailRepository.save(newOrderDetail);
        return newOrderDetail;
    }

    @Override
    public OrderDetail getOrderDetail(int id) {
        OrderDetail orderDetail = orderDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cannot order detail with id = "+ id));

        return orderDetail;
    }

    @Override
    public OrderDetail updateOrderDetail(int id, OrderDetailDTO orderDetailDTO) {
        OrderDetail existingOrderDetail = orderDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cannot find order detail with id = "+ id));

        Order existingOrder = orderRepository.findById(orderDetailDTO.getOrderID())
                .orElseThrow(() -> new RuntimeException("Cannot find order with id = "+ orderDetailDTO.getOrderID()));

        Product existingProduct = productRepository.findById(orderDetailDTO.getProductID())
                .orElseThrow(() -> new RuntimeException("Cannot find product with id = "+ orderDetailDTO.getProductID()));

        OrderDetail updateOrderDetail = existingOrderDetail
                .builder()
                .order(existingOrder)
                .product(existingProduct)
                .price(orderDetailDTO.getPrice())
                .numberOfProduct(orderDetailDTO.getNumberOfProduct())
                .totalMoney(orderDetailDTO.getTotalMoney())
                .color(orderDetailDTO.getColor())
                .build();

        orderDetailRepository.save(existingOrderDetail);
        return existingOrderDetail;
    }

    @Override
    public void deleteOrderDetail(int id) {
        orderDetailRepository.deleteById(id);
    }

    @Override
    public List<OrderDetail> getOrderDetails(int orderId) {
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);

        return orderDetailList;
    }
}
