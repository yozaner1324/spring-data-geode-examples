package example.springdata.geode.server.offheap.repo;

import example.springdata.geode.server.offheap.domain.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
