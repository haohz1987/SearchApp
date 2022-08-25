package com.haohz.search.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SearchDto (
    @SerializedName("title")
    var title: String? = "",
    @SerializedName("company")
    val company: MutableList<CompanyDto>? = null,
    @SerializedName("description")
    var description: String? = ""
):Serializable
data class CompanyDto(
    @SerializedName("name")
    var name: String? = "",
    @SerializedName("logoUrl")
    var logoUrl: String? = ""
):Serializable


