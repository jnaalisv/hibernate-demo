package com.example.hibernate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public final class SessionFactoryHolder {
    private static final Logger logger = LogManager.getLogger(SessionFactoryHolder.class);

    private static final SessionFactory sessionFactory;

    static {
        // A SessionFactory is set up once for an application!
        final var registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                // .loadProperties would read from std properties file
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            logger.error("Failure:", e);
            StandardServiceRegistryBuilder.destroy(registry);
            throw new RuntimeException(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    private SessionFactoryHolder() {}
}
