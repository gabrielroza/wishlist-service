package com.example.wishlistservice.adapter.input.dto

data class WishlistDTO(
    val customerId: String,
    val productIds: Set<String>
)