package com.jt.gallery

interface FullScreenView {
    fun showSystemUI()
    fun hideSystemUI()
    fun isNavigationVisible(): Boolean
}