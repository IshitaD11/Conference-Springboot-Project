package com.project.conferencespringdemo.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;


@Configuration
public class PersistenceConfiguration {

    // Spring looks for the returned DataSource object and tries to see if one already exists in Spring context. If does, it will replace its definition with the one that is found
    @Bean
    public DataSource dataSource(){
        // Body of the method is where configuration happens in java config class
        DataSourceBuilder<?> builder = DataSourceBuilder.create();
        builder.url("jdbc:mysql://localhost:3306/conference");
        builder.username("root");
        builder.password("MySQL@123");
        System.out.println("Custom dataSource Bean has been initiated");
        return builder.build();
    }
}
