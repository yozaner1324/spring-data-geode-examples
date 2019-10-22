package example.springdata.geode.client.basic.services;

import example.springdata.geode.client.basic.repo.CustomerRepository;
import example.springdata.geode.domain.Customer;
import org.apache.geode.cache.Region;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    private Region<Long, Customer> customerRegion;

    public CustomerService(CustomerRepository customerRepository, @Qualifier("Customers") Region<Long, Customer> customerRegion) {
        this.customerRepository = customerRepository;
        this.customerRegion = customerRegion;
    }

    private CustomerRepository getCustomerRepository() {
        return customerRepository;
    }

    public void save(Customer customer) {
        getCustomerRepository().save(customer);
    }

    public List<Customer> findAll() {
        return getCustomerRepository().findAll();
    }

    public Optional<Customer> findById(long id) {
        return getCustomerRepository().findById(id);
    }

    public int numberEntriesStoredLocally() {
        return customerRegion.size();
    }

    public int numberEntriesStoredOnServer() {
        return customerRegion.keySetOnServer().size();
    }

    public void deleteById(long id) {
        getCustomerRepository().deleteById(id);
    }
}
