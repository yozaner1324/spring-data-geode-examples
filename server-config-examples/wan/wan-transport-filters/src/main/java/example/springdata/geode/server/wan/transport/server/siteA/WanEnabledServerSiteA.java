package example.springdata.geode.server.wan.transport.server.siteA;

import java.util.Scanner;
import java.util.stream.LongStream;

import com.github.javafaker.Faker;
import example.springdata.geode.server.wan.transport.domain.Customer;
import example.springdata.geode.server.wan.transport.domain.EmailAddress;
import example.springdata.geode.server.wan.transport.repo.CustomerRepository;
import example.springdata.geode.server.wan.transport.server.siteA.config.SiteAWanEnabledServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackageClasses = SiteAWanEnabledServerConfig.class)
public class WanEnabledServerSiteA {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    public static void main(String[] args) {
        new SpringApplicationBuilder(WanEnabledServerSiteA.class)
                .web(WebApplicationType.NONE)
                .build()
                .run(args);
    }

    @Bean
    public ApplicationRunner siteARunner(CustomerRepository customerRepository) {
        return args -> {
            createCustomerData(customerRepository);
            new Scanner(System.in).nextLine();
        };
    }

    private void createCustomerData(CustomerRepository customerRepository) {
        logger.info("Inserting 301 entries on siteA");
        Faker faker = new Faker();
        LongStream.rangeClosed(0, 300)
                .forEach(customerId ->
                        customerRepository.save(
                                new Customer(customerId,
                                        new EmailAddress(faker.internet().emailAddress()), faker.name().firstName(), faker.name().lastName())));
    }
}