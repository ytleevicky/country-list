package com.vickylee.vicky_finaltest.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CountryDAO {

    @Insert
    fun insertFavCountry(country: Country)

    @Delete
    fun removeFavCountry(country: Country)

    @Query("SELECT * FROM table_fav_countries")
    fun getAllFavCountries(): LiveData<List<Country>>
}