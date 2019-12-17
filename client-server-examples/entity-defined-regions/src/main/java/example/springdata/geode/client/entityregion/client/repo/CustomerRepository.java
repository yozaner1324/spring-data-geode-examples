package example.springdata.geode.client.entityregion.client.repo;

import example.springdata.geode.client.entityregion.client.domain.Customer;
import org.springframework.data.gemfire.mapping.annotation.ClientRegion;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@ClientRegion("Customers")
public interface CustomerRepository extends CrudRepository<Customer, Long> {

	List<Customer> findAll();
}
