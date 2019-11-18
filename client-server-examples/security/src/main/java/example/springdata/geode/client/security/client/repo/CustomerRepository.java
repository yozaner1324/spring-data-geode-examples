package example.springdata.geode.client.security.client.repo;

import example.springdata.geode.client.security.domain.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
	List<Customer> findAll();
}
