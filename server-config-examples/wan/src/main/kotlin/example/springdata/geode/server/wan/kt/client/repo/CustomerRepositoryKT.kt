package example.springdata.geode.server.wan.kt.client.repo

import example.springdata.geode.server.wan.kt.domain.Customer
import org.springframework.data.repository.CrudRepository

interface CustomerRepositoryKT : CrudRepository<Customer, Long>
