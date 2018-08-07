package com.example.hibernate;

import com.example.domain.Customer;
import org.junit.jupiter.api.Test;

class CustomerTest extends AbstractHibernateTest {

    @Test
    void shouldPersistCustomerAndGenerateNewId() {
        var regularCustomer = new Customer("Regular Customer");

        assertTableEmpty("Customer");

        doInTransaction(session -> {
            session.save(regularCustomer);
        });

        assertExistsById("Customer", regularCustomer.getId());
    }

}
