package com.example.gym.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.gym.R
import kotlinx.android.synthetic.main.fragment_third_hello.*

private const val GENDER = "gender"
private const val KEY_GENDER = "key_gender"
private const val TAG = "ThirdHelloCall"
private const val TRAIN = "train"
private const val NAME_SHARED_PREFERENCES = "shared_preferences"

class ThirdHello : Fragment() {

    interface Callbacks {
        fun onThirdHello()
    }

    private var gender: Int? = null
    private var callbacks: Callbacks? = null
    private var train: Int? = null

    // TODO: Rename and change types of parameters

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gender = arguments?.getInt(KEY_GENDER) as Int
        Log.i(TAG, "gender = $gender")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = layoutInflater.inflate(R.layout.fragment_third_hello, container, false)
        val humanImageView = view.findViewById<ImageView>(R.id.human_imageView)

        if (gender != -1) {
            when (gender) {
                0 -> humanImageView.setBackgroundResource(R.drawable.woman)
                1 -> humanImageView.setBackgroundResource(R.drawable.man)
            }
        }
        return view
    }

    override fun onStart() {
        super.onStart()
        hands_imageButton.setOnClickListener {
            train = 1
            callbacks?.onThirdHello()
        }
        spine_imageButton.setOnClickListener {
            train = 2
            callbacks?.onThirdHello()
        }
        torso_imageButton.setOnClickListener {
            train = 3
            callbacks?.onThirdHello()
        }
        legs_imageButton.setOnClickListener {
            train = 4
            callbacks?.onThirdHello()
        }
    }

    override fun onStop() {
        super.onStop()
        if(gender != null) {
            val sharedPreferences = context?.getSharedPreferences(NAME_SHARED_PREFERENCES, Context.MODE_PRIVATE)
            val editor = sharedPreferences?.edit()
            editor?.putInt(TRAIN, train!!)
            editor?.apply()
        }
        Log.i(TAG, "train = $train")
    }


    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    companion object {
        fun newInstance(gender: Int): ThirdHello = ThirdHello().apply{
            arguments = Bundle().apply { putInt(KEY_GENDER, gender) }
        }
    }
}