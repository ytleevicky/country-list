package com.vickylee.vicky_finaltest.models


class Name(val common: String) {

}

class CountryJSONResponse(val name: Name, val capital: List<String>?, val population: Int) {

}

class Country(val name: String, val capital: List<String>?, val population: Int) : java.io.Serializable {
    override fun toString(): String {
        return "Country(name='$name', capital=$capital, population=$population)"
    }
}