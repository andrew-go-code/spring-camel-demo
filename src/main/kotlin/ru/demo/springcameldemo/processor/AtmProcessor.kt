package ru.demo.springcameldemo.processor

import org.apache.camel.Exchange
import org.apache.camel.Processor
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class AtmProcessor : Processor {
    override fun process(exchange: Exchange) {
        val message = exchange.getIn()
        val body = message.body
        val newBody: MutableList<Map<String, Any>> = ArrayList()
        if (body is List<*>){
            body.forEach {
                if (it is Map<*, *>){
                    val newRow : MutableMap<String, Any> = it as MutableMap<String, Any>
                    val cash = it["cash"] as BigDecimal
                    newRow["nocash"] = cash.compareTo(BigDecimal(5000)) == -1
                    newBody.add(it)
                }
            }
        } else {
            //TODO: maybe an exception here
        }
        exchange.getIn().body = if (newBody.size != 0) newBody else body
    }
}