package org.example.interfaces;

import org.example.Model.Domain.Customer;


public interface CustomerDAOint {
        void createCustomer(Customer customer);
        Customer getCustomerById(int id);
        void updateCustomer(Customer customer);
        void deleteCustomer(int id);


}
