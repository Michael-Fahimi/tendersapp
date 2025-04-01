package com.example.tendersapp.presentation.tenders


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tendersapp.domain.model.Result
import com.example.tendersapp.domain.model.TenderDomain
import com.example.tendersapp.domain.usecase.GetTendersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TendersViewModel @Inject constructor(
    private val getTendersUseCase: GetTendersUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<TendersState>(TendersState.Idle)
    val state: StateFlow<TendersState> = _state

    init {
        loadTenders()
    }

    fun loadTenders(page: Int = 1) {
        getTendersUseCase(page).onEach { result ->
            when (result) {
                is Result.Loading -> {
                    _state.value = TendersState.Loading
                }
                is Result.Success -> {
                    _state.value = TendersState.Success(result.data ?: emptyList())
                }
                is Result.Error -> {
                    _state.value = TendersState.Error(result.message ?: "An unexpected error occurred")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun refreshTenders() {
        loadTenders(1)
    }

    fun loadNextPage() {
        val currentState = _state.value
        if (currentState is TendersState.Success) {
            // Logic to determine next page
            loadTenders(currentState.currentPage + 1)
        }
    }
}
