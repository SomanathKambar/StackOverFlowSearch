package com.somanath.stackoverflowsearch.data.source

import com.somanath.stackoverflowsearch.api.StackOverflowApi
import com.somanath.stackoverflowsearch.data.ApiResponse
import com.somanath.stackoverflowsearch.data.model.StackOverFlowResponse
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject

class StackOverFlowRemoteDataSourceImpl @Inject constructor(private val api: StackOverflowApi) : StackOverFlowRemoteDataSource {

    private val compositeDisposable = CompositeDisposable()
    override fun searchQuery(query: String): Observable<ApiResponse<StackOverFlowResponse>> {
        val subject = BehaviorSubject.create<ApiResponse<StackOverFlowResponse>>()

        subject.onNext(ApiResponse.Loading)

        val dis = api.searchQuestions(query)
            .subscribeOn(Schedulers.io())
            .map { response ->
                ApiResponse.Success(response)
            }
            .subscribe(
                { apiResponse ->
                    subject.onNext(apiResponse)
                    subject.onComplete()
                },
                { throwable ->
                    subject.onNext(ApiResponse.Failure(throwable))
                    subject.onComplete()
                }
            )
        compositeDisposable.add(dis)
        return subject
    }
}

