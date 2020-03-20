package com.github.odaridavid.dimba.di

import com.github.odaridavid.dimba.interactors.GetLiveFixturesUseCase
import com.github.odaridavid.dimba.network.ApiClient
import com.github.odaridavid.dimba.repositories.FixturesRepository
import com.github.odaridavid.dimba.repositories.FixturesRepositoryImpl
import com.github.odaridavid.dimba.ui.fixtures.FixturesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

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
val network = module {
    single { ApiClient.provideLoggingInterceptor() }
    single { ApiClient.provideOkhttpClient(get()) }
    single { ApiClient.provideRetrofit(get()) }
    single { ApiClient.buildService(get()) }
}

val data = module {
    factory<FixturesRepository> { FixturesRepositoryImpl(get()) }
}

val viewModel = module {
    viewModel { FixturesViewModel(get()) }
}
val domain = module {
    factory { GetLiveFixturesUseCase(get()) }
}