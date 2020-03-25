package com.github.odaridavid.dimba.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.github.odaridavid.dimba.commons.*

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
abstract class BaseFragment<T> : Fragment() {

    fun onNetworkChange(block: (Boolean) -> Unit) {
        NetworkUtils.getNetworkStatus(context!!).observe(this, Observer { isConnected ->
            block(isConnected)
        })
    }

    fun handleState(result: ResultState<T>) {
        when (result) {
            is Error -> showOnError(ExceptionHandler(context!!).parse(result.e))
            is Success -> showOnSuccess(result)
            is Loading -> showLoading(true)
        }
    }

    abstract fun showLoading(isLoading: Boolean)

    open fun showOnSuccess(result: Success<T>) {
        showLoading(false)
    }

    open fun showOnError(message: String) {
        showLoading(false)
    }
}