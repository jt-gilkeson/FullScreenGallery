package com.jt.gallery

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_gallery.*
import java.util.*

class GalleryActivity : AppCompatActivity(), FullScreenView {

    companion object {
        private const val PAGER_OFFSCREEN_LIMIT = 10

        private const val IMAGE_LIST = "imageList"
        private const val POSITION = "position"

        @JvmStatic @JvmOverloads
        fun newIntent(context: Context, imageList: List<String>, currentImage: Int = 0) =
            Intent(context, GalleryActivity::class.java).apply {
                putExtra(IMAGE_LIST, ArrayList(imageList))
                putExtra(POSITION, currentImage)
            }
    }

    private lateinit var imageList: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.FullScreenImmersive)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        val currentImage: Int

        if (savedInstanceState != null) {
            imageList = savedInstanceState.getStringArrayList(IMAGE_LIST)
            currentImage = savedInstanceState.getInt(POSITION)
        } else {
            imageList = intent.getStringArrayListExtra(IMAGE_LIST)
            currentImage = intent.getIntExtra(POSITION, 0)
        }

        val galleryAdapter = GalleryAdapter(this, imageList)

        galleryViewPager.apply {
            offscreenPageLimit = PAGER_OFFSCREEN_LIMIT
            adapter = galleryAdapter
            currentItem = currentImage
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.apply {
            putStringArrayList(IMAGE_LIST, imageList)
            putInt(POSITION, galleryViewPager.currentItem)
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        // Ensure that we come out of immersive view if the activity no longer has focus
        if (!hasFocus) {
            showSystemUI()
        }
    }

    override fun showSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }

    override fun hideSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE)
    }

    override fun isNavigationVisible(): Boolean =
        window.decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_HIDE_NAVIGATION == 0
}
