package com.example.adornado_midterm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    private val _groups: MutableLiveData<MutableList<Group>> = MutableLiveData()
    val groups: LiveData<MutableList<Group>>
        get() = _groups

    fun setAllGroups(g: MutableList<Group>) {
        g.shuffle()
        _groups.value = g
    }

    //to make sure the original group list is not affected by shuffle
    private val _copyGroups: MutableLiveData<MutableList<Group>> = MutableLiveData()
    val copyGroups: LiveData<MutableList<Group>>
        get() = _copyGroups

    fun setAllCopyGroups(g: MutableList<Group>){

        _copyGroups.value = g
    }
}