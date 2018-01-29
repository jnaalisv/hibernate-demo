package com.example.hibernate;

import org.hibernate.Session;

import java.util.List;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractHibernateTest {

    protected static void assertTableEmpty(String tableName) {
        assertRowsInTable(tableName, "1=1", 0);
    }

    protected static void assertNotExistsById(String tableName, long id) {
        assertRowsInTable(tableName, "id="+id, 0);
    }

    protected static void assertExistsById(String tableName, long id) {
        assertRowsInTable(tableName, "id="+id, 1);
    }

    protected static void assertExistsById(String tableName, String id) {
        assertRowsInTable(tableName, "id='"+id+"'", 1);
    }

    protected static void assertRowsInTable(String tableName, String where, int expectedRows) {
        doInTransaction(session -> {
            List rows = session
                    .createNativeQuery("select * from "+tableName+" where "+where)
                    .list();

            assertThat(rows.size()).isEqualTo(expectedRows);
        });
    }

    protected static void doInTransaction(Consumer<Session> sessionConsumer) {
        Session session = SessionFactoryHolder.getSessionFactory().openSession();
        session.beginTransaction();

        sessionConsumer.accept(session);

        session.getTransaction().commit();
        session.close();
    }
}
