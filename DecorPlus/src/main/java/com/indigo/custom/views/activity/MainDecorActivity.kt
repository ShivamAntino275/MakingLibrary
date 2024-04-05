package com.indigo.custom.views.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.indigo.custom.R
import com.indigo.custom.databinding.ActivityMainDecorBinding

class MainDecorActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainDecorBinding.inflate(layoutInflater) }
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        navController = findNavController(R.id.fragmentMain)
        if (intent!=null && intent.hasExtra(getString(R.string.screenType))){
            if (intent.extras?.getString(getString(R.string.screenType))==getString(R.string.Refer)){
                navController.navigate(R.id.referAndEarnFragment)
            }
        }
    }

}