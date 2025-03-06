package com.somanath.stackoverflowsearch.api

import com.somanath.stackoverflowsearch.data.model.StackOverFlowResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface StackOverflowApi {
    @GET("search")
    fun searchQuestions(
        @Query("intitle") query: String,
        @Query("site") site: String = "stackoverflow"
    ): Single<StackOverFlowResponse>
}
