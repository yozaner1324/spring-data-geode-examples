package example.springdata.geode.functions.cascading.client.functions;

import example.springdata.geode.domain.Order;
import org.springframework.data.gemfire.function.annotation.FunctionId;
import org.springframework.data.gemfire.function.annotation.OnServers;

import java.util.List;

@OnServers
public interface OrderFunctionExecutions {

    @FunctionId("FindOrdersForCustomers")
    List<Order> findOrdersForCustomers(List<Long> customerIds);
}
