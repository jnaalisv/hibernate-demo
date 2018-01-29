package com.example.hibernate;

import com.example.domain.Customer;
import org.junit.Test;

public class CustomerTest extends AbstractHibernateTest {

    @Test
    public void shouldPersistCustomerAndGenerateNewId() {
        Customer regularCustomer = new Customer("Regular Customer");

        assertTableEmpty("customer");

        doInTransaction(session -> {
            session.save(regularCustomer);
        });

        assertExistsById("customer", regularCustomer.getId());
    }

}
