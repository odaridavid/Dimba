package com.github.odaridavid.dimba.ui.league

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.odaridavid.dimba.utils.Loading
import com.github.odaridavid.dimba.utils.ResultState
import com.github.odaridavid.dimba.interactors.GetAvailableLeaguesUseCase
import com.github.odaridavid.dimba.models.leagues.League
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
class LeaguesViewModel(private val getAvailableLeaguesUseCase: GetAvailableLeaguesUseCase) :
    ViewModel() {

    private val _leagues = MutableLiveData<ResultState<List<League>>>()

    val leagues: LiveData<ResultState<List<League>>>
        get() = _leagues

    init {
        getAllAvailableLeagues()
    }

    fun getAllAvailableLeagues() {
        _leagues.value = Loading<List<League>>()
        viewModelScope.launch(Dispatchers.IO) {
            _leagues.postValue(getAvailableLeaguesUseCase.invoke())
        }
    }

}