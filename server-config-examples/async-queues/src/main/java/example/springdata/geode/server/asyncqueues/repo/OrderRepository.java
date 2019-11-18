package example.springdata.geode.server.asyncqueues.repo;

import example.springdata.geode.server.asyncqueues.domain.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
