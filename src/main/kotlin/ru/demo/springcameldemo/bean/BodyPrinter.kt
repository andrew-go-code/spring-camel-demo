package ru.demo.springcameldemo.bean

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component


@Component
@Qualifier("outputPrintBean")
class BodyPrinter {
    fun print(body: String?) {
        if (body == null) {
            println("Body is null")
        }
        println("Body result >>>>> $body")
    }
}