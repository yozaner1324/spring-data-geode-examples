package example.springdata.geode.functions.cascading.client.repo;

import example.springdata.geode.functions.cascading.kt.domain.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
