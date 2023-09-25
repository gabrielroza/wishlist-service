package com.example.wishlistservice.usecase

import com.example.wishlistservice.usecase.repository.WishlistRepository

class HasProduct(
    private val wishlistRepository: WishlistRepository
) {

    fun customerHasProductOnWishlist(customerId: String, productId: String): Boolean {
        return wishlistRepository
            .findByCustomerId(customerId)
            ?.productIds
            ?.contains(productId) ?: false
    }
}
