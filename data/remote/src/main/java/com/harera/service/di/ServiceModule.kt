package com.harera.service.di

import com.harera.service.*
import com.harera.service.impl.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {

    @Binds
    abstract fun bindAuthManager(manager: UserServiceImpl): UserService

    @Binds
    abstract fun bindCartRepository(repository: CartServiceImpl): CartService

    @Binds
    abstract fun bindSearchRepository(repository: SearchServiceImpl): SearchService

    @Binds
    abstract fun bindCategoryRepository(repository: CategoryServiceImpl): CategoryService

    @Binds
    abstract fun bindOfferRepository(repository: OfferServiceImpl): OfferService

    @Binds
    abstract fun bindWishListRepository(repository: WishServiceImpl): WishService

    @Binds
    abstract fun bindProductRepository(repository: ProductServiceImpl): ProductService
}