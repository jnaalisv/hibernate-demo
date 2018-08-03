package com.example.hibernate;

import com.example.domain.Customer;
import com.example.domain.Order;
import com.example.domain.OrderLine;
import com.example.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class OrderTest extends AbstractHibernateTest {

    private Product ferriteMemoryCell;
    private Product crystallineCPU;
    private Customer customer;

    @BeforeEach
    public void before() {
        doInTransaction(session ->
                session
                        .createNativeQuery("truncate table product;")
                        .executeUpdate()
        );

        ferriteMemoryCell = new Product("Ferrite Memory Cell");
        crystallineCPU = new Product("Crystalline Central Processing Unit");
        customer = new Customer("John Doe");

        doInTransaction(session -> {
            session.save(ferriteMemoryCell);
            session.save(crystallineCPU);
            session.save(customer);
        });
    }

    @Test
    public void shouldCreateAnOrder() {
        var order = Order
                .forCustomer(customer.getId())
                .withOrderLine(new OrderLine(ferriteMemoryCell.getId(), new BigDecimal("2")))
                .withOrderLine(new OrderLine(crystallineCPU.getId(), new BigDecimal("1")));

        doInTransaction(session ->
            session.save(order)
        );

        assertRowsInTable("Order", "id = " + order.getId(), 1);
        assertRowsInTable("OrderLine", "order_id = " + order.getId(), 2);
    }
}
