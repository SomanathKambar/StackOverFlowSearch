package com.somanath.stackoverflowsearch.data.model

data class OtherSite(
    val api_site_parameter: String,
    val audience: String,
    val closed_beta_date: Int,
    val favicon_url: String,
    val high_resolution_icon_url: String,
    val icon_url: String,
    val launch_date: Int,
    val logo_url: String,
    val markdown_extensions: List<String>,
    val name: String,
    val open_beta_date: Int,
    val related_sites: List<RelatedSite>,
    val site_state: String,
    val site_type: String,
    val site_url: String,
    val styling: Styling
)