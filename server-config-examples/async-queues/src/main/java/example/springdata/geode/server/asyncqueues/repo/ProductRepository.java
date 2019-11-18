package example.springdata.geode.server.asyncqueues.repo;

import example.springdata.geode.server.asyncqueues.domain.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
