package se.yrgo.dataaccess;

import java.sql.*;
import java.util.*;

import org.springframework.jdbc.core.*;

import se.yrgo.domain.*;

public class CustomerDaoJdbcTemplateImpl implements CustomerDao {
    private static final String DELETE_SQL = "DELETE FROM CUSTOMER WHERE CUSTOMER_ID=?";
    private static final String UPDATE_SQL = "UPDATE CUSTOMER SET CALLS=?, NOTES=?, COMPANYNAME=?, EMAIL=?, TELEPHONE=? WHERE CUSTOMER_ID=?";
    private static final String INSERT_SQL = "INSERT INTO CUSTOMER (CALLS, NOTES, COMPANYNAME, EMAIL, TELEPHONE) VALUES (?,?,?,?,?)";
    private static final String GET_ALL_CUSTOMERS_SQL = "SELECT * FROM CUSTOMER";
    private static final String GET_CUSTOMER_BY_ID = "SELECT * FROM CUSTOMER WHERE CUSTOMER_ID=?";
    private static final String GET_CUSTOMER_BY_NAME = "SELECT * FROM CUSTOMER WHERE COMPANYNAME=?";
    private static final String ADD_CALL_SQL = "UPDATE CUSTOMER SET CALLS=? WHERE CUSTOMER_ID=?";
    private static final String CREATE_CALL_TABLE = "CREATE TABLE TBL_CALL (" +
            "ID INT PRIMARY KEY AUTO_INCREMENT, " +
            "TIMEADDATE TIMESTAMP, " +
            "NOTES VARCHAR(255)" +
            ")";

    private static final String CREATE_CUSTOMER_TABLE = "CREATE TABLE CUSTOMER (" +
            "ID INT PRIMARY KEY AUTO_INCREMENT, " +
            "COMPANYNAME VARCHAR(255), " +
            "EMAIL VARCHAR(255), " +
            "TELEPHONE VARCHAR(50), " +
            "NOTES VARCHAR(255)" +
            ")";

    private JdbcTemplate template;

    public CustomerDaoJdbcTemplateImpl(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public void create(Customer customer) {
        template.update(INSERT_SQL,
                customer.getCalls(),
                customer.getNotes(),
                customer.getCompanyName(),
                customer.getEmail(),
                customer.getTelephone());
    }

    @Override
    public void delete(Customer oldCustomer) throws RecordNotFoundException {
        this.template.update(DELETE_SQL, oldCustomer.getCustomerId());
    }

    @Override
    public void update(Customer customerToUpdate) throws RecordNotFoundException {
        this.template.update(UPDATE_SQL, customerToUpdate.getCalls(), customerToUpdate.getNotes(),
                customerToUpdate.getCompanyName(), customerToUpdate.getEmail(), customerToUpdate.getTelephone(),
                customerToUpdate.getCustomerId());
    }

    @Override
    public List<Customer> getAllCustomers() {
        return this.template.query(GET_ALL_CUSTOMERS_SQL, new CustomerRowMapper());
    }

    @Override
    public Customer getById(String customerId) throws RecordNotFoundException {
        return this.template.queryForObject(GET_CUSTOMER_BY_ID, new CustomerRowMapper(), customerId);
    }

    @Override
    public List<Customer> getByName(String name) {
        return this.template.query(GET_CUSTOMER_BY_NAME, new CustomerRowMapper(), name);
    }

    @Override
    public Customer getFullCustomerDetail(String customerId) throws RecordNotFoundException {
        return this.template.queryForObject(GET_CUSTOMER_BY_ID, new CustomerRowMapper(), customerId);
    }

    @Override
    public void addCall(Call newCall, String customerId) throws RecordNotFoundException {
        this.template.update(ADD_CALL_SQL, newCall.getNotes(), customerId);
    }

    public void createTables() {
        this.template.execute(CREATE_CALL_TABLE);
        this.template.execute(CREATE_CUSTOMER_TABLE);
    }
}

class CustomerRowMapper implements RowMapper<Customer> {
    public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
        String customerId = rs.getString("CUSTOMERID");
        String notes = rs.getString("NOTES");
        String companyName = rs.getString("COMPANYNAME");
        String email = rs.getString("EMAIL");
        String telephone = rs.getString("TELEPHONE");

        Customer customer = new Customer();
        customer.setCustomerId(customerId);
        customer.setNotes(notes);
        customer.setCompanyName(companyName);
        customer.setEmail(email);
        customer.setTelephone(telephone);

        customer.setCalls(new ArrayList<>());

        return customer;
    }
}
