package com.jt.gallery

import android.graphics.drawable.Drawable
import android.support.v4.view.PagerAdapter
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.view_image_gallery.view.*
import uk.co.senab.photoview.PhotoViewAttacher

class GalleryAdapter(
    private val view: FullScreenView,
    private val images: ArrayList<String>
) : PagerAdapter() {
    private val views: SparseArray<View> = SparseArray()

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val rootView: View =
            views[position] ?: LayoutInflater.from(container.context).inflate(R.layout.view_image_gallery, container, false)

        views.put(position, rootView)

        val image = images[position]

        Glide.with(rootView.context)
            .load(image)
            .listener(object : RequestListener<Drawable> {
                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    val photoView = PhotoViewAttacher(rootView.galleryImage)
                    photoView.onViewTapListener = tapListener
                    photoView.scaleType = ImageView.ScaleType.FIT_CENTER
                    return false
                }

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ) = false
            })
            .into(rootView.galleryImage)

        container.addView(rootView, container.width, container.height)
        return rootView
    }

    override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
        container.removeView(view as View)
        views.remove(position)
    }

    override fun isViewFromObject(view: View, obj: Any) = (view == obj)

    override fun getCount() = images.size

    private val tapListener = PhotoViewAttacher.OnViewTapListener { _, _, _ ->
        if (view.isNavigationVisible()) {
            view.hideSystemUI()
        } else {
            view.showSystemUI()
        }
    }
}