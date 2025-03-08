package com.somanath.stackoverflowsearch.data.repository

import com.somanath.stackoverflowsearch.data.source.StackOverFlowRemoteDataSource
import javax.inject.Inject

class StackOverflowRepositoryImpl @Inject constructor(
    private val remoteDataSource: StackOverFlowRemoteDataSource
) : StackOverFlowRepository {

    override fun searchQuery(query: String) = remoteDataSource.searchQuery(query)
}
