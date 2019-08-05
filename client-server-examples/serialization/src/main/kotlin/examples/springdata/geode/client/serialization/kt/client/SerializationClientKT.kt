package examples.springdata.geode.client.serialization.kt.client

import examples.springdata.geode.client.common.kt.client.BaseClientKT
import examples.springdata.geode.client.common.kt.client.service.CustomerServiceKT
import examples.springdata.geode.client.serialization.kt.client.config.PdxSerializationClientConfigKT
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.WebApplicationType
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.annotation.Bean

@SpringBootApplication(scanBasePackageClasses = [PdxSerializationClientConfigKT::class])
class SerializationClientKT : BaseClientKT {
    @Bean
    fun runner(customerServiceKT: CustomerServiceKT) = ApplicationRunner {
        populateData(customerServiceKT)
    }
}

fun main(args: Array<String>) {
    SpringApplicationBuilder(SerializationClientKT::class.java)
        .web(WebApplicationType.NONE)
        .build()
        .run(*args)
}