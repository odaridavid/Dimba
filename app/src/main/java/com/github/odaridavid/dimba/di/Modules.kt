package com.github.odaridavid.dimba.di

import androidx.room.Room
import com.github.odaridavid.dimba.commons.Constants
import com.github.odaridavid.dimba.db.DimbaDatabase
import com.github.odaridavid.dimba.db.LeaguesDao
import com.github.odaridavid.dimba.interactors.GetAvailableLeaguesUseCase
import com.github.odaridavid.dimba.interactors.GetLiveFixturesUseCase
import com.github.odaridavid.dimba.interactors.GetStandingsUseCase
import com.github.odaridavid.dimba.network.ApiClient
import com.github.odaridavid.dimba.repositories.*
import com.github.odaridavid.dimba.ui.fixtures.FixturesViewModel
import com.github.odaridavid.dimba.ui.league.LeaguesViewModel
import com.github.odaridavid.dimba.ui.standings.StandingsViewModel
import org.koin.android.ext.koin.androidContext
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
    single {
        ApiClient.provideOkhttpClient(
            httpLoggingInterceptor = get(),
            context = androidContext()
        )
    }
    single { ApiClient.provideRetrofit(okHttpClient = get()) }
    single { ApiClient.buildService(retrofit = get()) }
}

val data = module {
    fun provideLeagueDao(db: DimbaDatabase): LeaguesDao = db.leagueDao()
    single {
        Room.databaseBuilder(
                androidContext(),
                DimbaDatabase::class.java,
                Constants.APP_DATABASE_NAME
            )
            .build()
    }
    single { provideLeagueDao(db = get()) }
    factory<FixturesRepository> { FixturesRepositoryImpl(api = get()) }
    factory<StandingsRepository> { StandingsRepositoryImpl(api = get()) }
    factory<LeaguesRepository> { LeaguesRepositoryImpl(api = get(), leaguesDao = get()) }
}

val viewModel = module {
    viewModel { FixturesViewModel(getLiveFixturesUseCase = get()) }
    viewModel { StandingsViewModel(getStandingsUseCase = get()) }
    viewModel { LeaguesViewModel(getAvailableLeaguesUseCase = get()) }
}
val domain = module {
    factory { GetLiveFixturesUseCase(fixturesRepository = get()) }
    factory { GetStandingsUseCase(standingsRepository = get()) }
    factory { GetAvailableLeaguesUseCase(leaguesRepository = get()) }
}
