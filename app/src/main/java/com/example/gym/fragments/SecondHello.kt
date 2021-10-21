package com.example.gym.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.gym.R
import kotlinx.android.synthetic.main.fragment_second_hello.*

private const val GENDER = "gender"
private const val TAG = "SecondHelloCall"
private const val NAME_SHARED_PREFERENCES = "shared_preferences"


class SecondHello : Fragment() {

    private var gender: Int? = null

    interface Callbacks {
        fun onSecondHello(gender: Int)
    }

    private var callbacks: Callbacks? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second_hello, container, false)
    }

    override fun onStart() {
        super.onStart()
        female_imageButton.setOnClickListener {
            gender = 0
            female_imageButton.setBackgroundResource(R.drawable.launch_button_on)
            male_imageButton.setBackgroundResource(R.drawable.launch_button_off)
        }
        male_imageButton.setOnClickListener {
            gender = 1
            female_imageButton.setBackgroundResource(R.drawable.launch_button_off)
            male_imageButton.setBackgroundResource(R.drawable.launch_button_on)
        }
        NextButton.setOnClickListener {
            if ( gender != null){
                callbacks?.onSecondHello(gender!!)
            }
            else {
                Toast.makeText(requireContext(), "Choose gender", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        if(gender != null) {
            val sharedPreferences = context?.getSharedPreferences(NAME_SHARED_PREFERENCES, Context.MODE_PRIVATE)
            val editor = sharedPreferences?.edit()
            editor?.putInt(GENDER, gender!!)
            editor?.apply()
        }
        Log.i(TAG, "gender = $gender")
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    companion object {
        fun newInstance(): SecondHello = SecondHello()
    }
}