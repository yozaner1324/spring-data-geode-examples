package example.springdata.geode.functions.cascading.client.services;

import example.springdata.geode.domain.Order;
import example.springdata.geode.functions.cascading.client.functions.OrderFunctionExecutions;
import example.springdata.geode.functions.cascading.client.repo.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private OrderRepository orderRepository;

    private OrderFunctionExecutions orderFunctionExecutions;

    public OrderService(OrderRepository orderRepository, OrderFunctionExecutions orderFunctionExecutions) {
        this.orderRepository = orderRepository;
        this.orderFunctionExecutions = orderFunctionExecutions;
    }

    public void save(Order order) {
        orderRepository.save(order);
    }

    public List<Order> findOrdersForCustomers(List<Long> customerIds) {
        return orderFunctionExecutions.findOrdersForCustomers(customerIds);
    }
}
