package com.github.odaridavid.dimba.ui.fixtures

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.odaridavid.dimba.utils.Loading
import com.github.odaridavid.dimba.utils.ResultState
import com.github.odaridavid.dimba.interactors.GetLiveFixturesUseCase
import com.github.odaridavid.dimba.models.fixtures.LiveFixture
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
class FixturesViewModel(private val getLiveFixturesUseCase: GetLiveFixturesUseCase) :
    ViewModel() {

    private val _fixtures = MutableLiveData<ResultState<List<LiveFixture>>>()

    val fixtures: LiveData<ResultState<List<LiveFixture>>>
        get() = _fixtures

    init {
        getFixtures()
    }

    fun getFixtures() {
        _fixtures.value = Loading<List<LiveFixture>>()
        viewModelScope.launch(Dispatchers.IO) {
            _fixtures.postValue(getLiveFixturesUseCase.invoke())
        }
    }

}