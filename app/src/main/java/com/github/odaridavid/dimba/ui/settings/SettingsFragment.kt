package com.github.odaridavid.dimba.ui.settings

import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.github.odaridavid.dimba.R
import com.github.odaridavid.dimba.commons.Constants.THEME_DARK
import com.github.odaridavid.dimba.commons.Constants.THEME_LIGHT
import com.github.odaridavid.dimba.commons.Constants.THEME_SYSTEM
import com.github.odaridavid.dimba.commons.ThemeUtils
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
    private var themePreference: ListPreference? = null

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
                getString(
                    when (preference.value) {
                        THEME_LIGHT -> R.string.pref_summary_theme_light
                        THEME_DARK -> R.string.pref_summary_theme_dark
                        THEME_SYSTEM -> {
                            if (Build.VERSION.SDK_INT >= 29) R.string.pref_summary_theme_system_above_q else R.string.pref_summary_theme_system_below_q
                        }
                        else -> R.string.pref_summary_theme_light
                    }
                )
            }
    }

    private fun setupThemePreferenceIcons(themePreference: ListPreference?) {
        val themeValue =
            sharedPref.getString(getString(R.string.key_theme_preference), DEFAULT_THEME_VALUE)
        themePreference?.icon = getDrawable(
            context!!,
            when (themeValue) {
                THEME_LIGHT -> R.drawable.ic_day
                THEME_DARK -> R.drawable.ic_night
                THEME_SYSTEM -> R.drawable.ic_day
                else -> R.drawable.ic_day
            }
        )
    }

    override fun onResume() {
        super.onResume()
        setupThemePreferenceIcons(themePreference)
        registerThemeChangeListener()
    }

    private fun registerThemeChangeListener() {
        val themeKey = getString(R.string.key_theme_preference)
        sharedPref.registerOnSharedPreferenceChangeListener { prefs: SharedPreferences?, key: String? ->
            if (key == themeKey) {
                ThemeUtils.updateTheme(prefs!!, key)
            }
        }
    }

    companion object {
        const val DEFAULT_THEME_VALUE = "Light"
    }

}
