package com.example.wishlistservice.core.domain

data class Wishlist(
    val customerId: String,
    val productIds: List<String>
) {

    private companion object {
        const val SIZE_LIMIT = 20
    }

    init {
        if (productIds.size > SIZE_LIMIT) {
            throw WishlistProductLimitExceededException("A wishlist may not contain more than $SIZE_LIMIT products.")
        }
    }
}
