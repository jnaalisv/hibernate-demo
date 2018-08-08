package com.example.hibernate;

import org.hibernate.type.PostgresUUIDType;

import java.util.UUID;

import static com.example.hibernate.HibernateSessionManager.doInTransaction;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

            assertEquals(rows.size(), expectedRows);
        });
    }

    static  void assertRowsInTable(String tableName, Long id, int expectedRows) {
        doInTransaction(session -> {
            var rows = session
                    .createNativeQuery("select from "+tableName+" where id = :id")
                    .setParameter("id", id)
                    .list();

            assertEquals(rows.size(), expectedRows);
        });
    }

    static  void assertRowsInTable(String tableName, UUID id, int expectedRows) {
        doInTransaction(session -> {
            var rows = session
                    .createNativeQuery("select from "+tableName+" where id = :id")
                    .setParameter("id", id, PostgresUUIDType.INSTANCE)
                    .list();

            assertEquals(rows.size(), expectedRows);
        });
    }
}
