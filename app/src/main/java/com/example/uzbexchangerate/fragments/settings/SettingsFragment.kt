package com.example.uzbexchangerate.fragments.settings

import android.content.Intent
import com.example.uzbexchangerate.R
import com.example.uzbexchangerate.databinding.FragmentSettingsBinding
import com.example.uzbexchangerate.dialogs.ChangeLanguageDialog
import com.example.uzbexchangerate.fragments.BaseFragment
import com.example.uzbexchangerate.utils.ThemeModeState
import com.example.uzbexchangerate.utils.extensions.snackBar
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

            val languageDialog = ChangeLanguageDialog(requireContext(), shared.getLanguage()!!)
            languageDialog.setButtonClickListener {
                shared.setLanguage(it, requireContext(), true)
                languageDialog.dismiss()
                requireActivity().recreate()
            }

            languageBtn.setOnClickListener {
                languageDialog.show()
            }

            shareProgram.setOnClickListener {
                val sendIntent = Intent()
                sendIntent.action = Intent.ACTION_SEND
                sendIntent.putExtra(Intent.EXTRA_TEXT, "https://drive.google.com/file/d/1Umr0l9aQyySFDtpKKxcRKTf2hk61aFop/view?usp=sharing")
                sendIntent.type = "text/plain"
                sendIntent.setPackage("org.telegram.messenger")
                try {
                    startActivity(sendIntent)
                }catch (ex: android.content.ActivityNotFoundException){
                    snackBar(binding.root, ex.toString())
                }
            }

        }
    }

}