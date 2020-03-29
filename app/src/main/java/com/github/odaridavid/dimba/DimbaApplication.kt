package com.github.odaridavid.dimba

import android.app.Application
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import coil.Coil
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.util.CoilUtils
import com.github.odaridavid.dimba.utils.ThemeUtils
import com.github.odaridavid.dimba.di.*
import com.jakewharton.threetenabp.AndroidThreeTen
import okhttp3.OkHttpClient
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

/**
 *
 * Copyright 2020 David Odari
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 **/
class DimbaApplication : Application() {

    private val sharedPref: SharedPreferences by inject()

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())

        initKoin()

        initCoil()

        initPreferences()

        AndroidThreeTen.init(this)
    }

    private fun initPreferences() {
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false)
        ThemeUtils.updateTheme(sharedPref, getString(R.string.key_theme_preference))
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@DimbaApplication)
            modules(listOf(network, data, viewModel, domain, framework))
        }
    }

    private fun initCoil() {
        Coil.setDefaultImageLoader {
            ImageLoader(applicationContext) {
                crossfade(true)
                okHttpClient {
                    OkHttpClient.Builder()
                        .cache(CoilUtils.createDefaultCache(applicationContext))
                        .build()
                }
                componentRegistry {
                    add(SvgDecoder(applicationContext))
                }
            }
        }
    }
}