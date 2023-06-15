package com.example.demo.entity;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
@Data
public class Product  implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Float price;

    @Column(name = "amount")
    private Integer amount;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "tags")
    private String tags;

    @Column(name = "description")
    private String description;

    @Column(name = "information")
    private String information;

    @Column(name = "assessment")
    private String assessment;

    @Column(name = "sku", unique = true)
    private String sku;

    @OneToMany(mappedBy = "product")
    private List<Image> image= new ArrayList<>();
}
