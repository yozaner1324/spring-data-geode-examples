package example.springdata.geode.server.eviction.repo;

import example.springdata.geode.server.eviction.domain.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
