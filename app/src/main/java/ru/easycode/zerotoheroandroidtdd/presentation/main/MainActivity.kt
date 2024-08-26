package ru.easycode.zerotoheroandroidtdd.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.easycode.zerotoheroandroidtdd.R
import ru.easycode.zerotoheroandroidtdd.core.ProvideViewModel
import ru.easycode.zerotoheroandroidtdd.databinding.ActivityMainBinding
import ru.easycode.zerotoheroandroidtdd.presentation.add.AddFragment
import ru.easycode.zerotoheroandroidtdd.presentation.delete.DeleteFragment

class MainActivity : AppCompatActivity(), ProvideViewModel {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var rvAdapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = viewModel(MainViewModel::class.java)
        viewModel.init()

        setupRecycler()

        binding.addButton.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.rootLayout, AddFragment())
                .commit()
        }

        viewModel.liveData().observe(this) {
            rvAdapter.update(it)
        }

        rvAdapter.onItemClicked = { itemId ->
            val bundle = Bundle().apply {
                this.putLong("item_id", itemId)
            }
            supportFragmentManager.beginTransaction()
                .replace(R.id.rootLayout, DeleteFragment::class.java, bundle)
                .commit()
        }
    }

    private fun setupRecycler() {
        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvAdapter = RecyclerAdapter()
        recyclerView.adapter = rvAdapter
    }

    override fun <T : ViewModel> viewModel(viewModelClass: Class<T>): T {
        return (application as ProvideViewModel).viewModel(viewModelClass)
    }
}