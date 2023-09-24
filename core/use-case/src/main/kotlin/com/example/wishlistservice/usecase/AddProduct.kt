package com.example.wishlistservice.usecase

import com.example.wishlistservice.domain.Wishlist
import com.example.wishlistservice.usecase.repository.WishlistRepository

class AddProduct(
    private val wishlistRepository: WishlistRepository
) {

    fun addProductToCustomerWishlist(customerId: String, productId: String): Wishlist {
        return wishlistRepository.save(
            when (val wishlist = wishlistRepository.findByCustomerId(customerId)) {
                null -> Wishlist(customerId, productIds = setOf(productId))
                else -> wishlist.copy(productIds = wishlist.productIds + productId)
            }
        )
    }
}