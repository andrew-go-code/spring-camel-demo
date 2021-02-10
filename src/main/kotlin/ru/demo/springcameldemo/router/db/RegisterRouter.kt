package ru.demo.springcameldemo.router.db

import org.apache.camel.builder.RouteBuilder
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Component
import ru.demo.springcameldemo.config.DataSourceConfig.Companion.DATA_SOURCE_BEAN_NAME
import ru.demo.springcameldemo.processor.AtmProcessor
import ru.demo.springcameldemo.processor.EmptyAtmProcessor
import ru.demo.springcameldemo.router.file.SimpleFileRouteConfigurer

@Component
class RegisterRouter(
        private val atmProcessor: AtmProcessor,
        private val emptyAtmProcessor: EmptyAtmProcessor,
        private val resourceLoader: ResourceLoader
) : RouteBuilder() {

    override fun configure() {
        context.addRoutes {
            from("direct:register")
                    .to("direct:atms")
                    .process(atmProcessor)
                    .to("direct:atms_db")
                    .process(emptyAtmProcessor)
                    .to("direct:atms_file")
        }
        context.addRoutes(SimpleDbRouteConfigurer(
                routeName = "atms",
                sourceDs = DATA_SOURCE_BEAN_NAME,
                targetDs = DATA_SOURCE_BEAN_NAME,
                selectPath = "sql/atm/select.sql"
        ))
        context.addRoutes(SimpleDbRouteConfigurer(
                routeName = "atms_db",
                sourceDs = DATA_SOURCE_BEAN_NAME,
                targetDs = DATA_SOURCE_BEAN_NAME,
                insertPath = "sql/atm/insert.sql",
                deletePath = "sql/atm/delete.sql"
        ))

//        val resource = resourceLoader.getResource("classpath:basket/empty-atms.txt")
//        val absolutePath = resource.file.absolutePath
        //TODO: make path dynamic (hardcode for now)
        context.addRoutes(SimpleFileRouteConfigurer("atms_file","///Users/andrew/IdeaProjects/spring-camel-demo", "empty-atms.txt"))
    }
}