package examples.springdata.geode.client.security.kt.server

import examples.springdata.geode.client.security.kt.server.config.SecurityEnabledServerConfigurationKT
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.WebApplicationType
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.annotation.Bean

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

    @Bean
    fun runner() = ApplicationRunner {
        System.err.println("Press <ENTER> to exit")
        readLine()
    }
}