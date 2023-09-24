package com.example.wishlistservice.adapter.output.mongorepository.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("wishlists")
data class WishlistDocument(
    @Id
    val customerId: String,
    val productIds: Set<String>
)