/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.example.github.di

import android.app.Application
import androidx.room.Room
import com.android.example.github.BuildConfig
import com.example.data.api.GithubAuthService
import com.example.data.api.GithubService
import com.example.data.api_builder.ApiBuilder
import com.example.data.db.GithubDb
import com.example.data.db.RepoDao
import com.example.data.db.UserDao
import com.example.envvar.EnvVar
import dagger.Module
import dagger.Provides
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {
    @Singleton
    @Provides
    fun provideGithubService(
            apiBuilder: ApiBuilder
    ): GithubService {
        return apiBuilder.buildGithubService(
                baseUrl = "https://api.github.com/",
                loggingLevel = HttpLoggingInterceptor.Level.BODY
        )
    }

    @Singleton
    @Provides
    fun provideGithubAuthService(
            apiBuilder: ApiBuilder
    ): GithubAuthService {
        return apiBuilder.buildGithubAuthService(
                baseUrl = "https://github.com/"
        )
    }

    @Singleton
    @Provides
    fun provideDb(app: Application): GithubDb {
        return Room
                .databaseBuilder(app, GithubDb::class.java, "github.db")
                .fallbackToDestructiveMigration()
                .build()
    }

    @Singleton
    @Provides
    fun provideUserDao(db: GithubDb): UserDao {
        return db.userDao()
    }

    @Singleton
    @Provides
    fun provideRepoDao(db: GithubDb): RepoDao {
        return db.repoDao()
    }

    @Singleton
    @Provides
    fun provideEnvVar(): EnvVar {
        return object : EnvVar {
            override val GITHUB_CLIENT_ID = BuildConfig.GITHUB_CLIENT_ID
            override val GITHUB_CLIENT_SECRET = BuildConfig.GITHUB_CLIENT_SECRET
        }
    }
}

