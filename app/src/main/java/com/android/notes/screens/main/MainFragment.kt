package com.android.notes.screens.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.android.notes.R
import com.android.notes.databinding.FragmentMainBinding
import com.android.notes.models.AppNote
import com.android.notes.utilits.APP_ACTIVITY
import com.android.notes.utilits.AppReference

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val mBinding get() = _binding!!
    private lateinit var viewModel: MainFragmentViewModel
    private lateinit var recyclerView:RecyclerView
    private lateinit var mAdapter:MainAdapter
    private lateinit var mObserverList: Observer<List<AppNote>>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(layoutInflater,container,false)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        initialization()
    }

    private fun initialization() {
        setHasOptionsMenu(true)
        mAdapter = MainAdapter()
        recyclerView = mBinding.recyclerView
        recyclerView.adapter = mAdapter
        mObserverList = Observer {
            val list = it.asReversed()
            mAdapter.setList(list)
        }
        viewModel = ViewModelProvider(this).get(MainFragmentViewModel::class.java)
        viewModel.allNotes.observe(this,mObserverList)
       mBinding.btnAddNote.setOnClickListener {
            APP_ACTIVITY.navController.navigate(R.id.action_mainFragment_to_addNewNoteFragment)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.exit_action_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.btn_exit -> {
                viewModel.signOut()
                AppReference.setInitUser(false)
                    APP_ACTIVITY.navController.navigate(R.id.action_mainFragment_to_startFragment)

            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        viewModel.allNotes.removeObserver(mObserverList)
        recyclerView.adapter = null
    }
    companion object{
        fun click(note: AppNote){
            val bundle = Bundle()
            bundle.putSerializable("note",note)
            APP_ACTIVITY.navController.navigate(R.id.action_mainFragment_to_noteFragment,bundle)
        }
    }
}