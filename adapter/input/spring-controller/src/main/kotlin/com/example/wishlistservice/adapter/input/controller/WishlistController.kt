package com.example.wishlistservice.adapter.input.controller

import com.example.wishlistservice.adapter.input.dto.ErrorDTO
import com.example.wishlistservice.adapter.input.dto.ProductWishlistedDTO
import com.example.wishlistservice.adapter.input.dto.WishlistDTO
import com.example.wishlistservice.adapter.input.mapper.WishlistDTOMapper
import com.example.wishlistservice.domain.WishlistProductLimitExceededException
import com.example.wishlistservice.usecase.AddProduct
import com.example.wishlistservice.usecase.GetProducts
import com.example.wishlistservice.usecase.HasProduct
import com.example.wishlistservice.usecase.RemoveProduct
import com.example.wishlistservice.usecase.exception.WishlistProductNotFoundException
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/wishlist", produces = ["application/json"])
@Tag(name = "Wishlist API")
class WishlistController(
    private val addProduct: AddProduct,
    private val removeProduct: RemoveProduct,
    private val getProducts: GetProducts,
    private val hasProduct: HasProduct,
    private val wishlistDTOMapper: WishlistDTOMapper
) {

    @Operation(summary = "Add a product to customer's wishlist")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Returns the complete wishlist",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = WishlistDTO::class))]
            ),
            ApiResponse(
                responseCode = "422",
                description = "When wishlist would have more than 20 products",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = ErrorDTO::class))]
            )
        ]
    )
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

    @Operation(summary = "Remove a product from customer's wishlist")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "204",
                description = "Product removed",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = Void::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "When given product not on customer's wishlist",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = ErrorDTO::class))]
            )
        ]
    )
    @DeleteMapping("/customer/{customerId}/product/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun removeProduct(@PathVariable customerId: String, @PathVariable productId: String): ResponseEntity<*> {
        return try {
            removeProduct.removeProductFromCustomerWishlist(customerId = customerId, productId = productId)
            ResponseEntity.noContent().build<Void>()
        } catch (productNotFoundException: WishlistProductNotFoundException) {
            ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(wishlistDTOMapper.toErrorDto(productNotFoundException))
        }
    }

    @Operation(summary = "Get products from customer's wishlist")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Product list",
                content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = String::class))
                    )
                ]
            )
        ]
    )
    @GetMapping("/customer/{customerId}")
    fun getProducts(@PathVariable customerId: String): Set<String> = getProducts
        .getProducts(customerId = customerId)

    @Operation(summary = "Get whether a product is wishlisted")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Whether given product is wishlisted",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ProductWishlistedDTO::class)
                    )
                ]
            )
        ]
    )
    @GetMapping("/customer/{customerId}/product/{productId}")
    fun hasProduct(@PathVariable customerId: String, @PathVariable productId: String): ProductWishlistedDTO = hasProduct
        .customerHasProductOnWishlist(customerId = customerId, productId = productId)
        .let(wishlistDTOMapper::toProductWishlistedDTO)
}
