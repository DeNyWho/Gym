package com.example.gym.fragments

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gym.R
import kotlinx.android.synthetic.main.fragment_first_hello.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val TAG = "FirstHelloCall"

/**
 * A simple [Fragment] subclass.
 * Use the [FirstHello.newInstance] factory method to
 * create an instance of this fragment.
 */
class FirstHello : Fragment() {
    interface Callbacks {
        fun onFirstHello()
    }

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var callbacks: Callbacks? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first_hello, container, false)
    }

    override fun onStart() {
        super.onStart()
        imageButton1.setOnClickListener {
            callbacks?.onFirstHello()
            Log.i(TAG, "callback")
        }
        imageButton2.setOnClickListener {
            callbacks?.onFirstHello()
            Log.i(TAG, "callback")
        }
        imageButton3.setOnClickListener {
            callbacks?.onFirstHello()
            Log.i(TAG, "callback")
        }
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    companion object {
        fun newInstance(): FirstHello = FirstHello()
    }
}