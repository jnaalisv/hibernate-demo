package com.example.hibernate;

import com.example.domain.Customer;
import org.junit.jupiter.api.Test;

public class CustomerTest extends AbstractHibernateTest {

    @Test
    public void shouldPersistCustomerAndGenerateNewId() {
        Customer regularCustomer = new Customer("Regular Customer");

        assertTableEmpty("Customer");

        doInTransaction(session -> {
            session.save(regularCustomer);
        });

        assertExistsById("Customer", regularCustomer.getId());
    }

}
