package com.kursvalut


import android.content.Context

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import android.view.ViewGroup

import android.view.LayoutInflater
import android.view.View


class RecyclerViewAdapter(
    context: Context?,
    private val currencyArray: MutableList<String>,
    private val myListener: MyListener
) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    private val inflater: LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.recyclerview_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameView.text = currencyArray[position]
        holder.nameView.setOnClickListener(View.OnClickListener {
            myListener.MyClick(currencyArray, position)
        })
    }

    override fun getItemCount(): Int {
        return currencyArray.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameView: TextView = view.findViewById(R.id.textViewCurrencyRow)
    }

    init {
        inflater = LayoutInflater.from(context)
    }

    interface MyListener {
        fun MyClick(userArray: MutableList<String>, position: Int)
    }
}