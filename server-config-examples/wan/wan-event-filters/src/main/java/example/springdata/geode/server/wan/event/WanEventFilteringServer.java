package example.springdata.geode.server.wan.event;

import com.github.javafaker.Faker;
import com.github.javafaker.Internet;
import com.github.javafaker.Name;
import example.springdata.geode.server.wan.event.config.WanEventFiltersConfig;
import example.springdata.geode.server.wan.event.domain.Customer;
import example.springdata.geode.server.wan.event.domain.EmailAddress;
import example.springdata.geode.server.wan.event.repo.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.util.Scanner;
import java.util.stream.LongStream;

@SpringBootApplication(scanBasePackageClasses = WanEventFiltersConfig.class)
public class WanEventFilteringServer {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static void main(String[] args) {
        new SpringApplicationBuilder(WanEventFilteringServer.class)
                .web(WebApplicationType.NONE)
                .build()
                .run(args);
    }

    @Bean
    @Profile({"default", "SiteA"})
    public ApplicationRunner siteARunner() {
        return args -> new Scanner(System.in).nextLine();
    }

    @Bean
    @Profile("SiteB")
    public ApplicationRunner siteBRunner(CustomerRepository customerRepository) {
        return args -> {
            logger.info("Inserting 300 customers");
            createCustomers(customerRepository);
        };
    }

    private void createCustomers(CustomerRepository repository) {
        Faker faker = new Faker();
        Name fakerName = faker.name();
        Internet fakerInternet = faker.internet();
        LongStream.range(0, 300).forEach(index ->
                repository.save(new Customer(index,
                        new EmailAddress(fakerInternet.emailAddress()), fakerName.firstName(), fakerName.lastName())));
    }
}
