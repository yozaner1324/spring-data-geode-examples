package example.springdata.geode.server.eviction;

import example.springdata.geode.server.eviction.service.CustomerService;
import example.springdata.geode.server.eviction.service.OrderService;
import example.springdata.geode.server.eviction.service.ProductService;
import example.springdata.geode.domain.Customer;
import example.springdata.geode.domain.Order;
import example.springdata.geode.domain.Product;
import example.springdata.geode.server.eviction.service.CustomerService;
import example.springdata.geode.server.eviction.service.OrderService;
import example.springdata.geode.server.eviction.service.ProductService;
import org.apache.geode.cache.Region;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = EvictionServer.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class EvictionServerTest {

    @Resource(name = "Customers")
    private Region<Long, Customer> customers;

    @Resource(name = "Products")
    private Region<Long, Product> products;

    @Resource(name = "Orders")
    private Region<Long, Order> orders;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Before
    public void clearMemory() {
        System.gc();
    }

    @Test
    public void customerRepositoryWasAutoConfiguredCorrectly() {
        assertThat(this.customerService.size()).isEqualTo(300);
    }

    @Test
    public void productRepositoryWasAutoConfiguredCorrectly() {
        assertThat(this.productService.size()).isEqualTo(300);
    }

    @Test
    public void orderRepositoryWasAutoConfiguredCorrectly() {
        long numOrders = this.orderService.size();
        System.out.println("There are " + numOrders + " orders in the Orders region");
        assertThat(numOrders).isEqualTo(10);
    }

    @Test
    public void evictionIsConfiguredCorrectly() {
        assertThat(customers.getAttributes().getEvictionAttributes().getAction().isOverflowToDisk()).isTrue();
        assertThat(products.getAttributes().getEvictionAttributes().getAction().isOverflowToDisk()).isTrue();
        assertThat(orders.getAttributes().getEvictionAttributes().getAction().isLocalDestroy()).isTrue();
    }
}
