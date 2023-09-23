package com.example.wishlistservice.adapter.output.mongorepository.mapper

import com.example.wishlistservice.adapter.output.mongorepository.entity.WishlistDocument
import com.example.wishlistservice.domain.Wishlist
import org.springframework.stereotype.Service

@Service
class WishlistMapper {

    fun toEntity(wishlist: Wishlist) = WishlistDocument(
        customerId = wishlist.customerId,
        productIds = wishlist.productIds
    )

    fun toDomain(wishlist: WishlistDocument) = Wishlist(
        customerId = wishlist.customerId,
        productIds = wishlist.productIds
    )

}