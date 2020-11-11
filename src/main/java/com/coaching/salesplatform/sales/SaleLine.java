package com.coaching.salesplatform.sales;

import com.coaching.salesplatform.products.Product;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "sales_lines")
@Data
public
class SaleLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(name = "saleline_product",
            joinColumns = @JoinColumn(name = "saleline_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products;

    @ManyToOne
    private Sale sale;

    @Column(name = "QUANTITY")
    private Integer quantity;

}
