package example.springdata.geode.server.asyncqueues.kt.repo

import example.springdata.geode.domain.Order
import org.springframework.data.repository.CrudRepository

interface OrderRepositoryKT : CrudRepository<Order, Long>
