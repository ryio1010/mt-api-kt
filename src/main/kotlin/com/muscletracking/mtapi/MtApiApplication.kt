package com.muscletracking.mtapi

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy
import javax.sql.DataSource

@SpringBootApplication
class MtApiApplication

fun main(args: Array<String>) {
    runApplication<MtApiApplication>(*args)
}

@Bean
@Autowired
fun dataSource(dataSourceProperties: DataSourceProperties): DataSource {
    val dataSource =
        DriverManagerDataSource(dataSourceProperties.url, dataSourceProperties.username, dataSourceProperties.password)
    dataSource.setDriverClassName(dataSourceProperties.driverClassName)
    return TransactionAwareDataSourceProxy(dataSource)
}