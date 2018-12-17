package com.felipersn.idwallproject.presentation.ui.imagepreview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import androidx.appcompat.app.AppCompatActivity
import com.felipersn.idwallproject.R
import com.felipersn.idwallproject.common.extension.getStatusBarHeight
import com.felipersn.idwallproject.common.extension.loadImage
import kotlinx.android.synthetic.main.activity_image_preview.*

class ImagePreviewActivity : AppCompatActivity() {

    private lateinit var imagePreviewUrl: String

    //Gesture variables
    private lateinit var imagePreviewScaleGestureDetector: ScaleGestureDetector
    private var imagePreviewScaleFactor = 1.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_preview)

        initView()
    }

    private fun initView() {
        getIntentExtras()
        setupImageViewToReceiveAnimation()
        setupToolbar()
        loadImage()
        setupScaleGesture()
    }

    private fun setupToolbar() {
        appBarLayoutImagePreview.setPadding(0, getStatusBarHeight(), 0, 0)
        setSupportActionBar(toolbarImagePreview)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close_white)
    }

    private fun getIntentExtras() {
        imagePreviewUrl = intent.getStringExtra(INTENT_EXTRA_IMAGE_PREVIEW_URL)
    }

    private fun setupImageViewToReceiveAnimation() {
        imageViewPreview.transitionName = imagePreviewUrl
    }

    private fun loadImage() {
        imageViewPreview.loadImage(imagePreviewUrl)
    }

    //Control the pinch gesture to scale imageView
    private fun setupScaleGesture() {
        imagePreviewScaleGestureDetector =
                ScaleGestureDetector(baseContext, object : ScaleGestureDetector.OnScaleGestureListener {
                    override fun onScaleBegin(detector: ScaleGestureDetector?): Boolean {
                        return true
                    }

                    override fun onScaleEnd(detector: ScaleGestureDetector?) {}


                    override fun onScale(detector: ScaleGestureDetector?): Boolean {
                        detector?.let { scaleGestureDetector ->
                            imagePreviewScaleFactor *= scaleGestureDetector.scaleFactor
                        }

                        imageViewPreview.scaleX = imagePreviewScaleFactor
                        imageViewPreview.scaleY = imagePreviewScaleFactor
                        return true
                    }

                })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    //Touch event to detect pinch
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return imagePreviewScaleGestureDetector.onTouchEvent(event)
    }

    companion object {

        private const val INTENT_EXTRA_IMAGE_PREVIEW_URL = "INTENT_EXTRA_IMAGE_PREVIEW_URL"

        fun provideIntent(context: Context, imageUrl: String): Intent {
            val intent = Intent(context, ImagePreviewActivity::class.java)
            intent.putExtra(INTENT_EXTRA_IMAGE_PREVIEW_URL, imageUrl)
            return intent
        }
    }
}
