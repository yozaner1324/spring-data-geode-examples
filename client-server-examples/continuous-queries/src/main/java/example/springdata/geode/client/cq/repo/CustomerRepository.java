package example.springdata.geode.client.cq.repo;

import example.springdata.geode.domain.Customer;
import org.springframework.data.gemfire.mapping.annotation.ClientRegion;
import org.springframework.data.repository.CrudRepository;

@ClientRegion(name = "Customers")
public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
