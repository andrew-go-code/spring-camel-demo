package ru.demo.springcameldemo.aggregator

import org.apache.camel.AggregationStrategy
import org.apache.camel.Exchange
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class DealSummaryAggregator : AggregationStrategy {
    override fun aggregate(oldExchange: Exchange?, newExchange: Exchange?): Exchange? {
        if (oldExchange == null) {
            return newExchange
        }

        val oldBody = oldExchange.getIn().body as Map<String, Any>
        val newBody = newExchange?.getIn()?.body as MutableMap<String, Any>

        val oldSum = oldBody["sum"] as BigDecimal
        var newSum = newBody["sum"] as BigDecimal

        newSum = newSum.plus(oldSum)
        newBody["sum"] = newSum

        oldExchange.getIn().body = newBody
        return oldExchange
    }
}