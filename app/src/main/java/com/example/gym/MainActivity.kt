package com.example.gym

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.gym.fragments.MainFragment
import com.example.gym.fragments.SingIn
import com.example.gym.fragments.SingUp

private const val NAME_SHARED_PREFERENCES = "shared_preferences"
private const val FIRST_LAUNCH = "first_launch"

class MainActivity : AppCompatActivity(),
                     SingIn.Callbacks,
                     SingUp.Callbacks{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPreferences = getSharedPreferences(NAME_SHARED_PREFERENCES, Context.MODE_PRIVATE)
        val firstLaunch = sharedPreferences.getBoolean(FIRST_LAUNCH, false)
        if(!firstLaunch){
            startActivity(Intent(this, LaunchActivity::class.java))
            finish()
        }
        val currentFragment = supportFragmentManager.findFragmentById(R.id.container_fragment)
        if(currentFragment == null)
        {
            val fragment = SingIn.newInstance()
            supportFragmentManager.commit {
                add(R.id.container_fragment, fragment)
            }
        }
    }

    override fun onSignIn(mode: Int) {
        var fragment: Fragment? = null
        when(mode){
            0 -> fragment = SingUp.newInstance()
            1 -> fragment = MainFragment.newInstance()
        }
        if (fragment != null) changeFragment(fragment, true)
    }

    override fun onSingUp() {
        val fragment = SingIn.newInstance()
        changeFragment(fragment, false)
    }

    private fun changeFragment(fragment: Fragment, addBackStack: Boolean) {
        supportFragmentManager.commit {
            setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out,
                android.R.animator.fade_in, android.R.animator.fade_out)
            replace(R.id.container_fragment, fragment)
            if (addBackStack) {
                addToBackStack(null)
            }
        }
    }
}