package com.example.shopapp.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {
    @NotNull(message = "userID not blank")
    private Integer userID;

    @NotEmpty(message = "full name cannot be empty")
    private String fullName;
    private String email;
    private String phoneNumber;
    private String address;
    private String note;
    private float totalMoney;
    private String shippingMethod;
    private String shippingAddress;
    private String paymentMethod;

    private boolean active = true;
    private LocalDate shippingDate;

}
