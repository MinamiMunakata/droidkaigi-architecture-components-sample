package com.android.example.github.repository

import com.android.example.github.vo.AccessToken
import com.chibatching.kotpref.KotprefModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccessTokenRepository @Inject constructor() {

    private object Pref : KotprefModel() {
        var accessTokenValue by nullableStringPref()
    }

    fun save(token: AccessToken) {
        Pref.accessTokenValue = token.value
    }

    fun load(): AccessToken? {
        return Pref.accessTokenValue?.let {
            AccessToken(it)
        }
    }

    fun clear() {
        Pref.accessTokenValue = null
    }
}
