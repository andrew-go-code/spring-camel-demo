package ru.demo.springcameldemo.listener

import org.apache.camel.CamelContext
import org.springframework.boot.context.event.ApplicationStartedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import ru.demo.springcameldemo.router.StartRouter

@Component
class AppStartListener(
        private val camelContext: CamelContext
) {
    @EventListener(ApplicationStartedEvent::class)
    fun init() {
//        val endpoint = camelContext.getEndpoint("direct:route1")
//        producer.setDefaultEndpoint(endpoint)
//        producer.requestBodyAndHeader(body, headers)

//        camelContext.routeController.stopRoute("deals")
//        camelContext.removeRoute("deals")
//
        val producerTemplate = camelContext.createProducerTemplate()
//        val shutdownStrategy = camelContext.shutdownStrategy
        /** @see StartRouter */
        producerTemplate.setDefaultEndpointUri("direct:start")
        producerTemplate.sendBody("direct:start");
    }
}