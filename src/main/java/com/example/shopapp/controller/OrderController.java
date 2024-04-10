package com.example.shopapp.controller;

import com.example.shopapp.dtos.OrderDTO;
import com.example.shopapp.exceptions.DataNotFoundException;
import com.example.shopapp.models.Order;
import com.example.shopapp.service.Inp.OrderServiceInp;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {
    @Autowired
    OrderServiceInp orderServiceInp;

    @PostMapping()
    public ResponseEntity<?> createOrder(
            @Valid @RequestBody OrderDTO orderDTO,
            BindingResult result) throws Exception{
        if(result.hasErrors()){
            List<String> resultMessage = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(resultMessage);
        }
        Order newOrder = orderServiceInp.createOrder(orderDTO);

        return new ResponseEntity<>(newOrder, HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrder(@PathVariable int orderId){
        
        return new ResponseEntity<>(orderServiceInp.getOrder(orderId), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getAllOrdersByUserId(@PathVariable int id){

        return new ResponseEntity<>(orderServiceInp.findByUserId(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable int id){
        orderServiceInp.deleteOrder(id);
        return new ResponseEntity<>("Delete order with id = "+ id + "successfully", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(
            @PathVariable int id,
            @RequestBody OrderDTO orderDTO) throws DataNotFoundException {
        Order order = orderServiceInp.updateOrder(id, orderDTO);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }



}
