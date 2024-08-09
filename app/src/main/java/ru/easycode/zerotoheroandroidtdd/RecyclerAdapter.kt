package ru.easycode.zerotoheroandroidtdd

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.easycode.zerotoheroandroidtdd.databinding.TextLayoutBinding

class RecyclerAdapter(private val textList: List<String>) :
    RecyclerView.Adapter<RecyclerAdapter.TextViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextViewHolder {
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.text_layout, parent, false)
        return TextViewHolder(textView)
    }

    override fun onBindViewHolder(holder: TextViewHolder, position: Int) {
        val note = textList[position]
        holder.bind(note, position)
    }

    override fun getItemCount(): Int {
        return textList.size
    }

    inner class TextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = TextLayoutBinding.bind(itemView)

        fun bind(text: String, position: Int) {
            binding.elementTextView.text = text
        }
    }
}
