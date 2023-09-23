package com.example.wishlistservice.application.config

import com.example.wishlistservice.usecase.AddProduct
import com.example.wishlistservice.usecase.repository.WishlistRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UseCaseConfiguration {

    @Bean
    fun addProduct(wishlistRepository: WishlistRepository) = AddProduct(
        wishlistRepository
    )

}