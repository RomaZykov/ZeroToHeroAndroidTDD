package ru.easycode.zerotoheroandroidtdd.folder.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import ru.easycode.zerotoheroandroidtdd.core.ProvideViewModel
import ru.easycode.zerotoheroandroidtdd.databinding.FragmentEditFolderBinding

class EditFolderFragment : Fragment(), ProvideViewModel {

    private lateinit var binding: FragmentEditFolderBinding
    private lateinit var viewModel: EditFolderViewModel
    private lateinit var backPressedCallback: OnBackPressedCallback

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditFolderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = viewModel(EditFolderViewModel::class.java)

        val folderId = requireArguments().getLong("folder_id")
        val folderTitle = requireArguments().getString("folder_title")

        binding.folderEditText.setText(folderTitle.toString())
        binding.folderEditText.setSelection(folderTitle?.length ?: 0)

        backPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.comeback()
            }
        }

        binding.saveFolderButton.setOnClickListener {
            viewModel.renameFolder(folderId, binding.folderEditText.text.toString())
        }

        binding.deleteFolderButton.setOnClickListener {
            viewModel.deleteFolder(folderId)
        }
    }

    override fun <T : ViewModel> viewModel(clazz: Class<out ViewModel>): T {
        return (requireActivity().application as ProvideViewModel).viewModel(clazz)
    }
}