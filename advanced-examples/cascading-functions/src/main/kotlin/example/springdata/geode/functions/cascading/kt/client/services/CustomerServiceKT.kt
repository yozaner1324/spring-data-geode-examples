package example.springdata.geode.functions.cascading.kt.client.services

import example.springdata.geode.domain.Customer
import example.springdata.geode.functions.cascading.kt.client.functions.CustomerFunctionExecutionsKT
import example.springdata.geode.functions.cascading.kt.client.repo.CustomerRepositoryKT
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CustomerServiceKT(private val customerRepositoryKT: CustomerRepositoryKT,
                        private val customerFunctionExecutionsKT: CustomerFunctionExecutionsKT) {

    fun save(customer: Customer) = customerRepositoryKT.save(customer)
    fun listAllCustomers(): List<Long> = customerFunctionExecutionsKT.listAllCustomers()[0];
}