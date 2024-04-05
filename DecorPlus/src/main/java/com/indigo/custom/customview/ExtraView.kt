package com.indigo.custom.customview

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.ObservableField
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textview.MaterialTextView
import com.indigo.custom.R
import com.indigo.custom.models.ContestResponseModel
import com.indigo.custom.networks.NetworkProcess
import com.indigo.custom.networks.Repository
import com.indigo.custom.networks.RetrofitClient
import com.indigo.custom.recycleradapter.RecyclerAdapter
import com.indigo.custom.utils.formatDate
import com.indigo.custom.utils.toCamelCase
import com.indigo.custom.views.activity.MainDecorActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class ExtraView : ConstraintLayout {

    private lateinit var rvContest: RecyclerView
    private lateinit var rvContestSlab: RecyclerView
    private lateinit var tvTillDateValue: MaterialTextView
    private lateinit var tvDecs: MaterialTextView
    private lateinit var tvArcivedPointsEarnedvalue: MaterialTextView
    private lateinit var tvPointEarnedTextPremium: MaterialTextView
    private lateinit var tvRank: MaterialTextView
    private lateinit var tvArcivedPointsEarnedPercent: MaterialTextView
    private lateinit var tvTapToSeeAllPremium: MaterialTextView
    private lateinit var tvFloorCoatDateRange: MaterialTextView
    private lateinit var tvTopPrize: MaterialTextView
    private lateinit var clSchemeSlabDetails: ConstraintLayout
    private lateinit var clFloorCoat: ConstraintLayout
    private lateinit var clNoScheme: ConstraintLayout
    private lateinit var tvMonthTillDateValue: MaterialTextView
    private lateinit var tvFloorCoat: MaterialTextView
    private lateinit var clTap: ConstraintLayout
    private lateinit var tvContestClose: MaterialTextView
    var builder: AlertDialog.Builder? = null
    private var selectedContestId = ""
    private val repository = Repository()
    private val view = LayoutInflater.from(context).inflate(R.layout.custom_constraint_layout, this, true)
    private val contestAdapter = RecyclerAdapter<ContestResponseModel.Data.ContestData>(R.layout.item_layout_contest, true)
    private val contestSlabAdapter =
        RecyclerAdapter<ContestResponseModel.Data.ContestData.ContestDetails.ContestSlabDetail>(
            R.layout.item_home_scheme_layout,
            true
        )
    private val closeContestAdapter = RecyclerAdapter<ContestResponseModel.Data.ContestData.ContestDetails.PrizeDetail>(
            R.layout.item_close_scheme_layout,
            true
        )

    private val adapterClick = RecyclerAdapter.OnItemClick { view, position, type ->
        when (view.id) {
            R.id.cvContest -> {

                contestAdapter.items.forEachIndexed { index, contestData ->
                    contestData.isSelected = index==position
                }
                contestAdapter.notifyDataSetChanged()
                if (contestAdapter.items[position].contestDetails?.prizeDetails?.isNotEmpty() == true) {

                    /**
                     * Prize contest
                     */
                    tvPointEarnedTextPremium.text ="Prize Earned"
                    tvFloorCoat.text = contestAdapter.items[position].contestDetails?.contestName?.toCamelCase()?:"-"
                    if (contestAdapter.items[position].contestDetails?.contestStatus == "ongoing"){
                        tvContestClose.visibility = View.GONE
                    }else{
                        tvContestClose.visibility = View.VISIBLE

                    }
                    selectedContestId = contestAdapter.items[position].contestDetails?.id?:""
                    rvContestSlab.adapter = closeContestAdapter
                    clSchemeSlabDetails.visibility = View.GONE
                    tvTopPrize.visibility = View.VISIBLE
                    tvRank.text = "${contestAdapter.items[position]?.rank?:"-"}"
                    tvArcivedPointsEarnedvalue.text = "${contestAdapter.items[position].achievedTarget?:"-"} Pts"
                    if (contestAdapter.items[position].achievedTarget!=null){
                        tvArcivedPointsEarnedvalue.text = " ${contestAdapter.items[position].achievedTarget?:"-"} Pts"
                    }else{
                        tvArcivedPointsEarnedvalue.text = "-"
                    }
                        tvArcivedPointsEarnedPercent.text = " ${contestAdapter.items[position].currentPrizeDetails?.prizeName?.toCamelCase()?:"-"}"

                    closeContestAdapter.addItems(
                        contestAdapter.items[position].contestDetails?.prizeDetails ?: emptyList()
                    )
                    tvFloorCoatDateRange.text = formatDate(contestAdapter.items[position].contestDetails?.startDate?:"",contestAdapter.items[position].contestDetails?.endDate?:"")

                } else {

                    /**
                     * Normal contest
                     */
                    rvContestSlab.adapter = contestSlabAdapter
                    clSchemeSlabDetails.visibility = View.VISIBLE
                    tvPointEarnedTextPremium.text ="Bonus Earned"
                    tvTopPrize.visibility = View.GONE
                    tvFloorCoat.text = contestAdapter.items[position].contestDetails?.contestName?.toCamelCase()?:"-"
                    if (contestAdapter.items[position].contestDetails?.contestStatus == "ongoing"){
                        tvContestClose.visibility = View.GONE
                    }else{
                        tvContestClose.visibility = View.VISIBLE

                    }
                    selectedContestId = contestAdapter.items[position].contestDetails?.id?:""
                    tvRank.text = "${contestAdapter.items[position]?.rank?:"-"}"
                    tvArcivedPointsEarnedvalue.text = "${contestAdapter.items[position].achievedTarget?:"-"} Pts"
                    if (contestAdapter.items[position].achievedTarget!=null){
                        tvArcivedPointsEarnedvalue.text = " ${contestAdapter.items[position].achievedTarget?:"-"} Pts"
                    }else{
                        tvArcivedPointsEarnedvalue.text = "-"
                    }
                    contestAdapter.items.forEach {contestData ->
                        if(contestData.currentContestSlabDetails!=null){
                            contestData.contestDetails?.contestSlabDetails?.forEach {contest->
                                contest.isCurrent = contestData.currentContestSlabDetails?.slabNumber==(contest.slabNumber?:"0").toInt()
                                contest.isAchieved = (contestData.currentContestSlabDetails?.slabNumber?:0)>=(contest.slabNumber?:"0").toInt()
                            }
                        }
                    }
                    contestSlabAdapter.addItems(
                        contestAdapter.items[position].contestDetails?.contestSlabDetails
                            ?: emptyList()
                    )
//
                    contestSlabAdapter.notifyDataSetChanged()
                    tvFloorCoatDateRange.text = formatDate(contestAdapter.items[position].contestDetails?.startDate?:"",contestAdapter.items[position].contestDetails?.endDate?:"")

                }
            }
        }
    }

    init {
        contestAdapter.setOnItemClick(adapterClick)
    }

    // Constructors for creating the view programmatically
    constructor(context: Context) : super(context) {
        initialize(context, null)
    }

    // Constructors for inflating the view from XML
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initialize(context, attrs)
    }

    // Constructors for inflating the view from XML with a default style
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initialize(context, attrs)
    }

    private fun initialize(context: Context, attrs: AttributeSet?) {
        // Initialization logic here
        clSchemeSlabDetails = view.findViewById<ConstraintLayout>(R.id.clSchemeSlabDetails)
        clNoScheme = view.findViewById<ConstraintLayout>(R.id.clNoScheme)
        clFloorCoat = view.findViewById<ConstraintLayout>(R.id.clFloorCoat)
        rvContest = view.findViewById<RecyclerView>(R.id.rvContest)
        rvContestSlab = view.findViewById<RecyclerView>(R.id.rvContestSlab)
        tvTillDateValue = view.findViewById<MaterialTextView>(R.id.tvTillDateValue)
        tvDecs = view.findViewById<MaterialTextView>(R.id.tvDecs)
        tvTopPrize = view.findViewById<MaterialTextView>(R.id.tvTopPrize)
        tvPointEarnedTextPremium = view.findViewById<MaterialTextView>(R.id.tvPointEarnedTextPremium)
        tvFloorCoatDateRange = view.findViewById<MaterialTextView>(R.id.tvFloorCoatDateRange)
        tvRank = view.findViewById<MaterialTextView>(R.id.tvRankValue)
        tvTapToSeeAllPremium = view.findViewById<MaterialTextView>(R.id.tvTapToSeeAllPremium)
        tvMonthTillDateValue = view.findViewById<MaterialTextView>(R.id.tvMonthTillDateValue)
        tvArcivedPointsEarnedPercent =
            view.findViewById<MaterialTextView>(R.id.tvArcivedPointsEarnedPercent)
        tvArcivedPointsEarnedvalue =
            view.findViewById<MaterialTextView>(R.id.tvArcivedPointsEarnedvalue)
        tvFloorCoat = view.findViewById<MaterialTextView>(R.id.tvFloorCoat)
        clTap = view.findViewById<ConstraintLayout>(R.id.clTap)
        tvContestClose = view.findViewById<MaterialTextView>(R.id.tvContestClose)
        rvContest.adapter = contestAdapter
        fetchPointData()
        fetchContestData()

        clTap.setOnClickListener {
            it.context.startActivity(Intent(view.context,MainDecorActivity::class.java).putExtra(it.context.getString(R.string.screenType),it.context.getString(R.string.Contest)).putExtra("contestId",selectedContestId))
        }

        // Access attributes if needed
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.CustomConstraintLayout)
            // Access custom attributes using typedArray
            typedArray.recycle()
        }
    }

    fun fetchContestData() {
        CoroutineScope(Dispatchers.IO).launch {
            repository.makeApiCall {
                RetrofitClient.getApiService().contestDetails()
            }.collectLatest {

                when (it) {
                    is NetworkProcess.Loading -> {}
                    is NetworkProcess.Success -> {
                        CoroutineScope(Dispatchers.Main).launch {
                            isSwipe.set(false)
                            try {
                                if (it.data.data?.data?.isNotEmpty()==true){
                                    it.data.data?.data?.get(0)?.isSelected=true
                                    clNoScheme.visibility =View.GONE
                                    clFloorCoat.visibility =View.VISIBLE
                                }else{
                                    clNoScheme.visibility =View.VISIBLE
                                    clFloorCoat.visibility =View.GONE
                                }

                                contestAdapter.addItems(it.data.data?.data ?: emptyList())
                                if (it.data.data?.data?.isNotEmpty() == true) {
                                    if (it.data.data?.data?.get(0)?.contestDetails?.prizeDetails?.isNotEmpty() == true) {

                                        /**
                                         * Prize contest
                                         */
                                        selectedContestId = it.data.data?.data?.get(0)?.contestDetails?.id?:""
                                        rvContestSlab.adapter = closeContestAdapter
                                        clSchemeSlabDetails.visibility = View.GONE
                                        tvTopPrize.visibility = View.VISIBLE
                                        tvPointEarnedTextPremium.text =" Prize Earned"

                                        tvArcivedPointsEarnedvalue.text = "${it.data.data?.data?.get(0)?.achievedTarget?:"-"} Pts"
                                        if (it.data.data?.data?.get(0)?.achievedTarget!=null){
                                            tvArcivedPointsEarnedvalue.text = " ${it.data.data?.data?.get(0)?.achievedTarget?:"-"} Pts"
                                        }else{
                                            tvArcivedPointsEarnedvalue.text = "-"
                                        }
                                        tvArcivedPointsEarnedPercent.text = "${it.data.data?.data?.get(0)?.currentPrizeDetails?.prizeName?.toCamelCase()?:"-"}"
                                        if (it.data.data?.data?.get(0)?.contestDetails?.contestStatus == "ongoing"){
                                            tvContestClose.visibility = View.GONE
                                        }else{
                                            tvContestClose.visibility = View.VISIBLE

                                        }
                                        it.data.data?.data?.forEach {contestData ->
                                           if(contestData.currentPrizeDetails!=null){
                                               it.data.data?.data?.get(0)?.contestDetails?.prizeDetails?.forEach {prize->
                                                   prize.isCurrent = contestData.currentPrizeDetails?.prizeNumber==(prize.prize_number?:"0").toInt()
                                                   prize.isAchieved = (contestData.currentPrizeDetails?.prizeNumber?:0)>(prize.prize_number?:"0").toInt()
                                               }
                                           }
                                        }

                                        closeContestAdapter.addItems(
                                            it.data.data?.data?.get(0)?.contestDetails?.prizeDetails
                                                ?: emptyList()
                                        )
                                        tvFloorCoat.text = it.data.data?.data?.get(0)?.contestDetails?.contestName?.toCamelCase()?:"-"
                                        tvRank.text = "${it.data.data?.data?.get(0)?.rank?:"-"}"
                                        tvFloorCoatDateRange.text = formatDate(it.data.data?.data?.get(0)?.contestDetails?.startDate?:"",it.data.data?.data?.get(0)?.contestDetails?.endDate?:"")


                                    } else {

                                        /**
                                         * Normal contest
                                         */
                                        clSchemeSlabDetails.visibility = View.VISIBLE
                                        tvTopPrize.visibility = View.GONE
                                        tvFloorCoat.text = it.data.data?.data?.get(0)?.contestDetails?.contestName?.toCamelCase()?:"-"
                                        selectedContestId = it.data.data?.data?.get(0)?.contestDetails?.id?:""
                                        tvRank.text = "${it.data.data?.data?.get(0)?.rank?:"-"}"
                                        tvPointEarnedTextPremium.text =" Bonus Earned"
                                        rvContestSlab.adapter = contestSlabAdapter
                                        if (it.data.data?.data?.get(0)?.contestDetails?.contestStatus == "ongoing"){
                                            tvContestClose.visibility = View.GONE
                                        }else{
                                            tvContestClose.visibility = View.VISIBLE

                                        }
                                        tvArcivedPointsEarnedvalue.text = "${it.data.data?.data?.get(0)?.achievedTarget?:"-"} Pts"
                                        if (it.data.data?.data?.get(0)?.achievedTarget!=null){
                                            tvArcivedPointsEarnedvalue.text = " ${it.data.data?.data?.get(0)?.achievedTarget?:"-"} Pts"
                                        }else{
                                            tvArcivedPointsEarnedvalue.text = "-"
                                        }
                                        it.data.data?.data?.forEach {contestData ->
                                            if(contestData.currentContestSlabDetails!=null){
                                                it.data.data?.data?.get(0)?.contestDetails?.contestSlabDetails?.forEach {contest->
                                                    contest.isCurrent = contestData.currentContestSlabDetails?.slabNumber==(contest.slabNumber?:"0").toInt()
                                                    contest.isAchieved = (contestData.currentPrizeDetails?.prizeNumber?:0)>(contest.slabNumber?:"0").toInt()
                                                }
                                            }
                                        }
                                        contestSlabAdapter.addItems(
                                            it.data.data?.data?.get(0)?.contestDetails?.contestSlabDetails
                                                ?: emptyList()
                                        )
                                        contestSlabAdapter.notifyDataSetChanged()
                                        tvFloorCoatDateRange.text = formatDate(it.data.data?.data?.get(0)?.contestDetails?.startDate?:"",it.data.data?.data?.get(0)?.contestDetails?.endDate?:"")

                                    }
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


    fun fetchPointData() {
        CoroutineScope(Dispatchers.IO).launch {
            repository.makeApiCall {
                RetrofitClient.getApiService().pointEarned()
            }.collectLatest {
                when (it) {
                    is NetworkProcess.Loading -> {}
                    is NetworkProcess.Success -> {
                        isSwipe.set(false)
                        CoroutineScope(Dispatchers.Main).launch {
                            try {
                                tvMonthTillDateValue.text = "${it.data.data?.data?.totalPointsMonth!=null?:"-" }"
                                tvTillDateValue.text = "${it.data.data?.data?.totalPointsYear?:"-"}"
                                tvDecs.text = "Note: 1 Point = ${it.data.data?.data?.valuePerPoint ?:"-"} Indian Rupee"
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
    val isSwipe = ObservableField(false)

    val swipeListener = SwipeRefreshLayout.OnRefreshListener {
        isSwipe.set(true)
        fetchPointData()
    }

}


