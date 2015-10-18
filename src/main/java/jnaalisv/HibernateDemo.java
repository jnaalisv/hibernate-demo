package jnaalisv;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jnaalisv.domain.Customer;

public class HibernateDemo {

    private static final Logger logger = LoggerFactory.getLogger(HibernateDemo.class);

    public static void main(String[] args){

        SessionFactory sessionFactory = create();
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        Customer regularCustomer = new Customer("Regular Customer");

        session.save(regularCustomer);

        session.getTransaction().commit();
        session.close();


        System.exit(0);
    }

    private static SessionFactory create() {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                // .loadProperties would read from std properties file
                .build();
        try {
            return new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            logger.error("Failure:", e);
            StandardServiceRegistryBuilder.destroy(registry);
            throw new RuntimeException(e);
        }
    }
}
