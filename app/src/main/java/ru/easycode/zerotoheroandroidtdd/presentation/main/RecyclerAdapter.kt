package ru.easycode.zerotoheroandroidtdd.presentation.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.easycode.zerotoheroandroidtdd.R
import ru.easycode.zerotoheroandroidtdd.databinding.TextLayoutBinding
import ru.easycode.zerotoheroandroidtdd.presentation.ItemUi

class RecyclerAdapter() :
    RecyclerView.Adapter<RecyclerAdapter.TextViewHolder>() {

    private val itemsList: MutableList<ItemUi> = mutableListOf()
    var onItemClicked: (Long) -> Unit = {}

    fun update(list: List<ItemUi>) {
        val diffResult = DiffUtil.calculateDiff(MyDiffUtilCallback(itemsList, list))
        itemsList.clear()
        itemsList.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextViewHolder {
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.text_layout, parent, false)
        return TextViewHolder(textView)
    }

    override fun onBindViewHolder(holder: TextViewHolder, position: Int) {
        val item = itemsList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    inner class TextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = TextLayoutBinding.bind(itemView)

        fun bind(item: ItemUi) {
            binding.elementTextView.text = item.text

            binding.root.setOnClickListener {
                onItemClicked.invoke(item.id)
            }
        }
    }

    inner class MyDiffUtilCallback(
        private val oldList: List<ItemUi>,
        private val newList: List<ItemUi>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] === newList[newItemPosition]
        }

        override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
            return super.getChangePayload(oldItemPosition, newItemPosition)
        }
    }
}