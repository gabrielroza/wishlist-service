package com.example.wishlistservice.usecase

import com.example.wishlistservice.usecase.exception.WishlistProductNotFoundException
import com.example.wishlistservice.usecase.repository.WishlistRepository

class RemoveProduct(
    private val wishlistRepository: WishlistRepository
) {

    fun removeProductFromCustomerWishlist(customerId: String, productId: String) {
        val wishlist = wishlistRepository.findByCustomerId(customerId)
        when {
            wishlist == null || productId !in wishlist.productIds -> {
                throw WishlistProductNotFoundException("Product $productId not found on customer wishlist")
            }
            else -> wishlistRepository.save(wishlist.copy(productIds = wishlist.productIds - productId))
        }
    }

}