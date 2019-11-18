package example.springdata.geode.client.security.kt.server

import example.springdata.geode.client.security.kt.server.config.SecurityEnabledServerConfigurationKT
import org.springframework.boot.WebApplicationType
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder

@SpringBootApplication(scanBasePackageClasses = [SecurityEnabledServerConfigurationKT::class])
class SecurityEnabledServerKT {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplicationBuilder(SecurityEnabledServerKT::class.java)
                    .web(WebApplicationType.NONE)
                    .build()
                    .run(*args)
        }
    }
}