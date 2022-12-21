package com.vickylee.vicky_finaltest.models


class Name(val common: String) {

}

class CapitalInfo(val latlng: List<String>?) {

}

class Flags(val png: String, val svg: String) {

}

class Country(
    val name: String,
    val capital: List<String>?,
    val population: Int,
    val capitalInfo: List<String>?,
    val latlng: List<String>,
    val flagImg: String,
    val flag: String,
) : java.io.Serializable {

    override fun toString(): String {
        return "Country(name='$name', capital=$capital, population=$population, capitalInfo=$capitalInfo, latlng=$latlng, flagImg='$flagImg')"
    }
}

class CountryJSONResponse(
    val name: Name,
    val capital: List<String>?,
    val population: Int,
    val capitalInfo: CapitalInfo,
    val latlng: List<String>,
    val flags: Flags,
    val flag: String,
) {

}