package com.android.notes.database.firebase

import android.widget.Toast
import androidx.lifecycle.LiveData
import com.android.notes.MainActivity
import com.android.notes.database.DatabaseRepository
import com.android.notes.models.AppNote
import com.android.notes.screens.start.StartFragment
import com.android.notes.utilits.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AppFirebaseRepository: DatabaseRepository {

    init {

        AUTH = FirebaseAuth.getInstance()
    }

    override val allNotes: LiveData<List<AppNote>> = AllNotesLiveData()


    override suspend fun insert(note: AppNote, onSuccess: () -> Unit) {
        val idNote = REF_DATABASE.push().key.toString()
        val mapNote = hashMapOf<String,Any>()
        mapNote[ID_FIREBASE] = idNote
        mapNote[NAME] = note.name
        mapNote[TEXT] = note.text

        REF_DATABASE.child(idNote)
                .updateChildren(mapNote)
                .addOnSuccessListener { onSuccess() }
    }

    override suspend fun delete(note: AppNote, onSuccess: () -> Unit) {
       REF_DATABASE.child(note.idFirebase).removeValue()
               .addOnSuccessListener { onSuccess() }
               .addOnFailureListener { Toast.makeText(MainActivity(),"Пожалуйста введите имя заметки", Toast.LENGTH_SHORT).show()  }
    }

    override fun connectToDatabase(onSuccess: () -> Unit, onFail: (String) -> Unit) {


        if (AppReference.getInitUser()){
            initRefs()
            onSuccess()
        }else{
        AUTH.signInWithEmailAndPassword(EMAIL, PASSWORD)
                .addOnSuccessListener {
                    initRefs()
                    onSuccess() }
                .addOnFailureListener {
                    AUTH.createUserWithEmailAndPassword(EMAIL, PASSWORD)
                            .addOnSuccessListener {
                                initRefs()
                                onSuccess() }
                            .addOnFailureListener { onFail(it.message.toString()) }
                }
                }

    }

    private fun initRefs() {
        CURRENT_ID = AUTH.currentUser?.uid.toString()
        REF_DATABASE = FirebaseDatabase.getInstance().reference
                .child(CURRENT_ID)
    }

    override fun signOut() {
        AUTH.signOut()
    }
}