package com.z.traffic.feed.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import com.z.traffic.R
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_image.*
import javax.inject.Inject


class ImageViewActivity : AppCompatActivity() {
    @Inject
    lateinit var picasso: Picasso

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AndroidInjection.inject(this)

        setContentView(R.layout.activity_image)

        closeButton.setOnClickListener {
            finish()
        }

        intent.getStringExtra(IMAGE_URL_KEY)?.let {
            setImage(it)
        }
    }

    private fun setImage(imageUrl: String) {
        picasso.load(imageUrl).into(cameraImageDetail)
    }

    companion object {
        const val IMAGE_URL_KEY = "IMAGE_URL_KEY"
    }
}