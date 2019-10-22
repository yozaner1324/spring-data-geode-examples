package example.springdata.geode.server.asyncqueues.kt.repo

import example.springdata.geode.domain.Customer
import org.springframework.data.repository.CrudRepository

interface CustomerRepositoryKT : CrudRepository<Customer, Long>
