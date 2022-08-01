package com.example.restservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import feign.Client;
import feign.Request;
import feign.Response;

@SpringBootApplication
@EnableFeignClients
public class RestServiceApplication implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(RestServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        //Create the database table:
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS beers(name VARCHAR(100))");

        //Insert a record:
        jdbcTemplate.execute("INSERT INTO beers VALUES ('Stella')");

        //Read records:
        List<Beer> beers = jdbcTemplate.query("SELECT * FROM beers",
                (resultSet, rowNum) -> new Beer(resultSet.getString("name")));

        //Print read records:
        beers.forEach(System.out::println);
    }
}