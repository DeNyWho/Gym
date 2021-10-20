package com.example.gym.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gym.R
import kotlinx.android.synthetic.main.fragment_fourth_hello.*

private const val EXERCISE = "exercise"
private const val NAME_SHARED_PREFERENCES = "shared_preferences"

class FourthHello : Fragment() {

    interface Callbacks {
        fun onFourthHello()
    }

    private var callbacks: Callbacks? = null
    private var exercise: Int? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fourth_hello, container, false)
    }

    override fun onStart() {
        super.onStart()
        imageButton1.setOnClickListener {
            exercise = 0
            imageButton1.setBackgroundResource(R.drawable.launch_button_on)
            imageButton2.setBackgroundResource(R.drawable.launch_button_off)
            imageButton3.setBackgroundResource(R.drawable.launch_button_off)
        }
        imageButton2.setOnClickListener {
            exercise = 1
            imageButton1.setBackgroundResource(R.drawable.launch_button_off)
            imageButton2.setBackgroundResource(R.drawable.launch_button_on)
            imageButton3.setBackgroundResource(R.drawable.launch_button_off)
        }
        imageButton3.setOnClickListener {
            exercise = 2
            imageButton1.setBackgroundResource(R.drawable.launch_button_off)
            imageButton2.setBackgroundResource(R.drawable.launch_button_off)
            imageButton3.setBackgroundResource(R.drawable.launch_button_on)
        }
        imageButton4.setOnClickListener {
            callbacks?.onFourthHello()
        }
    }

    override fun onStop() {
        super.onStop()
        if (exercise != null){
            val sharedPreferences = context?.getSharedPreferences(NAME_SHARED_PREFERENCES, Context.MODE_PRIVATE)
            val editor = sharedPreferences?.edit()
            editor?.putInt(EXERCISE, exercise!!)
            editor?.apply()
        }
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    companion object {
        fun newInstance(): FourthHello = FourthHello()
    }
}