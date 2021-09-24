package com.android.notes.screens.note

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.android.notes.models.AppNote
import com.android.notes.utilits.REPOSITORY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteFragmentViewModel(application: Application): AndroidViewModel(application) {

    fun delete(note: AppNote, onSuccess:() -> Unit) =
            viewModelScope.launch(Dispatchers.IO){
                REPOSITORY.delete(note){
                    onSuccess()
                }
            }



}