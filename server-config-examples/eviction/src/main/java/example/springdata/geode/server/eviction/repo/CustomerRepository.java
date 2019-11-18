package example.springdata.geode.server.eviction.repo;

import example.springdata.geode.server.eviction.domain.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
