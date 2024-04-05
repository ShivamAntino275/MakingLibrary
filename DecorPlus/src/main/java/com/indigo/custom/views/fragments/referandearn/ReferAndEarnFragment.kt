package com.indigo.custom.views.fragments.referandearn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnScrollChangedListener
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.indigo.custom.databinding.FragmentReferalAndEarnBinding
import com.indigo.custom.views.activity.MainDecorActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference


class ReferAndEarnFragment : Fragment() {

    private val binding by lazy {
        FragmentReferalAndEarnBinding.inflate(layoutInflater).apply {
            model = viewModel
        }
    }

    private val viewModel: ReferalAndEarnVM by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.context = WeakReference(requireActivity())
        viewModel.getReferralsApi()


        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.clearFocus.collectLatest {
                if (it) {
                    binding.etMobileNumber.clearFocus()
                }
            }
        }

        // Inflate the layout for this fragment
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            onBackPressedCallback
        )



        return binding.root
    }


    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            requireActivity().finish()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivBackIcon.setOnClickListener {
            (requireActivity() as MainDecorActivity).finish()
        }

        binding.scrollView.viewTreeObserver.addOnScrollChangedListener(OnScrollChangedListener {
                val view = binding.scrollView.getChildAt(binding.scrollView.childCount - 1) as View
                val diff: Int = view.bottom - (binding.scrollView.height + binding.scrollView.scrollY)
                if (diff == 0 && viewModel.isScroll) {
                    // your pagination code
                    viewModel.currentPage+=1
                    viewModel.isScroll=false
                   viewModel.getReferralsApi(true)
                }
            });
    }
}