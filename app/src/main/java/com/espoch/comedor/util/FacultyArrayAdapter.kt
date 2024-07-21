package com.espoch.comedor.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.espoch.comedor.R
import com.espoch.comedor.models.Faculty

class FacultyArrayAdapter(context: Context, items: List<Faculty>) : ArrayAdapter<Faculty>(context, 0, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.faculty_spinner_item, parent, false)
        val item = getItem(position)
        val textView = view.findViewById<TextView>(R.id.spinner_item_text)
        textView.text = item?.name
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.faculty_spinner_dropdown, parent, false)
        val item = getItem(position)
        val textView = view.findViewById<TextView>(R.id.spinner_dropdown_item_text)
        textView.text = item?.name
        return view
    }
}