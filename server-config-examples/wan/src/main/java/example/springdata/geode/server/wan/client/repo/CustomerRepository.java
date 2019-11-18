package example.springdata.geode.server.wan.client.repo;

import example.springdata.geode.server.wan.domain.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

}