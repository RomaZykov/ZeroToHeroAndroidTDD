package ru.easycode.zerotoheroandroidtdd.folder.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.easycode.zerotoheroandroidtdd.R
import ru.easycode.zerotoheroandroidtdd.databinding.FolderLayoutBinding
import ru.easycode.zerotoheroandroidtdd.folder.FolderUi

class FolderListAdapter : RecyclerView.Adapter<FolderListAdapter.FolderViewHolder>() {

    private val folderList = mutableListOf<FolderUi>()
    var onFolderClicked: ((FolderUi) -> Unit) = {}

    fun update(list: List<FolderUi>) {
        val diffResult = DiffUtil.calculateDiff(MyDiffUtilCallback(folderList, list))
        folderList.clear()
        folderList.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderViewHolder {
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.folder_layout, parent, false)
        return FolderViewHolder(textView)
    }


    override fun onBindViewHolder(holder: FolderViewHolder, position: Int) {
        val item = folderList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return folderList.size
    }

    inner class FolderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = FolderLayoutBinding.bind(itemView)

        fun bind(item: FolderUi) {
            binding.folderTitleTextView.text = item.title
            binding.folderCountTextView.text = item.notesCount.toString()

            binding.root.setOnClickListener {
                onFolderClicked.invoke(item)
            }
        }
    }

    inner class MyDiffUtilCallback(
        private val oldList: List<FolderUi>,
        private val newList: List<FolderUi>
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
