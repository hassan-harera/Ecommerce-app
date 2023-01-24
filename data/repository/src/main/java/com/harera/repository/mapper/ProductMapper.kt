package com.harera.repository.mapper

import com.harera.local.model.ProductEntity
import com.harera.repository.domain.Product
import com.harera.service.domain.ServiceDomainProduct

object ProductMapper {

    fun toProductEntity(product: Product): ProductEntity {
        return ProductEntity(
            productPictureUrls = product.productPictureUrls,
            productId = product.productId,
            categoryName = product.categoryName,
            title = product.title,
            price = product.price,
            amount = product.amount,
            unit = product.unit
        )
    }

    fun toProductEntity(product: ServiceDomainProduct): ProductEntity {
        return ProductEntity(
            productPictureUrls = product.productPictureUrls,
            productId = product.productId,
            categoryName = product.categoryName,
            title = product.title,
            price = product.price,
            amount = product.amount,
            unit = product.unit
        )
    }

    fun toProduct(product: Product): ServiceDomainProduct {
        return ServiceDomainProduct(
            categoryName = product.categoryName,
            productId = product.productId,
            productPictureUrls = product.productPictureUrls,
            title = product.title,
            price = product.price,
            amount = product.amount,
            unit = product.unit
        )
    }

    fun toProduct(product: ServiceDomainProduct): Product {
        return Product(
            categoryName = product.categoryName,
            productId = product.productId,
            productPictureUrls = product.productPictureUrls,
            title = product.title,
            price = product.price,
            amount = product.amount,
            unit = product.unit
        )
    }

    fun toProduct(product: ProductEntity): Product {
        return Product(
            categoryName = product.categoryName,
            productId = product.productId,
            productPictureUrls = product.productPictureUrls,
            title = product.title,
            price = product.price,
            amount = product.amount,
            unit = product.unit
        )
    }
}