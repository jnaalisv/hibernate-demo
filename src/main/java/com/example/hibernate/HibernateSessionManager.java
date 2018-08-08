package com.example.hibernate;

import org.hibernate.Session;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class HibernateSessionManager {

    private static Session beginReadOnly() {
        var session = SessionFactoryHolder.getSessionFactory().openSession();
        session.setDefaultReadOnly(true);
        session.beginTransaction();
        return session;
    }

    private static Session begin() {
        var session = SessionFactoryHolder.getSessionFactory().openSession();
        session.beginTransaction();
        return session;
    }

    private static void commit(final Session session) {
        session.getTransaction().commit();
        session.close();
    }

    public static <T> List<T> readInTransaction(Function<Session, List<T>> sessionConsumer) {
        var session = beginReadOnly();
        var list = sessionConsumer.apply(session);
        commit(session);
        return list;
    }

    public static void doInTransaction(Consumer<Session> sessionConsumer) {
        var session = begin();
        sessionConsumer.accept(session);
        commit(session);
    }
}
