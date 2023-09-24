package com.example.wishlistservice.usecase

import com.example.wishlistservice.domain.Wishlist
import com.example.wishlistservice.usecase.repository.WishlistRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class HasProductTest() {

    private val wishlistRepository = mockk<WishlistRepository>()

    @Test
    fun `Should return false when there is no wishlist for customer`() {
        every { wishlistRepository.findByCustomerId(any()) } returns null
        assertFalse(
            HasProduct(wishlistRepository)
                .customerHasProductOnWishlist("Customer", "Product")
        )
    }

    @Test
    fun `Should return true when product is on wishlist`() {
        val product = "Product"
        val wishlist = Wishlist("Customer", setOf(product))
        every { wishlistRepository.findByCustomerId(any()) } returns wishlist
        assertTrue(
            HasProduct(wishlistRepository)
                .customerHasProductOnWishlist(wishlist.customerId, product)
        )
    }

    @Test
    fun `Should return true when product is not on wishlist`() {
        val product = "Product"
        val wishlist = Wishlist("Customer", setOf(product))
        every { wishlistRepository.findByCustomerId(any()) } returns wishlist
        assertFalse(
            HasProduct(wishlistRepository)
                .customerHasProductOnWishlist(wishlist.customerId, "Product 2")
        )
    }


}