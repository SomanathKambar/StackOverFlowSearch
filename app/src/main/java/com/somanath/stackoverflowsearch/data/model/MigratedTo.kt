package com.somanath.stackoverflowsearch.data.model

data class MigratedTo(
    val on_date: Int,
    val other_site: OtherSite,
    val question_id: Int
)