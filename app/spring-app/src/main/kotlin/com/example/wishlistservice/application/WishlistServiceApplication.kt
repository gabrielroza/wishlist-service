package com.example.wishlistservice.application

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan("com.example.wishlistservice")
class WishlistServiceApplication

fun main(args: Array<String>) {
	runApplication<WishlistServiceApplication>(*args)
}
