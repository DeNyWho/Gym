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
import com.example.gym.retrofit.NetworkService
import kotlinx.android.synthetic.main.fragment_sing_in.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Math.log

private const val NAME_SHARED_PREFERENCES = "shared_preferences"
private const val SP_TOKEN = "sp_t"
private const val TAG = "SingInFragment"

class SingIn : Fragment() {

    interface Callbacks{
        fun onSignIn(mode: Int)
    }
    private var callbacks: Callbacks? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks
    }

    override fun onStart() {
        super.onStart()
        Sign_in.setOnClickListener {
            if (User.text.toString() == "" && Password.text.toString() == "")
            {
                User.error = "Field 'user' is empty"
                Password.error = "Field 'password' is empty"
            }
            if (User.text.toString() != "") {
                if (Password.text.toString() != "") {
                    NetworkService.instance
                        ?.getJSONApi()
                        ?.signIn("${User.text}", "${Password.text}")
                        ?.enqueue(object : Callback<Map<String, Map<String, Int>>> {
                            override fun onResponse(
                                call: Call<Map<String, Map<String, Int>>>?,
                                response: Response<Map<String, Map<String, Int>>>?
                            ) {
                                val post = response?.body()
                                if (post != null) {
                                    saveToken(post)
                                }
                                callbacks?.onSignIn(1)
                                Toast.makeText(requireContext(), "Successfully", Toast.LENGTH_LONG)
                                    .show()
                                Log.i(TAG, "post = $post")
                            }

                            override fun onFailure(
                                call: Call<Map<String, Map<String, Int>>>,
                                t: Throwable
                            ) {
                                if (t?.message == "java.lang.NumberFormatException: For input string: " +
                                    "\"Error username or password\""
                                ) {
                                    Toast.makeText(
                                        requireContext(),
                                        "Wrong username or password",
                                        Toast.LENGTH_LONG
                                    ).show()
                                } else Toast.makeText(
                                    requireContext(),
                                    t?.message!!,
                                    Toast.LENGTH_LONG
                                ).show()
                                t.printStackTrace()
                            }
                        })
                } else Password.error = "Field 'password' is empty"
            } else User.error = "Field 'user' is empty"
        }
        textView3.setOnClickListener {
            val sharedPreferences = context?.getSharedPreferences(NAME_SHARED_PREFERENCES, Context.MODE_PRIVATE)
            val token = sharedPreferences?.getInt(SP_TOKEN, 0)
            Log.i(TAG, "token = $token")
            callbacks?.onSignIn(0)
        }
    }

    private fun saveToken(post: Map<String, Map<String, Int>>){
        val sharedPreferences = context?.getSharedPreferences(NAME_SHARED_PREFERENCES, Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        val token: Int = post["notice"]?.get("token")!!
        editor?.putInt(SP_TOKEN, token)
        editor?.apply()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sing_in, container, false)
    }

    companion object {
        fun newInstance(): SingIn = SingIn()
    }
}