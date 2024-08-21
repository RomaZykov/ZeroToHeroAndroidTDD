package ru.easycode.zerotoheroandroidtdd.presentation.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.easycode.zerotoheroandroidtdd.R
import ru.easycode.zerotoheroandroidtdd.databinding.TextLayoutBinding

class RecyclerAdapter() :
    RecyclerView.Adapter<RecyclerAdapter.TextViewHolder>() {

    private val textList: MutableList<String> = mutableListOf()

    fun update(list: List<String>) {
        val diffResult = DiffUtil.calculateDiff(MyDiffUtilCallback(textList, list))
        textList.clear()
        textList.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextViewHolder {
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.text_layout, parent, false)
        return TextViewHolder(textView)
    }

    override fun onBindViewHolder(holder: TextViewHolder, position: Int) {
        val note = textList[position]
        holder.bind(note)
    }

    override fun getItemCount(): Int {
        return textList.size
    }

    inner class TextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = TextLayoutBinding.bind(itemView)

        fun bind(text: CharSequence) {
            binding.elementTextView.text = text
        }
    }

    inner class MyDiffUtilCallback(
        private val oldList: List<String>,
        private val newList: List<String>
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