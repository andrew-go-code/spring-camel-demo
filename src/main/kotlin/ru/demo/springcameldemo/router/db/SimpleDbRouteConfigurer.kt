package ru.demo.springcameldemo.router.db

import org.apache.camel.AggregationStrategy
import org.apache.camel.builder.RouteBuilder
import org.apache.camel.model.ProcessorDefinition
import org.apache.camel.model.RouteDefinition
import ru.demo.springcameldemo.bean.BodyPrinter
import ru.demo.springcameldemo.util.CommonUtil

class SimpleDbRouteConfigurer(private val routeName: String,
                              private val sourceDs: String,
                              private val targetDs: String,
                              selectPath: String? = null,
                              insertPath: String? = null,
                              deletePath: String? = null,
                              private val aggregator: AggregationStrategy? = null,
                              private val skipped: Boolean = false
                           ) : RouteBuilder() {

    private var selectQ: String? = null
    private var insertQ: String? = null
    private var deleteQ: String? = null

    init {
        if (deletePath != null){
            deleteQ = CommonUtil.getResourceAsText(deletePath)
        }
        if (selectPath != null){
            selectQ = CommonUtil.getResourceAsText(selectPath)
        }
        if (insertPath != null){
            insertQ = CommonUtil.getResourceAsText(insertPath)
        }
    }

    override fun configure() {
        var routeDefinition: RouteDefinition = from("direct:$routeName")
                .routeId(this.routeName)

        if (skipped){
            println("skip $routeName")
            routeDefinition.to("mock:$routeName")
            return
        }

        /**
         * delete statement if present
         */
        deleteQ?.let { routeDefinition = addExpression(routeDefinition, it, sourceDs) }

        /**
         * select data statement
         */
        selectQ?.let { routeDefinition = addExpression(routeDefinition, it, sourceDs) }

        /**
         * Insert data statement
         */
        insertQ?.let { addInsertExpression(routeDefinition, it, targetDs) }
    }

    private fun addExpression(routeDefinition: RouteDefinition, query: String, dataSourceBeanName: String): RouteDefinition{
        return routeDefinition.to(getUri(query, dataSourceBeanName))
    }

    private fun addInsertExpression(routeDefinition: RouteDefinition, query: String, dataSourceBeanName: String):ProcessorDefinition<*> {
        val split: ProcessorDefinition<*> = if (aggregator != null) {
            routeDefinition
                    .split(body(), aggregator)
                    .stopOnAggregateException()
                    .bean(BodyPrinter::class.java)
                    .end()
                    .log("aggregation completed")
        } else {
            routeDefinition
                    .split(body())
        }

        return split
                .bean(BodyPrinter::class.java)
                .log(routeName)
                .to(getUri(query, dataSourceBeanName))
    }

    private fun getUri(query: String, dataSourceBeanName: String): String{
        return "sql:$query?dataSource=#$dataSourceBeanName"
    }
}