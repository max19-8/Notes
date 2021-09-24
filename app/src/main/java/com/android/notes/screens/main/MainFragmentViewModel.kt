package com.android.notes.screens.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.android.notes.utilits.REPOSITORY

class MainFragmentViewModel(application: Application):AndroidViewModel(application) {
    val allNotes = REPOSITORY.allNotes

    fun signOut(){
        REPOSITORY.signOut()
    }

}