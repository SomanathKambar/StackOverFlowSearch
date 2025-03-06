package com.somanath.stackoverflowsearch.data.model

data class StackOverFlowResponse(
    val has_more: Boolean,
    val items: List<Item>,
    val quota_max: Int,
    val quota_remaining: Int
)