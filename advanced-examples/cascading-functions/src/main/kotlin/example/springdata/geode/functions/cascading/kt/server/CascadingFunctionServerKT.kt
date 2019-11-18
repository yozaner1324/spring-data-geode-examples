package example.springdata.geode.functions.cascading.kt.server

import example.springdata.geode.functions.cascading.kt.server.config.CascadingFunctionServerConfigKT
import org.springframework.boot.WebApplicationType
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder

@SpringBootApplication(scanBasePackageClasses = [CascadingFunctionServerConfigKT::class])
class CascadingFunctionServerKT {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplicationBuilder(CascadingFunctionServerKT::class.java)
                    .web(WebApplicationType.NONE)
                    .build().apply {
                        setAdditionalProfiles(getProfile(args))
                    }
                    .run(*args)
                    .apply { registerShutdownHook() }
        }

        private fun getProfile(args: Array<String>): String =
                if (args.isNotEmpty()) {
                    args[0]
                } else {
                    "default"
                }
    }
}