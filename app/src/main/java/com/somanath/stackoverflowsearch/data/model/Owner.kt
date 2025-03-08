package com.somanath.stackoverflowsearch.data.model

data class Owner(
    val user_id: Long,
    val display_name: String,
    val profile_image: String?,
    val reputation: Int
)