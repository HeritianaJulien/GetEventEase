package com.example.geteventease.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.geteventease.data.EventRepository
import com.example.geteventease.data.local.EventEntity
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: EventRepository) : ViewModel() {
    init {
        viewModelScope.launch {
            repository.initializeFakeData()
        }
    }

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    val publishedEvents: StateFlow<List<EventEntity>> = combine(
        repository.publishedEvents,
        _searchQuery
    ) { events, query ->
        if (query.isBlank()) {
            events
        } else {
            events.filter { 
                it.title.contains(query, ignoreCase = true) || 
                it.category.contains(query, ignoreCase = true) 
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }
}
