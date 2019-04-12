package com.abnerescocio.edittextmask

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.abnerescocio.lib.MASK

class MaskAdapter(
        private val masks: Array<MASK>,
        private val onListInteraction: OnListInteraction
) : RecyclerView.Adapter<MaskAdapter.MaskVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MaskVH {
        return MaskVH(LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false))
    }

    override fun getItemCount() = masks.size

    override fun onBindViewHolder(holder: MaskVH, position: Int) {
        val mask = masks[position]
        holder.bind(mask)
        holder.itemView.setOnClickListener { onListInteraction.onClick(mask) }
    }


    class MaskVH(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        fun bind(mask: MASK) {
            itemView.findViewById<TextView>(android.R.id.text1).text = mask.name
        }

    }

    interface OnListInteraction {
        fun onClick(mask: MASK)
    }
}