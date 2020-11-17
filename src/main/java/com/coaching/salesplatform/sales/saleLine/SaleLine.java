package com.coaching.salesplatform.sales.saleLine;

import com.coaching.salesplatform.products.Product;
import com.coaching.salesplatform.sales.sale.Sale;
import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "sales_lines")
@Data
public
class SaleLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(optional = false)
    // @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JsonIgnoreProperties("salesLines")
    private Product product;

    @ManyToOne
    @JsonIgnoreProperties("saleLines")
    private Sale sale;

    @Column(name = "QUANTITY", nullable = false)
    private Integer quantity;
}
