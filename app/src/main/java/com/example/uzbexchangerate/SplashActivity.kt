package com.example.uzbexchangerate

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.dylanc.longan.context
import com.example.uzbexchangerate.databinding.ActivityMainBinding
import com.example.uzbexchangerate.databinding.ActivitySplashBinding
import com.example.uzbexchangerate.utils.SharedPreferencesHelper
import com.example.uzbexchangerate.utils.setChangeAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val preferences by lazy { SharedPreferencesHelper(context = context) }
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setChangeAppTheme(preferences)

        window?.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        CoroutineScope(Dispatchers.Main).launch {
            delay(1500)
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            this@SplashActivity.finish()
        }
    }

}