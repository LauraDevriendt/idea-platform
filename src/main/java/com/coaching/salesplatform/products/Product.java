package com.coaching.salesplatform.products;

import com.coaching.salesplatform.sales.saleLine.SaleLine;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "products")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "PRICE", nullable = false)
    private Integer price;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("product")
    private List<SaleLine> salesLines;
}
