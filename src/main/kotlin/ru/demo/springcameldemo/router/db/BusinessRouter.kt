package ru.demo.springcameldemo.router.db

import org.apache.camel.builder.RouteBuilder
import org.springframework.stereotype.Component
import ru.demo.springcameldemo.aggregator.DealSummaryAggregator
import ru.demo.springcameldemo.aggregator.FakeAggregator
import ru.demo.springcameldemo.config.DataSourceConfig.Companion.DATA_SOURCE_BEAN_NAME

@Component
class BusinessRouter(
        private val dealSummaryAggregator: DealSummaryAggregator
) : RouteBuilder() {

    override fun configure() {
//        val registry = context.registry

        context.addRoutes {
            from("direct:business")
                    /** @see BusinessRouterDynamic */
//                    .dynamicRouter(method("businessRouterDynamic", "slip"))
                    .to("direct:clients")
                    .multicast(FakeAggregator()).parallelProcessing().stopOnException()
                    .to("direct:accounts")
                    .to("direct:orders")
                    .end()
                    .filter { true } //could be dynamic value
                    .to("direct:deals")
                    .end()
                    .log("after deals")
                    .to("direct:deals-summary")
        }
        context.addRoutes(SimpleDbRouteConfigurer(
                routeName = "clients",
                sourceDs = DATA_SOURCE_BEAN_NAME,
                targetDs = DATA_SOURCE_BEAN_NAME,
                selectPath = "sql/client/select.sql",
                insertPath = "sql/client/insert.sql",
                deletePath = "sql/client/delete.sql"
        ))
        context.addRoutes(SimpleDbRouteConfigurer(
                routeName = "accounts",
                sourceDs = DATA_SOURCE_BEAN_NAME,
                targetDs = DATA_SOURCE_BEAN_NAME,
                selectPath = "sql/account/select.sql",
                insertPath = "sql/account/insert.sql",
                deletePath = "sql/account/delete.sql"
        ))
        context.addRoutes(SimpleDbRouteConfigurer(
                routeName = "orders",
                sourceDs = DATA_SOURCE_BEAN_NAME,
                targetDs = DATA_SOURCE_BEAN_NAME,
                selectPath = "sql/order/select.sql",
                insertPath = "sql/order/insert.sql",
                deletePath = "sql/order/delete.sql"
        ))
        context.addRoutes(SimpleDbRouteConfigurer(
                routeName = "deals",
                sourceDs = DATA_SOURCE_BEAN_NAME,
                targetDs = DATA_SOURCE_BEAN_NAME,
                selectPath = "sql/deal/select.sql",
                insertPath = "sql/deal/insert.sql",
                deletePath = "sql/deal/delete.sql",
                skipped = false
        ))
        context.addRoutes(SimpleDbRouteConfigurer(
                routeName = "deals-summary",
                sourceDs = DATA_SOURCE_BEAN_NAME,
                targetDs = DATA_SOURCE_BEAN_NAME,
                aggregator = dealSummaryAggregator,
                selectPath = "sql/deal-summary/select.sql",
                insertPath = "sql/deal-summary/insert.sql",
                deletePath = "sql/deal-summary/delete.sql"
        ))
    }
}