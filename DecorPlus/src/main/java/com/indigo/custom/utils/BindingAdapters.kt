package com.indigo.custom.utils

import android.text.Spanned
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.textview.MaterialTextView
import com.indigo.custom.R
import com.squareup.picasso.Picasso


/** Binding Adapters */
object BindingAdapters {


    @BindingAdapter(value = ["setRecyclerAdapter"], requireAll = false)
    @JvmStatic
    fun setRecyclerAdapter(
        recyclerView: RecyclerView?,
        adapter: RecyclerView.Adapter<*>?
    ) {
        recyclerView?.adapter = adapter
    }

    @BindingAdapter(value = ["setImageUrl"], requireAll = false)
    @JvmStatic
    fun setImageUrl(
        imageView: ImageView,
        url: String?
    ) {
        try {
            Picasso.get().load(url).placeholder(R.drawable.ic_bike).into(imageView)
        }catch (e:Exception){
            e.printStackTrace()
        }

    }
    @BindingAdapter(value = ["setDrawable"], requireAll = false)
    @JvmStatic
    fun setDrawable(
        imageView: ImageView,
        drawable: Int
    ) {
        imageView.setImageResource(drawable)
    }

    @BindingAdapter(value = ["setBackground"], requireAll = false)
    @JvmStatic
    fun setBackground(
        view: View,
        drawable: Int
    ) {
        view.background = ContextCompat.getDrawable(view.context, drawable)
    }

    @BindingAdapter(value = ["setFontFamily","setFont"], requireAll = false)
    @JvmStatic
    fun setFontFamily(view: MaterialTextView, isAchieve:Boolean, isCurrent:Boolean){
        if (isAchieve){
            val typeface = ResourcesCompat.getFont(view.context, R.font.helveticaneuemedium)
            view.setTypeface(typeface)
        }else if (isCurrent){
            val typeface = ResourcesCompat.getFont(view.context, R.font.helveticaneuemedium)
            view.setTypeface(typeface)
        }else{
            val typeface = ResourcesCompat.getFont(view.context, R.font.helveticaneueregular)
            view.setTypeface(typeface)
        }

    }

    @BindingAdapter(value = ["setFontFamilyPrize","setFontPrize"], requireAll = false)
    @JvmStatic
    fun setFontFamilyPrize(view: MaterialTextView, isAchieve:Boolean, isCurrent:Boolean){
        if (isAchieve){
            val typeface = ResourcesCompat.getFont(view.context, R.font.helveticaneueregular)
            view.setTypeface(typeface)
        }else if (isCurrent){
            val typeface = ResourcesCompat.getFont(view.context, R.font.helveticaneueregular)
            view.setTypeface(typeface)
        }else{
            val typeface = ResourcesCompat.getFont(view.context, R.font.helveticaneuelight)
            view.setTypeface(typeface)
        }

    }
//    @BindingAdapter(value = ["setFontFamily"], requireAll = false)
//    @JvmStatic
//    fun setFontFamily(view: TextView, isSelected:Boolean){
//        if (isSelected){
//            val typeface = ResourcesCompat.getFont(view.context, R.font.helveticaneueregular)
//            view.setTypeface(typeface)
//        }else{
//            val typeface = ResourcesCompat.getFont(view.context, R.font.helveticaneuemedium)
//            view.setTypeface(typeface)
//        }
//
//    }

    @BindingAdapter(value = ["setTextViewHtml"], requireAll = false)
    @JvmStatic
    fun setTextHtml(view: TextView, str:String?){
        try {
            val spannedHtml: Spanned = HtmlCompat.fromHtml(str?.trim()?:"", HtmlCompat.FROM_HTML_MODE_LEGACY)

            // Set the formatted text in the TextView
            view.text = spannedHtml
        }catch (ex:Exception){
            ex.printStackTrace()
        }

    }
    @BindingAdapter(value = ["setTextIsSelected"], requireAll = false)
    @JvmStatic
    fun setTextIsSelected(view: MaterialTextView, isSelected: Boolean) {
        view.isSelected =true
    }

    @BindingAdapter
        (
        value = [
            "swipeRefreshListener"
        ], requireAll = false
    )
    @JvmStatic
    fun setSwipeRefreshListener(
        recyclerView: SwipeRefreshLayout?,
        listener: SwipeRefreshLayout.OnRefreshListener?
    ) {
        recyclerView?.setOnRefreshListener(listener)
    }


}