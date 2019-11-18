package example.springdata.geode.client.cq.client.repo;

import example.springdata.geode.client.cq.domain.Customer;
import org.springframework.data.gemfire.mapping.annotation.ClientRegion;
import org.springframework.data.repository.CrudRepository;

@ClientRegion(name = "Customers")
public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
