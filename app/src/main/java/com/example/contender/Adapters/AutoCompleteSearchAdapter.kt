package com.example.contender.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.contender.Model.SmartPhoneItem
import com.example.contender.R
import kotlinx.android.synthetic.main.item_auto_complete.view.*

class AutoCompleteSearchAdapter(ctx: Context, itens: List<SmartPhoneItem>): ArrayAdapter<SmartPhoneItem>(ctx, 0, itens){

    override fun getView(position: Int, recycledView: View?, parent: ViewGroup): View{
        return this.createView(position, recycledView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return this.createView(position, convertView, parent)
    }

    private fun createView(position: Int, recycledView: View?, parent: ViewGroup): View{
        val item = getItem(position)

        val view = recycledView?: LayoutInflater.from(context).inflate(
            R.layout.item_auto_complete,
            parent,
            false
        )

        view.item_auto_complete.text = item.title

        return view
    }
}