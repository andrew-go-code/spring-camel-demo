package ru.demo.springcameldemo.router.file

import org.apache.camel.builder.RouteBuilder

class SimpleFileRouteConfigurer(
        private val routeName: String,
        private val filePath: String,
        private val fileName: String) : RouteBuilder() {
    override fun configure() {
        from("direct:$routeName")
                .to("file:$filePath?fileName=$fileName")
    }
}