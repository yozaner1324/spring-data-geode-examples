package examples.springdata.geode.functions.cascading.client.services;

import examples.springdata.geode.domain.Customer;
import examples.springdata.geode.functions.cascading.client.functions.CustomerFunctionExecutions;
import examples.springdata.geode.functions.cascading.client.repo.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;

    private CustomerFunctionExecutions customerFunctionExecutions;

    public CustomerService(CustomerRepository customerRepository, CustomerFunctionExecutions customerFunctionExecutions) {
        this.customerRepository = customerRepository;
        this.customerFunctionExecutions = customerFunctionExecutions;
    }

    public void save(Customer customer) {
        customerRepository.save(customer);
    }

    public List<Long> listAllCustomers() {
        return customerFunctionExecutions.listAllCustomers().get(0);
    }
}