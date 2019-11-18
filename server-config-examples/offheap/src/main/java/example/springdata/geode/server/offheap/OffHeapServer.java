package example.springdata.geode.server.offheap;

import com.github.javafaker.Commerce;
import com.github.javafaker.Faker;
import com.github.javafaker.Internet;
import com.github.javafaker.Name;
import example.springdata.geode.server.offheap.config.OffHeapServerConfig;
import example.springdata.geode.server.offheap.domain.Customer;
import example.springdata.geode.server.offheap.domain.EmailAddress;
import example.springdata.geode.server.offheap.domain.Product;
import example.springdata.geode.server.offheap.repo.CustomerRepository;
import example.springdata.geode.server.offheap.repo.ProductRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.stream.LongStream;

@SpringBootApplication(scanBasePackageClasses = OffHeapServerConfig.class)
public class OffHeapServer {
    public static void main(String[] args) {
        new SpringApplicationBuilder(OffHeapServer.class).web(WebApplicationType.NONE).build().run(args);
    }

    @Bean
    public ApplicationRunner runner(CustomerRepository customerRepository, ProductRepository productRepository) {
        return args -> {
            createCustomers(customerRepository);
            createProducts(productRepository);
        };
    }

    private void createCustomers(CustomerRepository repository) {
        Faker faker = new Faker();
        Name fakerName = faker.name();
        Internet fakerInternet = faker.internet();
        LongStream.range(0, 3000).forEach(index ->
                repository.save(new Customer(index,
                        new EmailAddress(fakerInternet.emailAddress()), fakerName.firstName(), fakerName.lastName())));
    }

    private void createProducts(ProductRepository productRepository) {
        Faker faker = new Faker();
        Commerce fakerCommerce = faker.commerce();
        LongStream.range(0, 1000)
                .parallel()
                .forEach(id ->
                        productRepository.save(new Product(id, fakerCommerce.productName(), new BigDecimal(fakerCommerce.price(0.01, 100000.0)), "")));
    }
}
