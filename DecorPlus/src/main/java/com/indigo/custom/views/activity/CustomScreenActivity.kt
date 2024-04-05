package com.indigo.custom.views.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.ObservableField
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.indigo.custom.R
import com.indigo.custom.databinding.ActivityCustomScreenBinding

class CustomScreenActivity : AppCompatActivity() {

    val binding by lazy { ActivityCustomScreenBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

}