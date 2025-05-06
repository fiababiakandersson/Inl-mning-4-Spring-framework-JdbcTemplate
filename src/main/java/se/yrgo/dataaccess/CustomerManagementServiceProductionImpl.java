package se.yrgo.dataaccess;

import java.util.*;

import se.yrgo.domain.*;
import se.yrgo.services.customers.*;

public class CustomerManagementServiceProductionImpl implements CustomerManagementService {
    private CustomerDao customerDao;

    public CustomerManagementServiceProductionImpl(CustomerDao dao) {
        this.customerDao = dao;
    }

    @Override
    public void newCustomer(Customer newCustomer) {
        customerDao.create(newCustomer);
    }

    @Override
    public void updateCustomer(Customer changedCustomer) {
        try {
            customerDao.update(changedCustomer);
        } catch (RecordNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCustomer(Customer oldCustomer) {
        try {
            customerDao.delete(oldCustomer);
        } catch (RecordNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Customer findCustomerById(String customerId) throws CustomerNotFoundException {
        Customer customer = new Customer();
        try {
            customer = customerDao.getById(customerId);
        } catch (RecordNotFoundException e) {
            e.printStackTrace();
        }

        return customer;
    }

    @Override
    public List<Customer> findCustomersByName(String name) {
        return customerDao.getByName(name);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerDao.getAllCustomers();
    }

    @Override
    public Customer getFullCustomerDetail(String customerId) throws CustomerNotFoundException {
        Customer customer = new Customer();
        try {
            customer = customerDao.getById(customerId);
        } catch (RecordNotFoundException e) {
            e.printStackTrace();
        }

        return customer;
    }

    @Override
    public void recordCall(String customerId, Call callDetails) throws CustomerNotFoundException {
        try {
            customerDao.addCall(callDetails, customerId);
        } catch (RecordNotFoundException e) {
            e.printStackTrace();
        }
    }
}
