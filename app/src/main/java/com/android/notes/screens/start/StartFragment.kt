package com.android.notes.screens.start

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.android.notes.R
import com.android.notes.databinding.FragmentStartBinding
import com.android.notes.utilits.*
import kotlinx.android.synthetic.main.fragment_start.*

class StartFragment : Fragment() {

    private var _binding : FragmentStartBinding? = null
     val mBinding get() = _binding!!
    private lateinit var mViewModel:StartFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentStartBinding.inflate(layoutInflater,container,false)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        mViewModel = ViewModelProvider(this).get(StartFragmentViewModel::class.java)
        if (AppReference.getInitUser()){
            mViewModel.initDatabase(AppReference.getTypeDB()){
                APP_ACTIVITY.navController.navigate(R.id.action_startFragment_to_mainFragment)
            }
        }else{
            initialization()
        }

    }

    private fun initialization(){
        mBinding.btnRoom.setOnClickListener {
            mViewModel.initDatabase(TYPE_ROOM){
                AppReference.setInitUser(true)
                AppReference.setTypeDB(TYPE_ROOM)
                APP_ACTIVITY.navController.navigate(R.id.action_startFragment_to_mainFragment)
            }
        }
        mBinding.btnFirebase.setOnClickListener {
            mBinding.startEmail.visibility = View.VISIBLE
            mBinding.startPassword.visibility = View.VISIBLE
            mBinding.btnLogin.visibility = View.VISIBLE

            mBinding.btnLogin.setOnClickListener {
                val inputEmail = mBinding.startEmail.text.toString()
                val inputPassword = mBinding.startPassword.text.toString()

                if (inputEmail.isNotEmpty() && inputPassword.isNotEmpty()){
                    EMAIL = inputEmail
                    PASSWORD = inputPassword
                    mViewModel.initDatabase(TYPE_FIREBASE){
                        AppReference.setInitUser(true)
                        AppReference.setTypeDB(TYPE_FIREBASE)
                      APP_ACTIVITY.navController.navigate(R.id.action_startFragment_to_mainFragment)
                    }
                }else{
                    Toast.makeText(context,"Введите умейл и пароль",Toast.LENGTH_SHORT).show()
                }

            }

        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}