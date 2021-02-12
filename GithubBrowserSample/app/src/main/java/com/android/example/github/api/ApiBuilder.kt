package com.android.example.github.api

import com.android.example.github.util.LiveDataCallAdapterFactory
import com.example.data.api.GithubService
import com.example.data.api_builder.AuthenticationInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class ApiBuilder @Inject constructor(
        private val authenticationInterceptor: AuthenticationInterceptor
) {
    fun buildGithubService(
            baseUrl: String,
            loggingLevel: HttpLoggingInterceptor.Level
    ): GithubService {
        val client = OkHttpClient.Builder()
                .addNetworkInterceptor(HttpLoggingInterceptor().apply { level = loggingLevel })
                .addInterceptor(authenticationInterceptor)
                .build()

        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .client(client)
                .build()
                .create(GithubService::class.java)
    }
}
