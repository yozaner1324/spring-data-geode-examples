package example.springdata.geode.server.lucene.repo;

import example.springdata.geode.server.lucene.domain.Customer;
import example.springdata.geode.server.lucene.domain.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
