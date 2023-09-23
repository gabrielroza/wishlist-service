package com.example.wishlistservice.adapter.output.database.repository.mongo

import com.example.wishlistservice.adapter.output.database.mapper.WishlistMapper
import com.example.wishlistservice.core.domain.Wishlist
import com.example.wishlistservice.core.usecase.repository.WishlistRepository
import org.springframework.stereotype.Service

@Service
class WishlistRepositoryImpl(
    private val wishlistMongoRepository: WishlistMongoRepository,
    private val wishlistMapper: WishlistMapper
) : WishlistRepository {

    override fun save(wishlist: Wishlist): Wishlist = wishlistMapper
        .toEntity(wishlist)
        .let(wishlistMongoRepository::save)
        .let(wishlistMapper::toDomain)

    override fun findByCustomerId(customerId: String): Wishlist? = wishlistMongoRepository
        .findByCustomerId(customerId)
        ?.let(wishlistMapper::toDomain)
}