package com.somanath.stackoverflowsearch.data.source

import com.somanath.stackoverflowsearch.data.ApiResponse
import com.somanath.stackoverflowsearch.data.model.StackOverFlowResponse
import io.reactivex.rxjava3.core.Observable

interface StackOverFlowRemoteDataSource {
    fun searchQuery(query:String): Observable<ApiResponse<StackOverFlowResponse>>
}