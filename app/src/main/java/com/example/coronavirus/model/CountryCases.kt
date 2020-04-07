package com.example.coronavirus.model

import com.google.gson.annotations.SerializedName

class CountryCases(
    @SerializedName("id")
    var id: String?,

    @SerializedName("country_name")
    var countryName: String?,

    @SerializedName("cases")
    var cases: String?,

    @SerializedName("total_cases")
    var totalCases: String?,

    @SerializedName("active_cases")
    var active: String?,

    @SerializedName("total_recovered")
    var recovered: String?,

    @SerializedName("serious_critical")
    var infected: String?,

    @SerializedName("deaths")
    var death: String?,

    @SerializedName("total_deaths")
    var totalDeaths: String?
)