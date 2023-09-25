package com.example.wishlistservice.adapter.output.mongorepository

import com.example.wishlistservice.adapter.output.mongorepository.mapper.WishlistDocumentMapper
import com.example.wishlistservice.adapter.output.mongorepository.mongo.WishlistMongoRepository
import com.example.wishlistservice.domain.Wishlist
import com.example.wishlistservice.usecase.repository.WishlistRepository
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import org.springframework.stereotype.Service

@Service
@EnableMongoRepositories
class WishlistRepositoryImpl(
    private val wishlistMongoRepository: WishlistMongoRepository,
    private val wishlistDocumentMapper: WishlistDocumentMapper
) : WishlistRepository {

    override fun save(wishlist: Wishlist): Wishlist = wishlistDocumentMapper
        .toEntity(wishlist)
        .let(wishlistMongoRepository::save)
        .let(wishlistDocumentMapper::toDomain)

    override fun findByCustomerId(customerId: String): Wishlist? = wishlistMongoRepository
        .findByCustomerId(customerId)
        ?.let(wishlistDocumentMapper::toDomain)
}
