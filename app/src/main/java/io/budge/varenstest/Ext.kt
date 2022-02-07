package io.budge.varenstest

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import coil.load


fun Context.drawable(@DrawableRes id: Int): Drawable =
    ResourcesCompat.getDrawable(resources, id, null)!!

fun Fragment.drawable(@DrawableRes id: Int) = requireActivity().drawable(id)

fun View.drawable(@DrawableRes id: Int) = context.drawable(id)

fun Context.color(@ColorRes id: Int): Int =
    ResourcesCompat.getColor(resources, id, null)

fun Fragment.color(@ColorRes id: Int) = requireActivity().color(id)

fun View.color(@ColorRes id: Int) = context.color(id)

inline fun <reified T> ViewGroup.inflate(@LayoutRes layoutRes: Int): T {
    return LayoutInflater.from(context).inflate(layoutRes, this, false) as T
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}