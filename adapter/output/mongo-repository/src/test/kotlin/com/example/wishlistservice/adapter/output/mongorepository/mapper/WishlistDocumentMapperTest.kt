package com.example.wishlistservice.adapter.output.mongorepository.mapper

import com.example.wishlistservice.adapter.output.mongorepository.entity.WishlistDocument
import com.example.wishlistservice.domain.Wishlist
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class WishlistDocumentMapperTest {

    @Test
    fun `Should map to domain`() {
        val wishlist = Wishlist("Customer", setOf("Product"))
        val document = WishlistDocument(wishlist.customerId, wishlist.productIds)
        assertThat(WishlistDocumentMapper().toDomain(document)).isEqualTo(wishlist)
    }

    @Test
    fun `Should map to document`() {
        val wishlist = Wishlist("Customer", setOf("Product"))
        val document = WishlistDocument(wishlist.customerId, wishlist.productIds)
        assertThat(WishlistDocumentMapper().toEntity(wishlist)).isEqualTo(document)
    }
}
