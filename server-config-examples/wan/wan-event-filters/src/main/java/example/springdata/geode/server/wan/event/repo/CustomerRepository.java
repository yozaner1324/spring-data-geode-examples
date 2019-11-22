package example.springdata.geode.server.wan.event.repo;

import example.springdata.geode.server.wan.event.domain.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
