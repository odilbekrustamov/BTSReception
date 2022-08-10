package com.exsample.btsreception.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.exsample.btsreception.databinding.ItemClientViewBinding
import com.exsample.btsreception.helper.OnItemClickEvent
import com.exsample.btsreception.model.ClientList

class ClientAdapter(private var onItemClickEvent: OnItemClickEvent,):
    ListAdapter<ClientList, RecyclerView.ViewHolder>(DiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = ItemClientViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder.ItemClientViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(itemCount - position - 1)
        when(holder){
            is ViewHolder.ItemClientViewHolder -> {
                holder.view.apply {
                    customerNumber.text = item.number
                    tvNumber.text = item.number

                    itemLayout.setOnClickListener {
                        onItemClickEvent.setOnOpenFragmentClickListener(item.number)
                    }

                    llCall.setOnClickListener {
                        onItemClickEvent.setOnCallClickListener(item.number)
                    }

                }
            }
        }
    }

    class DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<ClientList>() {
        override fun areItemsTheSame(oldItem: ClientList, newItem: ClientList): Boolean {
            return oldItem.number == newItem.number
        }

        override fun areContentsTheSame(oldItem: ClientList, newItem: ClientList): Boolean {
            return oldItem == newItem
        }
    }

    sealed class ViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
        class ItemClientViewHolder(val view: ItemClientViewBinding) : ViewHolder(view)
    }

}