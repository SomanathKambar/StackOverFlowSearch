package com.somanath.stackoverflowsearch.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.somanath.stackoverflowsearch.data.ApiResponse
import com.somanath.stackoverflowsearch.data.model.Item
import com.somanath.stackoverflowsearch.domain.SearchQueryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class StackOverflowViewModel @Inject constructor(
    private val useCase: SearchQueryUseCase
) : ViewModel() {

    private val _questions = MutableLiveData<List<Item>>()
    val questions: LiveData<List<Item>> get() = _questions

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val disposable = CompositeDisposable()

    fun searchQueries(query: String) {
        _loading.value = true
        disposable.clear()
        useCase.invoke(query)
            .subscribe(
                { apiResponse ->
                    when (apiResponse) {
                        is ApiResponse.Loading -> {
                            _loading.postValue( true)
                        }
                        is ApiResponse.Success -> {
                            _loading.postValue( false)
                            _questions.postValue(apiResponse.data.items)
                            disposable.clear()
                        }
                        is ApiResponse.Failure -> {
                            _loading.postValue(false)
                            _error.postValue( apiResponse.exception.message)
                            disposable.clear()
                        }
                    }
                },
                { throwable ->
                    _loading.postValue(false)
                    _error.postValue(throwable.message)
                }
            ).let { disposable.add(it) }

    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}
