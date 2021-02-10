package ru.demo.springcameldemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringCamelDemoApplication

fun main(args: Array<String>) {
    runApplication<SpringCamelDemoApplication>(*args)
}