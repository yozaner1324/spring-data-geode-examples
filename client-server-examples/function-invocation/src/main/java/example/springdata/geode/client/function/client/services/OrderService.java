package example.springdata.geode.client.function.client.services;

import example.springdata.geode.client.function.client.functions.OrderFunctionExecutions;
import example.springdata.geode.client.function.client.repo.OrderRepository;
import example.springdata.geode.domain.Order;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderFunctionExecutions orderFunctionExecutions;

    public OrderService(OrderRepository orderRepository, OrderFunctionExecutions orderFunctionExecutions) {
        this.orderRepository = orderRepository;
        this.orderFunctionExecutions = orderFunctionExecutions;
    }

    public void save(Order order) {
        orderRepository.save(order);
    }

    public List<BigDecimal> sumPricesForAllProductsForOrder(Long orderId) {
        return orderFunctionExecutions.sumPricesForAllProductsForOrder(orderId);
    }

    public Order findById(Long orderId) {
        return orderRepository.findById(orderId).get();
    }
}
