package ru.demo.springcameldemo.router.kafka

import org.apache.camel.builder.RouteBuilder
import ru.demo.springcameldemo.bean.BodyPrinter

/** just small example */
//@Component
class KafkaRouter : RouteBuilder() {
    override fun configure() {
        from("kafka:topic1?brokers=localhost:9092")
                .bean(BodyPrinter::class.java)
    }
}