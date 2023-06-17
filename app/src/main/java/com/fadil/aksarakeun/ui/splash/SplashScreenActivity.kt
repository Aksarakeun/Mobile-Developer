package com.fadil.aksarakeun.ui.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.fadil.aksarakeun.ui.main.MainActivity
import com.fadil.aksarakeun.R
import com.fadil.aksarakeun.ui.auth.AuthActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {
    private val viewModel: SplashScreenViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        lifecycleScope.launch {
            delay(1500)
            if (viewModel.accessToken == null) {
                AuthActivity.start(this@SplashScreenActivity)
            } else {
                MainActivity.start(this@SplashScreenActivity)
            }
            finish()
        }
    }
}