package example.springdata.geode.server.region.repo;

import example.springdata.geode.server.region.domain.Product;
import org.springframework.data.gemfire.mapping.annotation.Region;
import org.springframework.data.repository.CrudRepository;

@Region("Products")
public interface ProductRepository extends CrudRepository<Product, Long> {
}
