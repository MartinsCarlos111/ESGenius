package br.com.fiap.esgenius.repository

import br.com.fiap.esgenius.model.Company
import br.com.fiap.esgenius.serviceApi.CompanyApi

class CompanyRepository {
    private val apiService = CompanyApi.retrofitService
    private val authToken = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMn0.KMUFsIDTnFmyG3nMiGM6H9FNFUROf3wh7SmqJp-QV30"

    suspend fun getCompanies(): List<Company> {
        return apiService.getCompanies(authToken)
    }
}