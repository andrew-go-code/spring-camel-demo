package ru.demo.springcameldemo.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class DataSourceConfig {
    companion object {
        const val DATA_SOURCE_BEAN_NAME = "source"
    }

    /**
     * gonna be more then one dataSource
     * but for this example source and target are the same
     */
    @Bean(DATA_SOURCE_BEAN_NAME)
    @ConfigurationProperties(prefix = "spring.datasource")
    fun source(): DataSource {
        return DataSourceBuilder.create().build()
    }
}