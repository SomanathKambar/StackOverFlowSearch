package com.somanath.stackoverflowsearch

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.somanath.stackoverflowsearch.data.ApiResponse
import com.somanath.stackoverflowsearch.data.model.Item
import com.somanath.stackoverflowsearch.data.model.StackOverFlowResponse
import com.somanath.stackoverflowsearch.data.repository.StackOverFlowRepository
import com.somanath.stackoverflowsearch.domain.SearchQueryUseCase
import com.somanath.stackoverflowsearch.viewmodel.StackOverflowViewModel
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.TestScheduler
import io.reactivex.rxjava3.subjects.PublishSubject
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

inline fun <reified T> LiveData<T>.captureValues(): List<T?> {
    val mockObserver = Mockito.mock(Observer::class.java) as Observer<T?>
    val captor = ArgumentCaptor.forClass(Any::class.java) as ArgumentCaptor<T?>
    val list = mutableListOf<T?>()

    Mockito.doAnswer { invocation ->
        val value = captor.value
        list.add(value)
        null
    }.whenever(mockObserver).onChanged(captor.capture())

    this.observeForever(mockObserver)

    return list
}




class StackOverflowViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: StackOverflowViewModel


    var useCase: SearchQueryUseCase = mock()

    var repository: StackOverFlowRepository = mock()


    @Mock
    lateinit var loadingObserver: Observer<Boolean>

    @Mock
    lateinit var errorObserver: Observer<String>


     var questionsObserver: Observer<List<Item>> = mock()

    private val testScheduler = TestScheduler()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        useCase = SearchQueryUseCase(repository)
        viewModel = StackOverflowViewModel(useCase)

        viewModel.loading.observeForever(loadingObserver)
        viewModel.error.observeForever(errorObserver)
        viewModel.questions.observeForever(questionsObserver)

        RxJavaPlugins.setIoSchedulerHandler { testScheduler }
        RxJavaPlugins.setComputationSchedulerHandler { testScheduler }
        RxJavaPlugins.setNewThreadSchedulerHandler { testScheduler }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { testScheduler }
    }

    @After
    fun tearDown() {
        RxJavaPlugins.reset()
        RxAndroidPlugins.reset()
        viewModel.loading.removeObserver(loadingObserver)
        viewModel.error.removeObserver(errorObserver)
        viewModel.questions.removeObserver(questionsObserver)
    }

    @Test
    fun testLoadingState() {
        val query = "test query"

        val items = listOf(Item.default)
        val successResponse = ApiResponse.Success(StackOverFlowResponse(items))
        val successSubject = PublishSubject.create<ApiResponse<StackOverFlowResponse>>()
        successSubject.onNext(successResponse)


        Mockito.`when`(useCase.invoke(query)).thenReturn(successSubject)
        Mockito.`when`(repository.searchQuery(query)).thenReturn(successSubject)
        viewModel.searchQueries(query)
        viewModel.questions.captureValues()

        testScheduler.triggerActions()
        verify(loadingObserver).onChanged(true)
    }

    @Test
    fun testSuccessState() {
        val query = "test query"
        val items = listOf(Item.default)
        val successResponse = ApiResponse.Success(StackOverFlowResponse(items))

        val successSubject = PublishSubject.create<ApiResponse<StackOverFlowResponse>>()
        successSubject.onNext(successResponse)

        Mockito.`when`(useCase.invoke(query)).thenReturn(successSubject)
        Mockito.`when`(repository.searchQuery(query)).thenReturn(successSubject)

        val capturedValues = viewModel.questions.captureValues().firstOrNull()
        viewModel.searchQueries(query)
        testScheduler.triggerActions()

        verify(loadingObserver).onChanged(true)

        Assert.assertTrue(capturedValues.isNullOrEmpty())
        if (capturedValues != null) {
            Assert.assertEquals(items, capturedValues.firstOrNull())
        }
        verify(questionsObserver).onChanged(items)
    }





    @Test
    fun testFailureState() {
        val query = "test query"
        val errorMessage = "Error message"
        val failureResponse = ApiResponse.Failure(Throwable(errorMessage))
        val failureSubject = PublishSubject.create<ApiResponse<StackOverFlowResponse>>()
        failureSubject.onNext(failureResponse)

        Mockito.`when`(useCase.invoke(query)).thenReturn(failureSubject)
        Mockito.`when`(repository.searchQuery(query)).thenReturn(failureSubject)

        viewModel.searchQueries(query)
        testScheduler.triggerActions()

        verify(loadingObserver).onChanged(true)
        verify(errorObserver).onChanged(errorMessage)
    }

    @Test
    fun testErrorState() {
        val query = "test query"
        val errorMessage = "Network error"
        val throwable = Throwable(errorMessage)

        Mockito.`when`(useCase.invoke(query)).thenReturn(io.reactivex.rxjava3.core.Observable.error(throwable))

        viewModel.searchQueries(query)
        testScheduler.triggerActions()

        verify(loadingObserver).onChanged(true)
        verify(errorObserver).onChanged(errorMessage)
    }

    @Test
    fun testLiveDataCapturesValues() {
        val liveData = MutableLiveData<String>()
        val capturedValues = liveData.captureValues()

        liveData.value = "Test Value"
        liveData.value = "Another Value"

        Assert.assertEquals(listOf("Test Value", "Another Value"), capturedValues)
    }

}
