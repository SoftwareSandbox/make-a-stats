package be.swsb.makeastats.kotlinbackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinbackendApplication

fun main(args: Array<String>) {
    runApplication<KotlinbackendApplication>(*args)
}
