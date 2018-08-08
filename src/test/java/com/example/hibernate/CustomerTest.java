package com.example.hibernate;

import com.example.domain.Customer;
import org.junit.jupiter.api.Test;

import static com.example.hibernate.HibernateSessionManager.doInTransaction;

class CustomerTest extends AbstractHibernateTest {

    @Test
    void shouldPersistCustomerAndGenerateNewId() {
        var regularCustomer = new Customer("Regular Customer");

        assertTableEmpty("Customer");

        doInTransaction(session ->
            session.save(regularCustomer)
        );

        assertExistsById("Customer", regularCustomer.getId());
    }

}
