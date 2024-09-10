package ru.easycode.zerotoheroandroidtdd.folder.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import ru.easycode.zerotoheroandroidtdd.core.ProvideViewModel
import ru.easycode.zerotoheroandroidtdd.databinding.FragmentFolderListBinding

class FolderListFragment : Fragment(), ProvideViewModel {

    private lateinit var binding: FragmentFolderListBinding
    private lateinit var viewModel: FolderListViewModel
    private lateinit var adapter: FolderListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFolderListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = viewModel(FolderListViewModel::class.java)

        binding.foldersRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = FolderListAdapter()
        binding.foldersRecyclerView.adapter = adapter

        viewModel.init()

        binding.addButton.setOnClickListener {
            viewModel.addFolder()
        }

        viewModel.liveData().observe(viewLifecycleOwner) {
            adapter.update(it)
        }

        adapter.onFolderClicked = {
            viewModel.folderDetails(it)
        }
    }

    override fun <T : ViewModel> viewModel(clazz: Class<out ViewModel>): T {
        return (requireActivity().application as ProvideViewModel).viewModel(clazz)
    }
}