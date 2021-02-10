package ru.demo.springcameldemo.aggregator

import org.apache.camel.AggregationStrategy
import org.apache.camel.Exchange
import org.springframework.stereotype.Component

@Component
class FakeAggregator : AggregationStrategy {
    override fun aggregate(oldExchange: Exchange?, newExchange: Exchange?): Exchange? {
        if (oldExchange == null) {
            return newExchange
        }
        val oldBody = oldExchange.getIn().body as List<Map<String, Any>>
        val newBody = newExchange?.getIn()?.body as List<MutableMap<String, Any>>
        return newExchange
    }
}