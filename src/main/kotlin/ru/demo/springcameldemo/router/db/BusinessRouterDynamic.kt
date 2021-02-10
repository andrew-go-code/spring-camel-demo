package ru.demo.springcameldemo.router.db

import org.apache.camel.Headers
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
@Qualifier("businessRouterDynamic")
class BusinessRouterDynamic  {
    fun slip(body: String?, @Headers properties: Map<String, Any>? ): String?{
        return "direct:clients"
//        return null;
    }
}