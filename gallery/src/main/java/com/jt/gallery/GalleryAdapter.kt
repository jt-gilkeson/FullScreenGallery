package com.jt.gallery

import android.graphics.drawable.Drawable
import android.support.v4.view.PagerAdapter
import android.support.v7.app.AlertDialog
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
    private val images: ArrayList<String>,
    private val allowDelete: Boolean = false
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

                    if (allowDelete) {
                        photoView.setOnLongClickListener {
                            AlertDialog.Builder(it.context)
                                .setMessage(R.string.delete_message)
                                .setPositiveButton(R.string.delete) { dialog, _ ->
                                    deleteItem(position)
                                    dialog.dismiss()
                                }
                                .setNegativeButton(android.R.string.cancel) { dialog, _ -> dialog.dismiss() }
                                .create()
                                .show()

                            true
                        }
                    }

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

    private fun deleteItem(position: Int) {
        images.removeAt(position)
        views.remove(position)
        notifyDataSetChanged()

        view.resetAdapter(if (position > images.size - 1) images.size - 1 else position)
    }

    private val tapListener = PhotoViewAttacher.OnViewTapListener { _, _, _ ->
        if (view.isNavigationVisible()) {
            view.hideSystemUI()
        } else {
            view.showSystemUI()
        }
    }
}