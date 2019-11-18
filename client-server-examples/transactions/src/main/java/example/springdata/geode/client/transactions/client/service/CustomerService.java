package example.springdata.geode.client.transactions.client.service;

import example.springdata.geode.client.transactions.client.repo.CustomerRepository;
import example.springdata.geode.client.transactions.domain.Customer;
import example.springdata.geode.client.transactions.domain.EmailAddress;
import org.apache.geode.cache.Region;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Resource(name = "Customers")
    private Region<Long, Customer> customerRegion;

    public CustomerService(CustomerRepository customerRepository, @Qualifier("Customers") Region<Long, Customer> customerRegion) {
        this.customerRepository = customerRepository;
        this.customerRegion = customerRegion;
    }

    private CustomerRepository getCustomerRepository() {
        return customerRepository;
    }

    public Optional<Customer> findById(long id) {
        return getCustomerRepository().findById(id);
    }

    public int numberEntriesStoredOnServer() {
        return customerRegion.keySetOnServer().size();
    }

    @Transactional
    public List<Customer> createFiveCustomers() {
        return Arrays.stream(new Customer[]{new Customer(1L, new EmailAddress("1@1.com"), "John", "Melloncamp"),
                new Customer(2L, new EmailAddress("2@2.com"), "Franky", "Hamilton"),
                new Customer(3L, new EmailAddress("3@3.com"), "Sebastian", "Horner"),
                new Customer(4L, new EmailAddress("4@4.com"), "Chris", "Vettel"),
                new Customer(5L, new EmailAddress("5@5.com"), "Kimi", "Rosberg")})
                .map(customerRepository::save)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateCustomersSuccess() {
        customerRepository.save(new Customer(2L, new EmailAddress("2@2.com"), "Humpty", "Hamilton"));
    }

    @Transactional
    public void updateCustomersWithDelay(int millisDelay, Customer customer) {
        customerRepository.save(customer);
        try {
            Thread.sleep(millisDelay);
        } catch (InterruptedException e) {
        }
    }

    @Transactional
    public void updateCustomersFailure() {
        customerRepository.save(new Customer(2L, new EmailAddress("2@2.com"), "Numpty", "Hamilton"));
        throw new IllegalArgumentException("This should fail the transactions");
    }
}