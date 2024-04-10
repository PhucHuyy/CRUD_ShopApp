package com.example.shopapp.responses;

import com.example.shopapp.models.OrderDetail;
import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailResponse {
    private int id;
    private int orderId;
    private int productId;
    private float totalMoney;
    private int numberOfProduct;
    private String color;

    public static OrderDetailResponse fromOrderDetail(OrderDetail orderDetail){
        return OrderDetailResponse.builder()
                .id(orderDetail.getId())
                .orderId(orderDetail.getOrder().getId())
                .productId(orderDetail.getProduct().getId())
                .totalMoney(orderDetail.getTotalMoney())
                .numberOfProduct(orderDetail.getNumberOfProduct())
                .color(orderDetail.getColor())
                .build();
    }
}
