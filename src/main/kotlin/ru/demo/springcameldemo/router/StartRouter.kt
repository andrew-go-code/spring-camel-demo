package ru.demo.springcameldemo.router

import org.apache.camel.builder.RouteBuilder
import org.springframework.stereotype.Component
import ru.demo.springcameldemo.router.db.BusinessRouter
import ru.demo.springcameldemo.router.db.RegisterRouter

@Component
class StartRouter : RouteBuilder() {
    override fun configure() {
        context.addRoutes {
            var routeDefinition = from("direct:start")
            /** @see BusinessRouter */
            routeDefinition = routeDefinition.to("direct:business")
            /** @see RegisterRouter */
            routeDefinition.to("direct:register")
        }
    }
}