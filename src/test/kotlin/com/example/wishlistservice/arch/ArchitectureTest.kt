package com.example.wishlistservice.arch

import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.library.Architectures.layeredArchitecture
import org.junit.jupiter.api.Test

class ArchitectureTest {

    @Test
    fun `no name in an outer circle can be mentioned by an inner circle`() {
        val importedClasses = ClassFileImporter().importPackages("com.example.wishlistservice");
        layeredArchitecture()
            .consideringAllDependencies()
            .layer("entities").definedBy("com.example.wishlistservice.entities..")
            .layer("usecase").definedBy("com.example.wishlistservice.usecase..")
            .layer("adapter").definedBy("com.example.wishlistservice.adapter..")
            .layer("application").definedBy("com.example.wishlistservice.application..")
            .whereLayer("application").mayNotBeAccessedByAnyLayer()
            .whereLayer("adapter").mayOnlyBeAccessedByLayers("application")
            .whereLayer("usecase").mayOnlyBeAccessedByLayers("application", "adapter")
            .whereLayer("entities").mayOnlyBeAccessedByLayers("application", "adapter", "usecase")
            .whereLayer("entities").mayOnlyBeAccessedByLayers("application", "adapter", "usecase")
            .check(importedClasses)
    }

}