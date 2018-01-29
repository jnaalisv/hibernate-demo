package com.example.hibernate;

import com.example.domain.Product;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductTest extends AbstractHibernateTest {

    @Test
    public void shouldSaveAndFetchAProduct() {
        Product product = new Product("Ferrite Memory Cell");

        assertTableEmpty("product");

        doInTransaction(session -> {
            session.save(product);
        });

        assertExistsById("product", product.getId().toString());

        doInTransaction(session -> {
            Product product1 = session.find(Product.class, product.getId());
            assertThat(product1.getId()).isEqualTo(product.getId());
        });
    }

}
