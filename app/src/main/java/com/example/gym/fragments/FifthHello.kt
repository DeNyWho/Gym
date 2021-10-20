package com.example.gym.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gym.R
import kotlinx.android.synthetic.main.fragment_fifth_hello.*

private const val HEIGHT = "height"
private const val WEIGHT = "weight"
private const val NAME_SHARED_PREFERENCES = "shared_preferences"


class FifthHello : Fragment() {

    interface Callbacks {
        fun onFifthHello()
    }

    private var callbacks: Callbacks? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks
    }

    override fun onStart() {
        super.onStart()
        imageButton4.setOnClickListener {
            callbacks?.onFifthHello()
        }
    }

    override fun onStop() {
        super.onStop()
        val height: Int? = try {
            Height.text.toString().toInt()
        } catch (e: Exception) {
            null
        }
        val weight: Int? = try {
            Weight.text.toString().toInt()
        } catch (e: Exception) {
            null
        }

        if( height != null && weight != null){
            val sharedPreferences = context?.getSharedPreferences(NAME_SHARED_PREFERENCES, Context.MODE_PRIVATE)
            val editor = sharedPreferences?.edit()
            editor?.putInt(HEIGHT,height)
            editor?.putInt(WEIGHT,weight)
            editor?.apply()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fifth_hello, container, false)
    }

    companion object {
        fun newInstance(): FifthHello = FifthHello()
    }
}