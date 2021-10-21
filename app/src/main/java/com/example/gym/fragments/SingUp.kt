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
import kotlinx.android.synthetic.main.fragment_sing_up.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "SignUpFragment"
private const val NAME_SHARED_PREFERENCES = "shared_preferences"
private const val SP_Height ="sp_height"
private const val SP_Weight = "sp_weight"

class SingUp : Fragment() {

    interface Callbacks{
        fun onSingUp()
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
        return inflater.inflate(R.layout.fragment_sing_up, container, false)
    }

    override fun onStart() {
        super.onStart()
        val sharedPreferences = context?.getSharedPreferences(NAME_SHARED_PREFERENCES, Context.MODE_PRIVATE)
        val name = "${User.text}"
        val mail = "${Mail.text}"
        val height = sharedPreferences?.getInt(SP_Height, 1).toString()
        val weight = sharedPreferences?.getInt(SP_Weight, 1).toString()
        Sign_in.setOnClickListener {
            if (checkField(
                    name,
                    mail,
                    "${Password.text}",
                    "${RepeatPassword.text}",
                    height,
                    weight
                )
            ) {
                NetworkService.instance
                    ?.getJSONApi()
                    ?.signUp(name, mail, "${RepeatPassword.text}", height, weight)
                    ?.enqueue(object : Callback<Map<String, Map<String, String>>> {
                        override fun onResponse(
                            call: Call<Map<String, Map<String, String>>>,
                            response: Response<Map<String, Map<String, String>>>
                        ) {
                            callbacks?.onSingUp()
                            Toast.makeText(requireContext(), "Successfully", Toast.LENGTH_LONG)
                                .show()
                        }

                        override fun onFailure(
                            call: Call<Map<String, Map<String, String>>>,
                            t: Throwable
                        ) {
                            t.printStackTrace()
                        }
                    })
            }
        }
        textView5.setOnClickListener {
            callbacks?.onSingUp()
        }
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    private fun checkField(name: String,
                           mail: String,
                           password1: String,
                           password2: String,
                           height: String,
                           weight: String): Boolean
    {
        var mailf = true
        var flag = true
        var count = 0
        when{
            name == "" -> {
                flag = false
                count++
                User.error = "Username is empty"
            }
            mail == "" -> {
                flag = false
                count++
                Mail.error = "Mail is empty"
            }
            password1 == "" -> {
                flag = false
                count++
                Password.error = "Password is empty"
            }
            password2 == "" -> {
                flag = false
                count++
                RepeatPassword.error = "RepeatPassword is empty"
            }
            height == "" -> {
                flag = false
                count++
            }
            weight == "" -> {
                flag = false
                count++
            }
        }
        if (!flag && count > 3) Toast.makeText(requireContext(), "It's necessary to fill in all fields", Toast.LENGTH_LONG).show()
        if ( password1 != password2)
        {
            flag = false
            Password.error = "Passwords don't match"
            RepeatPassword.error = "Passwords don't match"
        }
        if (Mail.text.toString() != "") {
            if (android.util.Patterns.EMAIL_ADDRESS.matcher("${Mail.text}").matches()) mailf = true
            else Mail.error = "Incorrect email address"
        }
        count = 0
        return flag
    }

    companion object {
        fun newInstance(): SingUp = SingUp()
    }
}