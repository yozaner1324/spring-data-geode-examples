package example.springdata.geode.embedded.region

import org.springframework.boot.ApplicationRunner
import org.springframework.boot.WebApplicationType
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.annotation.Bean

@SpringBootApplication
class RegionTypeServerKT {
    @Bean
    internal fun runner() = ApplicationRunner {
        System.err.println("Press <ENTER> to exit")
        readLine()
    }
}

fun main(args: Array<String>) {
    SpringApplicationBuilder(RegionTypeServerKT::class.java)
            .web(WebApplicationType.NONE)
            .build()
            .run(*args)
}