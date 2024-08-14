package ru.easycode.zerotoheroandroidtdd.main

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.easycode.zerotoheroandroidtdd.R
import ru.easycode.zerotoheroandroidtdd.databinding.TextLayoutBinding

class RecyclerAdapter() :
    RecyclerView.Adapter<RecyclerAdapter.TextViewHolder>() {

    private val textList: MutableList<CharSequence> = mutableListOf()

    fun add(text: CharSequence) {
        textList.add(text)
        notifyItemInserted(textList.size - 1)
    }

    fun restore(list: List<CharSequence>) {
        textList.addAll(list)
        notifyItemRangeInserted(0, textList.size - 1)
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
}