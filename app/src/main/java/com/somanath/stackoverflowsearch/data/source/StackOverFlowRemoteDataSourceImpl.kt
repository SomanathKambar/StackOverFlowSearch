package com.somanath.stackoverflowsearch.data.source

import com.somanath.stackoverflowsearch.api.StackOverflowApi
import com.somanath.stackoverflowsearch.data.ApiResponse
import com.somanath.stackoverflowsearch.data.model.StackOverFlowResponse
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class StackOverFlowRemoteDataSourceImpl @Inject constructor(private val api: StackOverflowApi) : StackOverFlowRemoteDataSource {


    override fun searchQuery(query: String) : Observable<ApiResponse<StackOverFlowResponse>> {
        val compositeDisposable = CompositeDisposable()
        return Observable.create { emitter ->
            emitter.onNext(ApiResponse.Loading)
            val disposable =   api.searchQuestions(query)
                .subscribeOn(Schedulers.io())
                .subscribe({ response ->
                    emitter.onNext(ApiResponse.Success(response))
                    emitter.onComplete()
                    compositeDisposable.clear()
                }, { error ->
                    emitter.onNext(ApiResponse.Failure(error))
                    emitter.onComplete()
                    compositeDisposable.clear()
                })
            compositeDisposable.add(disposable)
        }
    }
}