package com.example.wishlistservice.core.usecase

import com.example.wishlistservice.core.domain.Wishlist
import com.example.wishlistservice.core.usecase.repository.WishlistRepository
import org.springframework.stereotype.Service

@Service
class AddProduct(
    private val wishlistRepository: WishlistRepository
) {

    fun addProductToCustomerWishlist(customerId: String, productId: String): Wishlist {
        return wishlistRepository.save(
            when (val wishlist = wishlistRepository.findByCustomerId(customerId)) {
                null -> Wishlist(customerId, productIds = listOf(productId))
                else -> wishlist.copy(productIds = wishlist.productIds + productId)
            }
        )
    }
}