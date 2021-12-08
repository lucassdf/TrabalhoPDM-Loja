package com.example.trabalhopdm.services

import com.example.trabalhopdm.modelo.Product
import retrofit2.Call
import retrofit2.http.*

interface ProductAPI {

    @GET("/users/{name}/history")
    fun getAll(@Path("name") name : String?): Call<List<Product>>

    @POST("/users/{uid}/history.json")
    fun insert(@Path("uid") name : String?, @Body purchase: Product): Call<Void>

}