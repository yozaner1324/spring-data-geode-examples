package examples.springdata.geode.functions.cascading.server.functions;

import examples.springdata.geode.domain.Customer;
import examples.springdata.geode.domain.Order;
import org.apache.geode.cache.Region;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.gemfire.function.annotation.GemfireFunction;
import org.springframework.data.gemfire.function.annotation.RegionData;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CascadingFunctions {

    private Region<Long, Order> orderData;

    public CascadingFunctions(@Qualifier("Orders") Region<Long, Order> orderData) {
        this.orderData = orderData;
    }

    @GemfireFunction(id = "ListAllCustomers", HA = true, optimizeForWrite = false, batchSize = 0, hasResult = true)
    public List<Long> listAllCustomers(@RegionData Map<Long, Customer> customerData) {
        System.out.println("I'm executing function: \"listAllCustomers\" size= " + customerData.size());
        return customerData.keySet().stream().filter(customerId -> customerId != null).collect(Collectors.toList());
    }

    @GemfireFunction(id = "FindOrdersForCustomers", HA = true, optimizeForWrite = true, batchSize = 0, hasResult = true)
    public List<Order> findOrdersForCustomers(List<Order> customerIds) {
        System.out.println("I'm executing function: \"findOrdersForCustomer\" size= " + orderData.size());
        List<Order> returnValue = orderData.values().stream().filter(order -> customerIds.contains(order.getCustomerId())).collect(Collectors.toList());
        System.out.println("Return Value: " + returnValue);
        return returnValue;
    }
}