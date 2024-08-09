package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.easycode.zerotoheroandroidtdd.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var rvAdapter: RecyclerAdapter

    private val textList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecycler(textList)

        binding.actionButton.setOnClickListener {
            val text = binding.inputEditText.text.toString()
            textList.add(text)
            binding.recyclerView.adapter?.notifyItemInserted(rvAdapter.itemCount - 1)
            binding.inputEditText.text?.clear()
        }
    }

    private fun setupRecycler(textList: List<String>) {
        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvAdapter = RecyclerAdapter(textList)
        recyclerView.adapter = rvAdapter
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArray("contentList", textList.toTypedArray())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val contentTextList = savedInstanceState.getStringArray("contentList")
        if (contentTextList != null) {
            textList.addAll(contentTextList)
        }
    }
}