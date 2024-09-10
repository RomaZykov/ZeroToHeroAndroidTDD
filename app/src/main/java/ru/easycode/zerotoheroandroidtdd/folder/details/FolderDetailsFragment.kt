package ru.easycode.zerotoheroandroidtdd.folder.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import ru.easycode.zerotoheroandroidtdd.core.ProvideViewModel
import ru.easycode.zerotoheroandroidtdd.databinding.FragmentFolderDetailsBinding

class FolderDetailsFragment : Fragment(), ProvideViewModel {

    private var _binding: FragmentFolderDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: FolderDetailsViewModel
    private lateinit var adapter: NotesInFolderAdapter
    private lateinit var backPressedCallback: OnBackPressedCallback
    private var folderId = 0L

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFolderDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = viewModel(FolderDetailsViewModel::class.java)

        binding.notesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = NotesInFolderAdapter()
        binding.notesRecyclerView.adapter = adapter

        backPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.comeback()
            }
        }

        folderId = requireArguments().getLong("folder_id")
        if (folderId != 0L) {
            viewModel.init(folderId)
        }
        binding.folderNameTextView.text = viewModel.fetchFolderTitle()

        viewModel.noteLiveData().observe(viewLifecycleOwner) {
            adapter.update(it)
            binding.notesCountTextView.text = adapter.itemCount.toString()
        }

        binding.addNoteButton.setOnClickListener {
            viewModel.createNote(folderId)
        }

        binding.editFolderButton.setOnClickListener {
            viewModel.editFolder(folderId, binding.folderNameTextView.text.toString())
        }

        adapter.onNoteClicked = {
            viewModel.editNote(folderId, it.id, it.title)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun <T : ViewModel> viewModel(clazz: Class<out ViewModel>): T {
        return (requireActivity().application as ProvideViewModel).viewModel(clazz)
    }
}