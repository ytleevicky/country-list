package com.vickylee.vicky_finaltest.models


class Name(val common: String) {

}

class CapitalInfo(val latlng: List<String>?) {

}

class CountryJSONResponse(val name: Name, val capital: List<String>?, val population: Int, val capitalInfo: CapitalInfo, val latlng: List<String>) {

}

class Country(val name: String, val capital: List<String>?, val population: Int, val capitalInfo: List<String>?, val latlng: List<String>) : java.io.Serializable {
    override fun toString(): String {
        return "Country(name='$name', capital=$capital, population=$population, capitalInfo=$capitalInfo, latlng=$latlng)"
    }
}