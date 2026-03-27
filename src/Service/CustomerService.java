package Service;

import model.Customer;

import java.util.*;

public class CustomerService {
    private static final CustomerService INSTANCE = new CustomerService();
    public final Map<String, Customer> customers = new HashMap<>();


    private CustomerService(){}

    public static CustomerService getInstance(){
        return INSTANCE;
    }

    public void addCustomer(String email, String firstName, String lastName){
        Customer customer = new Customer(firstName,lastName,email);
        customers.put(customer.getEmail(),customer);
    }

    public Customer getCustomer(String email){
        if(email==null) return null;
        return customers.get(email.toLowerCase());
    }
    public Collection<Customer> getAllCustomers(){
        return customers.values();

    }
}
