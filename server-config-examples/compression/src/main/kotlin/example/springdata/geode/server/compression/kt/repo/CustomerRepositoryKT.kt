package example.springdata.geode.server.compression.kt.repo

import example.springdata.geode.server.compression.kt.domain.Customer
import org.springframework.data.repository.CrudRepository

interface CustomerRepositoryKT : CrudRepository<Customer, Long>
