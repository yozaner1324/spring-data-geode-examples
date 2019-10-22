package example.springdata.geode.server.eviction.repo;

import example.springdata.geode.domain.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
