package example.springdata.geode.server.offheap;

import example.springdata.geode.server.offheap.domain.Customer;
import example.springdata.geode.server.offheap.domain.Product;
import example.springdata.geode.server.offheap.repo.CustomerRepository;
import example.springdata.geode.server.offheap.repo.ProductRepository;
import org.apache.geode.cache.Region;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = OffHeapServer.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class OffHeapServerTest {

    @Resource(name = "Customers")
    private Region<Long, Customer> customers;

    @Resource(name = "Products")
    private Region<Long, Product> products;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void customerRepositoryIsAutoConfiguredCorrectly() {
        assertThat(customerRepository.count()).isEqualTo(3000);
    }

    @Test
    public void productRepositoryIsAutoConfiguredCorrectly() {
        assertThat(productRepository.count()).isEqualTo(1000);
    }

    @Test
    public void offHeapConfiguredCorrectly() {
        assertThat(customers.getAttributes().getOffHeap()).isTrue();
        assertThat(products.getAttributes().getOffHeap()).isTrue();

        logger.info("Entries in 'Customers' region are stored " + (customers.getAttributes().getOffHeap()? "OFF": "ON") + " heap");
        logger.info("Entries in 'Products' region are stored " + (products.getAttributes().getOffHeap()? "OFF": "ON") + " heap");
    }
}