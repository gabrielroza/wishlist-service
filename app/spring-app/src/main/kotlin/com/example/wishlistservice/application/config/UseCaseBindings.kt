package com.example.wishlistservice.application.config

import com.example.wishlistservice.usecase.AddProduct
import com.example.wishlistservice.usecase.GetProducts
import com.example.wishlistservice.usecase.HasProduct
import com.example.wishlistservice.usecase.RemoveProduct
import com.example.wishlistservice.usecase.repository.WishlistRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UseCaseBindings {

    @Bean
    fun addProduct(wishlistRepository: WishlistRepository) = AddProduct(
        wishlistRepository
    )

    @Bean
    fun removeProduct(wishlistRepository: WishlistRepository) = RemoveProduct(
        wishlistRepository
    )

    @Bean
    fun getProducts(wishlistRepository: WishlistRepository) = GetProducts(
        wishlistRepository
    )

    @Bean
    fun hasProduct(wishlistRepository: WishlistRepository) = HasProduct(
        wishlistRepository
    )


}