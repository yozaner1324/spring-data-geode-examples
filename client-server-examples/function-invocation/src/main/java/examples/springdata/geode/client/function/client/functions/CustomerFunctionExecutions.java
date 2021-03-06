package examples.springdata.geode.client.function.client.functions;

import examples.springdata.geode.domain.Customer;
import org.springframework.data.gemfire.function.annotation.FunctionId;
import org.springframework.data.gemfire.function.annotation.OnRegion;

import java.util.List;

@OnRegion(region = "Customers")
public interface CustomerFunctionExecutions {

    @FunctionId("listConsumersForEmailAddressesFnc")
    List<List<Customer>> listAllCustomersForEmailAddress(String... emailAddresses);
}