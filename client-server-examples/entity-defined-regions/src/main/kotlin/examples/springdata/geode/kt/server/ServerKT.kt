package examples.springdata.geode.kt.server

import examples.springdata.geode.kt.server.config.EntityDefinedRegionServerConfigKT
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication


@SpringBootApplication(scanBasePackageClasses = [EntityDefinedRegionServerConfigKT::class])
class ServerKT

fun main(args: Array<String>) {
    SpringApplication.run(ServerKT::class.java, *args)
    println("Press <ENTER> to exit")
    readLine()
}