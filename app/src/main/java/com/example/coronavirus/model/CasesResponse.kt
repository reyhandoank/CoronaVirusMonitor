package com.example.coronavirus.model

import com.google.gson.annotations.SerializedName

class CasesResponse(
    @SerializedName("countries_stat")
    var allCountries: ArrayList<CountryCases>,

    @SerializedName("latest_stat_by_country")
    var latest: ArrayList<CountryCases>
)