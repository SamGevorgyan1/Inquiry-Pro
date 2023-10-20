package com.inquirypro.util.extensions

import android.view.View

object ViewExtension {
    fun hideViews(vararg views: View) {
        views.forEach { it.visibility = View.GONE }
    }
    fun showViews(vararg views: View) {
        views.forEach { it.visibility = View.VISIBLE }
    }
}

