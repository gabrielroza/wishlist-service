package com.example.wishlistservice.usecase.repository

import com.example.wishlistservice.entities.Wishlist

interface WishlistRepository {

    fun save(wishlist: Wishlist): Wishlist

    fun findByCustomerId(customerId: String): Wishlist?

}