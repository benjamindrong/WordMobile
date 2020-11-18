package com.benjamindrong.wordmobile

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @param application keeping a reference to a context that has a shorter lifecycle than your
 * ViewModel can cause a memory leak.
 * Extending AndroidViewModel is the proper way to make your view model application context aware.
 */
class WordViewModel(application: Application, handle: SavedStateHandle) : AndroidViewModel(application) {

    private val repository: WordRepository
//     Using LiveData and caching what getAlphabetizedWords returns has several benefits:
//         - We can put an observer on the data (instead of polling for changes) and only update the
//           the UI when the data actually changes.
//         - Repository is completely separated from the UI through the ViewModel.
    val allWords: LiveData<List<Word>>

    init {
        val wordsDao = WordRoomDatabase.getDatabase(application, viewModelScope).wordDao()
        repository = WordRepository(wordsDao)
        allWords = repository.allWords
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(word: Word) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(word)
    }

    /**
     * Factory for constructing WordViewModel with parameter
     */
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(WordViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return WordViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}