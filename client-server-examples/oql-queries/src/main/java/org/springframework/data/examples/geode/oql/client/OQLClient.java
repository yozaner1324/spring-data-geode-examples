package org.springframework.data.examples.geode.oql.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.examples.geode.model.Customer;
import org.springframework.data.examples.geode.model.EmailAddress;

/**
 * Creates a client to demonstrate basic CRUD operations. This client can be configured in 2 ways, depending on profile
 * selected. "proxy" profile will create a region with PROXY configuration that will store no data locally. "localCache"
 * will create a region that stores data in the local client, to satisfy the "near cache" paradigm.
 *
 * @author Udo Kohlmeyer
 */
@SpringBootApplication(scanBasePackageClasses = OQLClientApplicationConfig.class)
public class OQLClient {

	private final CustomerService customerService;

	public OQLClient(CustomerService customerService) {
		this.customerService = customerService;
	}

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(OQLClient.class, args);
		OQLClient client = applicationContext.getBean(OQLClient.class);

		CustomerService customerService = client.customerService;
		System.out.println("Inserting 3 entries for keys: 1, 2, 3");
		customerService.save(new Customer(1L, new EmailAddress("2@2.com"), "John", "Smith"));
		customerService.save(new Customer(2L, new EmailAddress("3@3.com"), "Frank", "Lamport"));
		customerService.save(new Customer(3L, new EmailAddress("5@5.com"), "Jude", "Simmons"));

		System.out.println("Find customer with key=2 using GemFireRepository: " + customerService.findById(2L).get());
		System.out.println("Find customer with key=2 using GemFireTemplate: " + customerService
			.findWithTemplate("select * from /Customers where id=$1", 2L));

		customerService.save(new Customer(1L, new EmailAddress("3@3.com"), "Jude", "Smith"));
		System.out
			.println("Find customers with emailAddress=3@3.com: " + customerService.findByEmailAddress("3@3.com"));

		System.out
			.println("Find customers with firstName=Frank: " + customerService.findByFirstNameUsingIndex("Frank"));
		System.out
			.println("Find customers with firstName=Jude: " + customerService.findByFirstNameUsingIndex("Jude"));
	}
}