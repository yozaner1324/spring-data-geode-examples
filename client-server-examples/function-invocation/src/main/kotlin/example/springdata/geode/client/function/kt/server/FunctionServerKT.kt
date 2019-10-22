package example.springdata.geode.client.function.kt.server

import example.springdata.geode.client.function.kt.server.config.FunctionServerApplicationConfigKT
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.WebApplicationType
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.annotation.Bean

@SpringBootApplication(scanBasePackageClasses = [FunctionServerApplicationConfigKT::class])
class FunctionServerKT() {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplicationBuilder(FunctionServerKT::class.java)
                    .web(WebApplicationType.NONE)
                    .build()
                    .run(*args)
        }
    }

    @Bean
    fun runner(): ApplicationRunner = ApplicationRunner {
        System.err.println("Press <ENTER> to exit")
        readLine()
    }
}