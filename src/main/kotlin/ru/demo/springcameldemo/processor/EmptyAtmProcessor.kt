package ru.demo.springcameldemo.processor

import org.apache.camel.Exchange
import org.apache.camel.Processor
import org.springframework.stereotype.Component

@Component
class EmptyAtmProcessor : Processor {
    override fun process(exchange: Exchange) {
        val message = exchange.getIn()
        val body = message.body
        val newBody: MutableList<String> = ArrayList()
        if (body is List<*>){
            body.forEach {
                if (it is Map<*, *>){
                    val nocash = it["nocash"] as Boolean
                    if (nocash){
                        var address = it["address"] as String
                        newBody.add(address)
                    }
                }
            }
        } else {
            //TODO: maybe an exception here
        }
        exchange.getIn().body = if (newBody.size != 0) newBody else body
    }
}