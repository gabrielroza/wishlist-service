package com.example.wishlistservice.usecase

import com.example.wishlistservice.domain.Wishlist
import com.example.wishlistservice.usecase.exception.WishlistProductNotFoundException
import com.example.wishlistservice.usecase.repository.WishlistRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class RemoveProductTest {

    private val wishlistRepository = mockk<WishlistRepository> {
        every { save(any()) } returnsArgument 0
    }

    @Test
    fun `Should throw exception when there is no wishlist for customer`() {
        val customer = "Customer"
        every { wishlistRepository.findByCustomerId(customer) } returns null
        val product = "Product"
        assertThrows<WishlistProductNotFoundException> {
            RemoveProduct(wishlistRepository).removeProductFromCustomerWishlist(customer, product)
        }
    }

    @Test
    fun `Should throw exception when product is not on wishlist`() {
        val customer = "Customer"
        every { wishlistRepository.findByCustomerId(customer) } returns Wishlist(
            customerId = customer, productIds =
            setOf("Product")
        )
        val product = "Product 2"
        assertThrows<WishlistProductNotFoundException> {
            RemoveProduct(wishlistRepository).removeProductFromCustomerWishlist(customer, product)
        }
    }

    @Test
    fun `Should remove product from wishlist`() {
        val customer = "Customer"
        val existingWishlist = Wishlist(customer, setOf("Product", "Product 2"))
        every { wishlistRepository.findByCustomerId(customer) } returns existingWishlist
        val productToRemove = "Product 2"
        val expectedWishlist = existingWishlist.copy(productIds = existingWishlist.productIds - productToRemove)
        RemoveProduct(wishlistRepository).removeProductFromCustomerWishlist(customer, productToRemove)
        verify { wishlistRepository.save(expectedWishlist) }
    }

}