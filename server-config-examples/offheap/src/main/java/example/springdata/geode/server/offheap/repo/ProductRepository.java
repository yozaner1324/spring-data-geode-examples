package example.springdata.geode.server.offheap.repo;

import example.springdata.geode.server.offheap.domain.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
