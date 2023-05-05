package ru.myfitness

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.myfitness.fragments.MainFragment
import ru.myfitness.utils.FragmentManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FragmentManager.setFragment(MainFragment.newInstance(), this)
    }

    override fun onBackPressed() {
        if (FragmentManager.currentFragment is MainFragment) super.onBackPressed()
        else FragmentManager.setFragment(MainFragment.newInstance(), this)
    }
}