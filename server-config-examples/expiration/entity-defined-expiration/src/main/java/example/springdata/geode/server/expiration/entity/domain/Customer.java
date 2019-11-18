package example.springdata.geode.server.expiration.entity.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.gemfire.expiration.IdleTimeoutExpiration;
import org.springframework.data.gemfire.expiration.TimeToLiveExpiration;
import org.springframework.data.gemfire.mapping.annotation.Region;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Region(name = "Customers")
@IdleTimeoutExpiration(action = "DESTROY", timeout = "2")
@TimeToLiveExpiration(action = "DESTROY", timeout = "4")
public class Customer implements Serializable {
    @Id
    private Long id;
    private EmailAddress emailAddress;
    private String firstName;
    private String lastName;
    private List<Address> addresses = new ArrayList<>();

    public Customer(Long id, EmailAddress emailAddress, String firstName, String lastName, Address... addresses) {
        this.id = id;
        this.emailAddress = emailAddress;
        this.firstName = firstName;
        this.lastName = lastName;
        this.addresses.addAll(Arrays.asList(addresses));
    }

    public void add(Address address) {
        addresses.add(address);
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }
}