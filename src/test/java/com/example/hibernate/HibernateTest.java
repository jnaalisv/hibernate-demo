package com.example.hibernate;

import com.example.domain.Customer;
import org.hibernate.Session;
import org.junit.Test;

import java.util.List;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

public class HibernateTest {

    @Test
    public void testSomeThing() {
        Customer regularCustomer = new Customer("Regular Customer");

        assertTableEmpty("customer");

        doInTransaction(session -> {
            session.save(regularCustomer);
        });

        assertExistsById("customer", regularCustomer.getId());
    }

    protected static void assertTableEmpty(String tableName) {
        assertRowsInTable(tableName, "1=1", 0);
    }

    protected static void assertNotExistsById(String tableName, long id) {
        assertRowsInTable(tableName, "id="+id, 0);
    }

    protected static void assertExistsById(String tableName, long id) {
        assertRowsInTable(tableName, "id="+id, 1);
    }

    protected static void assertRowsInTable(String tableName, String where, int expectedRows) {
        doInTransaction(session -> {
            List rows = session
                    .createNativeQuery("select * from "+tableName+" where "+where)
                    .list();

            assertThat(rows.size()).isEqualTo(expectedRows);
        });
    }

    private static void doInTransaction(Consumer<Session> sessionConsumer) {
        Session session = SessionFactoryHolder.getSessionFactory().openSession();
        session.beginTransaction();

        sessionConsumer.accept(session);

        session.getTransaction().commit();
        session.close();
    }
}
