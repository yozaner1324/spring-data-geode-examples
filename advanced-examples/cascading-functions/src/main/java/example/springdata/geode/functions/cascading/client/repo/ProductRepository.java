package example.springdata.geode.functions.cascading.client.repo;

import example.springdata.geode.functions.cascading.kt.domain.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
