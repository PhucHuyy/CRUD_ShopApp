package com.example.shopapp.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "products")
public class Product extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private float price;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Column(name = "description")
    private String desc;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product")
    private Set<ProductImage> productImageSet;

    @OneToMany(mappedBy = "product")
    private Set<OrderDetail> orderDetailSet;
}
