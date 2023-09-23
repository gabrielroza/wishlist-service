package com.example.wishlistservice.adapter.input.mapper

import com.example.wishlistservice.adapter.input.dto.WishlistDTO
import com.example.wishlistservice.core.domain.Wishlist
import org.springframework.stereotype.Service

@Service
class WishlistDTOMapper {

    fun toDTO(wishlist: Wishlist): WishlistDTO = WishlistDTO(
        customerId = wishlist.customerId,
        productIds = wishlist.productIds
    )

}