package com.github.odaridavid.dimba.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.github.odaridavid.dimba.commons.*
import kotlinx.android.synthetic.main.errorview.*
import kotlinx.android.synthetic.main.progressbar.*

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

    open fun showLoading(isLoading: Boolean) {
        dimba_progress_bar.isVisible(isLoading)
        error_text_view.isVisible(false)
    }

    open fun showOnSuccess(result: Success<T>) {
        showLoading(false)
    }

    fun showOnError(message: String) {
        showLoading(false)
        error_text_view.text = message
        error_text_view.isVisible(true)
    }
}