package com.example.hibernate;

import com.example.domain.Product;
import com.example.domain.ProductId;
import org.hibernate.type.PostgresUUIDType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.example.hibernate.HibernateSessionManager.doInTransaction;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
                assertEquals(product1.getId(), id);
            });
        }

         @Test
        void canBeQueriedWithHQL() {
            doInTransaction(session -> {

                var maybeProduct = session
                        .createQuery("from Product where id = :id", Product.class)
                        .setParameter("id", id.getValue(), PostgresUUIDType.INSTANCE)
                        .uniqueResultOptional();

                assertTrue(maybeProduct.isPresent());
            });
        }

        @Test
        void canBeQueriedWithSQL() {
            doInTransaction(session -> {

                var maybeProduct = session
                        .createNativeQuery("select * from product where id = :id", Product.class)
                        .setParameter("id", id.getValue(), PostgresUUIDType.INSTANCE)
                        .uniqueResultOptional();

                assertTrue(maybeProduct.isPresent());
            });
        }

    }
}
