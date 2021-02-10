package ru.demo.springcameldemo.converter

import org.apache.camel.Converter
import org.apache.camel.TypeConverters
import org.springframework.stereotype.Component
import java.io.InputStream

@Component
class FIleDataConverter: TypeConverters {
    @Converter
    fun convertArrayListToInputStream(body: List<*>): InputStream{
        val stringBuilder = StringBuilder()
        body.forEach {
            stringBuilder.append(it.toString())
        }
        return stringBuilder.toString().byteInputStream()
    }
}
