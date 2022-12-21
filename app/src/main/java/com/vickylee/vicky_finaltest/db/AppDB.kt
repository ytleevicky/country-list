package com.vickylee.vicky_finaltest.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import java.util.concurrent.Executors

@Database(entities = [Country::class], version = 2, exportSchema = false)
abstract class AppDB : RoomDatabase() {

    abstract fun countryDAO(): CountryDAO

    // If your app runs in a single process, you should follow the singleton design pattern
    // so that is can be shared by entire apps
    companion object {
        @Volatile
        private var db: AppDB? = null
        private const val NUMBER_OF_THREADS = 4   // background task

        val databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS)

        fun getDB(context: Context): AppDB? {
            if (db == null) {

                // Acquire an instance of Database
                db = Room.databaseBuilder(
                    context.applicationContext,
                    AppDB::class.java, "com.vickylee_fav_countries_db"
                )
                    .fallbackToDestructiveMigration() // if db schema has changed, delete everything from db
                    .build()        // create the instance of database
            }
            return db
        }
    }

}