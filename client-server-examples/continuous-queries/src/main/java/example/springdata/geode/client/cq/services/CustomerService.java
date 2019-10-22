package example.springdata.geode.client.cq.services;

import example.springdata.geode.client.cq.repo.CustomerRepository;
import example.springdata.geode.domain.Customer;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private CustomerRepository customerRepositoryKT;

    public CustomerService(CustomerRepository customerRepositoryKT) {
        this.customerRepositoryKT = customerRepositoryKT;
    }

    public void save(Customer customer) {
        customerRepositoryKT.save(customer);
    }
}