package example.springdata.geode.client.function.client.repo;

import example.springdata.geode.domain.Order;
import org.springframework.data.gemfire.mapping.annotation.ClientRegion;
import org.springframework.data.repository.CrudRepository;

@ClientRegion("Orders")
public interface OrderRepository extends CrudRepository<Order, Long> {

}
