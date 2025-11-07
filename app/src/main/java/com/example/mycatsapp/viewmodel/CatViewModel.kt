package com.example.mycatsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycatsapp.data.CatApiResponse
import com.example.mycatsapp.repository.CatRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * VIEWMODEL (Controller/Logic Layer)
 * 
 * Responsável por:
 * - Gerenciar estados da UI
 * - Executar lógica de negócio
 * - Comunicar com Repository
 * - Sobrevive a mudanças de configuração (rotação de tela)
 */
class CatViewModel : ViewModel() {
    
    // Repository (acesso aos dados)
    private val repository = CatRepository()
    
    // Estados da UI (StateFlow é observável)
    private val _catData = MutableStateFlow<CatApiResponse?>(null)
    val catData: StateFlow<CatApiResponse?> = _catData.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()
    
    /**
     * Busca dados da API
     */
    fun fetchCatData() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            
            try {
                val responseList = repository.searchCatImages(limit = 1)
                if (responseList.isNotEmpty()) {
                    _catData.value = responseList[0]
                } else {
                    _errorMessage.value = "Nenhuma imagem encontrada"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Erro: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    /**
     * Limpa os dados
     */
    fun clearData() {
        _catData.value = null
        _errorMessage.value = null
    }
}

