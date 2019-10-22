package example.springdata.geode.server.eventhandlers.kt.config

import example.springdata.geode.domain.Customer
import org.apache.geode.cache.util.CacheWriterAdapter

class CustomerCacheWriterKT : CacheWriterAdapter<Long, Customer>()
