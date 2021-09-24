package com.android.notes.screens.add_new_note

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.lifecycle.ViewModelProvider
import com.android.notes.R
import com.android.notes.databinding.FragmentAddNewNoteBinding
import com.android.notes.models.AppNote
import com.android.notes.utilits.APP_ACTIVITY


class AddNewNoteFragment : Fragment() {

    private  var _binding: FragmentAddNewNoteBinding? = null
    private val mBinding get() = _binding!!
    private lateinit  var viewModel:AddNewNoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddNewNoteBinding.inflate(layoutInflater,container,false)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        initialization()
    }

    private fun initialization() {
        viewModel = ViewModelProvider(this).get(AddNewNoteViewModel::class.java)
       mBinding.btnAddNewNote.setOnClickListener {
        val name =  mBinding.inputNameNote.text.toString()
        val text =  mBinding.inputTextNote.text.toString()
            if (name.isEmpty()){
                Toast.makeText(context,"Пожалуйста введите имя заметки",LENGTH_SHORT).show()
            }else{
               viewModel.insert(AppNote(name = name, text = text)){
                   APP_ACTIVITY.navController.navigate(R.id.action_addNewNoteFragment_to_mainFragment)
                  }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}