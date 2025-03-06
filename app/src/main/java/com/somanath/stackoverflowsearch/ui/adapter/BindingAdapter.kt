package com.somanath.stackoverflowsearch.ui.adapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.somanath.stackoverflowsearch.data.model.Item
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@BindingAdapter("android:date")
fun setDateItem(textView:TextView,item:Item) {
    val date =   SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date(item.creation_date * 1000L))
    textView.text = date.toString()
}