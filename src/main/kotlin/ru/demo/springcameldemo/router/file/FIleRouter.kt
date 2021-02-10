package ru.demo.springcameldemo.router.file

import org.apache.camel.builder.RouteBuilder
import org.springframework.stereotype.Component

/** just small example */
//@Component
class FIleRouter : RouteBuilder() {
    override fun configure() {
        from("direct:start")
                .to("file:/Users/andrew/Documents/test1")
                .log("qwe")
                .to("file:/Users/andrew/Documents/test2")
    }
}