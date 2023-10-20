package com.inquirypro.util

import androidx.annotation.AnimRes
import androidx.navigation.NavOptions
import com.inquirypro.R

object NavigationUtils {

   private fun createNavOptions(
        @AnimRes enterAnimationResId: Int,
        @AnimRes exitAnimationResId: Int
    ): NavOptions {
        return NavOptions.Builder()
            .setEnterAnim(enterAnimationResId)
            .setExitAnim(exitAnimationResId)
            .build()
    }

    fun defaultScaleAnimation(): NavOptions {
        return createNavOptions(R.anim.scale_in, R.anim.scale_out)
    }

    fun backAnimation():NavOptions{
        return NavOptions.Builder()
            .setExitAnim(R.anim.slide_in)
            .build()
    }

    fun slideAnimLeft():NavOptions {
        return NavOptions.Builder()
            .setEnterAnim(R.anim.slide_left)
            .build()
    }

    fun slideAnimRight():NavOptions {
        return NavOptions.Builder()
            .setEnterAnim(R.anim.slide_right)
            .build()
    }
}