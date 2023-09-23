package com.example.wishlistservice.adapter.output.database.repository.mongo

import com.example.wishlistservice.adapter.output.database.entity.WishlistDocument
import org.springframework.data.mongodb.repository.MongoRepository

interface WishlistMongoRepository : MongoRepository<WishlistDocument, String> {

    fun findByCustomerId(customerId: String): WishlistDocument?
}