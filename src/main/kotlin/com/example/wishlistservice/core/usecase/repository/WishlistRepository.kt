package com.example.wishlistservice.core.usecase.repository

import com.example.wishlistservice.core.domain.Wishlist

interface WishlistRepository {

    fun save(wishlist: Wishlist): Wishlist

    fun findByCustomerId(customerId: String): Wishlist?

}