package com.example.wishlistservice.application.config

import com.example.wishlistservice.usecase.AddProduct
import com.example.wishlistservice.usecase.GetProducts
import com.example.wishlistservice.usecase.HasProduct
import com.example.wishlistservice.usecase.RemoveProduct
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UseCaseBindingsTest {

    @Autowired
    private lateinit var addProduct: AddProduct

    @Autowired
    private lateinit var removeProduct: RemoveProduct

    @Autowired
    private lateinit var getProducts: GetProducts

    @Autowired
    private lateinit var hasProduct: HasProduct

    @Test
    fun `Should bind use cases`() {
        assertTrue(this::addProduct.isInitialized)
        assertTrue(this::removeProduct.isInitialized)
        assertTrue(this::getProducts.isInitialized)
        assertTrue(this::hasProduct.isInitialized)
    }
}
