package be.swsb.makeastats.kotlinbackend

import be.swsb.makeastats.kotlinbackend.controllers.util.ObjectMapperFactory
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@SpringBootApplication
class MakeAStatsApplication

fun main(args: Array<String>) {
    runApplication<MakeAStatsApplication>(*args)
}

@Autowired
fun configureCors(webMvcConfig: WebMvcConfigurer) {
    val registry = CorsRegistry()
    registry.addMapping("/**").allowedOrigins("*")
    webMvcConfig.addCorsMappings(registry)
}

@Bean
fun objectMapper(): ObjectMapper {
    return ObjectMapperFactory.instance()
}
