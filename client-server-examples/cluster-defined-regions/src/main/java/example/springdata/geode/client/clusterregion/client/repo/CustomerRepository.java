package example.springdata.geode.client.clusterregion.client.repo;

import example.springdata.geode.client.clusterregion.domain.Customer;
import org.springframework.data.gemfire.mapping.annotation.ClientRegion;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@ClientRegion("Customers")
public interface CustomerRepository extends CrudRepository<Customer, Long> {

	List<Customer > findAll();
}
