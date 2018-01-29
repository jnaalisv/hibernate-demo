package com.example.hibernate;

import com.example.domain.Product;
import org.hibernate.type.PostgresUUIDType;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductTest extends AbstractHibernateTest {

    private Product product;

    @Before
    public void before() {
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
    public void shouldSaveAndFetchAProduct() {
        assertExistsById("Product", product.getId());

        doInTransaction(session -> {
            Product product1 = session.find(Product.class, product.getId());
            assertThat(product1.getId()).isEqualTo(product.getId());
        });
    }


    @Test
    public void queryByUuidWorksWithHqlQuery() {
        doInTransaction(session -> {

            Optional<Product> maybeProduct = session
                    .createQuery("from Product where id = :id", Product.class)
                    .setParameter("id", product.getId().getValue(), PostgresUUIDType.INSTANCE)
                    .uniqueResultOptional();

            assertThat(maybeProduct.isPresent()).isTrue();
        });
    }

    @Test
    public void queryByUuidWorksWithSqlQuery() {
        doInTransaction(session -> {

            Optional<Product> maybeProduct = session
                    .createNativeQuery("select * from product where id = :id", Product.class)
                    .setParameter("id", product.getId().getValue(), PostgresUUIDType.INSTANCE)
                    .uniqueResultOptional();

            assertThat(maybeProduct.isPresent()).isTrue();
        });
    }

}
