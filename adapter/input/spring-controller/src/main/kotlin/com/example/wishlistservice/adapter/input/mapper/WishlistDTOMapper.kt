package com.example.wishlistservice.adapter.input.mapper

import com.example.wishlistservice.adapter.input.dto.ErrorDTO
import com.example.wishlistservice.adapter.input.dto.ProductWishlistedDTO
import com.example.wishlistservice.adapter.input.dto.WishlistDTO
import com.example.wishlistservice.domain.Wishlist
import org.springframework.stereotype.Service

@Service
class WishlistDTOMapper {

    fun toDTO(wishlist: Wishlist): WishlistDTO = WishlistDTO(
        customerId = wishlist.customerId,
        productIds = wishlist.productIds
    )

    fun toProductWishlistedDTO(productWishlisted: Boolean) = ProductWishlistedDTO(productWishlisted)

    fun toErrorDto(exception: Exception) = ErrorDTO(
        exception.message
    )
}