package com.example.gym

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.gym.fragments.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import androidx.fragment.app.commit
import kotlinx.coroutines.launch

private const val NAME_SHARED_PREFERENCES = "shared_preferences"
private const val FIRST_LAUNCH = "first_launch"


class LaunchActivity : AppCompatActivity(),
                       FirstHello.Callbacks,
                       SecondHello.Callbacks,
                       ThirdHello.Callbacks,
                       FourthHello.Callbacks,
                       FifthHello.Callbacks{

    val activityScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)

        activityScope.launch {
            delay(3000)

            val currentFragment = supportFragmentManager.findFragmentById(R.id.container_fragment)
            if (currentFragment == null ){
                val fragment = FirstHello.newInstance()
                supportFragmentManager.commit{
                    add(R.id.container_fragment, fragment)
                }
            }
        }

    }

    override fun onFirstHello() {
        val fragment = SecondHello.newInstance()
        changeFragments(fragment)
    }

    override fun onSecondHello(gender: Int) {
        val fragment = ThirdHello.newInstance()
        changeFragments(fragment)
    }

    override fun onThirdHello() {
        val fragment = FourthHello.newInstance()
        changeFragments(fragment)
    }

    override fun onFourthHello() {
        val fragment = FifthHello.newInstance()
        changeFragments(fragment)
    }

    override fun onFifthHello() {
        val sharedPreferences = getSharedPreferences(NAME_SHARED_PREFERENCES, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean(FIRST_LAUNCH, true)
        editor.apply()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun changeFragments(fragment: Fragment){
        supportFragmentManager.commit{
            setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out,
                                android.R.animator.fade_in, android.R.animator.fade_out)
            replace(R.id.container_fragment,fragment)
            addToBackStack(null)
        }
    }
}