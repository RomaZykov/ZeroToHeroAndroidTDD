package ru.easycode.zerotoheroandroidtdd.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.easycode.zerotoheroandroidtdd.App
import ru.easycode.zerotoheroandroidtdd.R
import ru.easycode.zerotoheroandroidtdd.core.BundleWrapper
import ru.easycode.zerotoheroandroidtdd.core.ProvideViewModel
import ru.easycode.zerotoheroandroidtdd.create.CreateScreen
import ru.easycode.zerotoheroandroidtdd.databinding.ActivityMainBinding
import ru.easycode.zerotoheroandroidtdd.databinding.ListLayoutBinding
import ru.easycode.zerotoheroandroidtdd.main.RecyclerAdapter

class ListFragment : Fragment() {
    private lateinit var viewModel: ListViewModel

    private var _binding: ListLayoutBinding? = null
    private val binding get() = _binding!!

    private lateinit var rvAdapter: RecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = ListLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (requireActivity() as ProvideViewModel).viewModel(ListViewModel::class.java)

        setupRecycler()

        binding.addButton.setOnClickListener {
            viewModel.create()
        }

        viewModel.liveData().observe(viewLifecycleOwner) { textList ->
            textList.map {
                rvAdapter.add(it.toString())
            }
        }
    }

    private fun setupRecycler() {
        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvAdapter = RecyclerAdapter()
        recyclerView.adapter = rvAdapter
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.save(BundleWrapper.Base(outState))
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            viewModel.restore(BundleWrapper.Base(savedInstanceState))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}