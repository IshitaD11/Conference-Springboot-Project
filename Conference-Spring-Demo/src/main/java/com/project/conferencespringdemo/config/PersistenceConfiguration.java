package com.project.conferencespringdemo.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

//Not in use, this is one type of DB setting can be used

//@Configuration
public class PersistenceConfiguration {

    // Spring looks for the returned DataSource object and tries to see if one already exists in Spring context. If does, it will replace its definition with the one that is found
//    @Bean
//    public DataSource dataSource(){
//        // Body of the method is where configuration happens in java config class
//        DataSourceBuilder<?> builder = DataSourceBuilder.create();
//        builder.url("<DB_URL>");
//        builder.username("<DB_Username>");
//        builder.password("<DB_PASSWORD>");
//        System.out.println("Custom dataSource Bean has been initiated");
//        return builder.build();
//    }
}
