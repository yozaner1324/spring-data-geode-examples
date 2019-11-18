package example.springdata.geode.client.security.client;

import example.springdata.geode.client.security.client.config.SecurityEnabledClientConfiguration;
import example.springdata.geode.client.security.client.repo.CustomerRepository;
import example.springdata.geode.client.security.domain.Customer;
import example.springdata.geode.client.security.domain.EmailAddress;
import example.springdata.geode.client.security.server.SecurityEnabledServer;
import org.apache.geode.cache.Region;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.gemfire.tests.integration.ForkingClientServerIntegrationTestsSupport;
import org.springframework.data.gemfire.util.RegionUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = SecurityEnabledClientConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class SecurityEnabledClientShiroTest extends ForkingClientServerIntegrationTestsSupport {

    @Autowired
    private CustomerRepository customerRepository;

    @Resource(name = "Customers")
    private Region<Long, Customer> customers;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @BeforeClass
    public static void setup() throws IOException {
        startGemFireServer(SecurityEnabledServer.class, "-Dspring.profiles.active=shiro-ini-configuration");
    }

    @Test
    public void customersRegionWasConfiguredCorrectly() {
        assertThat(this.customers).isNotNull();
        assertThat(this.customers.getName()).isEqualTo("Customers");
        assertThat(this.customers.getFullPath()).isEqualTo(RegionUtils.toRegionPath("Customers"));
        assertThat(this.customers).isEmpty();
    }

    @Test
    public void customerRepositoryWasConfiguredCorrectly() {
        assertThat(this.customerRepository).isNotNull();
    }

    @Test
    public void customerRepositoryWasAutoConfiguredCorrectly() {
        logger.info("Inserting 3 entries for keys: 1, 2, 3");
        Customer john = new Customer(1L, new EmailAddress("2@2.com"), "John", "Smith");
        Customer frank = new Customer(2L, new EmailAddress("3@3.com"), "Frank", "Lamport");
        Customer jude = new Customer(3L, new EmailAddress("5@5.com"), "Jude", "Simmons");
        customerRepository.save(john);
        customerRepository.save(frank);
        customerRepository.save(jude);
        assertThat(customers.keySetOnServer().size()).isEqualTo(3);
        logger.info("Customers saved on server:");
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList.size()).isEqualTo(3);
        assertThat(customerList.contains(john)).isTrue();
        assertThat(customerList.contains(frank)).isTrue();
        assertThat(customerList.contains(jude)).isTrue();
        customerList.forEach(customer -> logger.info("\t Entry: \n \t\t " + customer));
    }
}