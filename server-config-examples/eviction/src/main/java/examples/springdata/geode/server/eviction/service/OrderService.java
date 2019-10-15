package examples.springdata.geode.server.eviction.service;

import org.apache.geode.cache.Region;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class OrderService {

	private final Region orderRegion;

    public OrderService(@Qualifier("Orders") Region orderRegion)
	{
		this.orderRegion = orderRegion;
	}

	public int size(){
    	return orderRegion.size();
	}
}
