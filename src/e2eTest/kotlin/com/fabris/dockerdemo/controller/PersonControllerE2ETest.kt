package com.fabris.dockerdemo.controller

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlConfig
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.web.client.RestTemplate
import javax.sql.DataSource

@ExtendWith(SpringExtension::class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = ["reset.sql", "data.sql"])
@ContextConfiguration(classes = [TestDataSourceConfiguration::class])
@SqlConfig(dataSource = "rootDataSource")
class PersonControllerE2ETest {

    val restTemplate = RestTemplate()

    @Test
    fun testFindPersonByName() {
        val targetPort = System.getProperty("E2E_SERVER_PORT")
        val responseEntity = restTemplate.exchange("http://localhost:$targetPort/v1/persons?name=Albert", HttpMethod.GET, null, object:  ParameterizedTypeReference<List<PersonDto>>(){})
        assertEquals(HttpStatus.OK, responseEntity.statusCode)
        val persons = responseEntity.body
        assertNotNull(persons)
        assertEquals(2, persons!!.size)
        assertEquals("Einstein", persons[0].lastName)
        assertEquals("Surname", persons[1].lastName)
    }
}

@Configuration
class TestDataSourceConfiguration {

    @Bean
    fun rootDataSourceProperties(): DataSourceProperties {
        val dataSourceProperties = DataSourceProperties()
        dataSourceProperties.url = "jdbc:postgresql://localhost:5433/postgres?currentSchema=public"
        dataSourceProperties.username = "postgres"
        dataSourceProperties.password = "postgresql"
        return dataSourceProperties
    }

    @Bean
    fun rootDataSource(): DataSource {
        //logger.error("rootDataSource(): called ", new Throwable());
        return rootDataSourceProperties().initializeDataSourceBuilder().build()
    }

}