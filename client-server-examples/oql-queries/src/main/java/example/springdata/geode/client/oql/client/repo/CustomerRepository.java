package example.springdata.geode.client.oql.client.repo;

import example.springdata.geode.client.oql.domain.Customer;
import org.springframework.data.gemfire.repository.Query;
import org.springframework.data.gemfire.repository.query.annotation.Hint;
import org.springframework.data.gemfire.repository.query.annotation.Limit;
import org.springframework.data.gemfire.repository.query.annotation.Trace;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    @Trace
    @Limit(100)
    @Hint("emailAddressIndex")
    @Query("select * from /Customers customer where customer.emailAddress.value = $1")
    List<Customer> findByEmailAddressUsingIndex(String emailAddress);

    @Trace
    @Limit(100)
    @Query("select * from /Customers customer where customer.firstName = $1")
    List<Customer> findByFirstNameUsingIndex(String firstName);
}