package com.somanath.stackoverflowsearch.di

import com.somanath.stackoverflowsearch.api.StackOverflowApi
import com.somanath.stackoverflowsearch.data.repository.StackOverFlowRepository
import com.somanath.stackoverflowsearch.data.repository.StackOverflowRepositoryImpl
import com.somanath.stackoverflowsearch.data.source.StackOverFlowRemoteDataSource
import com.somanath.stackoverflowsearch.data.source.StackOverFlowRemoteDataSourceImpl
import com.somanath.stackoverflowsearch.domain.SearchQueryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.stackexchange.com/2.3/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create()) // Add RxJava adapter
            .build()
    }

    @Provides
    @Singleton
    fun provideStackOverflowApi(retrofit: Retrofit): StackOverflowApi {
        return retrofit.create(StackOverflowApi::class.java)
    }

    @Provides
    @Singleton
    fun provideStackOverflowRemoteDataSource(api: StackOverflowApi): StackOverFlowRemoteDataSource {
        return StackOverFlowRemoteDataSourceImpl(api)
    }

    @Provides
    @Singleton
    fun provideStackOverflowRepository(remoteDataSource: StackOverFlowRemoteDataSource): StackOverFlowRepository {
        return StackOverflowRepositoryImpl(remoteDataSource)
    }

    @Provides
    @Singleton
    fun provideSearchQueryUseCase(repository: StackOverFlowRepository): SearchQueryUseCase {
        return SearchQueryUseCase(repository)
    }
}
