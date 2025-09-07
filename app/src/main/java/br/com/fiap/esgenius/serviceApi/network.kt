package br.com.fiap.esgenius.serviceApi

import br.com.fiap.esgenius.model.Company
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header

private const val BASE_URL = "http://198.46.252.197:3000/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface CompanyApiService {
    @GET("array")
    suspend fun getCompanies(@Header("Authorization") token: String): List<Company>
}

object CompanyApi {
    val retrofitService: CompanyApiService by lazy {
        retrofit.create(CompanyApiService::class.java)
    }
}