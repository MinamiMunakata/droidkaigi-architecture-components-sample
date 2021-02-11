package com.android.example.github.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.lifecycle.lifecycleScope
import com.android.example.github.R
import com.android.example.github.di.Injectable
import com.android.example.github.util.LoginHelper
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginActivity : AppCompatActivity(), Injectable {
    @Inject
    lateinit var loginHelper: LoginHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val customTabsIntent = CustomTabsIntent.Builder().apply {
            setShowTitle(true)
        }.build()

        customTabsIntent.launchUrl(this, loginHelper.generateAuthorizationUrl())
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        intent?.let {
            lifecycleScope.launch {
                if(loginHelper.handleAuthRedirect(it)) {
                    Toast.makeText(this@LoginActivity, "Login success!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@LoginActivity, "Login failure!", Toast.LENGTH_SHORT).show()
                }
                finish()
            }
        }
    }
}
