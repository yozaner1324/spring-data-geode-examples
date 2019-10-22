package example.springdata.geode.client.cq.kt.services

import example.springdata.geode.client.cq.kt.repo.CustomerRepositoryKT
import example.springdata.geode.domain.Customer
import org.springframework.stereotype.Service

@Service
class CustomerServiceKT(private val customerRepositoryKT: CustomerRepositoryKT) {

    fun save(customer: Customer) = customerRepositoryKT.save(customer)
}