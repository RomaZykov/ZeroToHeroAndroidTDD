package ru.easycode.zerotoheroandroidtdd.presentation.delete

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.BackEventCompat
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import ru.easycode.zerotoheroandroidtdd.core.ProvideViewModel
import ru.easycode.zerotoheroandroidtdd.databinding.FragmentDeleteBinding

class DeleteFragment : Fragment() {

    private lateinit var binding: FragmentDeleteBinding
    private lateinit var viewModel: DeleteViewModel

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

        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackCancelled() {
                super.handleOnBackCancelled()
            }

            override fun handleOnBackPressed() {
                requireActivity().supportFragmentManager.beginTransaction()
                    .remove(this@DeleteFragment)
                    .commit()
                viewModel.comeback()
            }

            override fun handleOnBackProgressed(backEvent: BackEventCompat) {
                super.handleOnBackProgressed(backEvent)
            }

            override fun handleOnBackStarted(backEvent: BackEventCompat) {
                super.handleOnBackStarted(backEvent)
            }
        })

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
