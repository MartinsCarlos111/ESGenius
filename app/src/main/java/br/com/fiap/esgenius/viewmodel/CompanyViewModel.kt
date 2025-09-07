package br.com.fiap.esgenius.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.esgenius.model.Company
import br.com.fiap.esgenius.repository.CompanyRepository
import kotlinx.coroutines.launch

sealed interface CompanyUiState {
    data class Success(val companies: List<Company>) : CompanyUiState
    object Error : CompanyUiState
    object Loading : CompanyUiState
}

class CompanyViewModel : ViewModel() {

    private val companyRepository = CompanyRepository()

    var companyUiState: CompanyUiState by mutableStateOf(CompanyUiState.Loading)
        private set

    init {
        fetchCompanies()
    }

    fun fetchCompanies() {
        viewModelScope.launch {
            companyUiState = CompanyUiState.Loading
            try {
                val companies = companyRepository.getCompanies()
                companyUiState = CompanyUiState.Success(companies)
            } catch (e: Exception) {
                companyUiState = CompanyUiState.Error
            }
        }
    }
}