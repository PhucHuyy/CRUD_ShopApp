package com.example.shopapp.controller;

import com.example.shopapp.dtos.OrderDetailDTO;
import com.example.shopapp.exceptions.DataNotFoundException;
import com.example.shopapp.models.Order;
import com.example.shopapp.models.OrderDetail;
import com.example.shopapp.responses.OrderDetailResponse;
import com.example.shopapp.service.Inp.OrderDetailServiceInp;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/order_detail")
public class OrderDetailController {
    @Autowired
    OrderDetailServiceInp orderDetailServiceInp;

    @PostMapping()
    public ResponseEntity<?> createOrderDetail(@Valid @RequestBody OrderDetailDTO orderDetailDTO) throws DataNotFoundException {
        orderDetailServiceInp.createOrderDetail(orderDetailDTO);

        return new ResponseEntity<>(orderDetailDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetail(@PathVariable int id) {
        OrderDetail existingOrderDetail = orderDetailServiceInp.getOrderDetail(id);
        OrderDetailResponse orderDetailResponse = OrderDetailResponse.fromOrderDetail(existingOrderDetail);

        return new ResponseEntity<>(orderDetailResponse, HttpStatus.OK);
    }

    @GetMapping("/order/{order_id}")
    public ResponseEntity<?> getAllOrderDetail(@PathVariable int order_id) {
        //List<OrderDetail> orderDetailList = orderDetailServiceInp.getOrderDetails(order_id);

        return new ResponseEntity<>(orderDetailServiceInp.getOrderDetails(order_id), HttpStatus.OK);
    }


    // fix
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderDetail(@PathVariable int id,
                                               @Valid @RequestBody OrderDetailDTO orderDetailDTO) {
        orderDetailServiceInp.updateOrderDetail(id, orderDetailDTO);

        return new ResponseEntity<>("Update order detail with id = " + id + " successfully", HttpStatus.OK);
    }

    //fix
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderDetail(@PathVariable int id) {

        orderDetailServiceInp.deleteOrderDetail(id);
        return new ResponseEntity<>("Delete order with id = " + id + " successfully", HttpStatus.OK);
    }
}
