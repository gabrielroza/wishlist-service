package com.example.wishlistservice.adapter.input.controller

import com.example.wishlistservice.adapter.input.mapper.WishlistDTOMapper
import com.example.wishlistservice.usecase.AddProduct
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/wishlist")
class WishlistController(
    private val addProduct: AddProduct,
    private val wishlistDTOMapper: WishlistDTOMapper
) {

    @PostMapping("/customer/{customerId}/product/{productId}")
    fun addProduct(@PathVariable customerId: String, @PathVariable productId: String) = addProduct
        .addProductToCustomerWishlist(customerId = customerId, productId = productId)
        .let(wishlistDTOMapper::toDTO)

}