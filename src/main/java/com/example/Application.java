package com.example;

import com.example.application.CustomerService;
import com.example.domain.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    private static final CustomerService customerService = new CustomerService();

    public static void main(String[] args) {
        logger.info("Running Hibernate Application");

        do {
            System.out.println("Commands:");
            System.out.println("  q - Quit");
            System.out.println("  l - List");
            System.out.println("  s - Save");
            System.out.println("  r - seaRch");

            var inputScanner = new Scanner(System.in);

            var command = inputScanner.next();

            if ("l".equals(command)) {
                var customers = customerService.getCustomers();
                System.out.println(String.format("Found %d customer(s)", customers.size()));
                customers.forEach(customer -> System.out.println(String.format("  %s", customer)));

            } if ("s".equals(command)) {
                System.out.println("  Input new Customer name:");
                var newCustomerName = inputScanner.next();
                customerService.save(new Customer(newCustomerName));

            }  if ("r".equals(command)) {
                System.out.println("  Input search word:");
                var searchWord = inputScanner.next();
                var customers = customerService.findBy(searchWord);
                System.out.println(String.format("Found %d customer(s)", customers.size()));
                customers.forEach(customer -> System.out.println(String.format("  %s", customer)));

            } else if ("q".equals(command)) {
                System.out.println("bye");
                break;
            }
        } while(true);
    }
}
