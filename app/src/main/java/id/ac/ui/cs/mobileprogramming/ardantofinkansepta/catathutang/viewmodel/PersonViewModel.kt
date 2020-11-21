package id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.entity.Person
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.repository.PersonRepository
import kotlinx.coroutines.launch

class PersonViewModel(private val repository: PersonRepository) : ViewModel() {

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allPerson: LiveData<List<Person>> = repository.allPerson.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */

    fun insert(person: Person) = viewModelScope.launch {
        repository.insert(person)
    }

    fun delete(person: Person) = viewModelScope.launch {
        repository.delete(person)
    }

}