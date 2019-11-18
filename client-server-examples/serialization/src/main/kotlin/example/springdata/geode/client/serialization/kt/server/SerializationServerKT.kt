package example.springdata.geode.client.serialization.kt.server

import example.springdata.geode.client.serialization.kt.server.config.ServerApplicationConfigKT
import org.springframework.boot.WebApplicationType
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder

@SpringBootApplication(scanBasePackageClasses = [ServerApplicationConfigKT::class])
class SerializationServerKT {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplicationBuilder(SerializationServerKT::class.java)
                    .web(WebApplicationType.NONE)
                    .profiles("readSerialized")
                    .build()
                    .run(*args)
        }
    }
}