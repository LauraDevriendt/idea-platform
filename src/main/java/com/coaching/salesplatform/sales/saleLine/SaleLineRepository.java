package com.coaching.salesplatform.sales.saleLine;

import com.coaching.salesplatform.products.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SaleLineRepository extends JpaRepository<SaleLine, Long> {
}
