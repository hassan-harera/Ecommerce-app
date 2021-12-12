package com.harera.firebase.di

import com.harera.firebase.abstraction.*
import com.harera.firebase.impl.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModules {

    @Binds
    abstract fun bindAuthManager(manager: FirebaseAuthenticationService): AuthenticationService

    @Binds
    abstract fun bindCartRepository(repository: FirebaseCartService): CartService

    @Binds
    abstract fun bindSearchRepository(repository: FirebaseSearchService): SearchService

    @Binds
    abstract fun bindCategoryRepository(repository: FirebaseCategoryService): CategoryService

    @Binds
    abstract fun bindOfferRepository(repository: FirebaseOfferService): OfferService

    @Binds
    abstract fun bindWishListRepository(repository: FirebaseWishListService): WishService

    @Binds
    abstract fun bindUserRepository(repository: FirebaseUserService): UserService

    @Binds
    abstract fun bindProductRepository(repository: FirebaseProductService): ProductService

}