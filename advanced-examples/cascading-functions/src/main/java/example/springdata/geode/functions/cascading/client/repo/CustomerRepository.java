package example.springdata.geode.functions.cascading.client.repo;

import example.springdata.geode.domain.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
