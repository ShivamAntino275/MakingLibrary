package com.indigo.custom.views.fragments.contestleader

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.indigo.custom.databinding.FragmentContestLeadersBinding
import com.indigo.custom.views.activity.MainDecorActivity

class ContestLeadersFragment : Fragment() {

    val binding by lazy { FragmentContestLeadersBinding.inflate(layoutInflater).apply {
        model = viewModel
    } }
    val viewModel:ContestLeaderVM by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if ((requireActivity()).intent!=null && (requireActivity() as MainDecorActivity).intent.hasExtra("contestId")){
          val id = (requireActivity()).intent.extras?.getString("contestId")
            viewModel.contestId = id?:""
            viewModel.contestLeaderApi()
            viewModel.rankModel(id?:"")
        }

        binding.ivArrow.setOnClickListener {
            (requireActivity() as MainDecorActivity).finish()
        }
    }

}