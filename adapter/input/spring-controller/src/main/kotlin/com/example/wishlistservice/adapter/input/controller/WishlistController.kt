package com.example.wishlistservice.adapter.input.controller

import com.example.wishlistservice.adapter.input.dto.ProductWishlistedDTO
import com.example.wishlistservice.adapter.input.mapper.WishlistDTOMapper
import com.example.wishlistservice.domain.WishlistProductLimitExceededException
import com.example.wishlistservice.usecase.AddProduct
import com.example.wishlistservice.usecase.GetProducts
import com.example.wishlistservice.usecase.HasProduct
import com.example.wishlistservice.usecase.RemoveProduct
import com.example.wishlistservice.usecase.exception.WishlistProductNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/wishlist")
class WishlistController(
    private val addProduct: AddProduct,
    private val removeProduct: RemoveProduct,
    private val getProducts: GetProducts,
    private val hasProduct: HasProduct,
    private val wishlistDTOMapper: WishlistDTOMapper
) {

    @PutMapping("/customer/{customerId}/product/{productId}")
    fun addProduct(@PathVariable customerId: String, @PathVariable productId: String): ResponseEntity<*> {
        return try {
            ResponseEntity.ok(
                addProduct
                    .addProductToCustomerWishlist(customerId = customerId, productId = productId)
                    .let(wishlistDTOMapper::toDTO)
            )
        } catch (limitExceededException: WishlistProductLimitExceededException) {
            ResponseEntity.unprocessableEntity().body(wishlistDTOMapper.toErrorDto(limitExceededException))
        }
    }

    @DeleteMapping("/customer/{customerId}/product/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteProduct(@PathVariable customerId: String, @PathVariable productId: String): ResponseEntity<*> {
       return try {
           removeProduct.removeProductFromCustomerWishlist(customerId = customerId, productId = productId)
           ResponseEntity.noContent().build<Void>()
       } catch (productNotFoundException: WishlistProductNotFoundException) {
           ResponseEntity
               .status(HttpStatus.NOT_FOUND)
               .body(wishlistDTOMapper.toErrorDto(productNotFoundException))
       }
    }

    @GetMapping("/customer/{customerId}")
    fun getProducts(@PathVariable customerId: String): Set<String> = getProducts
        .getProducts(customerId = customerId)

    @GetMapping("/customer/{customerId}/product/{productId}")
    fun hasProduct(@PathVariable customerId: String, @PathVariable productId: String): ProductWishlistedDTO = hasProduct
        .customerHasProductOnWishlist(customerId = customerId, productId = productId)
        .let(wishlistDTOMapper::toProductWishlistedDTO)

}