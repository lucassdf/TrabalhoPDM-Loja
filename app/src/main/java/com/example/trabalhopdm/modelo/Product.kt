package com.example.trabalhopdm.modelo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Product")
data class Product (
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "idFB") var idFB: Int,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "desc") var desc: String,
    @ColumnInfo(name = "price") var price: Float,
    @ColumnInfo(name = "urlImg") var urlImg: String?,
    @ColumnInfo(name = "qtd") var qtd: Int?,
    @ColumnInfo(name = "purchased") var purchased: Boolean = false
)