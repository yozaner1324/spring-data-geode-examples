package example.springdata.geode.server.asyncqueues.kt.repo

import example.springdata.geode.server.asyncqueues.kt.domain.Order
import org.springframework.data.repository.CrudRepository

interface OrderRepositoryKT : CrudRepository<Order, Long>
