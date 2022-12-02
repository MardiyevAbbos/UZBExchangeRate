package com.example.uzbexchangerate.fragments.settings

import com.example.uzbexchangerate.R
import com.example.uzbexchangerate.databinding.FragmentSettingsBinding
import com.example.uzbexchangerate.fragments.BaseFragment
import com.example.uzbexchangerate.utils.SharedPreferencesHelper
import com.example.uzbexchangerate.utils.ThemeModeState
import com.example.uzbexchangerate.utils.setChangeAppTheme

class SettingsFragment : BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {
    
    override fun onViewCreate() {
        initViews()
    }

    private fun initViews() {
        binding.apply {

            val sharedTheme = shared.getTheme()
            nightMode.isEnabled = sharedTheme != ThemeModeState.SYSTEM.name
            systemMode.isChecked = sharedTheme == ThemeModeState.SYSTEM.name

            systemMode.setOnCheckedChangeListener { _, _ ->
                if (systemMode.isChecked) {
                    nightMode.isEnabled = false
                    shared.systemMode = sharedTheme != ThemeModeState.DARK.name
                    shared.setTheme(ThemeModeState.SYSTEM.name)
                } else {
                    shared.setTheme(if (shared.systemMode) ThemeModeState.LIGHT.name else ThemeModeState.DARK.name)
                    nightMode.isEnabled = true
                }
                setChangeAppTheme(shared)
            }
            modeTxt.text =
                if (shared.systemMode) getString(R.string.str_light_mode) else getString(R.string.str_dark_mode)
            nightMode.isChecked = !shared.systemMode
            nightMode.setOnCheckedChangeListener { _, _ ->
                shared.systemMode = !nightMode.isChecked
                shared.setTheme(if (nightMode.isChecked) ThemeModeState.DARK.name else ThemeModeState.LIGHT.name)
                setChangeAppTheme(shared)
            }

        }
    }

}