package com.indigo.custom.views.fragments.contestleader

import android.widget.TextView
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.textview.MaterialTextView
import com.indigo.custom.R
import com.indigo.custom.models.ContestResponseModel
import com.indigo.custom.models.RankResponseModel
import com.indigo.custom.networks.NetworkProcess
import com.indigo.custom.networks.Repository
import com.indigo.custom.networks.RetrofitClient
import com.indigo.custom.recycleradapter.RecyclerAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ContestLeaderVM : ViewModel() {
    var repository = Repository()
    var contestId = ""
    var isSlab = ObservableField(false)
    var isOngoing = ObservableField(false)
    val contestAdapter by lazy {
        RecyclerAdapter<ContestResponseModel.Data.ContestData>(
            R.layout.item_layout_contest,
            true
        )
    }
    val slabAdapter by lazy {
        RecyclerAdapter<ContestResponseModel.Data.ContestData.ContestDetails.ContestSlabDetail>(
            R.layout.item_layout_slabs,
            true
        )
    }
    val prizeAdapter by lazy {
        RecyclerAdapter<ContestResponseModel.Data.ContestData.ContestDetails.PrizeDetail>(
            R.layout.item_layout_prize,
            true
        )
    }
    val rankAdapter by lazy {
        RecyclerAdapter<RankResponseModel.Data.RankModelData>(
            R.layout.item_layout_contest_leader_position,
            true
        )
    }


    var adapterClick = RecyclerAdapter.OnItemClick{ view, position, type ->
        when(view.id){
            R.id.cvContest->{
                contestAdapter.items.forEachIndexed { index, contestData ->
                    contestData.isSelected = index==position
                }
                contestAdapter.notifyDataSetChanged()
                contestDetails(contestAdapter.items[position])
            }
        }
    }

    var contestDetails = ObservableField<ContestResponseModel.Data.ContestData>()

    init {
        contestAdapter.setOnItemClick(adapterClick)
    }

     fun contestLeaderApi() {
        CoroutineScope(Dispatchers.IO).launch {
            repository.makeApiCall {
                RetrofitClient.getApiService().contestDetails()
            }.collectLatest {
                when (it) {
                    is NetworkProcess.Loading -> {}
                    is NetworkProcess.Success -> {
                        CoroutineScope(Dispatchers.Main).launch {
                            try {
                                if (it.data.data?.data?.isNotEmpty() == true) {
                                    it.data.data?.data?.forEach {contestData->
                                        if(contestData.contestDetails?.contestSlabDetails!=null){
                                            contestData.contestDetails?.contestSlabDetails?.forEach {slabDetails->
                                                slabDetails.isCurrent = contestData.currentContestSlabDetails?.slabNumber == (slabDetails.slabNumber?:"0").toInt()
                                                slabDetails.isAchieved = (contestData.currentContestSlabDetails?.slabNumber?:0) >=(slabDetails.slabNumber?:"0").toInt()
                                            }
                                        }
                                        if(contestData.contestDetails?.id==contestId){
                                            contestData.isSelected=true
                                            contestDetails(contestData)
                                        }


                                    }
                                    contestAdapter.addItems(it.data.data?.data ?: emptyList())
                                }
                            } catch (ex: Exception) {
                                ex.printStackTrace()
                            }
                        }
                    }
                    is NetworkProcess.Error -> {
                        isSwipe.set(false)
                    }
                }

            }
        }
    }

    fun contestDetails(contestData:ContestResponseModel.Data.ContestData){
        try {
            contestDetails.set(contestData)
            if (contestData.contestDetails?.contestSlabDetails?.isEmpty() == true) {
                isSlab.set(false)
            } else {
                isSlab.set(true)
            }
            if (contestData.contestDetails?.contestStatus == "ongoing"){
                isOngoing.set(true)
            }else{
                isOngoing.set(false)
            }
            slabAdapter.addItems(contestData.contestDetails?.contestSlabDetails ?: emptyList())
            rankModel(contestData.contestDetails?.id?: "")
            contestData.contestDetails?.prizeDetails?.forEachIndexed { index, prizeDetail ->
                prizeDetail.isEven = index % 2 == 0
            }
            prizeAdapter.addItems(contestData.contestDetails?.prizeDetails ?: emptyList())
        }catch (ex:Exception){

        }
    }



    fun rankModel(contestId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.makeApiCall {
                RetrofitClient.getApiService().contestRank(contestId = contestId)
            }.collectLatest {
                when (it) {
                    is NetworkProcess.Loading -> {}
                    is NetworkProcess.Success -> {
                        CoroutineScope(Dispatchers.Main).launch{
                            try {
                                rankAdapter.addItems(it.data.data?.data ?: emptyList())

                            } catch (ex: Exception) {
                                ex.printStackTrace()
                            }
                        }

                    }

                    is NetworkProcess.Error -> {}
                }
            }
        }
    }
    val isSwipe = ObservableField(false)

    val swipeListener = SwipeRefreshLayout.OnRefreshListener {
        isSwipe.set(true)
        contestLeaderApi()
    }

}