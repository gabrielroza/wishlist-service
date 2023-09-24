package com.example.wishlistservice.usecase

import com.example.wishlistservice.domain.Wishlist
import com.example.wishlistservice.usecase.repository.WishlistRepository
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class GetProductsTest {

    private val wishlistRepository = mockk<WishlistRepository>()

    @Test
    fun `Should return empty when there is no wishlist`() {
        every { wishlistRepository.findByCustomerId(any()) } returns null
        assertThat(GetProducts(wishlistRepository).getProducts("customer"))
            .isEmpty()
    }

    @Test
    fun `Should return wishlist products`() {
        val customerId = "Customer"
        val wishlist = Wishlist(customerId, setOf("productId"))
        every { wishlistRepository.findByCustomerId(customerId) } returns wishlist
        assertThat(
            GetProducts(wishlistRepository).getProducts(customerId)
        ).isEqualTo(wishlist.productIds)
    }

}