package example.springdata.geode.server.expiration.entity.repo;

import example.springdata.geode.server.expiration.entity.domain.Customer;
import example.springdata.geode.server.expiration.entity.domain.Customer;
import org.springframework.data.gemfire.mapping.annotation.Region;
import org.springframework.data.repository.CrudRepository;

@Region("Customers")
public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
