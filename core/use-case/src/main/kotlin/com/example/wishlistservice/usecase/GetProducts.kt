package com.example.wishlistservice.usecase

import com.example.wishlistservice.usecase.repository.WishlistRepository

class GetProducts(
    private val wishlistRepository: WishlistRepository
) {

    fun getProducts(customerId: String): Set<String> {
        return wishlistRepository
            .findByCustomerId(customerId)
            ?.productIds ?: emptySet()
    }
}
