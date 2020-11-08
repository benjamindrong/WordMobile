package com.benjamindrong.wordmobile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope

/**
 * @param application keeping a reference to a context that has a shorter lifecycle than your
 * ViewModel can cause a memory leak.
 * Extending AndroidViewModel is the proper way to make your view model application context aware.
 */
class WordViewModel(application: Application) : AndroidViewModel(application) {

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
     * Launching a new corouting to insert the data in a non-blocking way
     */

}