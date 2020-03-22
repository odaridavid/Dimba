package com.github.odaridavid.dimba.commons

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import com.github.odaridavid.dimba.commons.Constants.PREF_KEY_NETWORK_AVAILABLE

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
object NetworkUtils {

    fun registerNetworkCallBack(context: Context, callback: ConnectivityManager.NetworkCallback) {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val nr = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_BLUETOOTH)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)

        if (Build.VERSION.SDK_INT >= 26)
            nr.addTransportType(NetworkCapabilities.TRANSPORT_WIFI_AWARE)

        if (Build.VERSION.SDK_INT >= 27)
            nr.addTransportType(NetworkCapabilities.TRANSPORT_LOWPAN)

        cm.registerNetworkCallback(nr.build(), callback)
    }

}

class NetworkCallback(val sharedPreferences: SharedPreferences) :
    ConnectivityManager.NetworkCallback() {

    var editor: SharedPreferences.Editor = sharedPreferences.edit()

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        editor.putBoolean(PREF_KEY_NETWORK_AVAILABLE, true)
        editor.apply()
    }

    override fun onLost(network: Network) {
        super.onLost(network)
        editor.putBoolean(PREF_KEY_NETWORK_AVAILABLE,false)
        editor.apply()
    }


}