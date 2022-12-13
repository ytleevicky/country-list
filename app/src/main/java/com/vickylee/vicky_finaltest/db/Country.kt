package com.vickylee.vicky_finaltest.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_fav_countries")
class Country(
    val countryName: String,
    val capital: String,
    val population: Int,
) {

    @PrimaryKey(autoGenerate = true)
    var id = 0

}