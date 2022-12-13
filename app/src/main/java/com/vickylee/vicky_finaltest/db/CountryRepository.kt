package com.vickylee.vicky_finaltest.db

import android.app.Application
import android.provider.ContactsContract
import androidx.lifecycle.LiveData

class CountryRepository(application: Application) {

    private val db: AppDB?
    private val countryDAO = application.let { AppDB.getDB(application)!!.countryDAO() }
    val allFavCountries: LiveData<List<Country>> = countryDAO.getAllFavCountries()

    init {
        db = AppDB.getDB(application!!)
    }

    suspend fun insertFavCountry(newCountry: Country) {
        AppDB.databaseWriteExecutor.execute { countryDAO.insertFavCountry(newCountry) }
    }

    suspend fun removeFavCountry(deleteCountry: Country) {
        AppDB.databaseWriteExecutor.execute { countryDAO.removeFavCountry(deleteCountry) }
    }
}