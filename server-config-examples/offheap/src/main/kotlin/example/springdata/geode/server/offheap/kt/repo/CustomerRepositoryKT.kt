package example.springdata.geode.server.offheap.kt.repo

import example.springdata.geode.server.offheap.kt.domain.Customer
import org.springframework.data.repository.CrudRepository

interface CustomerRepositoryKT : CrudRepository<Customer, Long>
