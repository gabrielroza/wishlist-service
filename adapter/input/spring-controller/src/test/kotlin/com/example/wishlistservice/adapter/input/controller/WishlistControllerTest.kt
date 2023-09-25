package com.example.wishlistservice.adapter.input.controller

import com.example.wishlistservice.adapter.input.dto.ErrorDTO
import com.example.wishlistservice.adapter.input.dto.ProductWishlistedDTO
import com.example.wishlistservice.adapter.input.dto.WishlistDTO
import com.example.wishlistservice.adapter.input.mapper.WishlistDTOMapper
import com.example.wishlistservice.domain.Wishlist
import com.example.wishlistservice.domain.WishlistProductLimitExceededException
import com.example.wishlistservice.usecase.AddProduct
import com.example.wishlistservice.usecase.GetProducts
import com.example.wishlistservice.usecase.HasProduct
import com.example.wishlistservice.usecase.RemoveProduct
import com.example.wishlistservice.usecase.exception.WishlistProductNotFoundException
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

class WishlistControllerTest {

    private val addProduct: AddProduct = mockk()
    private val removeProduct: RemoveProduct = mockk()
    private val getProducts: GetProducts = mockk()
    private val hasProduct: HasProduct = mockk()
    private val wishlistDTOMapper: WishlistDTOMapper = WishlistDTOMapper()

    private val controller = WishlistController(
        addProduct,
        removeProduct,
        getProducts,
        hasProduct,
        wishlistDTOMapper
    )

    @Test
    fun `Should return wishlistDTO when adding a product`() {
        every { addProduct.addProductToCustomerWishlist(any(), any()) } answers {
            Wishlist(arg(0), setOf(arg(1)))
        }
        val customer = "customer"
        val product = "product"
        val response = controller.addProduct(customer, product)
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body).isEqualTo(WishlistDTO(customer, setOf(product)))
    }

    @Test
    fun `Should return errorDTO when more products than allowed are added`() {
        val limitExceededException = WishlistProductLimitExceededException(
            message = "A wishlist may not contain more than N products."
        )
        every { addProduct.addProductToCustomerWishlist(any(), any()) } throws limitExceededException
        val customer = "customer"
        val product = "product"
        val response = controller.addProduct(customer, product)
        assertThat(response.statusCode).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
        assertThat(response.body).isEqualTo(ErrorDTO(limitExceededException.message))
    }

    @Test
    fun `Should return not found when removing a product that is not on the wishlist`() {
        val notFoundException = WishlistProductNotFoundException(
            "Product productId not found on customer wishlist"
        )
        every { removeProduct.removeProductFromCustomerWishlist(any(), any()) } throws notFoundException
        val response = controller.removeProduct("customer", "product")
        assertThat(response.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
        assertThat(response.body).isEqualTo(ErrorDTO(notFoundException.message))
    }

    @Test
    fun `Should return no content when removing a product`() {
        every { removeProduct.removeProductFromCustomerWishlist(any(), any()) } returns Unit
        val response = controller.removeProduct("customer", "product")
        assertThat(response.statusCode).isEqualTo(HttpStatus.NO_CONTENT)
    }

    @Test
    fun `Should return wishlist products`() {
        val products = setOf("Product 1", "Product 2")
        every { getProducts.getProducts(any()) } returns products
        assertThat(controller.getProducts("customer")).isEqualTo(products)
    }

    @Test
    fun `Should return whether product is on wishlist`() {
        every { hasProduct.customerHasProductOnWishlist(any(), any()) } returns true
        assertThat(controller.hasProduct("Customer", "Product")).isEqualTo(
            ProductWishlistedDTO(true)
        )
    }

}