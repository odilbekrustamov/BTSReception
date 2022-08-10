package com.exsample.btsreception.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.exsample.btsreception.databinding.ItemClientCallViewBinding
import com.exsample.btsreception.databinding.ItemClientViewBinding
import com.exsample.readnumbercall.model.CallNumber

class CallsAdapter: ListAdapter<CallNumber, RecyclerView.ViewHolder>(DiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = ItemClientCallViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder.ItemClientCallViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem( itemCount - position - 1)
        when(holder){
            is ViewHolder.ItemClientCallViewHolder -> {
                holder.view.apply {
                    tvNumber.text = item.number
                    tvDate.text = item.callTime
                    tvTimes.text = item.timeSpent
                    tvDirection.text = item.callType
                }
            }
        }
    }

    class DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<CallNumber>() {
        override fun areItemsTheSame(oldItem: CallNumber, newItem: CallNumber): Boolean {
            return oldItem.number == newItem.number
        }

        override fun areContentsTheSame(oldItem: CallNumber, newItem: CallNumber): Boolean {
            return oldItem == newItem
        }
    }

    sealed class ViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
        class ItemClientCallViewHolder(val view: ItemClientCallViewBinding) : ViewHolder(view)
    }

}