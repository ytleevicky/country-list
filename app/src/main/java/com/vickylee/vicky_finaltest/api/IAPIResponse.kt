package com.vickylee.vicky_finaltest.api

import com.vickylee.vicky_finaltest.models.Country
import com.vickylee.vicky_finaltest.models.CountryJSONResponse
import retrofit2.http.GET

interface IAPIResponse {

    @GET("all")
    suspend fun getAllCountries(): List<CountryJSONResponse>

}