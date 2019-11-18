package example.springdata.geode.server.asyncqueues.repo;

import example.springdata.geode.server.asyncqueues.domain.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
