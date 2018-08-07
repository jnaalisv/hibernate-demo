package com.example.hibernate;

import com.example.domain.Product;
import org.hibernate.type.PostgresUUIDType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTest extends AbstractHibernateTest {

    private Product product;

    @BeforeEach
    void before() {
        doInTransaction(session ->
                session
                    .createNativeQuery("delete from product;")
                    .executeUpdate()
        );

        product = new Product("Ferrite Memory Cell");

        doInTransaction(session ->
            session.save(product)
        );
    }

    @Test
    void shouldSaveAndFetchAProduct() {
        assertRowsInTable("product", product.getId().getValue(), 1);

        doInTransaction(session -> {
            Product product1 = session.find(Product.class, product.getId());
            assertThat(product1.getId()).isEqualTo(product.getId());
        });
    }


    @Test
    void queryByUuidWorksWithHqlQuery() {
        doInTransaction(session -> {

            var maybeProduct = session
                    .createQuery("from Product where id = :id", Product.class)
                    .setParameter("id", product.getId().getValue(), PostgresUUIDType.INSTANCE)
                    .uniqueResultOptional();

            assertThat(maybeProduct.isPresent()).isTrue();
        });
    }

    @Test
    void queryByUuidWorksWithSqlQuery() {
        doInTransaction(session -> {

            var maybeProduct = session
                    .createNativeQuery("select * from product where id = :id", Product.class)
                    .setParameter("id", product.getId().getValue(), PostgresUUIDType.INSTANCE)
                    .uniqueResultOptional();

            assertThat(maybeProduct.isPresent()).isTrue();
        });
    }

}
