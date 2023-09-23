package com.example.wishlistservice.application

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@SpringBootApplication
@ComponentScan("com.example.wishlistservice")
@EnableMongoRepositories(basePackages = ["com.example.wishlistservice.adapter.output.database"])
class WishlistServiceApplication

fun main(args: Array<String>) {
	runApplication<WishlistServiceApplication>(*args)
}
