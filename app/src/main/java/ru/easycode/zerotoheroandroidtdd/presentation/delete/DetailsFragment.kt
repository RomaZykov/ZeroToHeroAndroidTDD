package ru.easycode.zerotoheroandroidtdd.presentation.delete

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import ru.easycode.zerotoheroandroidtdd.core.ProvideViewModel
import ru.easycode.zerotoheroandroidtdd.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private lateinit var viewModel: DetailsViewModel
    private lateinit var onBackPressedCallback: OnBackPressedCallback

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (requireActivity() as ProvideViewModel).viewModel(DetailsViewModel::class.java)
        val args = requireArguments().getLong("item_id")

        onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().supportFragmentManager.beginTransaction()
                    .remove(this@DetailsFragment)
                    .commit()
                viewModel.comeback()
            }
        }

        viewModel.init(args)

        viewModel.liveData.observe(viewLifecycleOwner) {
            binding.itemTextView.text = it
            binding.itemInputEditText.setText(it)
            binding.itemInputEditText.setSelection(it.length)
        }

        binding.updateButton.setOnClickListener {
            viewModel.update(args, binding.itemInputEditText.text.toString())
            requireActivity().supportFragmentManager.beginTransaction()
                .remove(this)
                .commit()
        }

        binding.deleteButton.setOnClickListener {
            viewModel.delete(args)
            requireActivity().supportFragmentManager.beginTransaction()
                .remove(this)
                .commit()
        }
    }
}