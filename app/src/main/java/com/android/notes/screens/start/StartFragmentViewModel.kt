package com.android.notes.screens.start

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.android.notes.database.firebase.AppFirebaseRepository
import com.android.notes.database.room.AppRoomDatabase
import com.android.notes.database.room.AppRoomRepository
import com.android.notes.utilits.REPOSITORY
import com.android.notes.utilits.TYPE_FIREBASE
import com.android.notes.utilits.TYPE_ROOM

class StartFragmentViewModel(application: Application):AndroidViewModel(application) {

    private val mContext = application

    fun initDatabase(type:String,onSuccess:() -> Unit){
        when(type){
            TYPE_ROOM ->{
                val dao = AppRoomDatabase.getInstance(mContext).getAppRoomDao()
                REPOSITORY = AppRoomRepository(dao)
                onSuccess()
                }
            TYPE_FIREBASE -> {
                REPOSITORY = AppFirebaseRepository()
                REPOSITORY.connectToDatabase({onSuccess()},{Toast.makeText(mContext,it,Toast.LENGTH_SHORT).show()})
            }
        }
    }
}