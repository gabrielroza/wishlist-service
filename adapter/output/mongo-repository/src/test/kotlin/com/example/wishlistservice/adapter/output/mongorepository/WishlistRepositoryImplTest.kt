package com.example.wishlistservice.adapter.output.mongorepository

import com.example.wishlistservice.adapter.output.mongorepository.entity.WishlistDocument
import com.example.wishlistservice.adapter.output.mongorepository.mapper.WishlistDocumentMapper
import com.example.wishlistservice.adapter.output.mongorepository.mongo.WishlistMongoRepository
import com.example.wishlistservice.domain.Wishlist
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class WishlistRepositoryImplTest {

    private val wishlistMongoRepository: WishlistMongoRepository = mockk {
        every { save(any()) } returnsArgument 0
    }
    private val wishlistDocumentMapper: WishlistDocumentMapper = mockk()

    @Test
    fun `Should save a wishlist`() {
        val wishlist = Wishlist("Customer", setOf("Product"))
        val document = WishlistDocument(wishlist.customerId, wishlist.productIds)
        every { wishlistDocumentMapper.toEntity(wishlist) } returns document
        every { wishlistDocumentMapper.toDomain(document) } returns wishlist
        assertThat(
            WishlistRepositoryImpl(wishlistMongoRepository, wishlistDocumentMapper).save(wishlist)
        ).isEqualTo(wishlist)
        verify { wishlistMongoRepository.save(document) }
    }

    @Test
    fun `Should find a wishlist by customer id`() {
        val wishlist = Wishlist("Customer", setOf("Product"))
        val document = WishlistDocument(wishlist.customerId, wishlist.productIds)
        every { wishlistMongoRepository.findByCustomerId(any()) } returns document
        every { wishlistDocumentMapper.toDomain(document) } returns wishlist
        assertThat(
            WishlistRepositoryImpl(wishlistMongoRepository, wishlistDocumentMapper).findByCustomerId(wishlist.customerId)
        ).isEqualTo(wishlist)
    }

    @Test
    fun `Should return null when there is no wishlist by customer id`() {
        every { wishlistMongoRepository.findByCustomerId(any()) } returns null
        assertThat(
            WishlistRepositoryImpl(wishlistMongoRepository, wishlistDocumentMapper).findByCustomerId("customer")
        ).isNull()
    }
}
