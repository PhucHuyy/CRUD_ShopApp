package com.example.shopapp.dtos;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO {

    private int orderID;
    private int productID;
    private float price;
    private int numberOfProduct;
    private float totalMoney;
    private String color;
}
