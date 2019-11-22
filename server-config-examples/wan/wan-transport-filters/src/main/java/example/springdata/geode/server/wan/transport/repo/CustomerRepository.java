package example.springdata.geode.server.wan.transport.repo;

import example.springdata.geode.server.wan.transport.domain.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
