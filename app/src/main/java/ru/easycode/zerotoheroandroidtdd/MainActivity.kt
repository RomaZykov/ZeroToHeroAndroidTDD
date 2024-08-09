package ru.easycode.zerotoheroandroidtdd

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.easycode.zerotoheroandroidtdd.databinding.ActivityMainBinding
import java.util.ArrayList
import java.util.Arrays

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var rvAdapter: RecyclerAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = (application as App).viewModel
        setupRecycler()

        binding.actionButton.setOnClickListener {
            val text = binding.inputEditText.text.toString()
            rvAdapter.add(text)
            viewModel.add(text)
            binding.inputEditText.text?.clear()
        }

        viewModel.liveData().observe(this) { list ->
            if (savedInstanceState != null) {
                rvAdapter.restore(list)
            }
        }
    }

    private fun setupRecycler() {
        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvAdapter = RecyclerAdapter()
        recyclerView.adapter = rvAdapter
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.save(BundleWrapper.Base(outState))
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        viewModel.restore(BundleWrapper.Base(savedInstanceState))
    }
}
