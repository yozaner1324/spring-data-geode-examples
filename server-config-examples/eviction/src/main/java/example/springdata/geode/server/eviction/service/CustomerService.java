package example.springdata.geode.server.eviction.service;

import org.apache.geode.cache.Region;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class CustomerService {

	private final Region customerRegion;

    public CustomerService(@Qualifier("Customers") Region customerRegion)
	{
		this.customerRegion = customerRegion;
	}

	public int size(){
    	return customerRegion.size();
	}
}
