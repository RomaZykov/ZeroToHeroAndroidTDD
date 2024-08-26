package ru.easycode.zerotoheroandroidtdd.presentation.delete

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import ru.easycode.zerotoheroandroidtdd.core.ProvideViewModel
import ru.easycode.zerotoheroandroidtdd.databinding.FragmentDeleteBinding

class DeleteFragment : Fragment() {

    private lateinit var binding: FragmentDeleteBinding
    private lateinit var viewModel: DeleteViewModel
    private lateinit var onBackPressedCallback: OnBackPressedCallback

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDeleteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (requireActivity() as ProvideViewModel).viewModel(DeleteViewModel::class.java)
        val args = requireArguments().getLong("item_id")

        onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().supportFragmentManager.beginTransaction()
                    .remove(this@DeleteFragment)
                    .commit()
                viewModel.comeback()
            }
        }

        viewModel.init(args)

        viewModel.liveData.observe(viewLifecycleOwner) {
            binding.itemTitleTextView.text = it
        }

        binding.deleteButton.setOnClickListener {
            viewModel.delete(args)
            requireActivity().supportFragmentManager.beginTransaction()
                .remove(this)
                .commit()
        }
    }
}
