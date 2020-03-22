package com.github.odaridavid.dimba.network

import android.content.Context
import com.github.odaridavid.dimba.BuildConfig
import com.github.odaridavid.dimba.commons.Constants
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

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
object ApiClient {

    private const val HEADER_KEY_RAPID_API_HOST = "x-rapidapi-host"
    private const val HEADER_VALUE_RAPID_API_HOST = "api-football-v1.p.rapidapi.com"
    private const val HEADER_KEY_RAPID_API_KEY = "x-rapidapi-key"

    fun provideOkhttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        context: Context
    ): OkHttpClient {
        val httpCacheDirectory = File(context.cacheDir, "http-cache")
        val cacheSize: Long = 10 * 1024 * 1024
        val cache = Cache(httpCacheDirectory, cacheSize)
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor { chain ->
                val originalReq = chain.request()

                val newReqBuilder = originalReq.newBuilder()
                    .header(HEADER_KEY_RAPID_API_HOST, HEADER_VALUE_RAPID_API_HOST)
                    .header(HEADER_KEY_RAPID_API_KEY, BuildConfig.DIMBA_API_KEY)

                val newReq = newReqBuilder.build()

                chain.proceed(newReq)
            }
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .cache(cache)
            .build()
    }

    fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    fun buildService(retrofit: Retrofit): FootballApiService {
        return retrofit.create(FootballApiService::class.java)
    }

}