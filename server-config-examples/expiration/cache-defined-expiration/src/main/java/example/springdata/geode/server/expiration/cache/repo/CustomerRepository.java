package example.springdata.geode.server.expiration.cache.repo;

import example.springdata.geode.server.expiration.cache.domain.Customer;
import org.springframework.data.gemfire.mapping.annotation.Region;
import org.springframework.data.repository.CrudRepository;

@Region("Customers")
public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
