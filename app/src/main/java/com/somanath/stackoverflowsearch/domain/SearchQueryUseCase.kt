package com.somanath.stackoverflowsearch.domain

import com.somanath.stackoverflowsearch.data.repository.StackOverFlowRepository
import javax.inject.Inject

class SearchQueryUseCase @Inject constructor(private val repository: StackOverFlowRepository) {
     operator fun invoke(query: String)  = repository.searchQuery(query)
}