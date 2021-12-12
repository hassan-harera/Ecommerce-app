package com.harera.repository.di

import com.harera.repository.abstraction.*
import com.harera.repository.impl.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModules {

    @Binds
    abstract fun bindAuthManager(repository: UserRepositoryImpl): UserRepository

    @Binds
    abstract fun bindCartRepository(repository: CartRepositoryImpl): CartRepository

    @Binds
    abstract fun bindSearchRepository(repository: SearchRepositoryImpl): SearchRepository

    @Binds
    abstract fun bindCategoryRepository(repository: CategoryRepositoryImpl): CategoryRepository

    @Binds
    abstract fun bindOfferRepository(repository: OfferRepositoryImpl): OfferRepository

    @Binds
    abstract fun bindWishListRepository(repository: WishListRepositoryImpl): WishListRepository

    @Binds
    abstract fun bindProductRepository(repository: ProductRepositoryImpl): ProductRepository

}