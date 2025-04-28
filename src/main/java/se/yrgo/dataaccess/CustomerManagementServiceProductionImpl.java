package se.yrgo.dataaccess;

public class CustomerManagementServiceProductionImpl {
    private CustomerDao customerDao;

    public CustomerManagementServiceProductionImpl(CustomerDao dao) {
        this.customerDao = dao;
    }
}
