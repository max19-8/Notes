package com.android.notes.screens.note

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.android.notes.R
import com.android.notes.databinding.FragmentMainBinding
import com.android.notes.databinding.FragmentNoteBinding
import com.android.notes.models.AppNote
import com.android.notes.screens.main.MainAdapter
import com.android.notes.screens.main.MainFragmentViewModel
import com.android.notes.utilits.APP_ACTIVITY
import com.android.notes.utilits.REPOSITORY

class NoteFragment : Fragment() {

    private var _binding: FragmentNoteBinding? = null
    private val mBinding get() = _binding!!
    private lateinit var viewModel: NoteFragmentViewModel
    private lateinit  var mCurrentNote: AppNote

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentNoteBinding.inflate(layoutInflater,container,false)
        mCurrentNote = arguments?.getSerializable("note") as AppNote
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        initialization()
    }

    private fun initialization() {
        setHasOptionsMenu(true)
        viewModel = ViewModelProvider(this).get(NoteFragmentViewModel::class.java)
        mBinding.noteName.text = mCurrentNote.name
        mBinding.noteText.text = mCurrentNote.text
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.note_action_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.btn_delete -> {
                 viewModel.delete(mCurrentNote){
                     APP_ACTIVITY.navController.navigate(R.id.action_noteFragment_to_mainFragment)
                 }
            }
        }
        return super.onOptionsItemSelected(item)
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}