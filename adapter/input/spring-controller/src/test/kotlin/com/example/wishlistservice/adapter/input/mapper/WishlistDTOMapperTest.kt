package com.example.wishlistservice.adapter.input.mapper

import com.example.wishlistservice.adapter.input.dto.ErrorDTO
import com.example.wishlistservice.adapter.input.dto.ProductWishlistedDTO
import com.example.wishlistservice.adapter.input.dto.WishlistDTO
import com.example.wishlistservice.domain.Wishlist
import com.example.wishlistservice.domain.WishlistProductLimitExceededException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class WishlistDTOMapperTest {

    @Test
    fun `Should map to WishlistDTO`() {
        val wishlist = Wishlist("Customer", setOf("Product"))
        val dto = WishlistDTO(wishlist.customerId, wishlist.productIds)
        assertThat(WishlistDTOMapper().toDTO(wishlist)).isEqualTo(dto)
    }

    @Test
    fun `Should map to ProductWishlistedDTO`() {
        assertThat(
            WishlistDTOMapper()
                .toProductWishlistedDTO(false)
        ).isEqualTo(ProductWishlistedDTO(false))
    }

    @Test
    fun `Should map to toErrorDto`() {
        val message = "Exception message"
        assertThat(
            WishlistDTOMapper()
                .toErrorDto(WishlistProductLimitExceededException(message))
        ).isEqualTo(ErrorDTO(message))
    }
}
