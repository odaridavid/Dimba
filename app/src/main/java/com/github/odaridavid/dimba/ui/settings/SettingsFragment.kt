package com.github.odaridavid.dimba.ui.settings

import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.github.odaridavid.dimba.R
import org.koin.android.ext.android.inject

/**
 *
 * Copyright 2020 David Odari
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *            http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 **/
class SettingsFragment : PreferenceFragmentCompat() {

    private val sharedPref: SharedPreferences by inject()
    var themePreference: ListPreference? = null

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
        //Get Theme List Preference
        themePreference = findPreference(getString(R.string.key_theme_preference))
        configPreference(themePreference)
    }

    private fun configPreference(themePreference: ListPreference?) {
        //Options to display depending on android api version
        val themeArrayOptionsAboveQ = resources.getStringArray(R.array.theme_options_above_q)
        val themeArrayOptionsBelowQ = resources.getStringArray(R.array.theme_options_below_q)

        themePreference?.entries =
            if (Build.VERSION.SDK_INT >= 29) themeArrayOptionsAboveQ else themeArrayOptionsBelowQ

        //Setup Theme Summary Info
        themePreference?.summaryProvider =
            Preference.SummaryProvider<ListPreference> { preference ->
                when (preference.value) {
                    THEME_LIGHT -> themeArrayOptionsAboveQ[0]
                    THEME_DARK -> themeArrayOptionsAboveQ[1]
                    THEME_SYSTEM -> {
                        if (Build.VERSION.SDK_INT >= 29) themeArrayOptionsAboveQ[2] else themeArrayOptionsBelowQ[2]
                    }
                    else -> "Default"
                }
            }
    }

    private fun setupIcons(themePreference: ListPreference?) {
        val themeValue =
            sharedPref.getString(getString(R.string.key_theme_preference), DEFAULT_THEME_VALUE)
        themePreference?.icon = when (themeValue) {
            THEME_LIGHT -> getDrawable(context!!, R.drawable.ic_day)
            THEME_DARK -> getDrawable(context!!, R.drawable.ic_night)
            THEME_SYSTEM -> getDrawable(context!!, R.drawable.ic_day)
            else -> getDrawable(context!!, R.drawable.ic_day)
        }
    }

    override fun onResume() {
        super.onResume()
        setupIcons(themePreference)
        registerThemeChangeListener()
    }

    private fun registerThemeChangeListener() {
        val themePrefKey = getString(R.string.key_theme_preference)
        sharedPref.registerOnSharedPreferenceChangeListener { prefs: SharedPreferences?, key: String? ->
            if (key == themePrefKey) {
                when (prefs?.getString(themePrefKey, DEFAULT_THEME_VALUE)) {
                    THEME_LIGHT -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    THEME_DARK -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    THEME_SYSTEM ->
                        if (Build.VERSION.SDK_INT >= 29)
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                        else
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
                }
            }
        }
    }

    companion object {
        const val DEFAULT_THEME_VALUE = "Light"

        //THEME VALUES
        const val THEME_LIGHT = "Light"
        const val THEME_DARK = "Dark"
        const val THEME_SYSTEM = "System"
    }

}
