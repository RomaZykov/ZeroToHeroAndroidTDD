package ru.easycode.zerotoheroandroidtdd.note.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import ru.easycode.zerotoheroandroidtdd.core.ProvideViewModel
import ru.easycode.zerotoheroandroidtdd.databinding.FragmentEditNoteBinding

class EditNoteFragment : Fragment(), ProvideViewModel {

    private lateinit var binding: FragmentEditNoteBinding
    private lateinit var viewModel: EditNoteViewModel
    private lateinit var onBackPressedCallback: OnBackPressedCallback

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val noteId = requireArguments().getLong("note_id")
        val noteTitle = requireArguments().getString("note_title")
        binding.noteEditText.setText(noteTitle)

        viewModel = viewModel(EditNoteViewModel::class.java)
        viewModel.init(noteId)

        onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.comeback()
            }
        }

        binding.saveNoteButton.setOnClickListener {
            viewModel.renameNote(noteId, binding.noteEditText.text.toString())
        }

        binding.deleteNoteButton.setOnClickListener {
            viewModel.deleteNote(noteId)
        }
    }

    override fun <T : ViewModel> viewModel(clazz: Class<out ViewModel>): T {
        return (requireActivity().application as ProvideViewModel).viewModel(clazz)
    }
}