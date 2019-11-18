package example.springdata.geode.server.lucene.kt.repo;

import example.springdata.geode.server.lucene.kt.domain.CustomerKT;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepositoryKT extends CrudRepository<CustomerKT, Long> {
}
