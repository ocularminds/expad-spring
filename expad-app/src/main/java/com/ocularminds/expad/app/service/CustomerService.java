
package com.ocularminds.expad.app.service;

import com.ocularminds.expad.app.repository.Customers;
import com.ocularminds.expad.model.Account;
import com.ocularminds.expad.model.CreditCardCustomer;
import com.ocularminds.expad.model.Customer;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService{

    Customers customers;

    @Autowired
    public CustomerService(final Customers customers) {
        this.customers = customers;
    }

    public Account findAccountById(String id) {
        return customers.accountWithId(id);
    }

    public List<Account> accounts(String custid) {
        return customers.accounts(custid);
    }

    public List<Account> search(String type, String filter) {
        return customers.search(type, filter);
    }
    
    public Customer customer(String customerid) {
        return customers.get(customerid);
    }
    
    public Customer findCustomerByAccountId(String foracid){
        try {
            return customers.ofAccountId(foracid);
        } catch (Exception ex) {
            Logger.getLogger(CustomerService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public CreditCardCustomer creditCardCustomerWithAccountId(String foracid) {
        return customers.creditCardCustomerWithAccountId(foracid);
    }
}
