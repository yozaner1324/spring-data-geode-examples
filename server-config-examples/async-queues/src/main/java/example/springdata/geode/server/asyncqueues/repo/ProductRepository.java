package example.springdata.geode.server.asyncqueues.repo;

import example.springdata.geode.domain.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
