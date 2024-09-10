package ru.easycode.zerotoheroandroidtdd.note.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import ru.easycode.zerotoheroandroidtdd.core.ProvideViewModel
import ru.easycode.zerotoheroandroidtdd.databinding.FragmentCreateNoteBinding

class CreateNoteFragment : Fragment(), ProvideViewModel {

    private lateinit var binding: FragmentCreateNoteBinding
    private lateinit var viewModel: CreateNoteViewModel
    private lateinit var onBackPressedCallback: OnBackPressedCallback

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.comeback()
            }
        }

        viewModel = viewModel(CreateNoteViewModel::class.java)

        val folderId = requireArguments().getLong("folder_id")

        binding.saveNoteButton.setOnClickListener {
            viewModel.createNote(folderId, binding.createNoteEditText.text.toString())
        }
    }

    override fun <T : ViewModel> viewModel(clazz: Class<out ViewModel>): T {
        return (requireActivity().application as ProvideViewModel).viewModel(clazz)
    }
}