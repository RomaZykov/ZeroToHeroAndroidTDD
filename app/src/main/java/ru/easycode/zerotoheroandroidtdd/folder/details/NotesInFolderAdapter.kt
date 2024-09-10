package ru.easycode.zerotoheroandroidtdd.folder.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.easycode.zerotoheroandroidtdd.R
import ru.easycode.zerotoheroandroidtdd.databinding.NoteLayoutBinding
import ru.easycode.zerotoheroandroidtdd.note.NoteUi

class NotesInFolderAdapter : RecyclerView.Adapter<NotesInFolderAdapter.NoteViewHolder>() {

    private val notesList = mutableListOf<NoteUi>()
    var onNoteClicked: ((NoteUi) -> Unit) = {}

    fun update(list: List<NoteUi>) {
        val diffResult = DiffUtil.calculateDiff(MyDiffUtilCallback(notesList, list))
        notesList.clear()
        notesList.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_layout, parent, false)
        return NoteViewHolder(view)
    }


    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val item = notesList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = NoteLayoutBinding.bind(itemView)

        fun bind(item: NoteUi) {
            binding.noteTitleTextView.text = item.title

            binding.root.setOnClickListener {
                onNoteClicked.invoke(item)
            }
        }
    }

    inner class MyDiffUtilCallback(
        private val oldList: List<NoteUi>,
        private val newList: List<NoteUi>
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
    }
}
