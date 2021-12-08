package com.example.trabalhopdm.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.trabalhopdm.modelo.Product

@Database(entities = arrayOf(Product::class), version = 1)
abstract class Database: RoomDatabase() {
    abstract fun productDao(): ProductDao
}