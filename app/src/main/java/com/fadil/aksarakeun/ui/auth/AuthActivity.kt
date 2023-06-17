package com.fadil.aksarakeun.ui.auth

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fadil.aksarakeun.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
    }


    companion object{
        fun start(ctx: Context){
            ctx.startActivity(Intent(ctx, AuthActivity::class.java))
        }
    }
}