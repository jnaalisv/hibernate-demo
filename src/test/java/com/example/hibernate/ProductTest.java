package com.example.hibernate;

import com.example.domain.Product;
import com.example.domain.ProductId;
import org.hibernate.type.PostgresUUIDType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTest extends AbstractHibernateTest {

    private static ProductId id;

    @BeforeAll
    static void before() {
        doInTransaction(session ->
                session
                    .createNativeQuery("delete from product;")
                    .executeUpdate()
        );
    }

    @Test
    void productCanBeSaved() {
        var product = new Product("Ferrite Memory Cell");

        doInTransaction(session ->
                session.save(product)
        );

        id = product.getId();

        assertRowsInTable("product", id.getValue(), 1);
    }

    @Nested
    class aSavedProduct {

        @Test
        void isFoundById() {
            doInTransaction(session -> {
                Product product1 = session.find(Product.class, id);
                assertThat(product1.getId()).isEqualTo(id);
            });
        }

         @Test
        void canBeQueriedWithHQL() {
            doInTransaction(session -> {

                var maybeProduct = session
                        .createQuery("from Product where id = :id", Product.class)
                        .setParameter("id", id.getValue(), PostgresUUIDType.INSTANCE)
                        .uniqueResultOptional();

                assertThat(maybeProduct.isPresent()).isTrue();
            });
        }

        @Test
        void canBeQueriedWithSQL() {
            doInTransaction(session -> {

                var maybeProduct = session
                        .createNativeQuery("select * from product where id = :id", Product.class)
                        .setParameter("id", id.getValue(), PostgresUUIDType.INSTANCE)
                        .uniqueResultOptional();

                assertThat(maybeProduct.isPresent()).isTrue();
            });
        }

    }
}
