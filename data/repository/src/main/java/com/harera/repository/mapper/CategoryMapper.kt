package com.harera.repository.mapper

import com.harera.local.model.CategoryEntity
import com.harera.repository.domain.Category


object CategoryMapper {

    fun toCategory(categoryEntity: CategoryEntity): Category {
        return Category(
            categoryName = categoryEntity.categoryName,
            categoryImagerUrl = categoryEntity.categoryImagerUrl
        )
    }
}