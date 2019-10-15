package examples.springdata.geode.server.eviction.service;

import org.apache.geode.cache.Region;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ProductService {

	private final Region productRegion;

    public ProductService(@Qualifier("Products") Region productRegion)
	{
		this.productRegion = productRegion;
	}

	public int size(){
    	return productRegion.size();
	}
}
