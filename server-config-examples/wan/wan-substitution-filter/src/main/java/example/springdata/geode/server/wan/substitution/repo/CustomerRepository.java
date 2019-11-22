package example.springdata.geode.server.wan.substitution.repo;

import example.springdata.geode.server.wan.substitution.domain.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
