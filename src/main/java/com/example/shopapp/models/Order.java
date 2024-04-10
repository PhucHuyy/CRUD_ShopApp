package com.example.shopapp.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "fullname", length = 100)
    private String fullName;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "note", length = 100)
    private String note;

    @Column(name = "address", length = 200)
    private String address;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Column(name = "total_money")
    private float totalMoney;

    @Column(name = "status")
    private String status;

    @Column(name = "shipping_method", length = 100)
    private String shippingMethod;

    @Column(name = "shipping_address", length = 200)
    private String shippingAddress;

    @Column(name = "tracking_number", length = 100)
    private String trackingNumber;

    @Column(name = "payment_method", length = 100)
    private String paymentMethod;

    @Column(name = "shipping_date")
    private LocalDate shippingDate;

    @Column(name = "active")
    private boolean active;

    @OneToMany(mappedBy = "order")
    private Set<OrderDetail> orderDetailSet;

}
