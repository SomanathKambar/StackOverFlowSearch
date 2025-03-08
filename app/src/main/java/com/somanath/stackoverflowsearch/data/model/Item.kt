package com.somanath.stackoverflowsearch.data.model

data class Item(
    val question_id: Long,
    val title: String,
    val link: String,
    val creation_date: Long,
    val owner: Owner,
    val is_answered: Boolean,
    val view_count: Int,
    val answer_count: Int,
    val score: Int,
    val tags: List<String>
) {
    companion object {
        val default =  Item(
            question_id = 1L,
            title = "Test question",
            link = "https://stackoverflow.com/q/1",
            creation_date = 1672531200L, // Jan 1, 2023
            owner = Owner(
                user_id = 101,
                display_name = "Test User",
                profile_image = null?:"",
                reputation = 100
            ),
            is_answered = true,
            view_count = 100,
            answer_count = 2,
            score = 5,
            tags = listOf("android", "kotlin")
        )
    }
}