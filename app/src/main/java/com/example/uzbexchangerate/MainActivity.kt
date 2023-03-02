package com.example.uzbexchangerate

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.dylanc.longan.context
import com.example.uzbexchangerate.databinding.ActivityMainBinding
import com.example.uzbexchangerate.dialogs.NoConnectionDialog
import com.example.uzbexchangerate.utils.Constants.PREF_APP_THEME_MODE
import com.example.uzbexchangerate.utils.NetworkConnectionListener
import com.example.uzbexchangerate.utils.SharedPreferencesHelper
import com.example.uzbexchangerate.utils.extensions.collectLatestLA
import com.example.uzbexchangerate.utils.extensions.gone
import com.example.uzbexchangerate.utils.extensions.visible
import com.example.uzbexchangerate.utils.setChangeAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    private val preferences by lazy { SharedPreferencesHelper(context = context) }
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private val networkConnectionListener by lazy { NetworkConnectionListener() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setChangeAppTheme(preferences)
        initViews()

        val dialog by lazy { NoConnectionDialog(this@MainActivity) }
        networkConnectionListener.checkNetworkAvailable(this@MainActivity)
            .collectLatestLA(this){status ->
                when(status){
                    true -> dialog.dismiss()
                    false -> dialog.show()
                }
            }
    }

    private fun initViews() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_nav_host) as NavHostFragment
        navController = navHostFragment.findNavController()

        setupWithNavController(binding.bottomNavigationView, navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment, R.id.historyFragment, R.id.settingsFragment -> binding.bottomNavigationView.visible()
                else -> binding.bottomNavigationView.gone()
            }
        }
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        when(key){
            PREF_APP_THEME_MODE -> {
                setChangeAppTheme(preferences)
            }
        }
    }

    override fun onStop() {
        preferences.preferences.unregisterOnSharedPreferenceChangeListener(this)
        super.onStop()
    }

}