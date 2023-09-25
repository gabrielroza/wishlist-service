package com.example.wishlistservice.usecase

import com.example.wishlistservice.domain.Wishlist
import com.example.wishlistservice.usecase.repository.WishlistRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class AddProductTest {

    private val wishlistRepository = mockk<WishlistRepository> {
        every { save(any()) } returnsArgument 0
    }

    @Test
    fun `Should create wishlist when it does not exist`() {
        val customer = "Customer"
        every { wishlistRepository.findByCustomerId(customer) } returns null
        val product = "Product"
        val expectedWishlist = Wishlist(customer, setOf(product))
        assertThat(
            AddProduct(wishlistRepository).addProductToCustomerWishlist(
                customer,
                product
            )
        ).isEqualTo(expectedWishlist)
        verify { wishlistRepository.save(expectedWishlist) }
    }

    @Test
    fun `Should add product to existing wishlist`() {
        val customer = "Customer"
        val product = "Product"
        val existingWishlist = Wishlist(customer, setOf(product))
        every { wishlistRepository.findByCustomerId(customer) } returns existingWishlist
        val newProduct = "Product 2"
        val expectedWishlist = existingWishlist.copy(productIds = existingWishlist.productIds + newProduct)
        assertThat(
            AddProduct(wishlistRepository).addProductToCustomerWishlist(
                customer,
                newProduct
            )
        ).isEqualTo(expectedWishlist)
        verify { wishlistRepository.save(expectedWishlist) }
    }
}
