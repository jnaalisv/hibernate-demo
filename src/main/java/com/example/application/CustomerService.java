package com.example.application;

import com.example.domain.Customer;

import java.util.Collection;

import static com.example.hibernate.HibernateSessionManager.doInTransaction;
import static com.example.hibernate.HibernateSessionManager.readInTransaction;

public class CustomerService {

    public CustomerService() {

    }

    public void delete(final Customer customer) {
        doInTransaction(session ->
                session.delete(customer)
        );
    }

    public void update(final Customer customer) {
        doInTransaction(session ->
                session.merge(customer)
        );
    }

    public void save(final Customer customer) {
        doInTransaction(session ->
            session.persist(customer)
        );
    }

    public Collection<Customer> getCustomers() {
        return readInTransaction(
                session ->
                    session
                    .createNativeQuery("select * from customer", Customer.class)
                    .list()
        );
    }

    public Collection<Customer> findBy(String searchWord) {
        return readInTransaction(
            session ->
                session
                    .createNativeQuery("select * from customer where name like :name", Customer.class)
                    .setParameter("name", "%"+searchWord+"%")
                    .list()
        );
    }

}
