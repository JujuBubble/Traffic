package com.z.traffic

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.z.traffic.feed.view.FeedActivity

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_loading)
        Handler().postDelayed(Runnable {
            val intent = Intent(this@SplashScreenActivity, FeedActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
            finish()
        }, SPLASH_TIME_OUT)
    }

    companion object {
        private const val SPLASH_TIME_OUT = 3000L
    }
}
