package example.springdata.geode.server.wan.repo;

import example.springdata.geode.domain.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
