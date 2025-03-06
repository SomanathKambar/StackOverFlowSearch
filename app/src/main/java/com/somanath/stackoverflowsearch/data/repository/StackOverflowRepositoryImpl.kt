package com.somanath.stackoverflowsearch.data.repository

import com.somanath.stackoverflowsearch.data.ApiResponse
import com.somanath.stackoverflowsearch.data.model.StackOverFlowResponse
import com.somanath.stackoverflowsearch.data.source.StackOverFlowRemoteDataSource
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class StackOverflowRepositoryImpl @Inject constructor(
    private val remoteDataSource: StackOverFlowRemoteDataSource
) : StackOverFlowRepository {

    override fun searchQuery(query: String): Observable<ApiResponse<StackOverFlowResponse>> = remoteDataSource.searchQuery(query)
}
