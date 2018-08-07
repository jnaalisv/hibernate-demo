package com.example.hibernate;

import org.hibernate.Session;
import org.hibernate.type.PostgresUUIDType;

import java.util.UUID;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

abstract class AbstractHibernateTest {

    static void assertTableEmpty(String tableName) {
        assertRowsInTable(tableName, "1=1", 0);
    }

    static void assertExistsById(String tableName, long id) {
        assertRowsInTable(tableName, id, 1);
    }

    static void assertRowsInTable(String tableName, String where, int expectedRows) {
        doInTransaction(session -> {
            var rows = session
                    .createQuery("from "+tableName+" where "+where)
                    .list();

            assertThat(rows.size()).isEqualTo(expectedRows);
        });
    }

    static  void assertRowsInTable(String tableName, Long id, int expectedRows) {
        doInTransaction(session -> {
            var rows = session
                    .createNativeQuery("select from "+tableName+" where id = :id")
                    .setParameter("id", id)
                    .list();

            assertThat(rows.size()).isEqualTo(expectedRows);
        });
    }

    static  void assertRowsInTable(String tableName, UUID id, int expectedRows) {
        doInTransaction(session -> {
            var rows = session
                    .createNativeQuery("select from "+tableName+" where id = :id")
                    .setParameter("id", id, PostgresUUIDType.INSTANCE)
                    .list();

            assertThat(rows.size()).isEqualTo(expectedRows);
        });
    }

    static void doInTransaction(Consumer<Session> sessionConsumer) {
        var session = SessionFactoryHolder.getSessionFactory().openSession();
        session.beginTransaction();

        sessionConsumer.accept(session);

        session.getTransaction().commit();
        session.close();
    }
}
