package com.example.wishlistservice.adapter.output.mongorepository.mongo

import com.example.wishlistservice.adapter.output.mongorepository.entity.WishlistDocument
import org.springframework.data.mongodb.repository.MongoRepository

interface WishlistMongoRepository : MongoRepository<WishlistDocument, String> {

    fun findByCustomerId(customerId: String): WishlistDocument?
}
