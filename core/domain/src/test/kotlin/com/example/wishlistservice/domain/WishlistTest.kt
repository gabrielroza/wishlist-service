package com.example.wishlistservice.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class WishlistTest {

    @Test
    fun `Should not allow more than 20 products`() {
        assertThrows<WishlistProductLimitExceededException> {
            (0..20)
                .map(Int::toString)
                .toSet()
                .let { Wishlist("customer", it) }
        }
    }

    @Test
    fun `Should allow up to 20 products`() {
        assertDoesNotThrow {
            val products =(0..19).map(Int::toString).toSet()
            val wishlist = Wishlist("customer", products)
            assertEquals(products, wishlist.productIds)
            assertEquals("customer", wishlist.customerId)
        }
    }

}