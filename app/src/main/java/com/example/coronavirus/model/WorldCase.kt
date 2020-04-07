package com.example.coronavirus.model

import com.google.gson.annotations.SerializedName

class WorldCase (
    @SerializedName("total_cases")
    var totalCases: String?,

    @SerializedName("total_recovered")
    var totalRecovered: String?,

    @SerializedName("total_deaths")
    var totalDeaths: String?
)