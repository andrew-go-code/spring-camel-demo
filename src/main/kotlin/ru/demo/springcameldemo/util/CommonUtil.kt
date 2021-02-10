package ru.demo.springcameldemo.util

import com.google.common.io.Resources
import java.nio.charset.Charset

class CommonUtil {
    companion object {
        fun getResourceAsText(path: String): String{
            return Resources.toString(Resources.getResource(path), Charset.forName("utf-8"))
        }
    }
}